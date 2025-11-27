package com.example.lab01.repository;

import com.example.lab01.model.domain.User;
import com.example.lab01.model.domain.Wishlist;
import com.example.lab01.model.enumerations.WishlistStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @EntityGraph(attributePaths = {"books"})
    Optional<Wishlist> findByUserAndStatus(User user, WishlistStatus status);
}
