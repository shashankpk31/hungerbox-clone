import React from "react";
import { Outlet, useNavigate, useLocation } from "react-router-dom"; // Added useLocation
import { LogOut } from "lucide-react";
import { UI_CONFIG } from "../config/constants";

const DashboardLayout = ({ navigationLinks, brandName = "HungerBox" }) => {
  const navigate = useNavigate();
  const location = useLocation(); // Initialize location

  const handleNavClick = (path) => {
    // GUARD: If already on the path, stop navigation to prevent recursion crash
    if (location.pathname === path) return; 
    navigate(path);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/", { replace: true });
  };

  return (
    <div className="flex h-screen bg-surface-bg">
      <aside className="w-72 bg-gray-900 text-white flex flex-col shadow-2xl">
        <div className="p-8 text-2xl font-black text-brand-primary tracking-tighter border-b border-gray-800">
          {brandName}
        </div>

        <nav className="flex-1 p-6 space-y-3">
          {navigationLinks.map((link) => {
            const isActive = location.pathname === link.path;
            return (
              <button
                key={link.path}
                onClick={() => handleNavClick(link.path)} // USE THE GUARDED FUNCTION
                className={`w-full flex items-center gap-4 p-4 rounded-2xl transition-all group ${
                  isActive ? "bg-brand-primary text-white" : "hover:bg-gray-800 text-gray-400"
                }`}
              >
                <span className={isActive ? "text-white" : "group-hover:text-brand-primary"}>
                  {link.icon}
                </span>
                <span className={`font-semibold ${isActive ? "text-white" : "text-gray-300"}`}>
                  {link.label}
                </span>
              </button>
            );
          })}
        </nav>

        <button onClick={handleLogout} className="...">
          <LogOut size={20} /> Logout
        </button>
      </aside>

      <main className="flex-1 overflow-y-auto scrollbar-hide">
        <div className={UI_CONFIG.PAGE_PADDING}>
          <Outlet />
        </div>
      </main>
    </div>
  );
};

export default DashboardLayout;