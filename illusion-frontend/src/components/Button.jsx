import React from 'react';

function Button({ type = 'button', text, className = '', onClick }) {
  return (
    <button type={type} className={`inline-flex items-center rounded-full px-6 py-3 text-gray-500 hover:text-blue-500 border-2 border-gray-400
            transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-90 duration-200 focus:bg-transparent font-semibold ${className}`} onClick={onClick} >
      {text}
    </button>
  );
}

export default Button;

// <Button text="Cancel" onClick={() => console.log('Clicked cancel')} />
