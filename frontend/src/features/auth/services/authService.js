import axiosInstance from '../../../api/axiosInstance';

export const register = async (userData) => {
  const response = await axiosInstance.post('/auth/register', userData);
  return response
};

export const login = async (credentials) => {
  const response = await axiosInstance.post('/auth/login', credentials);  
  return response
};