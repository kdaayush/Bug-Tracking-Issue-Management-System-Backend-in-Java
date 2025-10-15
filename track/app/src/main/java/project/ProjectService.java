package project;

import org.example.common.DatabaseConnection;
import org.example.common.models.Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    public boolean addProject(String name, String description, int createdBy) {
        String sql = "INSERT INTO projects (name, description, created_by, status) VALUES (?, ?, ?, 'Planning')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, createdBy);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding project: " + e.getMessage());
            return false;
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("created_by"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching projects: " + e.getMessage());
        }
        return projects;
    }

    public boolean updateStatus(int projectId, String newStatus) {
        String sql = "UPDATE projects SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, projectId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating project status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting project: " + e.getMessage());
            return false;
        }
    }
}
