package com.example.lab01.web.controller;

import com.example.lab01.model.Book;
import com.example.lab01.service.BookService;
import com.example.lab01.web.dto.BookDto;
import com.example.lab01.web.dto.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management APIs")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves a list of all books in the library")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a specific book by its ID")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(convertToDto(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Add new book", description = "Adds a new book to the library")
    public ResponseEntity<BookDto> addBook(@RequestBody BookRequest request) {
        try {
            Book book = bookService.save(
                    request.getName(),
                    request.getCategory(),
                    request.getAuthorId(),
                    request.getAvailableCopies()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(book));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Updates an existing book entry")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        try {
            Book book = bookService.update(
                    id,
                    request.getName(),
                    request.getCategory(),
                    request.getAuthorId(),
                    request.getAvailableCopies()
            );
            return ResponseEntity.ok(convertToDto(book));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Deletes a book that is no longer in good condition")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/rent")
    @Operation(summary = "Rent book", description = "Marks a book as rented by decreasing available copies")
    public ResponseEntity<BookDto> rentBook(@PathVariable Long id) {
        try {
            Book book = bookService.rentBook(id);
            return ResponseEntity.ok(convertToDto(book));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private BookDto convertToDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAuthor().getName() + " " + book.getAuthor().getSurname(),
                book.getAvailableCopies()
        );
    }
}
