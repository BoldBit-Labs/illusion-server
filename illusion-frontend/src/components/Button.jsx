import React from "react";

function Button(props) {
  return (
    <div>
      <button
        type={props.type}
        className="cursor-pointer inline-flex items-center rounded-full px-6 py-3 text-xs text-gray-500 hover:text-blue-500 border-2 border-gray-400
                    transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-90 duration-200 focus:bg-transparent"
      >
        {props.text}
      </button>
    </div>
  );
}

export default Button;
