import React, { useState } from 'react';
import Button from '../../components/Button';
import Checkbox from '../../components/Checkbox';
import Text from '../../components/Text';
import Label from "../../components/Label";
import Input from "../../components/Input";
import Schema from './Schema';
import endpointServiceInstance from '../../services/EndpointService';
import Loader from '../../components/Loader';

const NewEndpointModal = ({ projectInfo, formSubmit }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const projectId = projectInfo;
  const [path, setPath] = useState("");
  const [description, setDescription] = useState("");
  const [schema, setSchema] = useState({});
  const [allowedMethods, setAllowedMethods] = useState({ GET: false, GETID: false, POST: false, PUT: false, DELETE: false });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  const validateForm = () => {
    let tempErrors = {};
    if (!path) tempErrors.path = "Name is required";
    if (!description) tempErrors.description = "Description is required";
    if (!allowedMethods.GET && !allowedMethods.GETID && !allowedMethods.POST && !allowedMethods.PUT && !allowedMethods.DELETE) {
      tempErrors.allowedMethods = "Select at least one method";
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (validateForm()) {
      setLoading(true);
      const data = { projectId, path, description, schema, allowedMethods };
      const success = await endpointServiceInstance.createEndpoint(data);
      if (success !== null) {
        setIsModalOpen(false);

        setPath("");
        setDescription("");
        setAllowedMethods({ GET: false, GETID: false, POST: false, PUT: false, DELETE: false });
        setSchema({});
        setLoading(false);
        formSubmit();
      } else {
        alert("Error in creating project");
        setLoading(false);
      }
    }
  };

  const handleCheckboxChange = (method) => {
    setAllowedMethods({ ...allowedMethods, [method]: !allowedMethods[method] });
  };

  const handlePathChange = (e) => {
    const value = e.target.value.trim();
    const newPath = (path.length < 1 && value !== "/") ? `/${value}` : value;

    if ((path.endsWith("/") && newPath.endsWith("/"))
      || (path.endsWith("-") && newPath.endsWith("-"))
      || (path.endsWith("_") && newPath.endsWith("_"))) {

      return;
    }

    if (validatePath(newPath)) {
      setPath(newPath);
    }
  };

  const validatePath = (path) => {
    const urlPathRegex = /^[a-zA-Z0-9/_-]+$/;
    return urlPathRegex.test(path);
  };

  return (
    <div>
      <div className="mt-8 w-fit">
        <Button text="New Endpoint" onClick={() => setIsModalOpen(true)} />
      </div>

      <Loader visibility={loading} />

      {isModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="relative bg-white rounded-lg shadow-lg w-full max-w-3xl max-h-[calc(100vh-4rem)] overflow-y-auto p-8">

            <Text tag='h2' size='xl' weight='bold' className={"mb-6"}>Create Endpoint</Text>
            <button type="button" onClick={() => setIsModalOpen(false)} className="absolute top-2 right-4 text-3xl text-gray-400 hover:text-red-500 hover:scale-150 duration-200">
              &times;
            </button>

            <form onSubmit={handleSubmit} noValidate>
              <Label htmlFor="path">Name</Label>
              <Input id="path" placeholder="/users" value={path} onChange={handlePathChange} className="mt-1 mb-3" />
              {errors.path && <Text tag='p' className="text-red-500">{errors.path}</Text>}

              <Label htmlFor="description" className="mt-4">Description</Label>
              <Input id="description" placeholder="Enter endpoint description" value={description} onChange={(e) => setDescription(e.target.value)} className="mt-1 mb-3" />
              {errors.description && <Text tag='p' className="text-red-500">{errors.description}</Text>}

              <Schema setSchema={setSchema} />

              <div className='mt-6'>
                <div className='flex items-center'>
                  <Checkbox id={"get"} checked={allowedMethods.GET} onChange={() => handleCheckboxChange("GET")} />
                  <Label htmlFor="get" className={"font-medium ml-4"}>
                    GET
                    <Text className={"text-blue-400 ml-2"}>{path ? `${path}` : '/'}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"getid"} checked={allowedMethods.GETID} onChange={() => handleCheckboxChange("GETID")} />
                  <Label htmlFor="getid" className={"font-medium ml-4"}>
                    GET <Text className="text-blue-400 ml-2">{path.endsWith('/') ? `${path}:id` : `${path}/:id`}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"post"} checked={allowedMethods.POST} onChange={() => handleCheckboxChange("POST")} />
                  <Label htmlFor="post" className={"font-medium ml-4"}>
                    POST  <Text className="text-blue-400 ml-2">{path ? `${path}` : '/'}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"put"} checked={allowedMethods.PUT} onChange={() => handleCheckboxChange("PUT")} />
                  <Label htmlFor="put" className={"font-medium ml-4"}>
                    PUT <Text className="text-blue-400 ml-2">{path.endsWith('/') ? `${path}:id` : `${path}/:id`}</Text>
                  </Label>
                </div>

                <div className='flex items-center my-4'>
                  <Checkbox id={"delete"} checked={allowedMethods.DELETE} onChange={() => handleCheckboxChange("DELETE")} />
                  <Label htmlFor="delete" className={"font-medium ml-4"}>
                    DELETE <Text className="text-blue-400 ml-2">{path.endsWith('/') ? `${path}:id` : `${path}/:id`}</Text>
                  </Label>
                </div>
                {errors.allowedMethods && <Text tag='p' className="text-red-500 mb-2">{errors.allowedMethods}</Text>}
              </div>

              <Button type="submit" text="Create Endpoint" />
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default NewEndpointModal;