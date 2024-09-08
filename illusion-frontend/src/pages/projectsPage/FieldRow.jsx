import React from 'react';
import Input from '../../components/Input';
import Dropdown from '../../components/Dropdown';

function FieldRow({ field, index, schemaFields, fieldTypes, handleFieldChange, removeCurrentFieldRow, addNewFieldRow }) {
    return (
        <div
            key={index}
            className="flex mt-2"
            style={{ marginLeft: `${field.depth * 20}px` }}
        >
            <div className="flex-1">
                <Input 
                    id={`fieldName-${index}`} 
                    name="name" 
                    placeholder="Enter field name" 
                    value={field.name} 
                    onChange={(e) => handleFieldChange(index, 'name', e.target.value)} 
                />
            </div>

            <div className="flex-1 ml-4">
                <Dropdown 
                    id={`type-${index}`} 
                    name="type" 
                    options={fieldTypes} 
                    value={field.type} 
                    onChange={(e) => handleFieldChange(index, 'type', e.target.value)} 
                />
            </div>

            <div className="flex-1">
                {schemaFields.length > 1 && (
                    <button 
                        onClick={() => removeCurrentFieldRow(index)} 
                        className="ml-4 text-red-500 text-4xl hover:scale-150 duration-200">
                        &times;
                    </button>
                )}
                
                {field.type === "Object" && (
                    <button 
                        onClick={() => addNewFieldRow(index)} 
                        className="ml-4 text-blue-500 text-4xl hover:scale-150 duration-200">
                        +
                    </button>
                )}
            </div>
        </div>
    );
}

export default FieldRow;
