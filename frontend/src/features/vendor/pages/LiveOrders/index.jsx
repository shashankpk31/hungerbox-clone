import React, { useState } from 'react';
import { Clock, CheckCircle } from 'lucide-react';

export default function KitchenDisplay() {
  const [orders, setOrders] = useState([
    { id: '101', item: 'Masala Dosa', user: 'Rahul K.', status: 'Cooking' },
    { id: '102', item: 'Chicken Biryani', user: 'Sneha S.', status: 'Pending' }
  ]);

  return (
    <div className="p-6 bg-slate-900 min-h-screen text-white">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-2xl font-bold uppercase tracking-widest">Live Kitchen Queue</h1>
        <div className="bg-green-500 px-4 py-1 rounded-full text-sm animate-pulse">Live</div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {orders.map(order => (
          <div key={order.id} className="bg-slate-800 rounded-2xl p-5 border-l-4 border-orange-500 shadow-xl">
            <div className="flex justify-between mb-4">
              <span className="text-slate-400 text-sm">#{order.id}</span>
              <span className="flex items-center gap-1 text-orange-400 text-sm">
                <Clock className="w-4 h-4" /> 12 mins ago
              </span>
            </div>
            <h3 className="text-xl font-bold mb-1">{order.item}</h3>
            <p className="text-slate-400 mb-6">{order.user}</p>
            
            <div className="flex gap-2">
              <button className="flex-1 bg-slate-700 hover:bg-slate-600 py-3 rounded-xl transition font-medium">
                Reject
              </button>
              <button className="flex-1 bg-primary hover:bg-orange-600 py-3 rounded-xl transition font-bold flex items-center justify-center gap-2">
                <CheckCircle className="w-5 h-5" /> Ready
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}