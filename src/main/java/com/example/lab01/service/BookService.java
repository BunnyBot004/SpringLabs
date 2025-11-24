package com.example.lab01.service;

import com.example.lab01.model.Book;
import com.example.lab01.model.enums.BookCategory;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(String name, BookCategory category, Long authorId, Integer availableCopies);
    Book update(Long id, String name, BookCategory category, Long authorId, Integer availableCopies);
    void deleteById(Long id);
    Book rentBook(Long id);
}
