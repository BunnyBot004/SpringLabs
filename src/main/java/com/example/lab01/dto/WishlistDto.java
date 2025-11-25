package com.example.lab01.dto;

import com.example.lab01.model.enumerations.WishlistStatus;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistDto(
        Long id,
        UserDto user,
        List<BookDto> books,
        LocalDateTime dateCreated,
        WishlistStatus status
) {
}
