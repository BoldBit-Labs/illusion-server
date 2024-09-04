import React, { useState, useEffect } from 'react';
import authServiceInstance from '../../services/AuthService';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';

const Projects = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const data = await authServiceInstance.fetchProjects("email...");
        setProjects(data);
      } catch (err) {
        console.error('Error fetching projects:', err);
        setError('Failed to load projects');
      } finally {
        setLoading(false);
      }
    };

    fetchProjects();
  }, []);

  const handleCreateProject = () => {
    navigate("/create-project"); // Navigate to a project creation page or similar
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <p className="text-lg font-semibold">Loading...</p>
      </div>
    );
  }

  return (
    <div className="p-8">
      {error ? (
        <div className="text-center">
          <p className="text-red-600">Error: {error}</p>
        </div>
      ) : projects.length === 0 ? (
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">No Projects</h2>
          <p className="text-gray-600 mb-6">You don't have any projects yet.</p>
          <Button onClick={handleCreateProject} text="Create New Project" />
        </div>
      ) : (
        <div>
          <h2 className="text-3xl font-bold mb-6">Projects</h2>
          <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {projects.map((project) => (
              <li key={project.id} className="bg-white p-6 rounded-lg shadow-md">
                <h3 className="text-xl font-semibold mb-2">{project.name}</h3>
                <p className="text-gray-700">{project.description}</p>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Projects;
