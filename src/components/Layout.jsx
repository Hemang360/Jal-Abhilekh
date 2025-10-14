import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';

const navItems = [
  { label: 'Dashboard', href: '/' },
  { label: 'Dams', href: '/' },
  { label: 'Alerts', href: '/' },
  { label: 'Trends', href: '/' },
  { label: 'Settings', href: '/' },
];

const Layout = ({ children }) => {
  const location = useLocation();

  return (
    <div className="min-h-screen bg-[#f5fbff]">
      <div className="flex min-h-screen">
        {/* Sidebar */}
        <aside className="w-64 bg-[#0b2239] text-white flex flex-col">
          <div className="h-16 px-5 flex items-center border-b border-white/10">
            <div className="w-8 h-8 rounded-lg bg-[#004b87] mr-3 flex items-center justify-center">
              <span className="text-white text-sm font-bold">DW</span>
            </div>
            <span className="font-semibold">Dam Water</span>
          </div>

          <div className="px-4 py-4 grid grid-cols-2 gap-3">
            <div className="bg-white/5 rounded-xl p-3">
              <p className="text-xs text-white/70">Total</p>
              <p className="text-2xl font-bold">16</p>
            </div>
            <div className="bg-white/5 rounded-xl p-3">
              <p className="text-xs text-white/70">Critical</p>
              <p className="text-2xl font-bold">4</p>
            </div>
            <div className="bg-white/5 rounded-xl p-3">
              <p className="text-xs text-white/70">Warning</p>
              <p className="text-2xl font-bold">4</p>
            </div>
            <div className="bg-white/5 rounded-xl p-3">
              <p className="text-xs text-white/70">Normal</p>
              <p className="text-2xl font-bold">8</p>
            </div>
          </div>

          <nav className="mt-2 px-2 flex-1">
            {navItems.map((item) => {
              const active = location.pathname === item.href;
              return (
                <Link
                  key={item.label}
                  to={item.href}
                  className={`flex items-center px-3 py-3 rounded-lg text-sm mb-1 transition-colors ${
                    active ? 'bg-white/10 text-white' : 'text-white/80 hover:bg-white/5'
                  }`}
                >
                  {item.label}
                </Link>
              );
            })}
          </nav>

          <div className="mt-auto p-4 text-white/70 text-sm">Operations Lead</div>
        </aside>

        {/* Main content */}
        <div className="flex-1">
          {/* Top bar */}
          <div className="h-16 bg-white border-b border-[#9ce1ff]/30 px-6 flex items-center justify-between">
            <h1 className="text-lg font-semibold text-[#0070c0]">Dam Dashboard</h1>
            <div className="flex items-center gap-3">
              <input
                type="text"
                placeholder="Search dams, regions, alerts..."
                className="w-80 px-3 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-[#25a4ff]"
              />
              <button className="btn-primary">Add Dam</button>
            </div>
          </div>

          <main className="p-6">
            {children}
            <Outlet />
          </main>
        </div>
      </div>
    </div>
  );
};

export default Layout;
