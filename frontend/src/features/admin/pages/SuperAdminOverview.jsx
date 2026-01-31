import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { adminService } from '../../admin/services/adminService';
import { Button } from '../../../components/ui/Button'; 
import { Plus, Building2, MapPin, Users, Loader2 } from 'lucide-react';

const SuperAdminOverview = () => {
  
 const { 
    data: organizations = [], 
    isLoading
  } = useQuery({
    queryKey: ['organizations'],
    queryFn: adminService.getAllOrganizations,
    retry: 1, 
    refetchOnWindowFocus: false, 
  });
  


  if (isLoading) {
    return (
      <div className="h-96 flex flex-col items-center justify-center gap-4 text-gray-400">
        <Loader2 className="animate-spin text-brand-primary" size={40} />
        <p className="font-bold animate-pulse">Loading System Data...</p>
      </div>
    );
  }

  return (
    <div className="space-y-8">
      {/* Header */}
      <div className="flex justify-between items-end">
        <div>
          <h1 className="text-4xl font-black text-gray-900 tracking-tight">Super Admin</h1>
          <p className="text-gray-500 font-medium">Manage global organizations and system configurations.</p>
        </div>
        {/* <Button variant="primary">
          <Plus className="mr-2" size={20} /> Create Organization
        </Button> */}
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <StatCard icon={<Building2 />} label="Total Orgs" value={organizations.length} color="text-orange-500" />
        <StatCard icon={<MapPin />} label="Active Locations" value="12" color="text-blue-500" />
        <StatCard icon={<Users />} label="Pending Vendors" value="4" color="text-purple-500" />
      </div>

      {/* Table Section */}
      <div className="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-gray-50 border-b border-gray-100">
            <tr>
              <th className="p-6 font-bold text-gray-600">Organization Name</th>
              <th className="p-6 font-bold text-gray-600">ID</th>
              <th className="p-6 font-bold text-gray-600">Actions</th>
            </tr>
          </thead>
          <tbody>
            {organizations.map((org) => (
              <tr key={org.id} className="border-b border-gray-50 hover:bg-gray-50/50 transition-colors">
                <td className="p-6 font-semibold text-gray-800">{org.name}</td>
                <td className="p-6 text-gray-400 text-sm font-mono">{org.id}</td>
                <td className="p-6 flex gap-2">
                  <Button variant="secondary" size="sm">Manage</Button>
                  <Button variant="ghost" size="sm">Settings</Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

const StatCard = ({ icon, label, value, color }) => (
  <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-sm flex items-center gap-5">
    <div className={`p-4 rounded-2xl bg-gray-50 ${color}`}>{icon}</div>
    <div>
      <p className="text-sm font-bold text-gray-400 uppercase tracking-wider">{label}</p>
      <p className="text-2xl font-black text-gray-900">{value}</p>
    </div>
  </div>
);

export default SuperAdminOverview;