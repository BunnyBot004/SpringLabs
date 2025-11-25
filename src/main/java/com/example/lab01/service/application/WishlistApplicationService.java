package com.example.lab01.service.application;

import com.example.lab01.dto.AuthorDto;
import com.example.lab01.dto.BookDto;
import com.example.lab01.dto.CountryDto;
import com.example.lab01.dto.UserDto;
import com.example.lab01.dto.WishlistDto;
import com.example.lab01.model.domain.Book;
import com.example.lab01.model.domain.User;
import com.example.lab01.model.domain.Wishlist;
import com.example.lab01.service.domain.WishlistDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistApplicationService {

    private final WishlistDomainService wishlistDomainService;

    public WishlistApplicationService(WishlistDomainService wishlistDomainService) {
        this.wishlistDomainService = wishlistDomainService;
    }

    public WishlistDto getActiveWishlist(User user) {
        Wishlist wishlist = wishlistDomainService.getOrCreateActiveWishlist(user);
        return toDto(wishlist);
    }

    public WishlistDto addBookToWishlist(User user, Long bookId) {
        Wishlist wishlist = wishlistDomainService.addBookToWishlist(user, bookId);
        return toDto(wishlist);
    }

    public WishlistDto removeBookFromWishlist(User user, Long bookId) {
        Wishlist wishlist = wishlistDomainService.removeBookFromWishlist(user, bookId);
        return toDto(wishlist);
    }

    public WishlistDto rentAllBooks(User user) {
        Wishlist wishlist = wishlistDomainService.rentAllBooksFromWishlist(user);
        return toDto(wishlist);
    }

    private WishlistDto toDto(Wishlist wishlist) {
        UserDto userDto = new UserDto(
                wishlist.getUser().getId(),
                wishlist.getUser().getUsername(),
                wishlist.getUser().getName(),
                wishlist.getUser().getSurname(),
                wishlist.getUser().getRole()
        );

        List<BookDto> bookDtos = wishlist.getBooks().stream()
                .map(this::bookToDto)
                .collect(Collectors.toList());

        return new WishlistDto(
                wishlist.getId(),
                userDto,
                bookDtos,
                wishlist.getDateCreated(),
                wishlist.getStatus()
        );
    }

    private BookDto bookToDto(Book book) {
        CountryDto countryDto = new CountryDto(
                book.getAuthor().getCountry().getId(),
                book.getAuthor().getCountry().getName(),
                book.getAuthor().getCountry().getContinent()
        );

        AuthorDto authorDto = new AuthorDto(
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getAuthor().getSurname(),
                countryDto
        );

        return new BookDto(
                book.getId(),
                book.getName(),
                book.getCategory(),
                authorDto,
                book.getAvailableCopies()
        );
    }
}
