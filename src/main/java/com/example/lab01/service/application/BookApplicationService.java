package com.example.lab01.service.application;

import com.example.lab01.dto.AuthorDto;
import com.example.lab01.dto.BookDto;
import com.example.lab01.dto.CountryDto;
import com.example.lab01.dto.CreateBookDto;
import com.example.lab01.model.domain.Book;
import com.example.lab01.service.domain.BookDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookApplicationService {

    private final BookDomainService bookDomainService;

    public BookApplicationService(BookDomainService bookDomainService) {
        this.bookDomainService = bookDomainService;
    }

    public List<BookDto> findAll() {
        return bookDomainService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookDto findById(Long id) {
        Book book = bookDomainService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDto(book);
    }

    public BookDto create(CreateBookDto createDto) {
        Book book = bookDomainService.save(
                createDto.name(),
                createDto.category(),
                createDto.authorId(),
                createDto.availableCopies()
        );
        return toDto(book);
    }

    public BookDto update(Long id, CreateBookDto createDto) {
        Book book = bookDomainService.update(
                id,
                createDto.name(),
                createDto.category(),
                createDto.authorId(),
                createDto.availableCopies()
        );
        return toDto(book);
    }

    public void deleteById(Long id) {
        bookDomainService.deleteById(id);
    }

    public BookDto rentBook(Long id) {
        Book book = bookDomainService.rentBook(id);
        return toDto(book);
    }

    private BookDto toDto(Book book) {
        CountryDto countryDto = new CountryDto(
                book.getAuthor().getCountry().getId(),
                book.getAuthor().getCountry().getName(),
                book.getAuthor().getCountry().getContinent()
        );

        AuthorDto authorDto = new AuthorDto(
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getAuthor().getSurname(),
                countryDto
        );

        return new BookDto(
                book.getId(),
                book.getName(),
                book.getCategory(),
                authorDto,
                book.getAvailableCopies()
        );
    }
}
