import api from '../../../api/axiosInstance';

export const orderService = {
  placeOrder: async (orderData) => {
    // orderData: { vendorId, items: [...], totalAmount }
    const response = await api.post('/api/orders/place', orderData);
    return response.data;
  },
  
  trackOrder: async (orderId) => {
    const response = await api.get(`/api/orders/${orderId}/track`);
    return response.data;
  }
};