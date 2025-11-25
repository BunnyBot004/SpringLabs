package com.example.lab01.model.domain;

import com.example.lab01.model.enumerations.WishlistStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlists")
@Getter
@Setter
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_books",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WishlistStatus status;

    public Wishlist() {
    }

    public Wishlist(User user) {
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.status = WishlistStatus.CREATED;
        this.books = new ArrayList<>();
    }
}
