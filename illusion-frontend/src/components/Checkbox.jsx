import React, { useState } from 'react';

function Checkbox({ id, checked, onChange, className, ...props }) {
    const [isChecked, setIsChecked] = useState(checked || false);

    const handleToggle = (event) => {
        setIsChecked(event.target.checked);
        onChange && onChange(event);
    };

    return (
        <label className={`relative inline-flex items-center cursor-pointer ${className}`}>
            <input
                id={id}
                type="checkbox"
                checked={isChecked}
                onChange={handleToggle}
                className="sr-only peer"
                {...props}
            />
            <div
                className={`group peer ring-0 rounded-full outline-none duration-300 after:duration-300 w-12 h-6 shadow-md
          ${isChecked ? 'bg-emerald-500 after:translate-x-6 after:content-["✔️"]' : 'bg-rose-400 after:content-["✖️"]'}
          peer-focus:outline-none after:rounded-full after:absolute after:bg-gray-50 after:outline-none after:h-5 after:w-5 after:top-0.5 after:left-0.5 after:-rotate-180 after:flex after:justify-center after:items-center peer-hover:after:scale-95 peer-checked:after:rotate-0
        `}
            />
        </label>
    );
}

export default Checkbox;