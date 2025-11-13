import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

@WebServlet("/api/recognitions")
public class RecognitionServlet extends HttpServlet {
    private static final String DATA_FILE = "recognitions.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Student> recognitions = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        loadData();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        String json = objectMapper.writeValueAsString(recognitions);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Student student = objectMapper.readValue(sb.toString(), Student.class);
        recognitions.add(student);
        saveData();

        response.getWriter().write("{\"success\": true}");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private void loadData() {
        try {
            String path = getServletContext().getRealPath("/WEB-INF/" + DATA_FILE);
            File file = new File(path);
            if (file.exists()) {
                Student[] array = objectMapper.readValue(file, Student[].class);
                recognitions = new ArrayList<>(Arrays.asList(array));
            }
        } catch (Exception e) {
            recognitions = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            String path = getServletContext().getRealPath("/WEB-INF/" + DATA_FILE);
            objectMapper.writeValue(new File(path), recognitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}