import React from 'react';

const SlideButton = ({ onClick, label, icon, className }) => {
    return (
        <button className={`ml-6 group flex items-center justify-start w-11 h-11 bg-slate-400 rounded-full cursor-pointer relative overflow-hidden transition-all 
                        duration-200 shadow-lg hover:w-24 hover:rounded-full active:translate-x-1 active:translate-y-1 ${className}`}
            onClick={onClick} >
            <div className="flex items-center justify-center w-full transition-all duration-300 group-hover:justify-start group-hover:px-3">
                {icon}
            </div>
            <div className="absolute right-5 transform translate-x-full opacity-0 text-white text-lg font-semibold transition-all duration-300 group-hover:translate-x-0 group-hover:opacity-100">
                {label}
            </div>
        </button>
    );
};

export default SlideButton;

// <SlideButton onClick={handleButtonClick} label="New" icon={<FaPlus className='text-white' />} />