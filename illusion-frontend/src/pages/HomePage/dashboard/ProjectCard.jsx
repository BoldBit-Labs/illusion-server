import React, { useState } from 'react'
import Text from '../../../components/Text';
import { useNavigate } from 'react-router-dom';
import { FaRegTrashCan } from "react-icons/fa6";
import Loader from '../../../components/Loader';
import projectServiceInstance from '../../../services/ProjectService';

function ProjectCard({ project, onDelete }) {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const openProject = (projectId) => {
        navigate(`/projects/${projectId}`);
    };

    const deleteProject = async (event, projectId) => {
        event.stopPropagation(); // Prevent the click from bubbling up to the <li>
        setLoading(true);

        const success = await projectServiceInstance.deleteProject(projectId);
        if (success) {
            onDelete();
        }
    };

    return (
        <div>
            <Loader visibility={loading} />
            <li key={project.id} className="flex items-center justify-between bg-slate-300 p-6 rounded-xl shadow-md hover:bg-slate-400 hover:scale-90 duration-200 cursor-pointer" onClick={() => openProject(project.id)}>
                <div className="w-full" >
                    <Text tag='h3' size='xl' weight='bold'>{project.name}</Text>
                    <Text>{project.description}</Text>
                </div>
                <FaRegTrashCan className="hover:scale-150 duration-200" onClick={(event) => deleteProject(event, project.id)} />
            </li>
        </div>
    )
}

export default ProjectCard