package com.Thakib.todo_app.auth;

import org.springframework.web.bind.annotation.*;

// Endpoints for registering and (soon) logging in. All start with /api/auth
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register = create a new account
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }
}