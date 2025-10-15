Jal Abhilekh
⚡ Overview

Jal Abhilekh is a mobile application designed to modernize river and reservoir water level monitoring.
The app leverages GPS, geofencing, image capture, and cloud integration to replace manual data entry with a secure, scalable, and technologically advanced solution.

This ensures accurate, real-time monitoring for:

Flood forecasting

Disaster preparedness

Water resource management

⚠️ Problem Statement

In India, water level monitoring is critical for flood forecasting and water resource management.
However, many readings are still taken manually at Central Water Commission (CWC) monitoring sites.
Manual reporting is prone to errors, delays, and data tampering.

Challenges

Unauthorized or skipped readings

Lack of real-time access to water levels

Manual data entry errors

Inconsistent traceability and auditability

💡 Solution

Jal Abhilekh provides a GPS-aware, mobile-first solution that:

Validates user location with geofencing

Requires real-time photo capture of gauge posts

Automatically records timestamp, GPS coordinates, and site ID

Allows manual entry or automatic reading from images

Supports role-based access for field staff, supervisors, and administrators

Integrates with a cloud dashboard for central monitoring

Works offline with local storage, syncing automatically when the internet is available

⚙️ Features
Login & Role Management

Roles: Field Staff, Supervisor, Admin

Offline mode for remote areas

Biometric login support

Data Capture

Live photo capture of gauge posts

Manual entry or scanning of water level readings

QR code verification at sites for authenticity

Location & Security

GPS-based geofencing

Alerts if outside the allowed zone

Tamper detection for skipped or unauthorized readings

Data Management & Dashboard

Real-time sync with central server

Role-based dashboards for supervisors and analysts

Historical data analysis and reporting

🛠️ Technology Stack
Component	Technology
Mobile App	Android (Jetpack Compose, Kotlin)
Backend	Firebase / Node.js
Database	Firebase Firestore / SQL
Geolocation	Google Maps API / Location Services
Image Processing	OpenCV / ML Kit (optional for automated gauge reading)
Authentication	Firebase Auth / Biometric API
🖥️ Screens / UI Flow

Login Screen – Email, Password, Role, Offline Mode

Dashboard – List of monitoring sites, real-time water levels

Capture Reading – Live photo, manual entry, QR verification

Map View – Locations of all monitoring sites, geofencing alerts

Reports / Analytics – Historical data, charts, flood prediction insights

Settings – User profile, app settings, data sync controls

<img width="179" height="395" alt="Screenshot 2025-10-14 213249" src="https://github.com/user-attachments/assets/57bfc350-080d-4ee3-be78-f038331105d2" />

How It Works:
Field staff logs in and selects their role.
They navigate to a monitoring site. The app validates location using geofencing.
Staff captures a photo of the gauge post and optionally enters the reading manually.
Data is tagged with timestamp, GPS coordinates, and site ID and stored locally if offline.
Once online, the app syncs data to the cloud for real-time access.
Supervisors and analysts can view all readings on a dashboard, generate reports, and detect anomalies.

Future Enhancements
AI-based water level reading directly from gauge images
Flood prediction model using historical water level data
Push notifications for abnormal water levels
Integration with IoT sensors for automated water monitoring


<img width="586" height="285" alt="Screenshot 2025-10-14 213302" src="https://github.com/user-attachments/assets/d9ba58d9-50e8-49c8-b6b5-e36dc59f279b" />

[🌐 View Live Website](https://jal-abhilekh-i3tx.vercel.app/)


