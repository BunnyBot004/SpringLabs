package com.example.lab01.service.domain;

import com.example.lab01.model.domain.Author;
import com.example.lab01.model.domain.Book;
import com.example.lab01.model.enumerations.BookCategory;
import com.example.lab01.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookDomainService {

    private final BookRepository bookRepository;
    private final AuthorDomainService authorDomainService;

    public BookDomainService(BookRepository bookRepository, AuthorDomainService authorDomainService) {
        this.bookRepository = bookRepository;
        this.authorDomainService = authorDomainService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(String name, BookCategory category, Long authorId, Integer availableCopies) {
        Author author = authorDomainService.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        Book book = new Book(name, category, author, availableCopies);
        return bookRepository.save(book);
    }

    public Book update(Long id, String name, BookCategory category, Long authorId, Integer availableCopies) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        Author author = authorDomainService.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        book.setName(name);
        book.setCategory(category);
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);

        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public Book rentBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies of this book");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return bookRepository.save(book);
    }
}
