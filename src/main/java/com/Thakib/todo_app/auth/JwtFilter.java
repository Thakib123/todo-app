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

        // Look for the "Authorization" header on the incoming request
        String authHeader = request.getHeader("Authorization");

        // DEBUG: confirms the filter is actually running, and for which URL.
        // If this line never shows in the logs, the filter isn't being hit at all.
        System.out.println(">>> JWT FILTER hit for: " + request.getMethod() + " " + request.getRequestURI());

        // DEBUG: shows exactly what came in the Authorization header.
        // If this prints "null", the token isn't being sent by the client (Postman) at all.
        System.out.println(">>> Authorization header: " + authHeader);

        // Only proceed if the header exists and is in the expected "Bearer <token>" format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // chop off the "Bearer " prefix to get the raw token

            // Check whether the token is correctly signed and not expired
            boolean valid = jwtUtil.isTokenValid(token);

            // DEBUG: tells us if validation passed or failed.
            // "false" here means the token was rejected — likely a signing/parsing issue on this environment.
            System.out.println(">>> Token valid? " + valid);

            if (valid) {
                // Pull the username out of the token so Spring knows who this is
                String username = jwtUtil.extractUsername(token);

                // DEBUG: confirms we successfully read the user and are about to authenticate them.
                // If we reach here but still get a 403, the problem is AFTER the filter.
                System.out.println(">>> Authenticated user: " + username);

                // Tell Spring Security this request belongs to an authenticated user
                var authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            // DEBUG: the header was missing or didn't start with "Bearer ".
            // Points to a client-side/token-sending problem rather than a validation problem.
            System.out.println(">>> No valid Bearer header present");
        }

        // Always continue the chain — authenticated or not.
        // (If not authenticated, the security rules will reject protected routes with 403.)
        filterChain.doFilter(request, response);
    }
}