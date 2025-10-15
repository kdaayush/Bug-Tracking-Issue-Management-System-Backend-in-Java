package project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.common.JsonUtil;
import org.example.common.models.Project;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectController implements HttpHandler {

    private final ProjectService projectService = new ProjectService();

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
        List<Project> projects = projectService.getAllProjects();
        JsonUtil.sendResponse(exchange, 200, projects);
    }

    private void handleAdd(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        int createdBy = (Integer) data.get("createdBy");

        boolean success = projectService.addProject(name, description, createdBy);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Project added successfully" : "Failed to add project");
    }

    private void handleUpdateStatus(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        int projectId = (Integer) data.get("projectId");
        String newStatus = (String) data.get("status");

        boolean success = projectService.updateStatus(projectId, newStatus);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Status updated successfully" : "Failed to update status");
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        int projectId = (Integer) data.get("projectId");

        boolean success = projectService.deleteProject(projectId);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "Project deleted successfully" : "Failed to delete project");
    }
}
