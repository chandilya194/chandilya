@echo off
echo Compiling Java servlet...
javac -cp "servlet-api.jar" RecognitionServlet.java

echo Creating WAR structure...
mkdir WEB-INF
mkdir WEB-INF\classes
copy RecognitionServlet.class WEB-INF\classes\
copy web.xml WEB-INF\

echo Creating WAR file...
jar -cvf student-recognition-wall.war index.html WEB-INF

echo Build complete! Deploy student-recognition-wall.war to your servlet container.
pause