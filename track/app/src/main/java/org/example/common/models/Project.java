package org.example.common.models;

public class Project {
    private int id;
    private String name;
    private String description;
    private int createdBy;
    private String status;

    public Project(int id, String name, String description, int createdBy, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCreatedBy() { return createdBy; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public void setStatus(String status) { this.status = status; }
}
