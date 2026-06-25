package com.Thakib.todo_app.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Thakib.todo_app.model.User;
import com.Thakib.todo_app.repository.UserRepository;

// Holds the registration logic
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring injects both the repository and the password hasher we defined in SecurityConfig
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public String register(AuthRequest request) {
        // Don't allow duplicate usernames
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        // Build the new user, HASHING the password before saving
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash it!
        userRepository.save(user);

        return "User registered successfully";
    }
}