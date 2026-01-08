import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import PhoneMockup from "../../../components/ui/PhoneMockup";
import RegisterForm from "../../auth/components/RegisterForm";
import LoginForm from "../../auth/components/LoginForm"; // Ensure this is imported
import HeroContent from "../components/HeroContent";

const LandingPage = () => {
  // 'hero', 'login', or 'register'
  const [view, setView] = useState("hero");

  return (
    <div className="min-h-screen bg-gradient-to-br from-orange-50 via-white to-orange-50 overflow-hidden">
      {/* Transparent Navbar */}
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

      <div className="container mx-auto flex flex-col lg:flex-row items-center justify-between min-h-screen pt-20 px-6 gap-12">
        
        {/* Left Side: Animated Mockup */}
        <div className="lg:w-1/2 flex justify-center items-center">
          <PhoneMockup />
        </div>

        {/* Right Side: Animated Content/Form Swap */}
        <div className="lg:w-1/2 flex justify-center lg:justify-start w-full">
          <div className="w-full max-w-md bg-white/40 backdrop-blur-sm p-2 rounded-3xl">
            <AnimatePresence mode="wait">
              {view === "hero" && (
                <motion.div 
                  key="hero"
                  initial={{ opacity: 0, x: 40 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -40 }}
                  transition={{ duration: 0.4 }}
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
                >
                   {/* Pass setView to form so it can switch to 'login' after success */}
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