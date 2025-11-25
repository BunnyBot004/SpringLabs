package com.example.lab01.web.controller;

import com.example.lab01.dto.WishlistDto;
import com.example.lab01.model.domain.User;
import com.example.lab01.service.application.WishlistApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Wishlist management endpoints")
public class WishlistController {

    private final WishlistApplicationService wishlistApplicationService;

    public WishlistController(WishlistApplicationService wishlistApplicationService) {
        this.wishlistApplicationService = wishlistApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get active wishlist", description = "Retrieve the current user's active wishlist")
    public ResponseEntity<WishlistDto> getActiveWishlist(@AuthenticationPrincipal User user) {
        WishlistDto wishlistDto = wishlistApplicationService.getActiveWishlist(user);
        return ResponseEntity.ok(wishlistDto);
    }

    @PostMapping("/add/{bookId}")
    @Operation(summary = "Add book to wishlist", description = "Add a book to the current user's wishlist (only if available)")
    public ResponseEntity<WishlistDto> addBookToWishlist(@AuthenticationPrincipal User user,
                                                          @PathVariable Long bookId) {
        WishlistDto wishlistDto = wishlistApplicationService.addBookToWishlist(user, bookId);
        return ResponseEntity.ok(wishlistDto);
    }

    @DeleteMapping("/remove/{bookId}")
    @Operation(summary = "Remove book from wishlist", description = "Remove a book from the current user's wishlist")
    public ResponseEntity<WishlistDto> removeBookFromWishlist(@AuthenticationPrincipal User user,
                                                               @PathVariable Long bookId) {
        WishlistDto wishlistDto = wishlistApplicationService.removeBookFromWishlist(user, bookId);
        return ResponseEntity.ok(wishlistDto);
    }

    @PostMapping("/rent-all")
    @Operation(summary = "Rent all books from wishlist", description = "Rent all books from the wishlist and mark it as completed")
    public ResponseEntity<WishlistDto> rentAllBooks(@AuthenticationPrincipal User user) {
        WishlistDto wishlistDto = wishlistApplicationService.rentAllBooks(user);
        return ResponseEntity.ok(wishlistDto);
    }
}
