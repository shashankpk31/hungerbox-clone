import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import Input from "../../../components/ui/Input";
import Button from "../../../components/ui/Button";
import AuthToggle from "../../../components/ui/Toggle/AuthToggle";
import EmployeeFields from "./forms/EmployeeFields";
import VendorFields from "./forms/VendorFields";
import { register } from "../services/authService";

const RegisterForm = ({ onSwitchToLogin, onBack }) => {
  const navigate = useNavigate();
  const [role, setRole] = useState("ROLE_EMPLOYEE"); 
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const payload = { ...formData, role };
    
    try {
      await register(payload);
      // Registration successful, send them to login
      onSwitchToLogin(); 
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full max-w-md">
      <div className="flex items-center justify-between mb-6">
        <button onClick={onBack} className="p-2 hover:bg-gray-100 rounded-full">
          <ArrowLeft size={20} className="text-gray-600" />
        </button>
        <AuthToggle activeRole={role} setRole={setRole} />
      </div>

      <div className="mb-6">
        <h2 className="text-3xl font-bold text-gray-900">Create Account</h2>
        <p className="text-gray-500 mt-1">Join HungerBox as a {role === "ROLE_EMPLOYEE" ? "Employee" : "Vendor"}.</p>
      </div>

      {error && <div className="mb-4 p-3 bg-red-50 text-red-700 text-sm rounded-md">{error}</div>}

      <form onSubmit={handleSubmit} className="space-y-3">
        <Input label="Full Name" name="username" placeholder="John Doe" onChange={handleInputChange} required />
        <Input label="Email Address" name="email" type="email" placeholder="john@company.com" onChange={handleInputChange} required />
        <Input label="Password" name="password" type="password" placeholder="••••••••" onChange={handleInputChange} required />

        <AnimatePresence mode="wait">
          <motion.div
            key={role}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
          >
            {role === "ROLE_EMPLOYEE" ? (
              <EmployeeFields onDataChange={handleInputChange} />
            ) : (
              <VendorFields onDataChange={handleInputChange} />
            )}
          </motion.div>
        </AnimatePresence>

        <div className="pt-4">
          <Button type="submit" className="w-full">
            {loading ? "Creating Account..." : "Register Now"}
          </Button>
        </div>
      </form>

      <p className="mt-6 text-center text-gray-600">
        Already have an account?{" "}
        <button onClick={onSwitchToLogin} className="text-orange-600 font-bold hover:underline">
          Sign In
        </button>
      </p>
    </div>
  );
};

export default RegisterForm;