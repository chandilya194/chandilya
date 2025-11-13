# Student Recognition Wall

A minimal web application to showcase student achievements.

## Files
- `index.html` - Complete frontend with embedded CSS/JS
- `RecognitionServlet.java` - Backend servlet for API
- `web.xml` - Web application configuration
- `build.bat` - Build script for Windows

## Features
- Add student recognitions
- View all recognitions in card layout
- Modern responsive UI
- LocalStorage persistence (frontend-only mode)
- Optional Java backend with servlet API

## Quick Start (Frontend Only)
1. Open `index.html` in any web browser
2. Data persists in browser's localStorage

## Full Setup (With Java Backend)
1. Download servlet-api.jar
2. Run `build.bat`
3. Deploy generated WAR to Tomcat
4. Access at `http://localhost:8080/student-recognition-wall`