import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import PhoneMockup from "../../../components/ui/PhoneMockup";
import RegisterForm from "../../auth/components/RegisterForm";
import LoginForm from "../../auth/components/LoginForm";
import AccountVerifyForm from "../../auth/components/AccountVerifyForm"; 
import HeroContent from "../components/HeroContent";
import { useNavigate } from "react-router-dom";
import { LOCL_STRG_KEY, ROLES, LANDING_PAGE_VIEW } from "../../../config/constants";
import { scaleAnimation, fadeAnimation } from "../../../config/animations";

const LandingPage = () => {
  const [view, setView] = useState(LANDING_PAGE_VIEW.HOME);
  const [pendingIdentifier, setPendingIdentifier] = useState(""); 
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem(LOCL_STRG_KEY.TOKEN);
    const storedUser = localStorage.getItem(LOCL_STRG_KEY.USER);

    if (token && storedUser && window.location.pathname === "/") {
      try {
        const user = JSON.parse(storedUser);
        const routes = {
          [ROLES.SUPER_ADMIN]: "/admin/dashboard",
          [ROLES.ORG_ADMIN]: "/org-admin/dashboard",
          [ROLES.VENDOR]: "/vendor/dashboard",
          [ROLES.EMPLOYEE]: "/home",
        };

        const targetPath = routes[user.role] || "/";
        navigate(targetPath, { replace: true });
      } catch (e) {
        localStorage.clear();
      }
    }
  }, [navigate]);

  const handleRegistrationSuccess = (identifier) => {
    setPendingIdentifier(identifier);
    setView(LANDING_PAGE_VIEW.ACC_VERIFY);
  };

  return (
    <div className="h-screen w-full bg-gradient-to-br from-orange-50 via-white to-orange-50 overflow-hidden flex flex-col">
      {/* Navbar */}
      <nav className="flex justify-between items-center p-6 bg-transparent absolute w-full z-50">
        <motion.h1
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-2xl font-bold text-orange-600 cursor-pointer"
          onClick={() => setView(LANDING_PAGE_VIEW.HOME)}
        >
          HungerBox
        </motion.h1>

        <div className="flex items-center space-x-6">
          <button
            onClick={() => setView(LANDING_PAGE_VIEW.LOGIN)}
            className="text-gray-700 font-semibold hover:text-orange-600 transition-colors"
          >
            Login
          </button>
          <button
            onClick={() => setView(LANDING_PAGE_VIEW.REGISTER)}
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
              {/* HERO / HOME VIEW */}
              {view === LANDING_PAGE_VIEW.HOME && (
                <motion.div
                  key={LANDING_PAGE_VIEW.HOME}
                  {...fadeAnimation}
                >
                  <HeroContent onAction={() => setView(LANDING_PAGE_VIEW.REGISTER)} />
                </motion.div>
              )}

              {/* REGISTER VIEW */}
              {view === LANDING_PAGE_VIEW.REGISTER && (
                <motion.div
                  key={LANDING_PAGE_VIEW.REGISTER}
                  {...scaleAnimation}
                >
                  <RegisterForm
                    onSwitchToLogin={() => setView(LANDING_PAGE_VIEW.LOGIN)}
                    onBack={() => setView(LANDING_PAGE_VIEW.HOME)}
                    onSuccess={handleRegistrationSuccess} 
                  />
                </motion.div>
              )}

              {/* ACCOUNT VERIFY VIEW */}
              {view === LANDING_PAGE_VIEW.ACC_VERIFY && (
                <motion.div
                  key={LANDING_PAGE_VIEW.ACC_VERIFY}
                  {...scaleAnimation}
                >
                  <AccountVerifyForm
                    identifier={pendingIdentifier}
                    onSuccess={() => setView(LANDING_PAGE_VIEW.LOGIN)}
                    onBack={() => setView(LANDING_PAGE_VIEW.REGISTER)}
                  />
                </motion.div>
              )}

              {/* LOGIN VIEW */}
              {view === LANDING_PAGE_VIEW.LOGIN && (
                <motion.div 
                  key={LANDING_PAGE_VIEW.LOGIN} 
                  {...scaleAnimation}
                >
                  <LoginForm
                    onSwitchToRegister={() => setView(LANDING_PAGE_VIEW.REGISTER)}
                    onBack={() => setView(LANDING_PAGE_VIEW.HOME)}
                    onUnverified={(identifier) => {
                      setPendingIdentifier(identifier); 
                      setView(LANDING_PAGE_VIEW.ACC_VERIFY); 
                    }}
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