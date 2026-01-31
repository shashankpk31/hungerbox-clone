import api from '../../../api/axiosInstance';
import { API_PATHS } from '../../../config/constants';

export const adminService = {  
  getAllOrganizations: async () => {
    return await api.get(`${API_PATHS.ORG_SERVICE}`);
  },

  createOrganization: async (orgData) => {
    return await api.post(`${API_PATHS.ORG_SERVICE}`, orgData);
  },

  // --- User & Admin Provisioning ---
  createOrgAdmin: async (adminData) => {
    // Expects: { email, password, organizationId }
    // Backend logic assigns ROLE_ORG_ADMIN based on this endpoint
    return await api.post(`${API_PATHS.IDENTITY_AUTH}/register/org-admin`, adminData);
  },

  // --- Regional / System Config ---
  createCity: async (cityData) => {
    return await api.post(`${API_PATHS.VENDOR_SERVICE}/admin/cities`, cityData);
  },

  // --- Approval Workflow (Shared or Org-Admin specific) ---
  getPendingApprovals: async () => {
    return await api.get(`${API_PATHS.IDENTITY_AUTH}/admin/pending-approvals`);
  },

  updateUserStatus: async (userId, status) => {
    return await api.put(`${API_PATHS.IDENTITY_AUTH}/admin/users/${userId}/status`, { status });
  }
};