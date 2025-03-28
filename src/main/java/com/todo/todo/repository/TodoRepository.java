package com.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.todo.models.Task;

@Repository
public interface TodoRepository extends JpaRepository<Task, Long> {

}
