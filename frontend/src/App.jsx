import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./routes/ProtectedRoute";
import EmployeeHome from "./features/employee/pages/Home";
import KitchenDisplay from "./features/vendor/pages/LiveOrders";
import Login from "./features/auth/pages/Login";

function App() {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<Login />} />

      {/* Employee Routes */}
      <Route element={<ProtectedRoute allowedRoles={['EMPLOYEE']} />}>
        <Route path="/" element={<EmployeeHome />} />
        {/* Add more employee routes here */}
      </Route>

      {/* Vendor Routes */}
      <Route element={<ProtectedRoute allowedRoles={['VENDOR']} />}>
        <Route path="/vendor/dashboard" element={<KitchenDisplay />} />
      </Route>

      {/* Admin Routes */}
      <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
        <Route path="/admin" element={<div>Admin Dashboard</div>} />
      </Route>
    </Routes>
  );
}

export default App;