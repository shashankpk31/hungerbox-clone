import { useQuery } from '@tanstack/react-query';
import api from '../../../api/axiosInstance';
import { Store } from 'lucide-react';

export const VendorList = () => {
  const { data: vendors, isLoading } = useQuery({
    queryKey: ['vendors'],
    queryFn: async () => {
      const res = await api.get('/api/catalog/vendors');
      return res.data;
    }
  });

  if (isLoading) return <div className="animate-pulse">Loading Stalls...</div>;

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-4">
      {vendors?.map(vendor => (
        <div key={vendor.id} className="border p-4 rounded-xl shadow-sm hover:shadow-md transition bg-white">
          <div className="flex items-center gap-3">
            <Store className="text-orange-500" />
            <h3 className="font-bold text-lg">{vendor.name}</h3>
          </div>
          <p className="text-gray-500 text-sm">{vendor.location}</p>
          <button className="mt-3 w-full bg-orange-500 text-white py-2 rounded-lg font-medium">
            View Menu
          </button>
        </div>
      ))}
    </div>
  );
};