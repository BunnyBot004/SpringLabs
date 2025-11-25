package com.example.lab01.service.application;

import com.example.lab01.dto.CountryDto;
import com.example.lab01.dto.CreateCountryDto;
import com.example.lab01.model.domain.Country;
import com.example.lab01.service.domain.CountryDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryApplicationService {

    private final CountryDomainService countryDomainService;

    public CountryApplicationService(CountryDomainService countryDomainService) {
        this.countryDomainService = countryDomainService;
    }

    public List<CountryDto> findAll() {
        return countryDomainService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CountryDto findById(Long id) {
        Country country = countryDomainService.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        return toDto(country);
    }

    public CountryDto create(CreateCountryDto createDto) {
        Country country = countryDomainService.save(
                createDto.name(),
                createDto.continent()
        );
        return toDto(country);
    }

    public CountryDto update(Long id, CreateCountryDto createDto) {
        Country country = countryDomainService.update(
                id,
                createDto.name(),
                createDto.continent()
        );
        return toDto(country);
    }

    public void deleteById(Long id) {
        countryDomainService.deleteById(id);
    }

    private CountryDto toDto(Country country) {
        return new CountryDto(
                country.getId(),
                country.getName(),
                country.getContinent()
        );
    }
}
