import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Layout from './components/Layout'
import Dashboard from './pages/Dashboard'
import DamDetail from './pages/DamDetail'
import './index.css'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Dashboard />} />
        <Route path="dam/:id" element={<DamDetail />} />
      </Route>
    </Routes>
  )
}

export default App
