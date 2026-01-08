import { motion } from "framer-motion";

const PhoneMockup = () => {
  return (
    <div className="relative mx-auto border-gray-800 dark:border-gray-800 bg-gray-800 border-[14px] rounded-[2.5rem] h-[600px] w-[300px] shadow-xl">
      <div className="w-[148px] h-[18px] bg-gray-800 top-0 left-1/2 -translate-x-1/2 absolute rounded-b-[1rem] z-20"></div>
      <div className="h-[46px] w-[3px] bg-gray-800 absolute -left-[17px] top-[124px] rounded-l-lg"></div>
      <div className="h-[46px] w-[3px] bg-gray-800 absolute -left-[17px] top-[178px] rounded-l-lg"></div>
      <div className="h-[64px] w-[3px] bg-gray-800 absolute -right-[17px] top-[142px] rounded-r-lg"></div>
      
      <div className="rounded-[2rem] overflow-hidden w-full h-full bg-white dark:bg-gray-900">
        {/* Animated App UI inside phone */}
        <motion.div 
          initial={{ y: 0 }}
          animate={{ y: -300 }}
          transition={{ duration: 8, repeat: Infinity, repeatType: "reverse", ease: "easeInOut" }}
          className="p-4 space-y-4"
        >
          {[1, 2, 3, 4, 5, 6].map((item) => (
            <div key={item} className="h-32 w-full bg-orange-100 rounded-xl p-3">
              <div className="h-4 w-3/4 bg-orange-200 rounded mb-2" />
              <div className="h-20 w-full bg-white rounded-lg" />
            </div>
          ))}
        </motion.div>
      </div>
    </div>
  );
};

export default PhoneMockup;