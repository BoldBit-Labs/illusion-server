import React from 'react';
import Text from '../../components/Text';

function ProjectNamePlate({ projectName, projectId, endpointPrefix }) {
    return (
        <div className="bg-slate-300 p-8 rounded-xl shadow-lg">
            <Text size='xxl' weight='semibold'>{projectName}</Text>
            <div className='mt-2'>
                <Text weight='medium'>
                    https://
                    <Text weight='medium' className={"bg-sky-400 rounded-md px-2 py-1 text-white"}>{projectId}</Text>
                    .illusion.com
                    { endpointPrefix ? <Text weight='medium' className={"bg-yellow-500 rounded-md px-2 py-1 text-white"}>{endpointPrefix}</Text> : '' }
                    /
                    <Text weight='medium' className={"bg-red-500 rounded-md px-2 py-1 text-white"}>:endpoint</Text>
                </Text>
            </div>
        </div>
    );
}

export default ProjectNamePlate;
