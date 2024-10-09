import React from 'react';
import Text from '../../components/Text';
import { FaRegTrashCan, FaRegCopy, FaRegPenToSquare } from "react-icons/fa6";

function EndpointCard({ project, endpoint }) {
    const fullUrl = `https://${endpoint.id}.illusion.com${project.endpointPrefix ? project.endpointPrefix : ''}${endpoint.path}`;

    const colors = [
        { text: 'text-red-800', bg: 'bg-red-200' },
        { text: 'text-pink-800', bg: 'bg-pink-200' },
        { text: 'text-blue-800', bg: 'bg-blue-200' },
        { text: 'text-teal-800', bg: 'bg-teal-200' },
        { text: 'text-gray-800', bg: 'bg-gray-200' },
        { text: 'text-lime-800', bg: 'bg-lime-200' },
        { text: 'text-cyan-800', bg: 'bg-cyan-200' },
        { text: 'text-green-800', bg: 'bg-green-200' },
        { text: 'text-yellow-800', bg: 'bg-yellow-200' },
        { text: 'text-purple-800', bg: 'bg-purple-200' },
        { text: 'text-indigo-800', bg: 'bg-indigo-200' },
        { text: 'text-orange-800', bg: 'bg-orange-200' },
    ];

    // Randomly select a color
    const randomColor = colors[Math.floor(Math.random() * colors.length)];

    return (
        <div>
            <li className={`mb-4 items-center justify-between p-4 rounded-xl border-dashed border-2 border-gray-500 hover:scale-90 duration-200 cursor-pointer`}>
                <div className="flex items-center justify-between mb-2">
                    <Text className={`px-2 rounded-lg ${randomColor.text} ${randomColor.bg}`}>{fullUrl}</Text>
                    <div className="flex">
                        <FaRegPenToSquare className="hover:scale-150 duration-200" onClick={(event) => { event.stopPropagation(); console.log("Edit Endpoint: "); }} />
                        <FaRegCopy className="ml-4 hover:scale-150 duration-200" onClick={(event) => { event.stopPropagation(); console.log("Copying URL: ", fullUrl); }} />
                        <FaRegTrashCan className="ml-4 hover:scale-150 duration-200" onClick={(event) => { event.stopPropagation(); console.log("Deleting endpoint: ", endpoint.id); }} />
                    </div>
                </div>
                <div className="flex items-center mb-2">
                    { endpoint.allowedMethods.GET ? <Text className="px-1 mr-3 rounded-lg bg-green-300 text-green-800" >GET</Text> : ""}
                    { endpoint.allowedMethods.GETID ? <Text className="px-1 mr-3 rounded-lg bg-sky-200 text-sky-800" >GET/:ID</Text> : ""}
                    { endpoint.allowedMethods.POST ? <Text className="px-1 mr-3 rounded-lg bg-yellow-200 text-yellow-800" >POST</Text> : ""}
                    { endpoint.allowedMethods.PUT ? <Text className="px-1 mr-3 rounded-lg bg-blue-200 text-blue-800" >PUT</Text> : ""}
                    { endpoint.allowedMethods.DELETE ? <Text className="px-1 mr-3 rounded-lg bg-red-200 text-red-800" >DELETE/:ID</Text> : ""}
                </div>
                <div>
                    <Text>{endpoint.description}</Text>
                </div>
            </li>
        </div>
    );
}

export default EndpointCard;
