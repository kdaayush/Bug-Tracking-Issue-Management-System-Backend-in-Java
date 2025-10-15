package bug;
import org.example.common.models.Bug;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.common.JsonUtil;

import java.io.IOException;
import java.util.Map;
import java.util.List;

public class BugController implements HttpHandler {

    private final BugService bugService = new BugService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            handleGetAll(exchange);
        } else if (method.equals("POST") && path.endsWith("/add")) {
            handleAdd(exchange);
        } else if (method.equals("PUT") && path.endsWith("/status")) {
            handleUpdateStatus(exchange);
        } else if (method.equals("DELETE")) {
            handleDelete(exchange);
        } else {
            JsonUtil.sendResponse(exchange, 404, "Invalid endpoint");
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Bug> bugs = bugService.getAllBugs();
        JsonUtil.sendResponse(exchange, 200, bugs);
    }

    private void handleAdd(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        String title = (String) data.get("title");
        String description = (String) data.get("description");
        int projectId = (Integer) data.get("projectId");
        int createdBy = (Integer) data.get("createdBy");

        boolean success = bugService.addBug(title, description, projectId, createdBy);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Bug added successfully" : "Failed to add bug");
    }

    private void handleUpdateStatus(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        int bugId = (Integer) data.get("bugId");
        String status = (String) data.get("status");

        boolean success = bugService.updateStatus(bugId, status);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Bug status updated" : "Failed to update status");
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        int bugId = (Integer) data.get("bugId");

        boolean success = bugService.deleteBug(bugId);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Bug deleted successfully" : "Failed to delete bug");
    }
}
