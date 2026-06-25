package com.Thakib.todo_app.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// @Component = Spring manages this so we can inject it into our auth service
@Component
public class JwtUtil {

    // The secret key used to sign tokens. Must be long enough for the algorithm.
    // In a real app this lives in a config file / environment variable, NOT in code.
    private final Key key = Keys.hmacShaKeyFor(
        "my-super-secret-key-that-is-at-least-32-bytes-long!!".getBytes()
    );

    // How long a token stays valid: 24 hours (in milliseconds)
    private final long expirationMs = 1000 * 60 * 60 * 24;

    // CREATE a token for a given username
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)                                  // who the token belongs to
                .issuedAt(new Date())                               // when it was made
                .expiration(new Date(System.currentTimeMillis() + expirationMs)) // when it expires
                .signWith(key)                                      // sign it so it can't be faked
                .compact();                                         // build the final string
    }

    // READ the username back out of a token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // VALIDATE a token: is it correctly signed AND not expired?
    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date()); // not expired = valid
        } catch (Exception e) {
            return false; // any error (bad signature, malformed, etc.) = invalid
        }
    }

    // Helper: verify the signature and pull out the token's data ("claims")
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}