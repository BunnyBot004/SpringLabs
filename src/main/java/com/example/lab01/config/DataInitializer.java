package com.example.lab01.config;

import com.example.lab01.model.domain.Author;
import com.example.lab01.model.domain.Book;
import com.example.lab01.model.domain.Country;
import com.example.lab01.model.domain.User;
import com.example.lab01.model.enumerations.BookCategory;
import com.example.lab01.model.enumerations.Role;
import com.example.lab01.repository.AuthorRepository;
import com.example.lab01.repository.BookRepository;
import com.example.lab01.repository.CountryRepository;
import com.example.lab01.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final CountryRepository countryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CountryRepository countryRepository,
                          AuthorRepository authorRepository,
                          BookRepository bookRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.countryRepository = countryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        // Initialize Users
        User user1 = new User("john.doe", passwordEncoder.encode("password123"), "John", "Doe", Role.ROLE_USER);
        User user2 = new User("jane.smith", passwordEncoder.encode("password123"), "Jane", "Smith", Role.ROLE_USER);
        User librarian = new User("librarian", passwordEncoder.encode("admin123"), "Admin", "Librarian", Role.ROLE_LIBRARIAN);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(librarian);

        // Initialize Countries
        Country usa = countryRepository.save(new Country("United States", "North America"));
        Country uk = countryRepository.save(new Country("United Kingdom", "Europe"));
        Country russia = countryRepository.save(new Country("Russia", "Europe"));
        Country france = countryRepository.save(new Country("France", "Europe"));
        Country colombia = countryRepository.save(new Country("Colombia", "South America"));

        // Initialize Authors
        Author georgeOrwell = authorRepository.save(new Author("George", "Orwell", uk));
        Author jkRowling = authorRepository.save(new Author("J.K.", "Rowling", uk));
        Author stephenKing = authorRepository.save(new Author("Stephen", "King", usa));
        Author leoTolstoy = authorRepository.save(new Author("Leo", "Tolstoy", russia));
        Author gabrielGarcia = authorRepository.save(new Author("Gabriel", "Garcia Marquez", colombia));
        Author janeAusten = authorRepository.save(new Author("Jane", "Austen", uk));
        Author agathiaChristie = authorRepository.save(new Author("Agatha", "Christie", uk));
        Author isaacAsimov = authorRepository.save(new Author("Isaac", "Asimov", usa));

        // Initialize Books
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
        System.out.println("Users created:");
        System.out.println("  - john.doe / password123 (USER)");
        System.out.println("  - jane.smith / password123 (USER)");
        System.out.println("  - librarian / admin123 (LIBRARIAN)");
    }
}
