package com.example.lab01.events;

import com.example.lab01.repository.AuthorsPerCountryViewRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventHandlers {

    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;

    public AuthorEventHandlers(AuthorsPerCountryViewRepository authorsPerCountryViewRepository) {
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
    }

    @EventListener
    public void handleAuthorCreated(AuthorCreatedEvent event) {
        this.authorsPerCountryViewRepository.refreshMaterializedView();
    }

    @EventListener
    public void handleAuthorUpdated(AuthorUpdatedEvent event) {
        this.authorsPerCountryViewRepository.refreshMaterializedView();
    }

    @EventListener
    public void handleAuthorDeleted(AuthorDeletedEvent event) {
        this.authorsPerCountryViewRepository.refreshMaterializedView();
    }
}
