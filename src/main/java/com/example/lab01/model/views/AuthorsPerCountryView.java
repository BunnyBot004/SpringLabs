package com.example.lab01.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.authors_per_country")
@Immutable
public class AuthorsPerCountryView {

    @Id
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "num_authors")
    private Integer numAuthors;
}
