import React from 'react';

function Label({ htmlFor, children, className, ...props }) {
  return (
    <label htmlFor={htmlFor} className={className} {...props}>
      {children}
    </label>
  );
}

export default Label;

// usage

// <Label htmlFor="username">Username:</Label>