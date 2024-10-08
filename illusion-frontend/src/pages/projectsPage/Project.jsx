import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProjectNamePlate from './ProjectNamePlate';
import NewEndpointModal from './NewEndpointModal';
import projectServiceInstance from '../../services/ProjectService';
import Loader from '../../components/Loader';

function Project() {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const data = await projectServiceInstance.getProject(projectId);
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
    return <Loader visibility={true} />; 
  }

  return (
    <div className="pt-safe-top">
      <Loader visibility={loading} />

      <div className="pt-8 p-12">
        <div className="w-fit">
          <ProjectNamePlate projectName={project.name} projectId={projectId} apiPrefix={project.apiPrefix}/>
        </div>

        <NewEndpointModal projectInfo={projectId} />
      </div>
    </div>
  );
}

export default Project;
