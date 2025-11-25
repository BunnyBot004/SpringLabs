package com.example.lab01.service.domain;

import com.example.lab01.model.domain.Country;
import com.example.lab01.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryDomainService {

    private final CountryRepository countryRepository;

    public CountryDomainService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    public Country save(String name, String continent) {
        Country country = new Country(name, continent);
        return countryRepository.save(country);
    }

    public Country update(Long id, String name, String continent) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));

        country.setName(name);
        country.setContinent(continent);

        return countryRepository.save(country);
    }

    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }
}
