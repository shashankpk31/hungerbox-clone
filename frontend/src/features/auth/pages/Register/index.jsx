import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import RegisterForm from '../../components/RegisterForm';
import { register } from '../../services/authService';

const RegisterPage = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleRegister = async (userData) => {
    setLoading(true);
    setError(null);
    try {
      await register(userData);
      // HungerBox often uses a success toast here
      navigate('/login'); 
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex flex-col justify-center bg-gray-50 py-12 px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        {/* You can add your HungerBox Clone Logo here */}
        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
          HungerBox Clone
        </h2>
        <p className="mt-2 text-center text-sm text-gray-600">
          Simplify your cafeteria experience
        </p>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10 border border-gray-100">
          {error && (
            <div className="mb-4 p-3 bg-red-50 text-red-700 text-sm rounded-md border border-red-200">
              {error}
            </div>
          )}
          
          <RegisterForm onSubmit={handleRegister} loading={loading} />

          <div className="mt-6 text-center">
            <span className="text-sm text-gray-600">Already using HungerBox? </span>
            <Link to="/login" className="text-sm font-medium text-blue-600 hover:text-blue-500">
              Sign in here
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;