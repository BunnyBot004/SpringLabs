package com.example.lab01.service.domain;

import com.example.lab01.events.AuthorCreatedEvent;
import com.example.lab01.events.AuthorDeletedEvent;
import com.example.lab01.events.AuthorUpdatedEvent;
import com.example.lab01.model.domain.Author;
import com.example.lab01.model.domain.Country;
import com.example.lab01.projection.AuthorNameProjection;
import com.example.lab01.repository.AuthorRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorDomainService {

    private final AuthorRepository authorRepository;
    private final CountryDomainService countryDomainService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthorDomainService(AuthorRepository authorRepository,
                               CountryDomainService countryDomainService,
                               ApplicationEventPublisher eventPublisher) {
        this.authorRepository = authorRepository;
        this.countryDomainService = countryDomainService;
        this.eventPublisher = eventPublisher;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author save(String name, String surname, Long countryId) {
        Country country = countryDomainService.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + countryId));

        Author author = new Author(name, surname, country);
        Author savedAuthor = authorRepository.save(author);
        eventPublisher.publishEvent(new AuthorCreatedEvent(this, savedAuthor));
        return savedAuthor;
    }

    public Author update(Long id, String name, String surname, Long countryId) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        Country country = countryDomainService.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + countryId));

        author.setName(name);
        author.setSurname(surname);
        author.setCountry(country);

        Author updatedAuthor = authorRepository.save(author);
        eventPublisher.publishEvent(new AuthorUpdatedEvent(this, updatedAuthor));
        return updatedAuthor;
    }

    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        authorRepository.deleteById(id);
        eventPublisher.publishEvent(new AuthorDeletedEvent(this, author));
    }

    public List<AuthorNameProjection> findAllNames() {
        return authorRepository.findAllProjectedBy();
    }
}
