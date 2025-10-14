import React from 'react'
import { Link } from 'react-router-dom'
import { MapPin, Droplets, Clock, AlertTriangle } from 'lucide-react'

const DamCard = ({ dam }) => {
  const getStatusColor = (status) => {
    switch (status) {
      case 'Safe': return 'status-safe'
      case 'Caution': return 'status-caution'
      case 'Critical': return 'status-critical'
      default: return 'status-safe'
    }
  }

  const getStatusIcon = (status) => {
    switch (status) {
      case 'Critical': return <AlertTriangle className="w-4 h-4" />
      default: return <Droplets className="w-4 h-4" />
    }
  }

  return (
    <Link to={`/dam/${dam.id}`} className="block">
      <div className="card p-6 hover:shadow-xl transition-all duration-300 hover:scale-105">
        {/* Header */}
        <div className="flex items-start justify-between mb-4">
          <div className="flex-1">
            <h3 className="text-lg font-semibold text-dam-primary mb-1">{dam.name}</h3>
            <p className="text-gray-600 text-sm">{dam.river_name} River</p>
          </div>
          <div className={`flex items-center px-3 py-1 rounded-full border text-xs font-medium ${getStatusColor(dam.status)}`}>
            {getStatusIcon(dam.status)}
            <span className="ml-1">{dam.status}</span>
          </div>
        </div>

        {/* Location */}
        <div className="flex items-center text-gray-600 text-sm mb-4">
          <MapPin className="w-4 h-4 mr-2" />
          <span>{dam.location}, {dam.state}</span>
        </div>

        {/* Water Level Info */}
        <div className="bg-white/50 rounded-lg p-4 mb-4">
          <div className="flex justify-between items-center mb-2">
            <span className="text-sm text-gray-600">Water Level</span>
            <span className="font-semibold text-dam-primary">{dam.current_level_meters}m</span>
          </div>
          <div className="flex justify-between items-center mb-2">
            <span className="text-sm text-gray-600">Capacity</span>
            <span className="font-semibold">{dam.percentage_full.toFixed(1)}%</span>
          </div>
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div 
              className={`h-2 rounded-full ${
                dam.status === 'Critical' ? 'bg-red-500' : 
                dam.status === 'Caution' ? 'bg-yellow-500' : 'bg-green-500'
              }`}
              style={{ width: `${Math.min(dam.percentage_full, 100)}%` }}
            ></div>
          </div>
        </div>

        {/* Last Updated */}
        <div className="flex items-center text-gray-500 text-xs">
          <Clock className="w-3 h-3 mr-1" />
          <span>Updated {new Date(dam.last_updated).toLocaleString()}</span>
        </div>

        {/* Alert Indicator */}
        {dam.alerts && dam.alerts.length > 0 && (
          <div className="mt-3 flex items-center text-red-600 text-xs">
            <AlertTriangle className="w-3 h-3 mr-1" />
            <span>{dam.alerts.length} alert{dam.alerts.length > 1 ? 's' : ''}</span>
          </div>
        )}
      </div>
    </Link>
  )
}

export default DamCard
