import React, { useState } from 'react';
import Button from '../../components/Button';
import Checkbox from '../../components/Checkbox';
import Text from '../../components/Text';
import Label from "../../components/Label";
import Input from "../../components/Input";

const NewEndpointModal = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [url, setUrl] = useState("");
  const [body, setBody] = useState("");
  const [methods, setMethods] = useState({ GET: false, POST: false, PUT: false, DELETE: false });
  const [errors, setErrors] = useState({});

  const validateForm = () => {
    let tempErrors = {};
    if (!name) tempErrors.name = "Name is required";
    if (!description) tempErrors.description = "Description is required";
    if (!url) tempErrors.url = "URL is required";
    if (!Object.values(methods).includes(true)) tempErrors.methods = "Select at least one method";

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (validateForm()) {
      const data = { name, description, url, body, methods };
      console.log("Form Data:", data);
      setIsModalOpen(false);
    }
  };

  const handleCheckboxChange = (method) => {
    setMethods({ ...methods, [method]: !methods[method] });
  };

  return (
    <div>
      <div className="mt-8 w-fit">
        <Button text="New Endpoint" onClick={() => setIsModalOpen(true)} />
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="relative bg-white rounded-lg shadow-lg w-full max-w-3xl max-h-[calc(100vh-4rem)] overflow-y-auto p-8">

            <button  onClick={() => setIsModalOpen(false)} className="absolute top-2 right-4 text-gray-400 hover:text-red-500 hover:scale-150 duration-200">
              &times;
            </button>
            <Text tag='h2' size='xl' weight='bold' className={"mb-6"}>Create Endpoint</Text>

            <form onSubmit={handleSubmit} noValidate>
              <Label htmlFor="name">Name</Label>
              <Input id="name" placeholder="Enter endpoint name" value={name} onChange={(e) => setName(e.target.value)} className="mt-1 mb-3" />
              {errors.name && <Text tag='p' className="text-red-500">{errors.name}</Text>}
              
              <Label htmlFor="description" className="mt-4">Description</Label>
              <Input id="description" placeholder="Enter endpoint description" value={description} onChange={(e) => setDescription(e.target.value)} className="mt-1 mb-3" />
              {errors.description && <Text tag='p' className="text-red-500">{errors.description}</Text>}

              <Label htmlFor="url" className="mt-4">URL</Label>
              <Input id="url" placeholder="Enter endpoint URL" value={url} onChange={(e) => setUrl(e.target.value)} className="mt-1 mb-3" />
              {errors.url && <Text tag='p' className="text-red-500">{errors.url}</Text>}

              <Label htmlFor="methods" className="mt-4">HTTP Methods</Label>
              <div className='mb-3'>
                {["GET", "POST", "PUT", "DELETE"].map((method) => (
                  <div key={method} className="flex items-center mt-3">
                    <Checkbox id={method.toLowerCase()} checked={methods[method]} onChange={() => handleCheckboxChange(method)} />
                    <Label htmlFor={method.toLowerCase()} className="font-medium ml-2">{method}</Label>
                  </div>
                ))}
              </div>
              {errors.methods && <Text tag='p' className="text-red-500 mb-2">{errors.methods}</Text>}

              <Label htmlFor="body" className="mt-4">Body</Label>
              <Input id="body" placeholder="Enter endpoint body (optional)" value={body} onChange={(e) => setBody(e.target.value)} className="mt-1 mb-6" />

              <Button type="submit" text="Create Endpoint" />
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default NewEndpointModal;
