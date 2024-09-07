import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import authServiceInstance from '../../services/AuthService';
import { FaPlus } from "react-icons/fa6";
import Text from "../../components/Text"
import SlideButton from '../../components/SlideButton';

const Dashboard = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const data = await authServiceInstance.fetchProjects();
        setProjects(data);
      } catch (err) {
        console.error('Error fetching projects:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchProjects();
  }, []);

  const handleCreateProject = () => {
    navigate("/create-project");
  };

  const projectClick = (projectId) => {
    navigate(`/projects/${projectId}`);
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        {/* TODO: loader */}
        <Text>Loading...</Text>
      </div>
    );
  }

  return (
    <div className="pt-44 px-16">
      {projects.length === 0 ? (
        <div className="text-center">
          <Text size='xxl' weight="bold">No Projects</Text>
          <Text tag='p' size='xxl' weight="bold">You don't have any projects yet.</Text>
          <Button onClick={handleCreateProject} text="Create New Project" />
        </div>
      ) : (
        <div>
          <div className="flex items-center mb-6">
            <Text weight="bold" className={"text-3xl"}>Projects</Text>
            <SlideButton onClick={handleCreateProject} label="New" icon={<FaPlus className='text-white' />} />
          </div>

          <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {projects.map((project) => (
              <li key={project.id} className="bg-slate-300 p-6 rounded-xl shadow-md hover:bg-slate-400 hover:scale-90 duration-200 cursor-pointer"
                onClick={() => projectClick(project.id)} >
                <Text tag='h3' size='xl' weight='bold'>{project.name}</Text>
                <Text>{project.description}</Text>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
