//http://localhost:8080/api/todos

package com.Thakib.todo_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Thakib.todo_app.model.Todo;
import com.Thakib.todo_app.service.TodoService;

// @RestController = this class handles web requests and returns JSON
@RestController
// @RequestMapping = every URL in this class starts with /api/todos
@RequestMapping("/api/todos")
public class TodoController {

    // Hold the service so the controller can delegate the real work to it
    private final TodoService todoService;

    // Constructor injection: Spring auto-provides the service
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET /api/todos = return the full list of todos
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    // GET /api/todos/1 = return one todo. @PathVariable pulls the "1" out of the URL
    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    // POST /api/todos = create a todo. @RequestBody turns the incoming JSON into a Todo object
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    // PUT /api/todos/1 = update todo #1 with the JSON you send
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    // DELETE /api/todos/1 = delete todo #1
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}