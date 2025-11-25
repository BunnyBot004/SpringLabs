package com.example.lab01.web.controller;

import com.example.lab01.dto.AuthorDto;
import com.example.lab01.dto.CreateAuthorDto;
import com.example.lab01.service.application.AuthorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "Author management endpoints")
public class AuthorController {

    private final AuthorApplicationService authorApplicationService;

    public AuthorController(AuthorApplicationService authorApplicationService) {
        this.authorApplicationService = authorApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorApplicationService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by ID", description = "Retrieves a specific author by their ID")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        try {
            AuthorDto author = authorApplicationService.findById(id);
            return ResponseEntity.ok(author);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Create author", description = "Create a new author (Librarian only)")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody CreateAuthorDto createDto) {
        AuthorDto author = authorApplicationService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Update author", description = "Update an existing author (Librarian only)")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody CreateAuthorDto createDto) {
        try {
            AuthorDto author = authorApplicationService.update(id, createDto);
            return ResponseEntity.ok(author);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Delete author", description = "Delete an author (Librarian only)")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            authorApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
