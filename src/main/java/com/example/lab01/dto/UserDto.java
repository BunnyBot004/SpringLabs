package com.example.lab01.dto;

import com.example.lab01.model.enumerations.Role;

public record UserDto(
        Long id,
        String username,
        String name,
        String surname,
        Role role
) {
}
