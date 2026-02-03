import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import { ROLES } from "../config/constants";
import { LayoutDashboard, Building2, MapPin, CheckCircle } from "lucide-react";

// Layouts
import DashboardLayout from "../layouts/DashboardLayout";

import LandingPage from "../features/home/pages/LandingPage";
import SuperAdminOverview from "../features/admin/pages/SuperAdminOverview"; // Moved from super-admin to admin
import OrganizationManager from "../features/admin/pages/OrganizationManager"; // Moved to admin
import ApprovalDashboard from "../features/org-admin/pages/ApprovalDashboard";
import OrgDashboard from "../features/org-admin/pages/OrgDashboard";


const AppRoutes = () => {
  const superAdminNav = [
    {
      label: "Dashboard",
      path: "/admin/dashboard",
      icon: <LayoutDashboard size={20} />,
    },
    {
      label: "Organizations",
      path: "/admin/organizations",
      icon: <Building2 size={20} />,
    },
  ];

  const orgAdminNav = [
    {
      label: "Organisation Dashboard",
      path: "/org-admin/approvals",
      icon: <CheckCircle size={20} />,
    },
    {
      label: "Pending Approvals",
      path: "/org-admin/approvals",
      icon: <CheckCircle size={20} />,
    },
    {
      label: "Locations",
      path: "/admin/locations",
      icon: <MapPin size={20} />,
    },
  ];

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<LandingPage />} />

      {/* Super Admin Module (Role: ROLE_SUPER_ADMIN) */}
      <Route element={<ProtectedRoute allowedRoles={[ROLES.SUPER_ADMIN]} />}>
        <Route
          element={
            <DashboardLayout
              navigationLinks={superAdminNav}
              brandName="BiteDash Super"
            />
          }
        >
          <Route path="admin/dashboard" element={<SuperAdminOverview />} />
          <Route path="admin/organizations" element={<OrganizationManager />} />
          {/* Default redirect for the module */}
          <Route
            path="admin"
            element={<Navigate to="/admin/dashboard" replace />}
          />
        </Route>
      </Route>

      {/* Org Admin Module (Role: ROLE_ORG_ADMIN) */}
      <Route element={<ProtectedRoute allowedRoles={[ROLES.ORG_ADMIN]} />}>
        <Route
          element={
            <DashboardLayout
              navigationLinks={orgAdminNav}
              brandName="BiteDash Org"
            />
          }
        >
          <Route path="org-admin/dashboard" element={<OrgDashboard />} />

          <Route path="org-admin/approvals" element={<ApprovalDashboard />} />
          <Route
            path="org-admin"
            element={<Navigate to="/org-admin/dashboard" replace />}
          />
        </Route>
      </Route>

      {/* Fallback for undefined routes */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default AppRoutes;
