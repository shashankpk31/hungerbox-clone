import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import PhoneMockup from "../../../components/ui/PhoneMockup";
import RegisterForm from "../../auth/components/RegisterForm";
import LoginForm from "../../auth/components/LoginForm";
import HeroContent from "../components/HeroContent";
import { useNavigate } from "react-router-dom";
import { LOCL_STRG_KEY, ROLES } from "../../../config/constants";

const LandingPage = () => {
  const [view, setView] = useState("hero");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem(LOCL_STRG_KEY.TOKEN);
    const storedUser = localStorage.getItem(LOCL_STRG_KEY.USER);

    // Use window.location.pathname check to prevent loop
    if (token && storedUser && window.location.pathname === "/") {
      try {
        const user = JSON.parse(storedUser);
        const routes = {
          [ROLES.SUPER_ADMIN]: "/admin/dashboard", // Fixed spelling
          [ROLES.ORG_ADMIN]: "/org-admin/dashboard", // Fixed spelling
          [ROLES.VENDOR]: "/vendor/dashboard",
          [ROLES.EMPLOYEE]: "/home",
        };

        const targetPath = routes[user.role] || "/";
        navigate(targetPath, { replace: true }); // Use replace: true
      } catch (e) {
        localStorage.clear(); // Clear corrupt data
      }
    }
  }, [navigate]);

  return (
    <div className="h-screen w-full bg-gradient-to-br from-orange-50 via-white to-orange-50 overflow-hidden flex flex-col">
      {/* Navbar */}
      <nav className="flex justify-between items-center p-6 bg-transparent absolute w-full z-50">
        <motion.h1
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-2xl font-bold text-orange-600 cursor-pointer"
          onClick={() => setView("hero")}
        >
          HungerBox
        </motion.h1>

        <div className="flex items-center space-x-6">
          <button
            onClick={() => setView("login")}
            className="text-gray-700 font-semibold hover:text-orange-600 transition-colors"
          >
            Login
          </button>
          <button
            onClick={() => setView("register")}
            className="bg-orange-600 hover:bg-orange-700 text-white px-6 py-2.5 rounded-full font-bold shadow-lg shadow-orange-200 transition-all active:scale-95"
          >
            Join Now
          </button>
        </div>
      </nav>

      {/* Main Container */}
      <div className="container mx-auto flex flex-col lg:flex-row items-center justify-between h-full pt-20 px-6 gap-12 overflow-hidden">
        {/* Mockup Section */}
        <div className="lg:w-1/2 flex justify-center items-center h-full max-h-[85vh]">
          <PhoneMockup />
        </div>

        {/* Dynamic Form Section */}
        <div className="lg:w-1/2 flex justify-center lg:justify-start w-full h-full items-center overflow-hidden">
          <div className="w-full max-w-md bg-white/40 backdrop-blur-sm p-4 rounded-3xl max-h-[80vh] overflow-y-auto scrollbar-hide">
            <AnimatePresence mode="wait">
              {view === "hero" && (
                <motion.div
                  key="hero"
                  initial={{ opacity: 0, x: 40 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -40 }}
                  className="scrollbar-hide"
                >
                  <HeroContent onAction={() => setView("register")} />
                </motion.div>
              )}

              {view === "register" && (
                <motion.div
                  key="register"
                  initial={{ opacity: 0, scale: 0.95 }}
                  animate={{ opacity: 1, scale: 1 }}
                  exit={{ opacity: 0, scale: 0.95 }}
                  className="scrollbar-hide"
                >
                  <RegisterForm
                    onSwitchToLogin={() => setView("login")}
                    onBack={() => setView("hero")}
                  />
                </motion.div>
              )}

              {view === "login" && (
                <motion.div
                  key="login"
                  initial={{ opacity: 0, scale: 0.95 }}
                  animate={{ opacity: 1, scale: 1 }}
                  exit={{ opacity: 0, scale: 0.95 }}
                  className="scrollbar-hide"
                >
                  <LoginForm
                    onSwitchToRegister={() => setView("register")}
                    onBack={() => setView("hero")}
                  />
                </motion.div>
              )}
            </AnimatePresence>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
