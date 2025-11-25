package com.example.lab01.dto;

public record LoginResponseDto(
        String token,
        UserDto user
) {
}
