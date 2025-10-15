package analytics;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.common.JsonUtil;

import java.io.IOException;

public class AnalyticsController implements HttpHandler {

    private final AnalyticsService analyticsService = new AnalyticsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.endsWith("/overview")) {
            JsonUtil.sendResponse(exchange, 200, analyticsService.getOverallStats());
        } else if (path.endsWith("/bugsByProject")) {
            JsonUtil.sendResponse(exchange, 200, analyticsService.getBugsByProject());
        } else if (path.endsWith("/bugsByUser")) {
            JsonUtil.sendResponse(exchange, 200, analyticsService.getBugsByUser());
        } else {
            JsonUtil.sendResponse(exchange, 404, "Invalid analytics endpoint");
        }
    }
}
