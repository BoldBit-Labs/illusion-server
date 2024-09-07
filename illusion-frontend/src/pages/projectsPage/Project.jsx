import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProjectNamePlate from './ProjectNamePlate';
import authServiceInstance from '../../services/AuthService';
import NewEndpointModal from './NewEndpointModal';


function Project() {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const data = await authServiceInstance.getProject(projectId);
        setProject(data);
      } catch (err) {
        console.error('Error fetching project:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchProject();
  }, [projectId]);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <p className="text-lg font-semibold">Loading...</p>
      </div>
    );
  }

  if (!project) {
    return (
      <div className="flex justify-center items-center h-screen">
        <p className="text-lg font-semibold text-red-500">Failed to load project data.</p>
      </div>
    );
  }

  return (
    <div className="pt-safe-top">
      <div className="pt-8 p-12">
        <div className="w-2/5">
          <ProjectNamePlate projectName={project.name} projectId={projectId} />
        </div>

        <NewEndpointModal />
      </div>
    </div>
  );
}

export default Project;
