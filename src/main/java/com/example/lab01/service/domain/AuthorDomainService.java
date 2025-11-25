package com.example.lab01.service.domain;

import com.example.lab01.model.domain.Author;
import com.example.lab01.model.domain.Country;
import com.example.lab01.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorDomainService {

    private final AuthorRepository authorRepository;
    private final CountryDomainService countryDomainService;

    public AuthorDomainService(AuthorRepository authorRepository, CountryDomainService countryDomainService) {
        this.authorRepository = authorRepository;
        this.countryDomainService = countryDomainService;
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
        return authorRepository.save(author);
    }

    public Author update(Long id, String name, String surname, Long countryId) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        Country country = countryDomainService.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + countryId));

        author.setName(name);
        author.setSurname(surname);
        author.setCountry(country);

        return authorRepository.save(author);
    }

    public void deleteById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
}
