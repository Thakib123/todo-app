package com.Thakib.todo_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Thakib.todo_app.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}