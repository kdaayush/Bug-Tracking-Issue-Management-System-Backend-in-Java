package org.example.common.models;

public class Comment {
    private int id;
    private int bugId;
    private int userId;
    private String text;

    public Comment(int id, int bugId, int userId, String text) {
        this.id = id;
        this.bugId = bugId;
        this.userId = userId;
        this.text = text;
    }

    public int getId() { return id; }
    public int getBugId() { return bugId; }
    public int getUserId() { return userId; }
    public String getText() { return text; }

    public void setId(int id) { this.id = id; }
    public void setBugId(int bugId) { this.bugId = bugId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setText(String text) { this.text = text; }
}
