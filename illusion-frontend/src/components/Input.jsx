import React from 'react';

function Input({
  id,
  type = 'text',
  placeholder,
  value,
  onChange,
  className,
  isDisabled = false,
  maxLength,
  ...rest
}) {
  const inputClasses = `block w-full rounded-2xl border border-neutral-300 bg-transparent py-4 pl-6 pr-20 text-base/6 text-neutral-950 transition placeholder:text-neutral-500 ${isDisabled ? 'opacity-50 cursor-not-allowed' : ''} focus:border-neutral-900 focus:outline-none focus:ring-neutral-900/5 ${className}`;

  return (
    <input
      id={id}
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className={inputClasses}
      disabled={isDisabled}
      maxLength={maxLength}
      {...rest}
    />
  );
}

export default Input;


//   <Input
//   id="id"
//   type="text"
//   placeholder="Enter your name"
//   value={name}
//   onChange={(e) => setName(e.target.value)}
//   className="text-blue-500 border-blue-500"
//   isDisabled={true} // Make input disabled
//   maxLength={20} // Custom prop
// />
