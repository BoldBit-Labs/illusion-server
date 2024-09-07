import React, { useState } from "react";
import authServiceInstance from "../../services/AuthService";
import { useNavigate } from "react-router-dom";
import Label from "../../components/Label";
import Input from "../../components/Input";
import Button from "../../components/Button";
import Text from "../../components/Text";

function NewProject() {
    const [projectName, setProjectName] = useState("");
    const [description, setDescription] = useState("");
    const [apiPrefix, setApiPrefix] = useState("");
    const navigate = useNavigate()

    const submit = async (e) => {
        e.preventDefault();

        const success = await authServiceInstance.createProject(projectName, description, apiPrefix);
        if (success) {
            navigate("/");
        } else {
            alert("Error in creating project");
        }
    };

    return (
        <div className="flex items-center justify-center h-screen">
            <div className="relative mx-auto mt-14 mb-5 rounded-2xl bg-slate-100 shadow-md w-1/3">
                <div className="p-4">
                    <Text weight="bold">NEW PROJECT</Text>
                </div>

                <form className="px-4" onSubmit={submit}>
                    <Label htmlFor="name">Name</Label>
                    <Input id="name" placeholder="Example: Todo App, Project X..." value={projectName} onChange={(e) => setProjectName(e.target.value)} className="mt-2 mb-6" required />

                    <Label htmlFor="description">Description</Label>
                    <Input id="description" placeholder="Description..." value={description} onChange={(e) => setDescription(e.target.value)} className="mt-2 mb-6" required />

                    <Label htmlFor="prefix">API Prefix</Label>
                    <Input id="prefix" placeholder="Example: /api/v1" value={apiPrefix} onChange={(e) => setApiPrefix(e.target.value)} className="mt-2 mb-6" required />

                    <div className="flex sticky bottom-0 justify-between p-4 rounded-br-2xl rounded-bl-2xl -mx-4">
                        <Button text={"Cancel"} />
                        <Button type="submit" text="Create" />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default NewProject;
