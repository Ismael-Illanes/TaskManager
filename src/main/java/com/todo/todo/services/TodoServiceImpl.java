package com.todo.todo.services;

import com.todo.todo.models.Task;
import com.todo.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void addTask(String title, String description, boolean done) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(done);
        todoRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void updateTask(Long id, String title, String description, boolean done) {
        Optional<Task> taskOptional = todoRepository.findById(id);
        if (taskOptional.isPresent() && taskOptional.get() != null) {
            Task task = taskOptional.get();
            task.setTitle(title);
            task.setDescription(description);
            task.setDone(done);
            todoRepository.save(task);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }
}
