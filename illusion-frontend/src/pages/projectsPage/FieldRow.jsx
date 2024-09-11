import React from 'react';
import Input from '../../components/Input';
import Dropdown from '../../components/Dropdown';

function FieldRow({ field, index, schemaFields, fieldTypes, handleFieldChange, removeCurrentFieldRow, addNewFieldRow }) {
    return (
        // Indent based on depth
        <div className="flex mt-2" style={{ marginLeft: `${field.depth * 20}px` }} >
            <Input id={`fieldName-${index}`} name="name" placeholder="Enter field name" className="flex-1" value={field.name} onChange={(e) => handleFieldChange(index, 'name', e.target.value)} />

            <Dropdown id={`type-${index}`} name="type" options={fieldTypes} className="ml-4" value={field.type} onChange={(e) => handleFieldChange(index, 'type', e.target.value)} />

            <div className="flex-1">
                {schemaFields.length > 1 && (<button type='button' onClick={() => removeCurrentFieldRow(index)} className="ml-4 text-red-500 text-4xl hover:scale-150 duration-200">&times;</button>)}

                {field.type === "Object" && (<button type='button' onClick={() => addNewFieldRow(index)} className="ml-4 text-blue-500 text-4xl hover:scale-150 duration-200"> + </button>)}
            </div>

            {/* Render nested fields if the current field is an Object */}
            {field.children && field.children.length > 0 && (
                <div className="w-full">
                    {field.children.map((child, childIndex) => (
                        <FieldRow
                            key={`${index}-${childIndex}`}
                            field={child}
                            index={childIndex}
                            schemaFields={schemaFields}
                            fieldTypes={fieldTypes}
                            handleFieldChange={handleFieldChange}
                            addNewFieldRow={addNewFieldRow}
                            removeCurrentFieldRow={removeCurrentFieldRow}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}

export default FieldRow;
