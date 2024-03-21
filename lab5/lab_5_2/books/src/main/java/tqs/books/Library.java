package tqs.books;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Library {
    private final List<Book> store = new ArrayList<>();

    public void add(Book book) {
        store.add(book);
    }

    public void remove(Book book) {
        store.remove(book);
    }

    public List<Book> findAllBooks() {
        return store;
    }

    public List<Book> findBooksByDate(final LocalDate from, final LocalDate to) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            LocalDate bookPublishedDate = book.getPublished();
            if (bookPublishedDate.isAfter(from) && bookPublishedDate.isBefore(to)) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> findBooksByAuthor(final String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> findBooksByCategory(final String category) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            if (book.getCategory().equals(category)) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> findBooksByTitle(final String title) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            if (book.getTitle().equals(title)) {
                books.add(book);
            }
        }
        return books;
    }
}
