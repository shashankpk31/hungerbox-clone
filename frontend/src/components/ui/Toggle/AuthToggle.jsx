import React from 'react';
import { motion } from 'framer-motion';

const AuthToggle = ({ activeRole, setRole }) => {
  return (
    <div className="relative flex items-center w-64 h-11 bg-gray-100 rounded-full p-1 shadow-inner border border-gray-200">
      {/* Sliding Background Pill */}
      <motion.div
        className="absolute top-1 bottom-1 left-1 w-[calc(50%-4px)] bg-white rounded-full shadow-md z-0"
        initial={false}
        animate={{
          x: activeRole === "ROLE_EMPLOYEE" ? 0 : "100%",
        }}
        transition={{ type: "spring", stiffness: 400, damping: 30 }}
      />

      {/* Employee Button */}
      <button
        type="button"
        onClick={() => setRole("ROLE_EMPLOYEE")}
        className={`relative z-10 flex-1 text-sm font-bold transition-colors duration-200 ${
          activeRole === "ROLE_EMPLOYEE" ? "text-orange-600" : "text-gray-500"
        }`}
      >
        Employee
      </button>

      {/* Vendor Button */}
      <button
        type="button"
        onClick={() => setRole("ROLE_VENDOR")}
        className={`relative z-10 flex-1 text-sm font-bold transition-colors duration-200 ${
          activeRole === "ROLE_VENDOR" ? "text-orange-600" : "text-gray-500"
        }`}
      >
        Vendor
      </button>
    </div>
  );
};

export default AuthToggle;