# Student Recognition Wall

A web application to showcase student achievements with HTML frontend and Java backend.

## Files
- `index.html` - Frontend with embedded CSS/JS
- `RecognitionServer.java` - Java HTTP server backend
- `run-java.bat` - Java run script
- `Dockerfile-java` - Docker for Java

## Features
- Add student recognitions
- View all recognitions in card layout
- Modern responsive UI
- JSON file persistence
- REST API with Java

## Quick Start

### Java Backend
1. Install Java 11+
2. Run `run-java.bat` or:
   ```bash
   javac RecognitionServer.java
   java RecognitionServer
   ```
3. Open `index.html` in browser

### Docker
```bash
docker build -f Dockerfile-java -t recognition-java .
docker run -p 5000:5000 recognition-java
```

## API Endpoints
- `GET /api/recognitions` - Get all recognitions
- `POST /api/recognitions` - Add new recognition

## Access
- Backend: `http://localhost:5000`
- Frontend: Open `index.html` in browser