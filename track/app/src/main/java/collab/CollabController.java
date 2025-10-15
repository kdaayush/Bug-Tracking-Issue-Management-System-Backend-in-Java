package collab;

import org.example.common.models.User;
import org.example.common.models.Project;

public class CollabController {

    private final CollabService collabService = new CollabService();

    public void assignUser(User user, Project project) {
        collabService.assignUserToProject(user, project);
    }

    public void removeUser(User user, Project project) {
        collabService.removeUserFromProject(user, project);
    }

    public void showProjectMembers(Project project) {
        System.out.println("Project Members: " + collabService.getUsersForProject(project));
    }
}
