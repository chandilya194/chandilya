import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

@WebServlet("/api/recognitions")
public class RecognitionServlet extends HttpServlet {
    private List<Map<String, String>> recognitions = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < recognitions.size(); i++) {
            Map<String, String> rec = recognitions.get(i);
            json.append("{")
                .append("\"studentName\":\"").append(rec.get("studentName")).append("\",")
                .append("\"achievement\":\"").append(rec.get("achievement")).append("\",")
                .append("\"description\":\"").append(rec.get("description")).append("\",")
                .append("\"date\":\"").append(rec.get("date")).append("\"")
                .append("}");
            if (i < recognitions.size() - 1) json.append(",");
        }
        json.append("]");
        
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String json = sb.toString();
        Map<String, String> recognition = new HashMap<>();
        
        // Simple JSON parsing
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                recognition.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        
        recognition.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        recognitions.add(recognition);

        response.getWriter().write("{\"success\": true}");
    }
}