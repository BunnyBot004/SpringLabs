package com.example.lab01.dto;

import com.example.lab01.model.enumerations.Role;

public record CreateUserDto(
        String username,
        String password,
        String name,
        String surname,
        Role role
) {
}
