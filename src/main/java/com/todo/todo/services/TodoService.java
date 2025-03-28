package com.todo.todo.services;

import java.util.List;

import com.todo.todo.models.Task;

public interface TodoService {
    void addTask(String title, String description, boolean done);

    void deleteTask(Long id);

    void updateTask(Long id, String title, String description, boolean done);

    List<Task> getAllTasks();

    Task getTaskById(Long id);
}
