package com.task.task.services;

import java.util.List;

import com.task.task.models.Task;

public interface TaskService {
    void addTask(String title, String description, boolean done);

    void deleteTask(Long id);

    void updateTask(Long id, String title, String description, boolean done);

    List<Task> getAllTasks();

    Task getTaskById(Long id);
}
