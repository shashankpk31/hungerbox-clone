import React from 'react';
import LoginForm from '../../components/LoginForm';

const Login = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="max-w-md w-full p-6">
        <LoginForm 
          onSwitchToRegister={() => window.location.href = '/'} 
          onBack={() => window.location.href = '/'} 
        />
      </div>
    </div>
  );
};

export default Login; 