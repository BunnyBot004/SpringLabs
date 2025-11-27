package com.example.lab01.jobs;

import com.example.lab01.repository.BooksPerAuthorViewRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTasks {

    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public ScheduledTasks(BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    /**
     * Refreshes the books_per_author materialized view every hour
     * Cron expression: "0 0 * * * *" means:
     * - 0 seconds
     * - 0 minutes (top of the hour)
     * - * every hour
     * - * every day
     * - * every month
     * - * every day of week
     */
    @Scheduled(cron = "0 0 * * * *")
    public void refreshBooksPerAuthorView() {
        this.booksPerAuthorViewRepository.refreshMaterializedView();
    }
}
