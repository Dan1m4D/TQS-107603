package tqs.books;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BookSearchSteps {
    private Library library;
    private List<Book> searchResults;

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    private LocalDate toIso8601Date(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Given("A library with the following books")
    public void aLibraryWithTheFollowingBooks(DataTable booksTable) {
        library = new Library();
        List<Map<String, String>> books = booksTable.asMaps(String.class, String.class);
        for (Map<String, String> book : books) {
            String title = book.get("Title");
            String author = book.get("Author");
            String category = book.get("Category");
            // Assuming the published date is in the format "yyyy-MM-dd"
            LocalDate published = toIso8601Date(book.get("Publication Date"));
            library.add(new Book(title, author, category, published));
        }
    }

    @Given("a book titled {string} written by {string} published in {iso8601Date} and category {string}")
    public void aBookTitledWrittenByPublishedInAndCategory(String title, String author, LocalDate published,
            String category) {
        library = new Library();
        library.add(new Book(title, author, category, published));
    }

    @When("I add a book titled {string} written by {string} published in {iso8601Date} and category {string}")
    public void iAddABookTitledWrittenByPublishedInAndCategory(String title, String author, LocalDate published,
            String category) {
        library.add(new Book(title, author, category, published));
    }

    @When("I remove a book titled {string} written by {string} published in {iso8601Date} and category {string}")
    public void iRemoveABookTitledWrittenByPublishedInAndCategory(String title, String author, LocalDate published,
            String category) {
        library.remove(new Book(title, author, category, published));
    }

    @When("I search for books written by {string}")
    public void iSearchForBooksBy(String author) {
        searchResults = library.findBooksByAuthor(author);
    }

    @When("I search for books titled {string}")
    public void iSearchForBooksTitled(String title) {
        searchResults = library.findBooksByTitle(title);
    }

    @When("I search for books in the {string} category")
    public void iSearchForBooksInCategory(String category) {
        searchResults = library.findBooksByCategory(category);
    }

    @When("I search for books published from {iso8601Date} to {iso8601Date}")
    public void iSearchForBooksPublishedFromTo(LocalDate from, LocalDate to) {
        searchResults = library.findBooksByDate(from, to);
    }

    @When("I search for all books")
    public void iSearchForAllBooks() {
        searchResults = library.findAllBooks();
    }

    @Then("I should see a list with {int} books")
    public void iShouldSeeAListWithBooks(int count) {
        int size = searchResults.size();
        assertEquals(count, size);
    }

    @And("the list should contain {string}")
    public void theListShouldContain(String title) {
        boolean found = false;
        for (Book book : searchResults) {
            if (book.getTitle().equals(title)) {
                found = true;
                break;
            }
        }
        assertEquals(true, found);
    }

    @And("the list should not contain {string}")
    public void theListShouldNotContain(String title) {
        boolean found = false;
        for (Book book : searchResults) {
            if (book.getTitle().equals(title)) {
                found = true;
                break;
            }
        }
        assertEquals(false, found);
    }

}