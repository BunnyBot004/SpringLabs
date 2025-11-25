package com.example.lab01.dto;

import com.example.lab01.model.enumerations.BookCategory;

public record CreateBookDto(
        String name,
        BookCategory category,
        Long authorId,
        Integer availableCopies
) {
}
