package com.example.lab01.web.controller;

import com.example.lab01.model.Country;
import com.example.lab01.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Countries", description = "Country management APIs")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }
}
