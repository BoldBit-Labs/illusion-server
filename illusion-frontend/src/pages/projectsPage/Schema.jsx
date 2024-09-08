import React, { useState } from "react";
import Text from "../../components/Text";
import Label from "../../components/Label";
import Input from "../../components/Input";
import SlideButton from "../../components/SlideButton";
import { FaPlus } from "react-icons/fa6";
import Dropdown from "../../components/Dropdown";

const fieldTypes = ["String", "Number", "Float", "Boolean", "Date", "Object", "Array"];

const Schema = () => {
    const [schemaFields, setSchemaFields] = useState([]);
    const [field, setField] = useState({ name: "", type: "String" });

    const handleFieldChange = (e) => {
        const { name, value } = e.target;
        setField((prevField) => ({
            ...prevField,
            [name]: value,
        }));
    };

    const addField = () => {
        if (field.name.trim()) {
            setSchemaFields((prevFields) => [...prevFields, field]);
            setField({ name: "", type: "String" });
        } else {
            alert("Field name is required");
        }
    };

    const addNestedField = (index) => {
        const newField = { name: "", type: "String" };
        const updatedFields = [...schemaFields];
        const targetField = updatedFields[index];

        if (!targetField.fields) {
            targetField.fields = [];
        }

        targetField.fields.push(newField);
        setSchemaFields(updatedFields);
    };

    const removeField = (index) => {
        setSchemaFields((prevFields) =>
            prevFields.filter((_, i) => i !== index)
        );
    };

    const handleNestedFieldChange = (parentIndex, nestedIndex, e) => {
        const { name, value } = e.target;
        const updatedFields = [...schemaFields];
        const targetField = updatedFields[parentIndex];

        if (targetField.fields) {
            targetField.fields[nestedIndex] = {
                ...targetField.fields[nestedIndex],
                [name]: value,
            };
        }

        setSchemaFields(updatedFields);
    };

    const schemaJSON = schemaFields.reduce((acc, { name, type, fields }) => {
        acc[name] = type;
        if (fields) {
            acc[name] = fields.reduce((subAcc, { name, type }) => {
                subAcc[name] = type;
                return subAcc;
            }, {});
        }
        return acc;
    }, {});

    return (
        <div className="mt-10">
            <Text weight="semibold">Define Your Request Schema</Text>

            <div className="flex mt-2">
                <Text className="flex-1">Field Name</Text>
                <Text className="flex-1">Type</Text>
            </div>


            <div className="flex mt-4">
                <div className="flex-1">
                    <Label htmlFor="fieldName">New Field Name</Label>
                    <Input id="fieldName" name="name" placeholder="Enter field name" value={field.name} onChange={handleFieldChange} className="mt-1 mb-3" />
                </div>
                <div className="flex-1 ml-4">
                    <Label htmlFor="type">Type</Label>
                    <Dropdown id="type" name="type" value={field.type} onChange={handleFieldChange} options={fieldTypes} className="mt-1 mb-3" />
                </div>
            </div>
            <SlideButton onClick={addField} label="Add Field" icon={<FaPlus className="text-white" />} />

            <div className="mt-8">
                <Text weight="semibold">Schema Preview</Text>
                <br />
                {schemaFields.length > 0 ? (
                    <pre className="p-3 bg-gray-100 rounded-md overflow-auto"><code>{JSON.stringify(schemaJSON, null, 2)}</code></pre>
                ) : (
                    <pre className="p-3 bg-gray-100 rounded-md overflow-auto"><code>{"{ }"}</code></pre>
                )}
            </div>
        </div>
    );
};

export default Schema;
