import React from 'react';

function Button({ children, onClick, type = "button", variant = "primary", className = "" }) {
  const baseStyles = "w-full py-3 px-6 rounded-xl font-bold transition-all active:scale-95 flex justify-center items-center gap-2";
  const variants = {
    primary: "bg-orange-600 text-white shadow-lg shadow-orange-200 hover:bg-orange-700",
    outline: "bg-white border-2 border-gray-200 text-gray-700 hover:border-orange-600 hover:text-orange-600"
  };

  return (
    <button 
      type={type} 
      onClick={onClick} 
      className={`${baseStyles} ${variants[variant]} ${className}`}
    >
      {children}
    </button>
  );
}

export default Button;