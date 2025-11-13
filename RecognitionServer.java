import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;

public class RecognitionServer {
    private static final String DATA_FILE = "recognitions.json";
    private static List<Map<String, String>> recognitions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        loadData();
        HttpServer server = HttpServer.create(new InetSocketAddress(5000), 0);
        
        server.createContext("/api/recognitions", new RecognitionHandler());
        server.setExecutor(null);
        
        System.out.println("Server started on http://localhost:5000");
        server.start();
    }

    static class RecognitionHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                handleGet(exchange);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                handlePost(exchange);
            }
        }

        private void handleGet(HttpExchange exchange) throws IOException {
            String json = buildJsonArray(recognitions);
            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        }

        private void handlePost(HttpExchange exchange) throws IOException {
            String body = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> recognition = parseJson(body);
            recognition.put("date", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            recognitions.add(recognition);
            saveData();
            
            String response = "{\"success\": true}";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }

        private void addCorsHeaders(HttpExchange exchange) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        }
    }

    private static String buildJsonArray(List<Map<String, String>> list) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> item = list.get(i);
            json.append("{")
                .append("\"studentName\":\"").append(escape(item.get("studentName"))).append("\",")
                .append("\"achievement\":\"").append(escape(item.get("achievement"))).append("\",")
                .append("\"description\":\"").append(escape(item.get("description"))).append("\",")
                .append("\"date\":\"").append(escape(item.get("date"))).append("\"")
                .append("}");
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }

    private static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().replaceAll("^\\{|\\}$", "");
        String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("^\"|\"$", "");
                String value = keyValue[1].trim().replaceAll("^\"|\"$", "");
                map.put(key, value);
            }
        }
        return map;
    }

    private static String escape(String str) {
        return str == null ? "" : str.replace("\"", "\\\"").replace("\n", "\\n");
    }

    private static void loadData() {
        try {
            if (Files.exists(Paths.get(DATA_FILE))) {
                String content = Files.readString(Paths.get(DATA_FILE));
                // Simple JSON array parsing
                content = content.trim().replaceAll("^\\[|\\]$", "");
                if (!content.isEmpty()) {
                    String[] items = content.split("\\},\\s*\\{");
                    for (String item : items) {
                        item = item.replaceAll("^\\{|\\}$", "");
                        recognitions.add(parseJson("{" + item + "}"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No existing data file found, starting fresh.");
        }
    }

    private static void saveData() {
        try {
            String json = buildJsonArray(recognitions);
            Files.writeString(Paths.get(DATA_FILE), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}