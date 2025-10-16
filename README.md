# Jal Abhilekh
⚡ Overview

Jal Abhilekh is a mobile application designed to modernize river and reservoir water level monitoring.
The app leverages GPS, geofencing, image capture, and cloud integration to replace manual data entry with a secure, scalable, and technologically advanced solution.<br>
This ensures accurate, real-time monitoring for:<br>
Flood forecasting<br>
Disaster preparedness<br>
Water resource management<br>

# ⚠️ Problem Statement

In India, water level monitoring is critical for flood forecasting and water resource management.
However, many readings are still taken manually at Central Water Commission (CWC) monitoring sites.
Manual reporting is prone to errors, delays, and data tampering.

Challenges: <br>
Unauthorized or skipped readings<br>
Lack of real-time access to water levels<br>
Manual data entry errors<br>
Inconsistent traceability and auditability<br>

# 💡 Solution

Jal Abhilekh provides a GPS-aware, mobile-first solution that:<br>
Validates user location with geofencing<br>
Requires real-time photo capture of gauge posts<br>
Automatically records timestamp, GPS coordinates, and site ID<br>
Allows manual entry or automatic reading from images<br>
Supports role-based access for field staff, supervisors, and administrators<br>
Integrates with a cloud dashboard for central monitoring<br>
Works offline with local storage, syncing automatically when the internet is available<br>

⚙️ Features<br>
Login & Role Management<br>
Roles: Field Staff, Supervisor, Admin<br>
Offline mode for remote areas<br> login support<br>
Data Capture<br>
Live photo capture of gauge posts<br>
Manual entry or scanning of water level readings<br>
QR code verification at sites for authenticity<br>
Location & Security<br>
GPS-based geofencing<br>
Alerts if outside the allowed zone<br>
Tamper detection for skipped or unauthorized readings<br>
Data Management & Dashboard<br>
Real-time sync with central server<br>
Role-based dashboards for supervisors and analysts<br>
Historical data analysis and reporting<br>

# 🛠️ Technology Stack
Component	Technology
Mobile App	Android (Jetpack Compose, Kotlin)
Backend	Firebase / Node.js
Database	Firebase Firestore / SQL
Geolocation	Google Maps API / Location Services
Image Processing	OpenCV / ML Kit (optional for automated gauge reading)
Authentication	Firebase Auth / Biometric API<br>

 # 🖥️ Screens / UI Flow

Login Screen – Email, Password, Role, Offline Mode<br>
Dashboard – List of monitoring sites, real-time water levels<br>
Capture Reading – Live photo, manual entry, QR verification<br>
Map View – Locations of all monitoring sites, geofencing alerts<br>
Reports / Analytics – Historical data, charts, flood prediction insights<br>
Settings – User profile, app settings, data sync controls<br><br><br>
<img width="179" height="395" alt="Screenshot 2025-10-14 213249" src="https://github.com/user-attachments/assets/57bfc350-080d-4ee3-be78-f038331105d2" />
<br>
# How It Works:<br>
Field staff logs in and selects their role.<br>
They navigate to a monitoring site. The app validates location using geofencing.<br>
Staff captures a photo of the gauge post and optionally enters the reading manually.<br>
Data is tagged with timestamp, GPS coordinates, and site ID and stored locally if offline.<br>
Once online, the app syncs data to the cloud for real-time access.<br>
Supervisors and analysts can view all readings on a dashboard, generate reports, and detect anomalies.<br>
<br>
Future Enhancements<br>
AI-based water level reading directly from gauge images<br>
Flood prediction model using historical water level data<br>
Push notifications for abnormal water levels<br>
Integration with IoT sensors for automated water monitoring<br>
<br><br>

<img width="586" height="285" alt="Screenshot 2025-10-14 213302" src="https://github.com/user-attachments/assets/d9ba58d9-50e8-49c8-b6b5-e36dc59f279b" />
<br>
[🌐 View Live Website](https://jal-abhilekh-i3tx.vercel.app/)


