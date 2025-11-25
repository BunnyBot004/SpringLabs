package com.example.lab01.dto;

import com.example.lab01.model.enumerations.BookCategory;

public record BookDto(
        Long id,
        String name,
        BookCategory category,
        AuthorDto author,
        Integer availableCopies
) {
}
