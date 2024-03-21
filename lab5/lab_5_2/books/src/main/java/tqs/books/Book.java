package tqs.books;

import java.time.LocalDate;

public class Book {
    private final String title;
    private final String author;
    private final String category;
    private final LocalDate published;

    public Book(String title, String author, String category, LocalDate published) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.published = published;
    }

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.category = "Unknown";
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getPublished() {
        return published;
    }
}
