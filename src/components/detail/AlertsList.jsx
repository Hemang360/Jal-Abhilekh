import React from 'react'
import { AlertTriangle, AlertCircle, Info } from 'lucide-react'

const AlertsList = ({ alerts }) => {
  const getAlertIcon = (severity) => {
    switch (severity) {
      case 'critical':
        return <AlertTriangle className="w-5 h-5 text-red-600" />
      case 'warning':
        return <AlertCircle className="w-5 h-5 text-yellow-600" />
      case 'info':
        return <Info className="w-5 h-5 text-blue-600" />
      default:
        return <Info className="w-5 h-5 text-blue-600" />
    }
  }

  const getAlertColor = (severity) => {
    switch (severity) {
      case 'critical':
        return 'border-red-200 bg-red-50'
      case 'warning':
        return 'border-yellow-200 bg-yellow-50'
      case 'info':
        return 'border-blue-200 bg-blue-50'
      default:
        return 'border-blue-200 bg-blue-50'
    }
  }

  const getSeverityText = (severity) => {
    switch (severity) {
      case 'critical':
        return 'Critical Alert'
      case 'warning':
        return 'Warning'
      case 'info':
        return 'Information'
      default:
        return 'Alert'
    }
  }

  return (
    <div className="card p-6">
      <h3 className="text-xl font-semibold text-dam-primary mb-4">Active Alerts</h3>
      <div className="space-y-3">
        {alerts.map((alert, index) => (
          <div 
            key={index} 
            className={`border rounded-lg p-4 ${getAlertColor(alert.severity)}`}
          >
            <div className="flex items-start space-x-3">
              {getAlertIcon(alert.severity)}
              <div className="flex-1">
                <div className="flex items-center justify-between mb-2">
                  <h4 className="font-medium text-gray-900">
                    {getSeverityText(alert.severity)}
                  </h4>
                  <span className="text-xs text-gray-500">
                    {new Date(alert.timestamp).toLocaleString()}
                  </span>
                </div>
                <p className="text-sm text-gray-700">{alert.message}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default AlertsList
