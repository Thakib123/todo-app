package com.Thakib.todo_app.auth;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component so Spring manages it. OncePerRequestFilter = runs once on every request
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Look for the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // 2. If it exists and starts with "Bearer ", pull out the token part
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // chop off "Bearer "

            // 3. If the token is valid, tell Spring this user is authenticated
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.extractUsername(token);

                var authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 4. Always continue the chain (whether authenticated or not)
        filterChain.doFilter(request, response);
    }
}