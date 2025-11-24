package com.example.lab01.web.dto;

import com.example.lab01.model.enums.BookCategory;

public class BookDto {
    private Long id;
    private String name;
    private BookCategory category;
    private Long authorId;
    private String authorName;
    private Integer availableCopies;

    public BookDto() {
    }

    public BookDto(Long id, String name, BookCategory category, Long authorId, String authorName, Integer availableCopies) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.authorId = authorId;
        this.authorName = authorName;
        this.availableCopies = availableCopies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }
}
