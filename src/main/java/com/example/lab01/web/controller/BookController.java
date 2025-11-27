package com.example.lab01.web.controller;

import com.example.lab01.dto.BookDto;
import com.example.lab01.dto.CreateBookDto;
import com.example.lab01.model.views.BooksPerAuthorView;
import com.example.lab01.repository.BooksPerAuthorViewRepository;
import com.example.lab01.service.application.BookApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management endpoints")
public class BookController {

    private final BookApplicationService bookApplicationService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public BookController(BookApplicationService bookApplicationService,
                          BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.bookApplicationService = bookApplicationService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves a list of all books in the library")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookApplicationService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a specific book by its ID")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        try {
            BookDto book = bookApplicationService.findById(id);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Add new book", description = "Adds a new book to the library (Librarian only)")
    public ResponseEntity<BookDto> addBook(@RequestBody CreateBookDto createDto) {
        try {
            BookDto book = bookApplicationService.create(createDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Update book", description = "Updates an existing book entry (Librarian only)")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody CreateBookDto createDto) {
        try {
            BookDto book = bookApplicationService.update(id, createDto);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Delete book", description = "Deletes a book that is no longer in good condition (Librarian only)")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/rent")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Rent book", description = "Marks a book as rented by decreasing available copies (Librarian only)")
    public ResponseEntity<BookDto> rentBook(@PathVariable Long id) {
        try {
            BookDto book = bookApplicationService.rentBook(id);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-author")
    @Operation(summary = "Get books count by author", description = "Returns the number of books per author from materialized view (refreshed hourly)")
    public ResponseEntity<List<BooksPerAuthorView>> getBooksByAuthor() {
        List<BooksPerAuthorView> stats = booksPerAuthorViewRepository.findAll();
        return ResponseEntity.ok(stats);
    }
}
