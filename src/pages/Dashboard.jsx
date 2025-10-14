import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Search, Filter, Clock, ChevronDown, TrendingUp, TrendingDown, Minus, ArrowRight, Bell, Plus } from 'lucide-react'

const Dashboard = () => {
  const [dams, setDams] = useState([])
  const [filteredDams, setFilteredDams] = useState([])
  const [searchTerm, setSearchTerm] = useState('')
  const [statusFilter, setStatusFilter] = useState('all')
  const [regionFilter, setRegionFilter] = useState('all')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // Simulate API call
    setTimeout(() => {
      setDams(sampleDams)
      setFilteredDams(sampleDams)
      setLoading(false)
    }, 1000)
  }, [])

  useEffect(() => {
    let filtered = dams

    // Apply search filter
    if (searchTerm) {
      filtered = filtered.filter(dam => 
        dam.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        dam.river_name.toLowerCase().includes(searchTerm.toLowerCase())
      )
    }

    // Apply status filter
    if (statusFilter !== 'all') {
      filtered = filtered.filter(dam => dam.status === statusFilter)
    }

    // Apply region filter
    if (regionFilter !== 'all') {
      filtered = filtered.filter(dam => dam.state === regionFilter)
    }

    setFilteredDams(filtered)
  }, [dams, searchTerm, statusFilter, regionFilter])

  const getTrendIcon = (dam) => {
    if (!dam.historical_data || dam.historical_data.length < 2) return Minus
    const recent = dam.historical_data[dam.historical_data.length - 1].percentage
    const previous = dam.historical_data[dam.historical_data.length - 2].percentage
    if (recent > previous) return TrendingUp
    if (recent < previous) return TrendingDown
    return Minus
  }

  const getTrendValue = (dam) => {
    if (!dam.historical_data || dam.historical_data.length < 2) return '0%'
    const recent = dam.historical_data[dam.historical_data.length - 1].percentage
    const previous = dam.historical_data[dam.historical_data.length - 2].percentage
    const change = recent - previous
    return `${change > 0 ? '+' : ''}${change.toFixed(1)}%`
  }

  const getStatusConfig = (status) => {
    switch (status) {
      case 'Critical':
        return { className: 'bg-red-100 text-red-700', label: 'Critical' }
      case 'Caution':
        return { className: 'bg-yellow-100 text-yellow-700', label: 'Warning' }
      default:
        return { className: 'bg-green-100 text-green-700', label: 'Normal' }
    }
  }

  const criticalCount = dams.filter(d => d.status === 'Critical').length
  const regions = [...new Set(dams.map(d => d.state))].sort()

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Top Stats */}
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
        {[
          { label: 'Avg Level', value: '96.7%' },
          { label: 'Inflows', value: '+12.5%' },
          { label: 'Outflows', value: '-8.3%' },
          { label: 'Alerts', value: '8' },
          { label: 'Rainfall', value: '45mm' },
          { label: 'Capacity', value: '86%' }
        ].map((stat, index) => (
          <div key={index} className="bg-white rounded-lg p-4 border border-gray-200">
            <p className="text-gray-500 text-xs mb-1">{stat.label}</p>
            <p className="text-gray-900 text-xl font-bold">{stat.value}</p>
          </div>
        ))}
      </div>

      {/* Live Overview Filters */}
      <div className="bg-white rounded-xl p-6 border border-gray-200">
        <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
          <div className="flex flex-wrap items-center gap-3">
            <h3 className="text-lg font-bold text-gray-900">Live Overview</h3>
            {[
              { value: 'all', label: 'All', color: 'bg-blue-100 text-blue-700' },
              { value: 'Safe', label: 'Normal', color: 'bg-green-100 text-green-700' },
              { value: 'Caution', label: 'Warning', color: 'bg-yellow-100 text-yellow-700' },
              { value: 'Critical', label: 'Critical', color: 'bg-red-100 text-red-700' }
            ].map((option) => (
              <button
                key={option.value}
                onClick={() => setStatusFilter(option.value)}
                className={`px-4 py-2 rounded-full font-medium text-sm transition-all ${
                  statusFilter === option.value
                    ? option.color
                    : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                }`}
              >
                {option.label}
              </button>
            ))}
          </div>

          <div className="flex flex-wrap items-center gap-3">
            <div className="flex items-center gap-2 px-3 py-1.5 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
              <Filter className="w-4 h-4 text-gray-600" />
              <select 
                value={regionFilter} 
                onChange={(e) => setRegionFilter(e.target.value)}
                className="border-0 bg-transparent text-sm text-gray-700 focus:outline-none"
              >
                <option value="all">All regions</option>
                {regions.map(region => (
                  <option key={region} value={region}>{region}</option>
                ))}
              </select>
            </div>
            
            <button className="flex items-center gap-2 px-4 py-2 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
              <span className="text-sm text-gray-700">Status: Any</span>
              <ChevronDown className="w-4 h-4 text-gray-400" />
            </button>

            <button className="flex items-center gap-2 px-4 py-2 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
              <Clock className="w-4 h-4 text-gray-600" />
              <span className="text-sm text-gray-700">Last 24 hours</span>
              <ChevronDown className="w-4 h-4 text-gray-400" />
            </button>
          </div>
        </div>

        <div className="flex gap-2 mt-4">
          <span className="inline-flex items-center gap-2 px-4 py-2 bg-green-500 text-white rounded-lg font-medium text-sm">
            <span className="w-2 h-2 bg-white rounded-full animate-pulse"></span>
            Live
          </span>
          <span className="inline-flex items-center gap-2 px-4 py-2 bg-emerald-500 text-white rounded-lg font-medium text-sm">
            Rising
          </span>
        </div>
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
        {/* All Dams Table */}
        <div className="lg:col-span-3">
          <div className="bg-white rounded-xl border border-gray-200 overflow-hidden">
            <div className="px-6 py-4 border-b border-gray-200">
              <h3 className="text-lg font-bold text-gray-900">All Dams</h3>
            </div>

            <div className="overflow-x-auto">
              <table className="w-full">
                <thead>
                  <tr className="border-b border-gray-200 bg-gray-50">
                    <th className="text-left px-6 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wider">Dam</th>
                    <th className="text-left px-6 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wider">Current Level</th>
                    <th className="text-left px-6 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wider">24h Change</th>
                    <th className="text-left px-6 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wider">Status</th>
                    <th className="text-right px-6 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredDams.map((dam, index) => {
                    const TrendIcon = getTrendIcon(dam)
                    const trendValue = getTrendValue(dam)
                    const statusConfig = getStatusConfig(dam.status)
                    const isPositive = trendValue.startsWith('+')
                    const isNegative = trendValue.startsWith('-')

                    return (
                      <tr
                        key={dam.id}
                        className="border-b border-gray-100 hover:bg-gray-50 transition-colors"
                      >
                        <td className="px-6 py-4">
                          <div className="flex items-center gap-3">
                            <div className="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center">
                              <span className="text-blue-600 font-semibold text-sm">
                                {dam.name.substring(0, 2).toUpperCase()}
                              </span>
                            </div>
                            <div>
                              <p className="font-semibold text-gray-900">{dam.name}</p>
                              <p className="text-sm text-gray-500">{dam.river_name}</p>
                            </div>
                          </div>
                        </td>
                        <td className="px-6 py-4">
                          <div>
                            <p className="font-semibold text-gray-900">{dam.percentage_full}%</p>
                            <p className="text-sm text-gray-500">capacity</p>
                          </div>
                        </td>
                        <td className="px-6 py-4">
                          <div className="flex items-center gap-2">
                            <TrendIcon className={`w-4 h-4 ${
                              isPositive ? 'text-blue-500' : 
                              isNegative ? 'text-orange-500' : 
                              'text-gray-400'
                            }`} />
                            <span className={`font-semibold ${
                              isPositive ? 'text-blue-600' : 
                              isNegative ? 'text-orange-600' : 
                              'text-gray-500'
                            }`}>
                              {trendValue}
                            </span>
                          </div>
                        </td>
                        <td className="px-6 py-4">
                          <span className={`inline-flex px-3 py-1 rounded-full text-xs font-semibold ${statusConfig.className}`}>
                            {statusConfig.label}
                          </span>
                        </td>
                        <td className="px-6 py-4 text-right">
                          <Link 
                            to={`/dam/${dam.id}`}
                            className="inline-flex items-center gap-1 text-blue-600 hover:text-blue-700 font-medium text-sm transition-colors"
                          >
                            View
                            <ArrowRight className="w-4 h-4" />
                          </Link>
                        </td>
                      </tr>
                    )
                  })}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        {/* Right Sidebar */}
        <div className="space-y-6">
          {/* Network Capacity */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <h3 className="text-lg font-bold text-gray-900 mb-6">Network Capacity</h3>
            
            <div className="relative w-48 h-48 mx-auto">
              <svg className="w-full h-full transform -rotate-90">
                <circle
                  cx="96"
                  cy="96"
                  r="80"
                  fill="none"
                  stroke="#E5E7EB"
                  strokeWidth="16"
                />
                <circle
                  cx="96"
                  cy="96"
                  r="80"
                  fill="none"
                  stroke="#0066CC"
                  strokeWidth="16"
                  strokeLinecap="round"
                  strokeDasharray={`${2 * Math.PI * 80}`}
                  strokeDashoffset={`${2 * Math.PI * 80 * 0.03}`}
                />
              </svg>
              <div className="absolute inset-0 flex items-center justify-center">
                <div className="text-center">
                  <p className="text-5xl font-bold text-gray-900">97%</p>
                  <p className="text-sm text-gray-500 mt-1">Avg across dams</p>
                </div>
              </div>
            </div>
          </div>

          {/* Inflow vs Outflow */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-bold text-gray-900">Inflow vs Outflow</h3>
              <button className="text-sm text-blue-600 hover:text-blue-700 font-medium">
                Details →
              </button>
            </div>
            <div className="h-32 bg-gradient-to-r from-blue-50 to-blue-100 rounded-lg flex items-end justify-around p-4">
              {[70, 85, 60, 90, 75, 80].map((height, i) => (
                <div
                  key={i}
                  className="w-8 bg-blue-500 rounded-t"
                  style={{ height: `${height}%` }}
                />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

// Sample data
const sampleDams = [
  {
    id: 1,
    name: "Bhakra Dam",
    river_name: "Sutlej",
    location: "Bilaspur",
    state: "Himachal Pradesh",
    type: "Gravity",
    current_level_meters: 168.5,
    capacity_meters: 190.0,
    percentage_full: 88.7,
    status: "Safe",
    last_updated: "2024-01-15T10:30:00Z",
    latitude: 31.4104,
    longitude: 76.4333,
    description: "One of the highest gravity dams in the world, providing irrigation and hydroelectric power.",
    alerts: [],
    image_url: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=300&fit=crop"
  },
  {
    id: 2,
    name: "Tehri Dam",
    river_name: "Bhagirathi",
    location: "Tehri",
    state: "Uttarakhand",
    type: "Embankment",
    current_level_meters: 820.2,
    capacity_meters: 830.0,
    percentage_full: 98.8,
    status: "Caution",
    last_updated: "2024-01-15T09:45:00Z",
    latitude: 30.3753,
    longitude: 78.4744,
    description: "The highest dam in India, providing hydroelectric power and water supply.",
    alerts: [
      {
        message: "Water level approaching maximum capacity",
        severity: "warning",
        timestamp: "2024-01-15T09:00:00Z"
      }
    ],
    image_url: "https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=500&h=300&fit=crop"
  },
  {
    id: 3,
    name: "Sardar Sarovar Dam",
    river_name: "Narmada",
    location: "Kevadia",
    state: "Gujarat",
    type: "Gravity",
    current_level_meters: 138.7,
    capacity_meters: 163.0,
    percentage_full: 85.1,
    status: "Safe",
    last_updated: "2024-01-15T11:15:00Z",
    latitude: 21.8278,
    longitude: 73.7453,
    description: "Multi-purpose dam providing irrigation, drinking water, and hydroelectric power.",
    alerts: [],
    image_url: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=300&fit=crop"
  },
  {
    id: 4,
    name: "Hirakud Dam",
    river_name: "Mahanadi",
    location: "Sambalpur",
    state: "Odisha",
    type: "Embankment",
    current_level_meters: 195.8,
    capacity_meters: 195.0,
    percentage_full: 100.4,
    status: "Critical",
    last_updated: "2024-01-15T08:20:00Z",
    latitude: 21.5167,
    longitude: 83.8667,
    description: "One of the longest dams in the world, providing flood control and irrigation.",
    alerts: [
      {
        message: "Water level exceeds safe capacity - Emergency protocols activated",
        severity: "critical",
        timestamp: "2024-01-15T08:00:00Z"
      }
    ],
    image_url: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=300&fit=crop"
  },
  {
    id: 5,
    name: "Nagarjuna Sagar Dam",
    river_name: "Krishna",
    location: "Nalgonda",
    state: "Telangana",
    type: "Masonry",
    current_level_meters: 179.2,
    capacity_meters: 190.0,
    percentage_full: 94.3,
    status: "Safe",
    last_updated: "2024-01-15T12:00:00Z",
    latitude: 16.5740,
    longitude: 79.3167,
    description: "One of the largest masonry dams in the world, providing irrigation and hydroelectric power.",
    alerts: [],
    image_url: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=300&fit=crop"
  },
  {
    id: 6,
    name: "Indira Sagar Dam",
    river_name: "Narmada",
    location: "Khandwa",
    state: "Madhya Pradesh",
    type: "Gravity",
    current_level_meters: 262.1,
    capacity_meters: 262.0,
    percentage_full: 100.0,
    status: "Caution",
    last_updated: "2024-01-15T07:30:00Z",
    latitude: 22.1667,
    longitude: 76.6000,
    description: "Multi-purpose dam providing irrigation, drinking water, and hydroelectric power.",
    alerts: [
      {
        message: "Water level at maximum capacity",
        severity: "warning",
        timestamp: "2024-01-15T07:00:00Z"
      }
    ],
    image_url: "https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=500&h=300&fit=crop"
  }
]

export default Dashboard
