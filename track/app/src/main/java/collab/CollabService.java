package collab;

import org.example.common.models.User;
import org.example.common.models.Project;

import java.util.*;

public class CollabService {

    private final Map<Integer, List<User>> projectUsers = new HashMap<>();

    public void assignUserToProject(User user, Project project) {
        projectUsers.computeIfAbsent(project.getId(), k -> new ArrayList<>()).add(user);
        System.out.println("Assigned " + user.getName() + " to project " + project.getName());
    }

    public void removeUserFromProject(User user, Project project) {
        List<User> users = projectUsers.get(project.getId());
        if (users != null) {
            users.removeIf(u -> u.getId() == user.getId());
            System.out.println("Removed " + user.getName() + " from project " + project.getName());
        }
    }

    public List<User> getUsersForProject(Project project) {
        return projectUsers.getOrDefault(project.getId(), new ArrayList<>());
    }
}
