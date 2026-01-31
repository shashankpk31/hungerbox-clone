import { motion, AnimatePresence } from "framer-motion";
import { useState, useEffect } from "react";
import { User, Search, Flame, QrCode, CheckCircle2 } from "lucide-react";

const PhoneMockup = () => {
  const [step, setStep] = useState(0);
  const steps = [
    { text: "User Logging In...", icon: <User className="text-orange-500" /> },
    { text: "Browsing Menu...", icon: <Search className="text-orange-500" /> },
    { text: "Kitchen Preparing...", icon: <Flame className="text-orange-500" /> },
    { text: "Order Prepared!", icon: <CheckCircle2 className="text-green-500" /> },
    { text: "Scan QR to Pickup", icon: <QrCode className="text-gray-800" /> },
  ];

  useEffect(() => {
    const interval = setInterval(() => {
      setStep((prev) => (prev + 1) % steps.length);
    }, 3000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="relative border-gray-800 bg-gray-800 border-[10px] rounded-[2.5rem] h-[500px] w-[250px] shadow-xl overflow-hidden shrink-0">
      <div className="w-24 h-4 bg-gray-800 top-0 left-1/2 -translate-x-1/2 absolute rounded-b-xl z-20"></div>
      
      <div className="h-full w-full bg-white p-6 flex flex-col items-center justify-center text-center">
        <AnimatePresence mode="wait">
          <motion.div
            key={step}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            className="flex flex-col items-center gap-4"
          >
            <div className="p-4 bg-orange-50 rounded-2xl">
              {steps[step].icon}
            </div>
            <p className="font-bold text-gray-700 text-sm">{steps[step].text}</p>
          </motion.div>
        </AnimatePresence>

        {/* Mock UI elements */}
        <div className="absolute bottom-10 left-6 right-6 space-y-2">
          <div className="h-2 w-full bg-gray-100 rounded" />
          <div className="h-2 w-2/3 bg-gray-100 rounded" />
        </div>
      </div>
    </div>
  );
};

export default PhoneMockup;