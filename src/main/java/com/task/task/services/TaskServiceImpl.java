package com.task.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.task.models.Task;
import com.task.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void addTask(String title, String description, boolean done) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(done);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTask(Long id, String title, String description, boolean done) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent() && taskOptional.get() != null) {
            Task task = taskOptional.get();
            task.setTitle(title);
            task.setDescription(description);
            task.setDone(done);
            taskRepository.save(task);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
}
