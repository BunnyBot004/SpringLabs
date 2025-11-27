package com.example.lab01.repository;

import com.example.lab01.model.domain.Author;
import com.example.lab01.projection.AuthorNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<AuthorNameProjection> findAllProjectedBy();
}
