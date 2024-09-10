import React from 'react';
import Text from './Text';

const SlideButton = ({ onClick, label, icon, className }) => {
    return (
        <button type="button" className={`ml-6 group flex items-center justify-start w-11 h-11 bg-slate-400 rounded-full cursor-pointer relative overflow-hidden transition-all 
                        duration-200 shadow-lg hover:w-fit hover:rounded-full active:translate-x-1 active:translate-y-1 ${className}`}
            onClick={onClick} >
            <div className="flex items-center justify-center w-full transition-all duration-300 group-hover:justify-start group-hover:px-3">
                {icon}
            </div>
            <div className="overflow-hidden flex-shrink-0 max-w-0 group-hover:max-w-xs transition-all duration-300">
                <Text weight='semibold' className={"whitespace-nowrap mr-4 text-white"}>{label}</Text>
            </div>
        </button>
    );
};

export default SlideButton;

// <SlideButton onClick={handleButtonClick} label="New" icon={<FaPlus className='text-white' />} />