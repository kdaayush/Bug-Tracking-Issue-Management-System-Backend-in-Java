package org.example.common.models;

public class Bug {
    private int id;
    private String title;
    private String description;
    private int projectId;
    private int createdBy;
    private String status;

    public Bug(int id, String title, String description, int projectId, int createdBy, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.createdBy = createdBy;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getProjectId() { return projectId; }
    public int getCreatedBy() { return createdBy; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
