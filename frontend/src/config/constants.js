export const API_PATHS = {
  IDENTITY_AUTH: '/auth',
  VENDOR_SERVICE: '/vendor',
  ORG_SERVICE: '/organization',
};

export const ROLES = {
  SUPER_ADMIN: 'ROLE_SUPER_ADMIN', // Manages Orgs & Locations
  ORG_ADMIN: 'ROLE_ORG_ADMIN',     // Manages Vendors/Employees within Org
  VENDOR: 'ROLE_VENDOR',           // Manages Kitchen & Menu
  EMPLOYEE: 'ROLE_EMPLOYEE',       // Orders Food
};

export const USER_STATUS = {
  PENDING: 'PENDING_APPROVAL', // Vendor to be approved by Org Admin
  ACTIVE: 'ACTIVE',
  BLOCKED: 'BLOCKED',
};


export const AUTH_CONST = {
  TOKEN: "hb_token",
  USER:"hb_user"
};

export const LOCL_STRG_KEY = {
  TOKEN: "hb_token",
  USER:"hb_user"
};


export const UI_CONFIG = {
  CARD_STYLE: "bg-white rounded-hb border border-surface-border shadow-sm",
  PAGE_PADDING: "p-6 lg:p-10",
};


export const LANDING_PAGE_VIEW = {
  HOME: 'hero', 
  LOGIN: 'login',    
  REGISTER: 'register', 
  ACC_VERIFY: 'verify',      
};