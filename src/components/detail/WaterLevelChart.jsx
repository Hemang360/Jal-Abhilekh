import React from 'react'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Area, AreaChart } from 'recharts'

const WaterLevelChart = ({ dam, metric = 'meters' }) => {
  const data = dam.historical_data || []

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric' 
    })
  }

  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-white p-3 border border-gray-200 rounded-lg shadow-lg">
          <p className="text-sm font-medium text-gray-900">{formatDate(label)}</p>
          <p className="text-sm text-blue-600">
            {metric === 'meters' ? `${payload[0].value}m` : `${payload[0].value}%`}
          </p>
        </div>
      )
    }
    return null
  }

  const getYAxisDomain = () => {
    if (metric === 'meters') {
      const values = data.map(d => d.level_meters)
      const min = Math.min(...values)
      const max = Math.max(...values)
      return [Math.floor(min - 5), Math.ceil(max + 5)]
    } else {
      return [0, 100]
    }
  }

  const getDataKey = () => {
    return metric === 'meters' ? 'level_meters' : 'percentage'
  }

  return (
    <div className="h-80">
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart data={data} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
          <defs>
            <linearGradient id="colorGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#3B82F6" stopOpacity={0.3}/>
              <stop offset="95%" stopColor="#3B82F6" stopOpacity={0.05}/>
            </linearGradient>
          </defs>
          <CartesianGrid strokeDasharray="3 3" stroke="#e5e7eb" />
          <XAxis 
            dataKey="date" 
            tickFormatter={formatDate}
            stroke="#6b7280"
            fontSize={12}
            axisLine={false}
            tickLine={false}
          />
          <YAxis 
            domain={getYAxisDomain()}
            stroke="#6b7280"
            fontSize={12}
            axisLine={false}
            tickLine={false}
            label={{ 
              value: metric === 'meters' ? 'Meters' : 'Percentage (%)', 
              angle: -90, 
              position: 'insideLeft',
              style: { textAnchor: 'middle' }
            }}
          />
          <Tooltip content={<CustomTooltip />} />
          <Area
            type="monotone"
            dataKey={getDataKey()}
            stroke="#3B82F6"
            strokeWidth={3}
            fill="url(#colorGradient)"
            dot={{ fill: '#3B82F6', strokeWidth: 2, r: 4 }}
            activeDot={{ r: 6, stroke: '#3B82F6', strokeWidth: 2 }}
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  )
}

export default WaterLevelChart
