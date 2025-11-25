package com.example.lab01.security;

public class JwtConstants {
    public static final String SECRET_KEY = "your-secret-key-must-be-at-least-256-bits-long-for-hs256-algorithm-to-work-properly";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
