package com.Thakib.todo_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity = this becomes a database table. @Table names it "users" (USER is reserved in SQL)
@Entity
@Table(name = "users")
public class User {

    // Primary key, auto-generated like in Todo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique = no two users can register the same username
    @Column(unique = true, nullable = false)
    private String username;

    // Stores the HASHED password, never the plain text one
    @Column(nullable = false)
    private String password;

    // ---- Getters and Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}