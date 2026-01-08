import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import LandingPage from "../features/home/pages/LandingPage";
import AdminRegister from "../features/auth/pages/AdminRegister";
import EmployeeHome from "../features/employee/pages/Home";
import KitchenDisplay from "../features/vendor/pages/LiveOrders";
import Login from "../features/auth/pages/Login"; 

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/admin-registration" element={<AdminRegister />} />

      {/* Employee Protected Routes */}
      <Route element={<ProtectedRoute allowedRoles={['EMPLOYEE']} />}>
        <Route path="/home" element={<EmployeeHome />} />
      </Route>

      {/* Vendor Protected Routes */}
      <Route element={<ProtectedRoute allowedRoles={['VENDOR']} />}>
        <Route path="/vendor/dashboard" element={<KitchenDisplay />} />
      </Route>

      {/* Admin Protected Routes */}
      <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
        <Route path="/admin" element={<div>Admin Dashboard</div>} />
      </Route>
    </Routes>
  );
};

export default AppRoutes;