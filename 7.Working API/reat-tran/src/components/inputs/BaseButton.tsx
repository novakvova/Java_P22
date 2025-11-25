import React from "react";

interface BaseButtonProps {
    children: React.ReactNode;
    onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
    className?: string;
    type?: "button" | "submit" | "reset";
    disabled?: boolean;
    id?: string;
}

const BaseButton: React.FC<BaseButtonProps> = ({
                                                   children,
                                                   onClick,
                                                   className,
                                                   type = "button",
                                                   disabled = false,
                                                   id
                                               }) => {
    return (
        <button
            id={id}
            type={type}
            disabled={disabled}
            onClick={onClick}
            className={`transition-colors disabled:opacity-50 disabled:cursor-not-allowed ${className}`}
        >
            {children}
        </button>
    );
};

export default BaseButton;
