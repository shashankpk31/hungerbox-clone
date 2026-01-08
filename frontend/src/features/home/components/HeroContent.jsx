import { motion } from "framer-motion";
import { Utensils, Zap, ShieldCheck } from "lucide-react";

const HeroContent = ({ onAction }) => {
  const features = [
    { icon: <Zap className="text-orange-500" size={20} />, text: "Zero Wait Time" },
    { icon: <Utensils className="text-orange-500" size={20} />, text: "Multiple Vendors" },
    { icon: <ShieldCheck className="text-orange-500" size={20} />, text: "Secure Payments" },
  ];

  return (
    <div className="flex flex-col text-left">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <span className="bg-orange-100 text-orange-600 px-4 py-1.5 rounded-full text-sm font-semibold tracking-wide uppercase">
          Corporate Dining Simplified
        </span>
        
        <h1 className="mt-6 text-5xl font-extrabold text-gray-900 leading-tight">
          Fueling your <span className="text-orange-600">Workday</span> with one tap.
        </h1>
        
        <p className="mt-4 text-lg text-gray-600 leading-relaxed">
          The ultimate Hungerbox clone for modern workplaces. Whether you're an employee 
          craving a quick lunch or a vendor managing a kitchen, we've got you covered.
        </p>

        {/* Feature Pill Grid */}
        <div className="flex flex-wrap gap-4 mt-8">
          {features.map((f, i) => (
            <div key={i} className="flex items-center gap-2 bg-white shadow-sm border border-gray-100 px-3 py-2 rounded-lg">
              {f.icon}
              <span className="text-sm font-medium text-gray-700">{f.text}</span>
            </div>
          ))}
        </div>

        {/* Dynamic Action Buttons */}
        <div className="mt-10 flex items-center gap-4">
          <button
            onClick={onAction}
            className="bg-orange-600 hover:bg-orange-700 text-white px-8 py-4 rounded-xl font-bold text-lg shadow-lg shadow-orange-200 transition-all active:scale-95"
          >
            Get Started Now
          </button>
          
          <button 
             onClick={onAction} // Could also trigger login specifically
             className="bg-white border-2 border-gray-200 hover:border-orange-600 hover:text-orange-600 text-gray-700 px-8 py-4 rounded-xl font-bold text-lg transition-all"
          >
            View Demo
          </button>
        </div>
      </motion.div>
    </div>
  );
};

export default HeroContent;