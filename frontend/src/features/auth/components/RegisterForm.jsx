import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ArrowLeft, User, Mail, Lock, Briefcase, Store } from "lucide-react";
import Input from "../../../components/ui/Input";
import Button from "../../../components/ui/Button";
import AuthToggle from "../../../components/ui/Toggle/AuthToggle";
import EmployeeFields from "./forms/EmployeeFields";
import VendorFields from "./forms/VendorFields";
import { useAuth } from "../../../context/AuthContext";
import { register } from "../services/authService";

const RegisterForm = ({ onSwitchToLogin, onBack }) => {
  const [role, setRole] = useState("EMPLOYEE"); // 'EMPLOYEE' or 'VENDOR'
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    // Role specific fields will be merged here
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // payload constructed as per our RegisterRequest.java record
    const payload = {
      ...formData,
      role,
      // These might be undefined if not visible, which is fine for the record
      employeeId: formData.employeeId,
      companyName: formData.companyName,
      shopName: formData.shopName,
      gstNumber: formData.gstNumber,
    };

    try {
      // 1. Call your API
      const response = await register(payload);

      if (response.success) {
        // 2. Since your requirement says redirect to login screen on success:
        alert("Registration Successful! Please login.");
        onSwitchToLogin();
      }
    } catch (error) {
      console.error(
        "Registration failed",
        error.response?.data?.message || error.message
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full max-w-md">
      {/* Header & Back Action */}
      <div className="flex items-center justify-between mb-6">
        <button
          onClick={onBack}
          className="p-2 hover:bg-gray-100 rounded-full transition-colors"
        >
          <ArrowLeft size={20} className="text-gray-600" />
        </button>
        <AuthToggle activeRole={role} setRole={setRole} />
      </div>

      <div className="mb-6">
        <h2 className="text-3xl font-bold text-gray-900">Create Account</h2>
        <p className="text-gray-500 mt-1">
          Join HungerBox as a {role.toLowerCase()}.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-1">
        {/* Common Fields */}
        <Input
          label="Full Name"
          name="username"
          placeholder="John Doe"
          onChange={handleInputChange}
          required
        />
        <Input
          label="Email Address"
          name="email"
          type="email"
          placeholder="john@company.com"
          onChange={handleInputChange}
          required
        />
        <Input
          label="Password"
          name="password"
          type="password"
          placeholder="••••••••"
          onChange={handleInputChange}
          required
        />

        {/* Dynamic Fields based on Toggle */}
        <AnimatePresence mode="wait">
          <motion.div
            key={role}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ duration: 0.2 }}
          >
            {role === "EMPLOYEE" ? (
              <EmployeeFields onDataChange={handleInputChange} />
            ) : (
              <VendorFields onDataChange={handleInputChange} />
            )}
          </motion.div>
        </AnimatePresence>

        <div className="pt-4">
          <Button type="submit">
            {loading ? "Creating Account..." : "Register Now"}
          </Button>
        </div>
      </form>

      <p className="mt-6 text-center text-gray-600">
        Already have an account?{" "}
        <button
          onClick={onSwitchToLogin}
          className="text-orange-600 font-bold hover:underline"
        >
          Sign In
        </button>
      </p>
    </div>
  );
};

export default RegisterForm;
