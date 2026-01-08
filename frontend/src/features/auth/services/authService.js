import axiosInstance from '../../../api/axiosInstance';

export const register = async (userData) => {
  const response = await axiosInstance.post('/auth/register', userData);
  return response.data;
};