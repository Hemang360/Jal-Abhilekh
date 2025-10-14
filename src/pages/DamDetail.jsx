import React, { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { ArrowLeft, MapPin, Droplets, Clock, Calendar, TrendingUp } from 'lucide-react'
import WaterLevelChart from '../components/detail/WaterLevelChart'

const DamDetail = () => {
  const { id } = useParams()
  const [dam, setDam] = useState(null)
  const [loading, setLoading] = useState(true)
  const [chartMetric, setChartMetric] = useState('meters')

  useEffect(() => {
    // Simulate API call
    setTimeout(() => {
      const foundDam = sampleDams.find(d => d.id === parseInt(id))
      setDam(foundDam)
      setLoading(false)
    }, 1000)
  }, [id])

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    )
  }

  if (!dam) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Dam not found</h2>
          <p className="text-gray-600 mb-4">The dam you're looking for doesn't exist.</p>
          <Link to="/" className="btn-primary">
            <ArrowLeft className="w-4 h-4 mr-2" />
            Back to Dashboard
          </Link>
        </div>
      </div>
    )
  }

  const getStatusConfig = (status) => {
    switch (status) {
      case 'Critical':
        return { className: 'bg-red-100 text-red-700 border-red-200', label: 'Critical' }
      case 'Caution':
        return { className: 'bg-yellow-100 text-yellow-700 border-yellow-200', label: 'Warning' }
      default:
        return { className: 'bg-green-100 text-green-700 border-green-200', label: 'Normal' }
    }
  }

  const statusConfig = getStatusConfig(dam.status)

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-white rounded-xl border border-gray-200 p-6">
        <Link 
          to="/" 
          className="inline-flex items-center gap-2 text-gray-600 hover:text-gray-800 mb-4 transition-colors"
        >
          <ArrowLeft className="w-4 h-4" />
          Back to Dashboard
        </Link>

        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 mb-2">{dam.name}</h1>
            <div className="flex items-center gap-4 text-gray-600">
              <div className="flex items-center gap-2">
                <Droplets className="w-4 h-4 text-blue-600" />
                <span>{dam.river_name}</span>
              </div>
              <span>•</span>
              <div className="flex items-center gap-2">
                <MapPin className="w-4 h-4" />
                <span>{dam.location}, {dam.state}</span>
              </div>
            </div>
          </div>
          <span className={`inline-flex px-4 py-2 rounded-full text-sm font-semibold border ${statusConfig.className}`}>
            {statusConfig.label}
          </span>
        </div>
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left Column - Main Content */}
        <div className="lg:col-span-2 space-y-6">
          {/* Water Level Trends */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <div className="flex items-center justify-between mb-6">
              <h3 className="text-xl font-semibold text-gray-900">Water Level Trends</h3>
              <div className="flex bg-gray-100 rounded-lg p-1">
                <button
                  onClick={() => setChartMetric('percentage')}
                  className={`px-3 py-1 rounded-md text-sm font-medium transition-colors ${
                    chartMetric === 'percentage' 
                      ? 'bg-white text-gray-900 shadow-sm' 
                      : 'text-gray-600 hover:text-gray-900'
                  }`}
                >
                  %
                </button>
                <button
                  onClick={() => setChartMetric('meters')}
                  className={`px-3 py-1 rounded-md text-sm font-medium transition-colors ${
                    chartMetric === 'meters' 
                      ? 'bg-white text-gray-900 shadow-sm' 
                      : 'text-gray-600 hover:text-gray-900'
                  }`}
                >
                  Meters
                </button>
              </div>
            </div>
            <WaterLevelChart dam={dam} metric={chartMetric} />
          </div>

          {/* About This Dam */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <h3 className="text-xl font-semibold text-gray-900 mb-4">About This Dam</h3>
            <p className="text-gray-700 leading-relaxed">{dam.description}</p>
          </div>
        </div>

        {/* Right Column - Sidebar */}
        <div className="space-y-6">
          {/* Current Status */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <h3 className="text-xl font-semibold text-gray-900 mb-6">Current Status</h3>
            
            <div className="space-y-6">
              <div>
                <p className="text-sm text-gray-600 mb-2">Water Level</p>
                <div className="flex items-end gap-2">
                  <span className="text-4xl font-bold text-blue-600">{dam.current_level_meters}</span>
                  <span className="text-gray-600 mb-1">meters</span>
                </div>
              </div>

              <div>
                <div className="flex justify-between items-center mb-2">
                  <p className="text-sm text-gray-600">Capacity</p>
                  <span className="text-2xl font-bold text-blue-600">{dam.percentage_full}%</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-3 overflow-hidden">
                  <div 
                    className={`h-full rounded-full transition-all duration-1000 ${
                      dam.status === 'Critical' ? 'bg-gradient-to-r from-red-500 to-rose-600' :
                      dam.status === 'Caution' ? 'bg-gradient-to-r from-yellow-500 to-orange-500' :
                      'bg-gradient-to-r from-green-500 to-emerald-500'
                    }`}
                    style={{ width: `${Math.min(dam.percentage_full, 100)}%` }}
                  />
                </div>
                <p className="text-xs text-gray-500 mt-1">Max: {dam.capacity_meters}m</p>
              </div>

              <div className="pt-4 border-t border-gray-200">
                <div className="flex items-center gap-2 text-sm text-gray-600">
                  <Calendar className="w-4 h-4" />
                  <span>Last updated: {new Date(dam.last_updated).toLocaleString()}</span>
                </div>
              </div>
            </div>
          </div>

          {/* Dam Information */}
          <div className="bg-white rounded-xl border border-gray-200 p-6">
            <h3 className="text-xl font-semibold text-gray-900 mb-4">Dam Information</h3>
            <div className="space-y-3">
              <div className="flex justify-between py-2 border-b border-gray-100">
                <span className="text-sm text-gray-600">Type</span>
                <span className="font-medium">{dam.type}</span>
              </div>
              <div className="flex justify-between py-2 border-b border-gray-100">
                <span className="text-sm text-gray-600">Location</span>
                <span className="font-medium">{dam.location}</span>
              </div>
              <div className="flex justify-between py-2">
                <span className="text-sm text-gray-600">State</span>
                <span className="font-medium">{dam.state}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

// Sample data (same as Dashboard)
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
    image_url: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 165.2, percentage: 86.9 },
      { date: "2024-01-02", level_meters: 166.1, percentage: 87.4 },
      { date: "2024-01-03", level_meters: 166.8, percentage: 87.8 },
      { date: "2024-01-04", level_meters: 167.2, percentage: 88.0 },
      { date: "2024-01-05", level_meters: 167.8, percentage: 88.3 },
      { date: "2024-01-06", level_meters: 168.1, percentage: 88.5 },
      { date: "2024-01-07", level_meters: 168.3, percentage: 88.6 },
      { date: "2024-01-08", level_meters: 168.4, percentage: 88.6 },
      { date: "2024-01-09", level_meters: 168.5, percentage: 88.7 },
      { date: "2024-01-10", level_meters: 168.5, percentage: 88.7 },
      { date: "2024-01-11", level_meters: 168.4, percentage: 88.6 },
      { date: "2024-01-12", level_meters: 168.3, percentage: 88.6 },
      { date: "2024-01-13", level_meters: 168.2, percentage: 88.5 },
      { date: "2024-01-14", level_meters: 168.4, percentage: 88.6 },
      { date: "2024-01-15", level_meters: 168.5, percentage: 88.7 }
    ]
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
    image_url: "https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 815.2, percentage: 98.2 },
      { date: "2024-01-02", level_meters: 816.1, percentage: 98.3 },
      { date: "2024-01-03", level_meters: 817.0, percentage: 98.4 },
      { date: "2024-01-04", level_meters: 817.8, percentage: 98.5 },
      { date: "2024-01-05", level_meters: 818.5, percentage: 98.6 },
      { date: "2024-01-06", level_meters: 819.1, percentage: 98.7 },
      { date: "2024-01-07", level_meters: 819.6, percentage: 98.7 },
      { date: "2024-01-08", level_meters: 820.0, percentage: 98.8 },
      { date: "2024-01-09", level_meters: 820.1, percentage: 98.8 },
      { date: "2024-01-10", level_meters: 820.2, percentage: 98.8 },
      { date: "2024-01-11", level_meters: 820.1, percentage: 98.8 },
      { date: "2024-01-12", level_meters: 820.0, percentage: 98.8 },
      { date: "2024-01-13", level_meters: 819.9, percentage: 98.8 },
      { date: "2024-01-14", level_meters: 820.1, percentage: 98.8 },
      { date: "2024-01-15", level_meters: 820.2, percentage: 98.8 }
    ]
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
    image_url: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 135.2, percentage: 82.9 },
      { date: "2024-01-02", level_meters: 136.1, percentage: 83.5 },
      { date: "2024-01-03", level_meters: 136.8, percentage: 83.9 },
      { date: "2024-01-04", level_meters: 137.2, percentage: 84.2 },
      { date: "2024-01-05", level_meters: 137.8, percentage: 84.5 },
      { date: "2024-01-06", level_meters: 138.1, percentage: 84.7 },
      { date: "2024-01-07", level_meters: 138.3, percentage: 84.8 },
      { date: "2024-01-08", level_meters: 138.4, percentage: 84.9 },
      { date: "2024-01-09", level_meters: 138.5, percentage: 85.0 },
      { date: "2024-01-10", level_meters: 138.6, percentage: 85.0 },
      { date: "2024-01-11", level_meters: 138.5, percentage: 85.0 },
      { date: "2024-01-12", level_meters: 138.4, percentage: 84.9 },
      { date: "2024-01-13", level_meters: 138.3, percentage: 84.8 },
      { date: "2024-01-14", level_meters: 138.5, percentage: 84.9 },
      { date: "2024-01-15", level_meters: 138.7, percentage: 85.1 }
    ]
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
    image_url: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 190.2, percentage: 97.5 },
      { date: "2024-01-02", level_meters: 191.1, percentage: 98.0 },
      { date: "2024-01-03", level_meters: 192.0, percentage: 98.5 },
      { date: "2024-01-04", level_meters: 192.8, percentage: 98.9 },
      { date: "2024-01-05", level_meters: 193.5, percentage: 99.2 },
      { date: "2024-01-06", level_meters: 194.1, percentage: 99.5 },
      { date: "2024-01-07", level_meters: 194.6, percentage: 99.7 },
      { date: "2024-01-08", level_meters: 195.0, percentage: 100.0 },
      { date: "2024-01-09", level_meters: 195.2, percentage: 100.1 },
      { date: "2024-01-10", level_meters: 195.4, percentage: 100.2 },
      { date: "2024-01-11", level_meters: 195.5, percentage: 100.3 },
      { date: "2024-01-12", level_meters: 195.6, percentage: 100.3 },
      { date: "2024-01-13", level_meters: 195.7, percentage: 100.4 },
      { date: "2024-01-14", level_meters: 195.7, percentage: 100.4 },
      { date: "2024-01-15", level_meters: 195.8, percentage: 100.4 }
    ]
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
    image_url: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 175.2, percentage: 92.2 },
      { date: "2024-01-02", level_meters: 176.1, percentage: 92.7 },
      { date: "2024-01-03", level_meters: 176.8, percentage: 93.1 },
      { date: "2024-01-04", level_meters: 177.2, percentage: 93.3 },
      { date: "2024-01-05", level_meters: 177.8, percentage: 93.6 },
      { date: "2024-01-06", level_meters: 178.1, percentage: 93.7 },
      { date: "2024-01-07", level_meters: 178.3, percentage: 93.8 },
      { date: "2024-01-08", level_meters: 178.4, percentage: 93.9 },
      { date: "2024-01-09", level_meters: 178.5, percentage: 94.0 },
      { date: "2024-01-10", level_meters: 178.6, percentage: 94.0 },
      { date: "2024-01-11", level_meters: 178.5, percentage: 94.0 },
      { date: "2024-01-12", level_meters: 178.4, percentage: 93.9 },
      { date: "2024-01-13", level_meters: 178.3, percentage: 93.8 },
      { date: "2024-01-14", level_meters: 178.7, percentage: 94.1 },
      { date: "2024-01-15", level_meters: 179.2, percentage: 94.3 }
    ]
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
    image_url: "https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=500&h=300&fit=crop",
    historical_data: [
      { date: "2024-01-01", level_meters: 258.2, percentage: 98.5 },
      { date: "2024-01-02", level_meters: 259.1, percentage: 98.9 },
      { date: "2024-01-03", level_meters: 260.0, percentage: 99.2 },
      { date: "2024-01-04", level_meters: 260.8, percentage: 99.5 },
      { date: "2024-01-05", level_meters: 261.5, percentage: 99.8 },
      { date: "2024-01-06", level_meters: 261.8, percentage: 99.9 },
      { date: "2024-01-07", level_meters: 261.9, percentage: 99.9 },
      { date: "2024-01-08", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-09", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-10", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-11", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-12", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-13", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-14", level_meters: 262.0, percentage: 100.0 },
      { date: "2024-01-15", level_meters: 262.1, percentage: 100.0 }
    ]
  }
]

export default DamDetail
