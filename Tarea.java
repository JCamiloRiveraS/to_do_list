
package com.javarivera.com.tareas;

import java.util.Date;

public class Tarea {
    private String title;
    private String description;
    private Date dueDate;
    private int priority;
    private String[] tags;
    private boolean completed;

    public Tarea(String title, String description, Date dueDate, int priority, String[] tags) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.tags = tags;
        this.completed = false;
    }

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getDaysRemaining() {
        long diff = dueDate.getTime() - System.currentTimeMillis();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
}
