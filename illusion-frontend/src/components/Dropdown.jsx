import React from 'react';

function Dropdown({ id, name, value, onChange, options = [], className = '', isDisabled = false, ...rest }) {
    const dropdownClasses = `block w-fit rounded-2xl border border-neutral-300 bg-transparent py-4 pl-6 pr-10 text-base text-neutral-950 transition focus:border-neutral-900 focus:outline-none focus:ring-neutral-900/5 ${isDisabled ? 'opacity-50 cursor-not-allowed' : ''} ${className}`;

    return (
        <select id={id} name={name} value={value} onChange={onChange} className={dropdownClasses} disabled={isDisabled} {...rest} >
            {options.map((option) => (
                <option key={option} value={option}>
                    {option}
                </option>
            ))}
        </select>
    );
}

export default Dropdown;

// Example Usage:

// <Dropdown
//     name="type"
//     value={field.type}
//     onChange={handleFieldChange}
//     options={fieldTypes}
//     className="custom-class"
//     isDisabled={false}
// />
