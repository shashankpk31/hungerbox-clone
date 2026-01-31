import axios from 'axios';
import toast from 'react-hot-toast';
import { ERR } from '../config/errMsgConstants';
import { LOCL_STRG_KEY } from '../config/constants';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8089',
  headers: { 'Content-Type': 'application/json' }
});

api.interceptors.response.use(
  (response) => response.data.success ? response.data.data : Promise.reject(response.data.message),
  (error) => {
    const message = error.response?.data?.message || ERR.GENERIC_ERROR;
    
    // Auto-toast for specific system errors
    if (error.response?.status === 403) {
      toast.error("Access Denied: You don't have permission.");
    }
    
    return Promise.reject(message);
  }
);

api.interceptors.request.use((config) => {
  const token = localStorage.getItem(LOCL_STRG_KEY.TOKEN);
  
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});


export default api;