package com.example.lab01.service.application;

import com.example.lab01.dto.AuthorDto;
import com.example.lab01.dto.CountryDto;
import com.example.lab01.dto.CreateAuthorDto;
import com.example.lab01.model.domain.Author;
import com.example.lab01.service.domain.AuthorDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorApplicationService {

    private final AuthorDomainService authorDomainService;

    public AuthorApplicationService(AuthorDomainService authorDomainService) {
        this.authorDomainService = authorDomainService;
    }

    public List<AuthorDto> findAll() {
        return authorDomainService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AuthorDto findById(Long id) {
        Author author = authorDomainService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return toDto(author);
    }

    public AuthorDto create(CreateAuthorDto createDto) {
        Author author = authorDomainService.save(
                createDto.name(),
                createDto.surname(),
                createDto.countryId()
        );
        return toDto(author);
    }

    public AuthorDto update(Long id, CreateAuthorDto createDto) {
        Author author = authorDomainService.update(
                id,
                createDto.name(),
                createDto.surname(),
                createDto.countryId()
        );
        return toDto(author);
    }

    public void deleteById(Long id) {
        authorDomainService.deleteById(id);
    }

    private AuthorDto toDto(Author author) {
        CountryDto countryDto = new CountryDto(
                author.getCountry().getId(),
                author.getCountry().getName(),
                author.getCountry().getContinent()
        );

        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getSurname(),
                countryDto
        );
    }
}
