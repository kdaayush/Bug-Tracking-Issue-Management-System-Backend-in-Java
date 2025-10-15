package org.example;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import auth.AuthController;
import project.ProjectController;
import bug.BugController;
import analytics.AnalyticsController;
import collab.CollabController;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Controllers
        AuthController authController = new AuthController();
        ProjectController projectController = new ProjectController();
        BugController bugController = new BugController();
        AnalyticsController analyticsController = new AnalyticsController();
        CollabController collabController = new CollabController();

        // Auth routes
        server.createContext("/auth/register", authController);
        server.createContext("/auth/login", authController);

        // Project routes
        server.createContext("/projects/add", projectController);
        server.createContext("/projects/list", projectController);
        server.createContext("/projects/update", projectController);
        server.createContext("/projects/delete", projectController);

        // Bug routes
        server.createContext("/bugs/add", bugController);
        server.createContext("/bugs/list", bugController);
        server.createContext("/bugs/delete", bugController);
        server.createContext("/bugs/resolve", bugController);

        // Analytics routes
        server.createContext("/analytics/overview", analyticsController);
        server.createContext("/analytics/bugsByProject", analyticsController);
        server.createContext("/analytics/bugsByUser", analyticsController);

        // Collaboration routes (manual usage via CollabController methods for now)
        // For API routes, you can add /collab/assign, /collab/remove etc.

        server.setExecutor(null); // default executor
        server.start();

        System.out.println("Server started on http://localhost:8080");
    }
}
