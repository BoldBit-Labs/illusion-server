import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProjectNamePlate from './ProjectNamePlate';
import NewEndpointModal from './NewEndpointModal';
import projectServiceInstance from '../../services/ProjectService';
import Loader from '../../components/Loader';
import Text from '../../components/Text';
import endpointServiceInstance from '../../services/EndpointService';
import EndpointCard from './EndpointCard';

function Project() {
  const { projectId } = useParams();
  const [project, setProject] = useState(null);
  const [endpoints, setEndpoints] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {  
    const fetchProject = async () => {
      try {
        const data = await projectServiceInstance.getProject(projectId);
        setProject(data);
        await fetchEndpoints();
      } catch (err) {
        console.error('Error fetching project:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchProject();
  }, [projectId]);
  

  const fetchEndpoints = async () => {
    try {
      if(!loading){
        setLoading(true);
      }
      const data = await endpointServiceInstance.fetchEndpoints(projectId);
      setEndpoints(data);
      setLoading(false);
    } catch (err) {
      console.error('Error fetching endpoints:', err);
    }
  };

  if (loading) {
    return <Loader visibility={true} />; 
  }

  return (
    <div className="pt-safe-top">
      <div className="pt-8 p-12">
        {project && (
          <div className="w-fit">
            <ProjectNamePlate projectName={project.name} projectId={projectId} endpointPrefix={project.endpointPrefix} />
          </div>
        )}

        <NewEndpointModal projectIdRef={projectId} endpointPrefixRef={project.endpointPrefix} formSubmit={fetchEndpoints} />
      </div>

      <div className="w-2/3 pl-12">
        {endpoints.length === 0 ? (
          <div className="text-center">
            <Text size='xxl' weight="bold">No endpoints</Text>
          </div>
        ) : (
          <ul className="">
            {endpoints.map((endpoint) => (
              <EndpointCard project={project} endpoint={endpoint} onDelete={fetchEndpoints} />
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}

export default Project;
