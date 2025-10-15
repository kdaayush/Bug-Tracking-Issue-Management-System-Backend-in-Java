package bug;

import org.example.common.DatabaseConnection;
import org.example.common.models.Bug;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BugService {

    public boolean addBug(String title, String description, int projectId, int createdBy) {
        String sql = "INSERT INTO bugs (title, description, project_id, created_by, status) VALUES (?, ?, ?, ?, 'Open')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, projectId);
            stmt.setInt(4, createdBy);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding bug: " + e.getMessage());
            return false;
        }
    }

    public List<Bug> getAllBugs() {
        List<Bug> bugs = new ArrayList<>();
        String sql = "SELECT * FROM bugs";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bugs.add(new Bug(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("project_id"),
                        rs.getInt("created_by"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bugs: " + e.getMessage());
        }
        return bugs;
    }

    public boolean updateStatus(int bugId, String newStatus) {
        String sql = "UPDATE bugs SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, bugId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating bug status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBug(int bugId) {
        String sql = "DELETE FROM bugs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bugId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting bug: " + e.getMessage());
            return false;
        }
    }
}
