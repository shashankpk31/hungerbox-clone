import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { ArrowLeft, Lock, Mail } from 'lucide-react';
import Input from '../../../components/ui/Input';
import Button from '../../../components/ui/Button';

const LoginForm = ({ onSwitchToRegister, onBack }) => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    // Simulate API Call
    try {
      console.log("Logging in with:", formData);
      // await authService.login(formData);
    } catch (error) {
      console.error("Login failed", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full">
      {/* Back Button */}
      <button 
        onClick={onBack}
        className="flex items-center text-gray-500 hover:text-orange-600 transition-colors mb-6 group"
      >
        <ArrowLeft size={18} className="mr-2 group-hover:-translate-x-1 transition-transform" />
        <span className="text-sm font-medium">Back to Home</span>
      </button>

      <div className="mb-8">
        <h2 className="text-3xl font-bold text-gray-900">Welcome Back</h2>
        <p className="text-gray-500 mt-2">Enter your details to access your HungerBox account.</p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-2">
        <Input 
          label="Business Email"
          name="email"
          type="email"
          placeholder="name@company.com"
          value={formData.email}
          onChange={handleChange}
          required
        />

        <Input 
          label="Password"
          name="password"
          type="password"
          placeholder="••••••••"
          value={formData.password}
          onChange={handleChange}
          required
        />

        <div className="flex justify-end mb-6">
          <button type="button" className="text-sm font-semibold text-orange-600 hover:text-orange-700">
            Forgot Password?
          </button>
        </div>

        <Button 
          type="submit" 
          variant="primary" 
          className="h-14"
        >
          {loading ? "Authenticating..." : "Sign In"}
        </Button>
      </form>

      <div className="mt-8 text-center">
        <p className="text-gray-600">
          Don't have an account?{' '}
          <button 
            onClick={onSwitchToRegister}
            className="text-orange-600 font-bold hover:underline underline-offset-4"
          >
            Create one for free
          </button>
        </p>
      </div>
    </div>
  );
};

export default LoginForm;