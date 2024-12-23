package com.teamtreehouse.techdegrees.model;

// Implementing Todo Model
public class Todo {
    private int id;
    private String name;
    private boolean isCompleted;

    // Constructor
    //changed boolean in isComplete to string for test
    public Todo(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;
        return id == todo.id && isCompleted == todo.isCompleted && name.equals(todo.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + Boolean.hashCode(isCompleted);
        return result;
    }
}
