package com.Thakib.todo_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Thakib.todo_app.model.Todo;
import com.Thakib.todo_app.repository.TodoRepository;

// @Service = tells Spring to manage this class so it can be injected elsewhere
@Service
public class TodoService {

    // Holds the repository so we can talk to the database. final = can't be reassigned
    private final TodoRepository todoRepository;

    // Constructor injection: Spring auto-passes the repository in, so we never write "new"
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // READ all: returns every todo in the database
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // READ one: find a todo by id, or throw an error if it doesn't exist (avoids null)
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    // CREATE: save a brand new todo to the database
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // UPDATE: fetch the existing todo, change its fields, then save it back
    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo todo = getTodoById(id);                       // reuse our method (also handles "not found")
        todo.setTitle(updatedTodo.getTitle());             // copy new title in
        todo.setDescription(updatedTodo.getDescription()); // copy new description in
        todo.setCompleted(updatedTodo.isCompleted());      // copy new completed status in
        return todoRepository.save(todo);                  // save = updates since the id already exists
    }

    // DELETE: remove the todo with this id from the database
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}