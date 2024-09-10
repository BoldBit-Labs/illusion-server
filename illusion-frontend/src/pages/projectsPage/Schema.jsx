import React, { useState } from 'react';
import FieldRow from './FieldRow';
import SlideButton from '../../components/SlideButton';
import { FaPlus } from "react-icons/fa6";
import Text from '../../components/Text';
import Input from '../../components/Input';
import Dropdown from '../../components/Dropdown';

function Schema() {
    const [schemaFields, setSchemaFields] = useState([{ name: 'id', type: 'String', depth: 0, isMandatory: true, isDisabled: true }]);
    const fieldTypes = ["String", "Number", "Float", "Boolean", "Date", "Array", "Object"];

    const handleFieldChange = (index, key, value) => {
        const updatedFields = [...schemaFields];
        updatedFields[index][key] = value;
        setSchemaFields(updatedFields);
    };

    const addField = () => {
        setSchemaFields([...schemaFields, { name: '', type: 'String', depth: 0 }]);
    };

    const addNewFieldRow = (parentIndex) => {
        const updatedFields = [...schemaFields];
        updatedFields.splice(parentIndex + 1, 0, { name: '', type: 'String', depth: schemaFields[parentIndex].depth + 1 });
        setSchemaFields(updatedFields);
    };

    const removeCurrentFieldRow = (index) => {
        const fieldToRemove = schemaFields[index];
        const updatedFields = [...schemaFields];

        updatedFields.splice(index, 1);

        if (fieldToRemove.type === 'Object') {
            const currentDepth = fieldToRemove.depth;

            let nextIndex = index;
            while (nextIndex < updatedFields.length && updatedFields[nextIndex].depth > currentDepth) {
                updatedFields.splice(nextIndex, 1);
            }
        }

        setSchemaFields(updatedFields);
    };

    const buildNestedSchema = (fields, depth = 0) => {
        const nestedSchema = {};

        for (let i = 0; i < fields.length; i++) {
            const field = fields[i];

            if (field.depth === depth) {
                if (field.type === 'Object') {
                    const nestedFields = fields.slice(i + 1).filter(f => f.depth > depth);
                    nestedSchema[field.name] = buildNestedSchema(nestedFields, depth + 1);
                } else {
                    nestedSchema[field.name] = field.type;
                }
            }
        }

        return nestedSchema;
    };

    return (
        <div>
            <Text>Schema</Text>
            <div className='p-4 rounded-2xl border-gray-400 border-2'>

                <div className='flex'>
                    <div className='flex flex-2'>
                        <Input name="id" placeholder="984kbf2ed8e3472fc73" value="id" isDisabled={true} />
                        <Dropdown name="type" options={fieldTypes} value="String" isDisabled={true} className='ml-4' />
                    </div>
                    <div className='flex-1'></div>
                </div>

                {schemaFields.slice(1).map((field, index) => (
                    <FieldRow
                        key={index + 1} // Adjust the key because the first field is "id"
                        field={field}
                        index={index + 1}
                        schemaFields={schemaFields}
                        fieldTypes={fieldTypes}
                        handleFieldChange={handleFieldChange}
                        addNewFieldRow={addNewFieldRow}
                        removeCurrentFieldRow={removeCurrentFieldRow}
                    />
                ))}

                <SlideButton onClick={addField} label="Add Field" className={"mt-4"} icon={<FaPlus className="text-white" />} />

                <div className="mt-8">
                    <Text weight="semibold">Schema Preview</Text>
                    <pre className="p-3 bg-gray-100 rounded-md overflow-auto">
                        <code>{JSON.stringify(buildNestedSchema(schemaFields), null, 2)}</code>
                    </pre>
                </div>
            </div>
        </div>
    );
}

export default Schema;
