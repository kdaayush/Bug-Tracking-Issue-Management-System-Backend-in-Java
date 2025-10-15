package auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.common.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthController implements HttpHandler {

    private final AuthService authService = new AuthService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (path.endsWith("/register") && method.equals("POST")) {
            handleRegister(exchange);
        } else if (path.endsWith("/login") && method.equals("POST")) {
            handleLogin(exchange);
        } else {
            JsonUtil.sendResponse(exchange, 404, "Invalid endpoint");
        }
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        String username = (String) data.get("username");
        String password = (String) data.get("password");
        String role = (String) data.get("role");

        boolean success = authService.registerUser(username, password, role);
        JsonUtil.sendResponse(exchange, success ? 200 : 400,
                success ? "User registered successfully!" : "Registration failed!");
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        Map<String, Object> data = JsonUtil.parseJson(exchange);
        String username = (String) data.get("username");
        String password = (String) data.get("password");

        String token = authService.loginUser(username, password);
        Map<String, Object> response = new HashMap<>();
        if (token != null) {
            response.put("token", token);
            JsonUtil.sendResponse(exchange, 200, response);
        } else {
            JsonUtil.sendResponse(exchange, 401, "Invalid username or password");
        }
    }
}
