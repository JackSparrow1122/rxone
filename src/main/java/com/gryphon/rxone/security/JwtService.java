package com.gryphon.rxone.security;

import com.gryphon.rxone.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // 🔐 Use a strong secret (at least 32 chars for HS256)
    private static final String SECRET = "this_is_a_super_secret_key_for_jwt_123456";

    private static final long EXPIRATION_MILLIS = 86400000L; // 24 hours

    // ✅ Generate secure key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ Generate token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpirationMillis() {
        return EXPIRATION_MILLIS;
    }

    // ✅ Extract email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Validate token
    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    // 🔍 Check expiry
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 🔍 Parse claims safely
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}