import { Routes, Route,Navigate } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import LandingPage from "../features/home/pages/LandingPage";
import AdminRegister from "../features/auth/pages/AdminRegister";
import EmployeeHome from "../features/employee/pages/Home";
import KitchenDisplay from "../features/vendor/pages/LiveOrders";
import Login from "../features/auth/pages/Login"; 

const AppRoutes = () => {
  return (
    <Routes>
      {/* 1. Public Routes: Accessible only if NOT logged in */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/admin-registration" element={<AdminRegister />} />

      {/* 2. Employee Routes */}
      <Route element={<ProtectedRoute allowedRoles={['ROLE_EMPLOYEE']} />}>
        <Route path="/home" element={<EmployeeHome />} />
      </Route>

      {/* 3. Vendor Routes */}
      <Route element={<ProtectedRoute allowedRoles={['ROLE_VENDOR']} />}>
        <Route path="/vendor/dashboard" element={<KitchenDisplay />} />
      </Route>

      {/* 4. Admin Routes */}
      <Route element={<ProtectedRoute allowedRoles={['ROLE_ADMIN']} />}>
        <Route path="/admin" element={<div>Admin Dashboard</div>} />
      </Route>

      {/* 5. Fallback: If route doesn't exist */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default AppRoutes;