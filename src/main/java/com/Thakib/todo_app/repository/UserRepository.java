package com.Thakib.todo_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thakib.todo_app.model.User;

// Gives us all the CRUD methods for User, just like TodoRepository did for Todo
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom finder: look up a user by their username (needed for login)
    // Spring auto-writes the query just from the method name!
    Optional<User> findByUsername(String username);
}