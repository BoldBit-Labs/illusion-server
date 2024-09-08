import React, { useState } from 'react';
import FieldRow from './FieldRow';
import SlideButton from '../../components/SlideButton';
import { FaPlus } from "react-icons/fa6";
import Text from '../../components/Text';

function Form() {
    const [schemaFields, setSchemaFields] = useState([
        { name: '', type: '', depth: 0 }
    ]);

    const fieldTypes = ["String", "Number", "Float", "Boolean", "Date", "Array", "Object"];

    const handleFieldChange = (index, fieldName, value) => {
        const updatedFields = [...schemaFields];
        updatedFields[index][fieldName] = value;
        setSchemaFields(updatedFields);
    };

    const addField = () => {
        const updatedFields = [...schemaFields, { name: '', type: '', depth: 0 }];
        setSchemaFields(updatedFields);
    };

    const addNewFieldRow = (index) => {
        const parentDepth = schemaFields[index].depth;
        const updatedFields = [...schemaFields];
        updatedFields.splice(index + 1, 0, { name: '', type: '', depth: parentDepth + 1 });
        setSchemaFields(updatedFields);
    };

    const removeCurrentFieldRow = (index) => {
        const currentDepth = schemaFields[index].depth;
        const updatedFields = schemaFields.filter((_, i) => {
            return i < index || schemaFields[i].depth <= currentDepth;
        });
        setSchemaFields(updatedFields);
    };

    return (
        <div>
            {schemaFields.map((field, index) => (
                <FieldRow
                    key={index}
                    field={field}
                    index={index}
                    schemaFields={schemaFields}
                    fieldTypes={fieldTypes}
                    handleFieldChange={handleFieldChange}
                    addNewFieldRow={addNewFieldRow}
                    removeCurrentFieldRow={removeCurrentFieldRow}
                />
            ))}
            <SlideButton onClick={addField} label="Add Field" icon={<FaPlus className="text-white" />} />
            <div className="mt-8">
                <Text weight="semibold">Schema Preview</Text>
                <pre className="p-3 bg-gray-100 rounded-md overflow-auto">
                    <code>{JSON.stringify(JSON.stringify(schemaFields) || "{ }")}</code>
                </pre>
            </div>
        </div>
    );
}

export default Form;
