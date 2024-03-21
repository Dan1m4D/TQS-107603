Feature: Search for a book

    Background: Book Search
        Given A library with the following books
        | Title | Author | Publication Date | Category |
        | The Hobbit | J.R.R. Tolkien | 1937-09-21 | Fantasy |
        | The Lord of the Rings | J.R.R. Tolkien | 1954-07-29 | Fantasy |
        | The Silmarillion | J.R.R. Tolkien | 1977-09-15 | Fantasy |
        | The Lion, the Witch and the Wardrobe | C.S. Lewis | 1950-10-16 | Fantasy |
        | Rich Dad Poor Dad | Robert T Kiyosaki | 1997-04-01 | Finance |
        | The Chocolate Factory | Roald Dahl | 1964-01-17 | Fantasy |

    Scenario: Search for books written by author
        When I search for books written by "J.R.R. Tolkien"
        Then I should see a list with 3 books
        And the list should contain "The Silmarillion"

    Scenario: Search for books by title
        When I search for books titled "The Hobbit"
        Then I should see a list with 1 books
        And the list should contain "The Hobbit"


    Scenario: Search for a book by category
        When I search for books in the "Fantasy" category
        Then I should see a list with 5 books
        And the list should contain "The Lion, the Witch and the Wardrobe"

    Scenario: Search for a book by publication date
        When I search for books published from 1950-01-01 to 1964-01-01
        Then I should see a list with 2 books
        And the list should contain "The Lord of the Rings"

    Scenario: See all books
        When I search for all books
        Then I should see a list with 6 books
        And the list should contain "Rich Dad Poor Dad"

    Scenario: Search for a book that does not exist
        When I search for books written by "J.K Rowling"
        Then I should see a list with 0 books

    Scenario: Add a book
        When I add a book titled "The Goblet of Fire" written by "J.K. Rowling" published in 2000-07-08 and category "Fantasy"
        And I search for all books
        Then I should see a list with 7 books
        And the list should contain "The Goblet of Fire"

    Scenario: Remove a book
        When I remove a book titled "The Goblet of Fire" written by "J.K. Rowling" published in 2000-07-08 and category "Fantasy"
        And I search for all books
        Then I should see a list with 6 books
        And the list should not contain "The Goblet of Fire"