package analytics;

import org.example.common.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsService {

    public Map<String, Integer> getOverallStats() {
        Map<String, Integer> stats = new HashMap<>();
        String totalBugs = "SELECT COUNT(*) AS count FROM bugs";
        String openBugs = "SELECT COUNT(*) AS count FROM bugs WHERE status='Open'";
        String inProgressBugs = "SELECT COUNT(*) AS count FROM bugs WHERE status='In Progress'";
        String closedBugs = "SELECT COUNT(*) AS count FROM bugs WHERE status='Closed'";
        String totalProjects = "SELECT COUNT(*) AS count FROM projects";
        String totalUsers = "SELECT COUNT(*) AS count FROM users";

        try (Connection conn = DatabaseConnection.getConnection()) {
            stats.put("totalBugs", getCount(conn, totalBugs));
            stats.put("openBugs", getCount(conn, openBugs));
            stats.put("inProgressBugs", getCount(conn, inProgressBugs));
            stats.put("closedBugs", getCount(conn, closedBugs));
            stats.put("totalProjects", getCount(conn, totalProjects));
            stats.put("totalUsers", getCount(conn, totalUsers));
        } catch (SQLException e) {
            System.out.println("Analytics error: " + e.getMessage());
        }

        return stats;
    }

    public Map<String, Integer> getBugsByProject() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT p.name, COUNT(b.id) AS count FROM bugs b JOIN projects p ON b.project_id = p.id GROUP BY p.name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("name"), rs.getInt("count"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bugs by project: " + e.getMessage());
        }
        return data;
    }

    public Map<String, Integer> getBugsByUser() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT u.name, COUNT(b.id) AS count FROM bugs b JOIN users u ON b.assigned_to = u.id GROUP BY u.name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("name"), rs.getInt("count"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bugs by user: " + e.getMessage());
        }

        return data;
    }

    private int getCount(Connection conn, String query) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt("count") : 0;
        }
    }
}
