import React, { useState } from 'react';
import Button from '../../components/Button';
import Checkbox from '../../components/Checkbox';
import Text from '../../components/Text';
import Label from "../../components/Label";
import Input from "../../components/Input";
import Schema from './Schema';

const NewEndpointModal = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [methods, setMethods] = useState({ GET: false, GETID: false, POST: false, PUT: false, DELETE: false });
  const [errors, setErrors] = useState({});

  const validateForm = () => {
    let tempErrors = {};
    if (!name) tempErrors.name = "Name is required";
    if (!description) tempErrors.description = "Description is required";
    if (!methods.GET && !methods.POST && !methods.PUT && !methods.DELETE) {
      tempErrors.methods = "Select at least one method";
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (validateForm()) {
      const data = { name, description, methods };
      console.log("Form Data:", data);
      setIsModalOpen(false);
    }
  };

  const handleCheckboxChange = (method) => {
    setMethods({ ...methods, [method]: !methods[method] });
  };

  const nameRegex = /^[a-zA-Z0-9-]*$/;
  const handleNameChange = (e) => {
    const value = e.target.value;
    if (nameRegex.test(value)) {
      setName(value);
    }
  };

  return (
    <div>
      <div className="mt-8 w-fit">
        <Button text="New Endpoint" onClick={() => setIsModalOpen(true)} />
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="relative bg-white rounded-lg shadow-lg w-full max-w-3xl max-h-[calc(100vh-4rem)] overflow-y-auto p-8">

            <Text tag='h2' size='xl' weight='bold' className={"mb-6"}>Create Endpoint</Text>
            <button onClick={() => setIsModalOpen(false)} className="absolute top-2 right-4 text-3xl text-gray-400 hover:text-red-500 hover:scale-150 duration-200">&times;</button>

            <form onSubmit={handleSubmit} noValidate>
              <Label htmlFor="name">Name</Label>
              <Input id="name" placeholder="Enter endpoint name" value={name} onChange={handleNameChange} className="mt-1 mb-3" />
              {errors.name && <Text tag='p' className="text-red-500">{errors.name}</Text>}

              <Label htmlFor="description" className="mt-4">Description</Label>
              <Input id="description" placeholder="Enter endpoint description" value={description} onChange={(e) => setDescription(e.target.value)} className="mt-1 mb-3" />
              {errors.description && <Text tag='p' className="text-red-500">{errors.description}</Text>}

              <Schema />

              <div className='mt-6'>
                <div className='flex items-center'>
                  <Checkbox id={"get"} checked={methods.GET} onChange={() => handleCheckboxChange("GET")} />
                  <Label htmlFor="get" className={"font-medium ml-4"}>
                    GET
                    <Text className={"text-blue-400 ml-2"}>/{name}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"getid"} checked={methods.GETID} onChange={() => handleCheckboxChange("GETID")} />
                  <Label htmlFor="getid" className={"font-medium ml-4"}>
                    GET <Text className="text-blue-400 ml-2">{name ? `/${name}/:id` : '/:id'}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"post"} checked={methods.POST} onChange={() => handleCheckboxChange("POST")} />
                  <Label htmlFor="post" className={"font-medium ml-4"}>
                    POST  <Text className="text-blue-400 ml-2">/{name}</Text>
                  </Label>
                </div>

                <div className='flex items-center mt-4'>
                  <Checkbox id={"put"} checked={methods.PUT} onChange={() => handleCheckboxChange("PUT")} />
                  <Label htmlFor="put" className={"font-medium ml-4"}>
                    PUT <Text className="text-blue-400 ml-2">{name ? `/${name}/:id` : '/:id'}</Text>
                  </Label>
                </div>

                <div className='flex items-center my-4'>
                  <Checkbox id={"delete"} checked={methods.DELETE} onChange={() => handleCheckboxChange("DELETE")} />
                  <Label htmlFor="delete" className={"font-medium ml-4"}>
                    DELETE <Text className="text-blue-400 ml-2">{name ? `/${name}/:id` : '/:id'}</Text>
                  </Label>
                </div>
                {errors.methods && <Text tag='p' className="text-red-500 mb-2">{errors.methods}</Text>}
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