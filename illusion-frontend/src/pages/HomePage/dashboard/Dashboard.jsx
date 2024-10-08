import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { FaPlus } from "react-icons/fa6";
import Text from "../../../components/Text";
import SlideButton from '../../../components/SlideButton';
import ProjectCard from './ProjectCard';
import Loader from '../../../components/Loader';
import projectServiceInstance from '../../../services/ProjectService';

const Dashboard = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProjects();
  }, []);
  
  const fetchProjects = async () => {
    setLoading(true);
    try {
      const data = await projectServiceInstance.fetchProjects();
      setProjects(data);
    } catch (err) {
      console.error('Error fetching projects:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateProject = () => {
    navigate("/create-project");
  };

  return (
    <div className="pt-44 px-16">
      <Loader visibility={loading} />

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
              <ProjectCard key={project.id} project={project} onDelete={fetchProjects} />
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
