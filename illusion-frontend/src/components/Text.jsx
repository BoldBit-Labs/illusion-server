import React from 'react';

const baseClasses = {
    default: '',
    headline: 'text-2xl font-bold',
    subheadline: 'text-xl font-semibold',
    body: 'text-base',
    caption: 'text-sm',
};

const sizeClasses = {
    base: '',
    small: 'text-sm',
    large: 'text-lg',
    xl: 'text-xl',
    xxl: 'text-2xl',
};

const weightClasses = {
    regular: '',
    bold: 'font-bold',
    semibold: 'font-semibold',
    medium: 'font-medium',
    light: 'font-light',
};

const alignmentClasses = {
    left: '',
    center: 'text-center',
    right: 'text-right',
    justify: 'justify-content-start',
};

function Text({
    children,
    variant = 'default',
    size = 'base',
    color = 'neutral-900',
    weight = 'regular',
    align = 'left',
    className,
    tag = 'span',
    ...props
}) {
    const textClasses = `${baseClasses[variant]} ${sizeClasses[size]} ${weightClasses[weight]} ${alignmentClasses[align]} ${className}`;
    const Tag = tag;

    return (
        <Tag className={textClasses} {...props}>
            {children}
        </Tag>
    );
}

export default Text;


    // <Text tag="h1" variant="headline">This is a headline</Text>
    // <Text tag="p" variant="body">
    //     This is a body text with nested <Text tag="span" variant="caption">caption</Text> text.
    // </Text>
    // <Text tag="span" size="large" color="blue" weight="bold">A large, blue, bold text.</Text> 
