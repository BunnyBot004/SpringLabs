package com.example.lab01.dto;

public record AuthorDto(
        Long id,
        String name,
        String surname,
        CountryDto country
) {
}
