# Student Recognition Wall

A web application to showcase student achievements and recognitions.

## Features
- Add student recognitions with name, achievement, and description
- View all recognitions in a responsive card layout
- Modern UI with gradient background and hover effects
- JSON-based data persistence

## Technology Stack
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java Servlets
- **Build Tool**: Maven
- **Server**: Any servlet container (Tomcat, Jetty, etc.)

## Setup Instructions

1. **Prerequisites**:
   - Java 11 or higher
   - Maven 3.6+
   - Apache Tomcat 9+ or similar servlet container

2. **Build the project**:
   ```bash
   mvn clean package
   ```

3. **Deploy**:
   - Copy the generated WAR file from `target/` to your Tomcat `webapps/` directory
   - Start Tomcat server

4. **Access**:
   - Open browser and navigate to `http://localhost:8080/student-recognition-wall`

## API Endpoints
- `GET /api/recognitions` - Retrieve all recognitions
- `POST /api/recognitions` - Add new recognition

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   ├── Student.java
│   │   └── RecognitionServlet.java
│   └── webapp/
│       ├── index.html
│       ├── styles.css
│       ├── script.js
│       └── WEB-INF/
│           └── web.xml
```