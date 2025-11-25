package com.example.lab01.service.domain;

import com.example.lab01.model.domain.Book;
import com.example.lab01.model.domain.User;
import com.example.lab01.model.domain.Wishlist;
import com.example.lab01.model.enumerations.WishlistStatus;
import com.example.lab01.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistDomainService {

    private final WishlistRepository wishlistRepository;
    private final BookDomainService bookDomainService;

    public WishlistDomainService(WishlistRepository wishlistRepository, BookDomainService bookDomainService) {
        this.wishlistRepository = wishlistRepository;
        this.bookDomainService = bookDomainService;
    }

    public Optional<Wishlist> findActiveWishlistByUser(User user) {
        return wishlistRepository.findByUserAndStatus(user, WishlistStatus.CREATED);
    }

    public Wishlist getOrCreateActiveWishlist(User user) {
        return findActiveWishlistByUser(user)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist(user);
                    return wishlistRepository.save(wishlist);
                });
    }

    public Wishlist addBookToWishlist(User user, Long bookId) {
        Book book = bookDomainService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies of this book");
        }

        Wishlist wishlist = getOrCreateActiveWishlist(user);

        if (!wishlist.getBooks().contains(book)) {
            wishlist.getBooks().add(book);
            return wishlistRepository.save(wishlist);
        }

        return wishlist;
    }

    public Wishlist removeBookFromWishlist(User user, Long bookId) {
        Wishlist wishlist = findActiveWishlistByUser(user)
                .orElseThrow(() -> new RuntimeException("No active wishlist found"));

        Book book = bookDomainService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        wishlist.getBooks().remove(book);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist rentAllBooksFromWishlist(User user) {
        Wishlist wishlist = findActiveWishlistByUser(user)
                .orElseThrow(() -> new RuntimeException("No active wishlist found"));

        if (wishlist.getBooks().isEmpty()) {
            throw new RuntimeException("Wishlist is empty");
        }

        // Rent all books (decrease available copies)
        for (Book book : wishlist.getBooks()) {
            bookDomainService.rentBook(book.getId());
        }

        // Mark wishlist as completed
        wishlist.setStatus(WishlistStatus.COMPLETED);
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> findAll() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }
}
