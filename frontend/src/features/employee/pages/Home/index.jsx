import React from 'react';
import { Search, ShoppingBag } from 'lucide-react';
import { VendorList } from '../../components/VendorList';

export default function EmployeeHome() {
  return (
    <div className="max-w-md mx-auto md:max-w-4xl pb-20">
      <header className="p-4 flex justify-between items-center bg-white sticky top-0 z-10 shadow-sm">
        <div>
          <h1 className="text-xl font-bold text-primary">HungerBox</h1>
          <p className="text-xs text-gray-500">Corporate Cafeteria - Block A</p>
        </div>
        <div className="relative">
          <ShoppingBag className="w-6 h-6 text-gray-700" />
          <span className="absolute -top-1 -right-1 bg-primary text-white text-[10px] rounded-full w-4 h-4 flex items-center justify-center">0</span>
        </div>
      </header>

      <div className="p-4">
        <div className="relative mb-6">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
          <input 
            type="text" 
            placeholder="Search for food or stalls..." 
            className="w-full pl-10 pr-4 py-3 rounded-xl border-none bg-gray-100 focus:ring-2 focus:ring-primary outline-none"
          />
        </div>

        <h2 className="font-bold text-lg mb-4">Popular Stalls</h2>
        <VendorList />
      </div>
    </div>
  );
}