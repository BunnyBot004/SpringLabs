package com.example.lab01.config;

import com.example.lab01.model.Author;
import com.example.lab01.model.Book;
import com.example.lab01.model.Country;
import com.example.lab01.model.enums.BookCategory;
import com.example.lab01.repository.AuthorRepository;
import com.example.lab01.repository.BookRepository;
import com.example.lab01.repository.CountryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final CountryRepository countryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(CountryRepository countryRepository,
                          AuthorRepository authorRepository,
                          BookRepository bookRepository) {
        this.countryRepository = countryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initData() {
        Country usa = countryRepository.save(new Country("United States", "North America"));
        Country uk = countryRepository.save(new Country("United Kingdom", "Europe"));
        Country russia = countryRepository.save(new Country("Russia", "Europe"));
        Country france = countryRepository.save(new Country("France", "Europe"));
        Country colombia = countryRepository.save(new Country("Colombia", "South America"));

        Author georgeOrwell = authorRepository.save(new Author("George", "Orwell", uk));
        Author jkRowling = authorRepository.save(new Author("J.K.", "Rowling", uk));
        Author stephenKing = authorRepository.save(new Author("Stephen", "King", usa));
        Author leoTolstoy = authorRepository.save(new Author("Leo", "Tolstoy", russia));
        Author gabrielGarcia = authorRepository.save(new Author("Gabriel", "Garcia Marquez", colombia));
        Author janeAusten = authorRepository.save(new Author("Jane", "Austen", uk));
        Author agathiaChristie = authorRepository.save(new Author("Agatha", "Christie", uk));
        Author isaacAsimov = authorRepository.save(new Author("Isaac", "Asimov", usa));

        bookRepository.save(new Book("1984", BookCategory.CLASSICS, georgeOrwell, 5));
        bookRepository.save(new Book("Animal Farm", BookCategory.CLASSICS, georgeOrwell, 3));
        bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", BookCategory.FANTASY, jkRowling, 10));
        bookRepository.save(new Book("Harry Potter and the Chamber of Secrets", BookCategory.FANTASY, jkRowling, 8));
        bookRepository.save(new Book("The Shining", BookCategory.THRILLER, stephenKing, 4));
        bookRepository.save(new Book("It", BookCategory.THRILLER, stephenKing, 6));
        bookRepository.save(new Book("War and Peace", BookCategory.HISTORY, leoTolstoy, 2));
        bookRepository.save(new Book("Anna Karenina", BookCategory.DRAMA, leoTolstoy, 3));
        bookRepository.save(new Book("One Hundred Years of Solitude", BookCategory.NOVEL, gabrielGarcia, 5));
        bookRepository.save(new Book("Pride and Prejudice", BookCategory.CLASSICS, janeAusten, 7));
        bookRepository.save(new Book("Murder on the Orient Express", BookCategory.THRILLER, agathiaChristie, 4));
        bookRepository.save(new Book("Foundation", BookCategory.FANTASY, isaacAsimov, 5));
        bookRepository.save(new Book("I, Robot", BookCategory.FANTASY, isaacAsimov, 6));

        System.out.println("Sample data initialized successfully!");
    }
}
