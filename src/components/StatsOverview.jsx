import React from 'react'
import { Droplets, AlertTriangle, CheckCircle } from 'lucide-react'

const StatsOverview = ({ dams }) => {
  const totalDams = dams.length
  const safeDams = dams.filter(dam => dam.status === 'Safe').length
  const cautionDams = dams.filter(dam => dam.status === 'Caution').length
  const criticalDams = dams.filter(dam => dam.status === 'Critical').length
  
  const avgCapacity = dams.reduce((sum, dam) => sum + dam.percentage_full, 0) / totalDams

  const stats = [
    {
      title: 'Total Dams',
      value: totalDams,
      icon: <Droplets className="w-6 h-6" />,
      color: 'text-dam-primary',
      bgColor: 'bg-dam-highlight/20'
    },
    {
      title: 'Safe Status',
      value: safeDams,
      icon: <CheckCircle className="w-6 h-6" />,
      color: 'text-green-600',
      bgColor: 'bg-green-100'
    },
    {
      title: 'Caution Status',
      value: cautionDams,
      icon: <AlertTriangle className="w-6 h-6" />,
      color: 'text-yellow-600',
      bgColor: 'bg-yellow-100'
    },
    {
      title: 'Critical Status',
      value: criticalDams,
      icon: <AlertTriangle className="w-6 h-6" />,
      color: 'text-red-600',
      bgColor: 'bg-red-100'
    },
    {
      title: 'Avg Capacity',
      value: `${avgCapacity.toFixed(1)}%`,
      icon: <Droplets className="w-6 h-6" />,
      color: 'text-dam-primary',
      bgColor: 'bg-dam-highlight/20'
    }
  ]

  return (
    <div className="grid grid-cols-2 lg:grid-cols-5 gap-4 mb-8">
      {stats.map((stat, index) => (
        <div key={index} className="card p-4">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600 mb-1">{stat.title}</p>
              <p className={`text-2xl font-bold ${stat.color}`}>{stat.value}</p>
            </div>
            <div className={`p-3 rounded-lg ${stat.bgColor} ${stat.color}`}>
              {stat.icon}
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

export default StatsOverview
