import React from 'react'
import { Filter } from 'lucide-react'

const DamFilters = ({ filters, onFilterChange }) => {
  const states = [
    'Himachal Pradesh',
    'Uttarakhand', 
    'Gujarat',
    'Odisha',
    'Telangana',
    'Madhya Pradesh'
  ]

  const statuses = ['Safe', 'Caution', 'Critical']
  const types = ['Gravity', 'Arch', 'Embankment', 'Buttress', 'Multipurpose', 'Hydroelectric']

  return (
    <div className="flex flex-col sm:flex-row space-y-2 sm:space-y-0 sm:space-x-4">
      {/* State Filter */}
      <select
        value={filters.state}
        onChange={(e) => onFilterChange({ state: e.target.value })}
        className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-dam-button focus:border-transparent text-sm"
      >
        <option value="">All States</option>
        {states.map(state => (
          <option key={state} value={state}>{state}</option>
        ))}
      </select>

      {/* Status Filter */}
      <select
        value={filters.status}
        onChange={(e) => onFilterChange({ status: e.target.value })}
        className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-dam-button focus:border-transparent text-sm"
      >
        <option value="">All Status</option>
        {statuses.map(status => (
          <option key={status} value={status}>{status}</option>
        ))}
      </select>

      {/* Type Filter */}
      <select
        value={filters.type}
        onChange={(e) => onFilterChange({ type: e.target.value })}
        className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-dam-button focus:border-transparent text-sm"
      >
        <option value="">All Types</option>
        {types.map(type => (
          <option key={type} value={type}>{type}</option>
        ))}
      </select>

      {/* Clear Filters */}
      {(filters.state || filters.status || filters.type) && (
        <button
          onClick={() => onFilterChange({ state: '', status: '', type: '' })}
          className="px-3 py-2 text-sm text-gray-600 hover:text-gray-800 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
        >
          Clear
        </button>
      )}
    </div>
  )
}

export default DamFilters
