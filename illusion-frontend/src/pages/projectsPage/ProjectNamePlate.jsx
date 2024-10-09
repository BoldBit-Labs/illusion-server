import React from 'react';
import Text from '../../components/Text';

function ProjectNamePlate({ projectName, projectId, endpointPrefix }) {
    return (
        <div className="bg-slate-300 p-8 rounded-xl shadow-lg">
            <Text size='xxl' weight='semibold'>{projectName}</Text>
            <div className='mt-2'>
                <Text weight='medium'>
                    https://
                    <Text weight='medium' className={"bg-sky-300 rounded-md px-2 py-1 text-sky-800"}>{projectId}</Text>
                    .illusion.com
                    { endpointPrefix ? <Text weight='medium' className={"bg-yellow-200 rounded-md px-2 py-1 text-yellow-800"}>{endpointPrefix}</Text> : '' }
                    /
                    <Text weight='medium' className={"bg-red-200 rounded-md px-2 py-1 text-red-800"}>:endpoint</Text>
                </Text>
            </div>
        </div>
    );
}

export default ProjectNamePlate;
