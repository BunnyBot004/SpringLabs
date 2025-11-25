package com.example.lab01.web.controller;

import com.example.lab01.dto.CountryDto;
import com.example.lab01.dto.CreateCountryDto;
import com.example.lab01.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Countries", description = "Country management endpoints")
public class CountryController {

    private final CountryApplicationService countryApplicationService;

    public CountryController(CountryApplicationService countryApplicationService) {
        this.countryApplicationService = countryApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryApplicationService.findAll();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get country by ID", description = "Retrieves a specific country by its ID")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        try {
            CountryDto country = countryApplicationService.findById(id);
            return ResponseEntity.ok(country);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Create country", description = "Create a new country (Librarian only)")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CreateCountryDto createDto) {
        CountryDto country = countryApplicationService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(country);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Update country", description = "Update an existing country (Librarian only)")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CreateCountryDto createDto) {
        try {
            CountryDto country = countryApplicationService.update(id, createDto);
            return ResponseEntity.ok(country);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    @Operation(summary = "Delete country", description = "Delete a country (Librarian only)")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        try {
            countryApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
