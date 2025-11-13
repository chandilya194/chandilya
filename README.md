# Student Recognition Wall

A web application to showcase student achievements with HTML frontend and Python backend.

## Files
- `index.html` - Frontend with embedded CSS/JS
- `app.py` - Python Flask backend
- `requirements.txt` - Python dependencies
- `Dockerfile` - Docker configuration
- `docker-compose.yml` - Docker Compose setup
- `run.bat` - Run script

## Features
- Add student recognitions
- View all recognitions in card layout
- Modern responsive UI
- JSON file persistence
- Flask REST API

## Quick Start

### Option 1: Local Python
1. Install Python 3.9+
2. Run `run.bat` or:
   ```bash
   pip install -r requirements.txt
   python app.py
   ```
3. Open `index.html` in browser

### Option 2: Docker
```bash
docker-compose up -d
```

## API Endpoints
- `GET /api/recognitions` - Get all recognitions
- `POST /api/recognitions` - Add new recognition

## Access
- Backend: `http://localhost:5000`
- Frontend: Open `index.html` in browser