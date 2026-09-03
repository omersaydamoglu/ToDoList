package com.nazlim.test2todolist.dto;

import java.time.LocalDate;

public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private String priority;

    public TodoResponse(Long id, String title, String description, boolean completed, LocalDate dueDate, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.priority= priority;
    }


    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDate getDueDate() { return dueDate; }
    public String getPriority() {
        return priority;
    }
}