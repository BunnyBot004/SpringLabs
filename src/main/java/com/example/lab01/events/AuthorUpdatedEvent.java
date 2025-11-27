package com.example.lab01.events;

import com.example.lab01.model.domain.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthorUpdatedEvent extends ApplicationEvent {

    private final Author author;

    public AuthorUpdatedEvent(Object source, Author author) {
        super(source);
        this.author = author;
    }
}
