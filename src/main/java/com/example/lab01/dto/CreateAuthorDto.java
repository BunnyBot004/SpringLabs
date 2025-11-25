package com.example.lab01.dto;

public record CreateAuthorDto(
        String name,
        String surname,
        Long countryId
) {
}
