
import React, { useState, useEffect, useMemo } from "react";
import { Link, useLocation } from "react-router-dom";
import { createPageUrl } from "../src/utils";
import { Waves, LayoutDashboard, AlertCircle, Map, Activity, Settings, LogOut, Database, Loader2 } from "lucide-react";
import { Dam } from "../src/entities/Dam";

export default function Layout({ children, currentPageName }) {
  const location = useLocation();
  const [dams, setDams] = useState([]);
  const [loading, setLoading] = useState(true);

  // Array of Indian states to filter the data
  const indianStates = ["Uttarakhand", "Himachal Pradesh", "Gujarat", "Odisha", "Telangana", "Kerala", "Maharashtra", "Madhya Pradesh", "Karnataka", "Punjab", "Rajasthan", "Uttar Pradesh", "West Bengal", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Haryana", "Jammu and Kashmir", "Jharkhand", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Sikkim", "Tripura", "Andhra Pradesh", "Tamil Nadu"];

  useEffect(() => {
    const loadDams = async () => {
      setLoading(true);
      try {
        const allDams = await Dam.list();
        const indianDams = allDams.filter(dam => indianStates.includes(dam.state));
        setDams(indianDams);
      } catch (error) {
        console.error("Failed to load dams:", error);
        setDams([]); // Ensure dams is an empty array on error
      } finally {
        setLoading(false);
      }
    };
    loadDams();
  }, []);

  const stats = useMemo(() => {
    if (loading) return { total: 0, critical: 0, warning: 0, normal: 0, normalPercent: 0 };
    const total = dams.length;
    const critical = dams.filter(d => d.status === 'Critical').length;
    const warning = dams.filter(d => d.status === 'Caution').length; // Assuming 'Caution' maps to 'Warning'
    const normal = dams.filter(d => d.status === 'Safe').length; // Assuming 'Safe' maps to 'Normal'
    const normalPercent = total > 0 ? ((normal / total) * 100).toFixed(1) : 0;
    return { total, critical, warning, normal, normalPercent };
  }, [dams, loading]);

  const navItems = [
    { name: "Dashboard", path: createPageUrl("Dashboard"), icon: LayoutDashboard },
    { name: "Dams", path: createPageUrl("Dashboard"), icon: Database }, // Keeping as dashboard path for now as per outline
    { name: "Alerts", path: createPageUrl("Dashboard") + "?filter=Critical", icon: AlertCircle },
    { name: "Trends", path: createPageUrl("Dashboard"), icon: Activity },
    { name: "Settings", path: "#", icon: Settings }
  ];

  const renderSidebarStats = () => {
    if (loading) {
      return (
        <div className="flex justify-center items-center h-48"> {/* Added height for loader */}
          <Loader2 className="w-6 h-6 text-white animate-spin" />
        </div>
      );
    }
    return (
      <div className="grid grid-cols-2 gap-3 mb-6">
        <div className="bg-white/5 backdrop-blur-sm rounded-xl p-4 border border-white/10">
          <p className="text-gray-400 text-xs mb-1">Total</p>
          <p className="text-white text-2xl font-bold">{stats.total}</p>
        </div>
        <div className="bg-white/5 backdrop-blur-sm rounded-xl p-4 border border-white/10">
          <p className="text-gray-400 text-xs mb-1">Critical</p>
          <p className="text-white text-2xl font-bold">{stats.critical}</p>
        </div>
        <div className="bg-white/5 backdrop-blur-sm rounded-xl p-4 border border-white/10">
          <p className="text-gray-400 text-xs mb-1">Warning</p>
          <p className="text-white text-2xl font-bold">{stats.warning}</p>
        </div>
        <div className="bg-white/5 backdrop-blur-sm rounded-xl p-4 border border-white/10">
          <p className="text-gray-400 text-xs mb-1">Normal</p>
          <p className="text-white text-2xl font-bold">{stats.normal}</p>
          <p className="text-emerald-400 text-xs mt-1">{stats.normalPercent}%</p>
        </div>
      </div>
    );
  };

  return (
    <div className="flex h-screen bg-gray-50" style={{ backgroundColor: '#F8FAFB' }}>
      <aside className="w-64 flex-shrink-0" style={{ backgroundColor: '#0A2540' }}>
        <div className="flex flex-col h-full">
          <div className="p-6">
            <div className="flex items-center gap-3 mb-8">
              <div className="bg-white/10 p-2 rounded-lg">
                <Waves className="w-6 h-6 text-white" />
              </div>
              <div>
                <h1 className="text-white font-bold text-lg">Dam Water</h1>
              </div>
            </div>
            {renderSidebarStats()}
          </div>

          <nav className="flex-1 px-3">
            <p className="text-gray-400 text-xs font-semibold mb-2 px-3">Navigate</p>
            {navItems.map((item) => {
              const Icon = item.icon;
              // Determine active state for general items, excluding when alerts filter is active
              const isActive = location.pathname === item.path && !location.search.includes('filter=Critical');
              // Determine active state specifically for the "Alerts" item when its filter is present
              const isAlertsActive = item.name === 'Alerts' && location.search.includes('filter=Critical');

              return (
                <Link
                  key={item.name}
                  to={item.path}
                  className={`flex items-center gap-3 px-3 py-2.5 rounded-lg mb-1 transition-all ${
                    (isActive || isAlertsActive)
                      ? 'bg-white/10 text-white'
                      : 'text-gray-400 hover:bg-white/5 hover:text-white'
                  }`}
                >
                  <Icon className="w-5 h-5" />
                  <span className="font-medium">{item.name}</span>
                </Link>
              );
            })}
          </nav>

          <div className="p-4 border-t border-white/10">
            <div className="flex items-center gap-3 mb-3">
              <div className="w-10 h-10 bg-gradient-to-br from-blue-400 to-blue-600 rounded-full flex items-center justify-center">
                <span className="text-white font-semibold text-sm">AM</span>
              </div>
              <div className="flex-1">
                <p className="text-white font-medium text-sm">Operations Lead</p>
                <p className="text-gray-400 text-xs">Admin Access</p>
              </div>
            </div>
            <button className="flex items-center gap-2 text-gray-400 hover:text-white transition-colors text-sm w-full px-3 py-2 rounded-lg hover:bg-white/5">
              <LogOut className="w-4 h-4" />
              <span>Sign out</span>
            </button>
          </div>
        </div>
      </aside>

      <main className="flex-1 overflow-auto">
        {React.cloneElement(children, { dams, loading })}
      </main>

      <style>{`
        :root {
          --sidebar-bg: #0A2540;
          --main-bg: #F8FAFB;
          --card-bg: #FFFFFF;
          --primary-blue: #0066CC;
        }
        
        * {
          -webkit-font-smoothing: antialiased;
          -moz-osx-font-smoothing: grayscale;
        }

        body {
          font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', sans-serif;
        }
      `}</style>
    </div>
  );
}
