package tqs.bus.bdd;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuyTicketSteps {
    private WebDriver driver;
    private HomePage homePage;
    private TicketsPage ticketsPage;
    private TicketDetailsPage ticketDetailsPage;
    private TripSelectionPage tripSelectionPage;
    /*
     * @Given("User is on the homepage")
     * public void user_is_on_the_homepage() {
     * driver = new ChromeDriver();
     * driver.get("http://localhost:3000/");
     * driver.manage().window().setSize(new Dimension(1028, 1209));
     * homePage = new HomePage(driver);
     * }
     * 
     * @When("User selects Aveiro as origin and Mirandela as destination")
     * public void user_selects_origin_and_destination() {
     * homePage.selectOrigin("Aveiro");
     * homePage.selectDestination("Mirandela");
     * }
     * 
     * @Then("User should see available trips")
     * public void user_should_see_available_trips() {
     * assertTrue(homePage.isTripsAvailable());
     * }
     * 
     * @When("User selects a trip")
     * public void user_selects_a_trip() {
     * searchResultPage = homePage.searchForTrips();
     * searchResultPage.selectFirstTrip();
     * }
     * 
     * @When("User selects a seat")
     * public void user_selects_a_seat() {
     * ticketDetailsPage.selectSeat();
     * }
     * 
     * @When("User enters name and email")
     * public void user_enters_name_and_email() {
     * ticketDetailsPage.enterEmail("daniel.madureira@ua.pt");
     * ticketDetailsPage.enterName("Daniel Madureira");
     * }
     * 
     * @Then("User should see the ticket details")
     * public void user_should_see_the_ticket_details() {
     * assertTrue(ticketDetailsPage.isTicketDetailsDisplayed());
     * }
     * 
     * @Given("I am on the home page")
     * public void i_am_on_the_home_page() {
     * driver = new ChromeDriver();
     * driver.get("http://localhost:3000/");
     * driver.manage().window().setSize(new Dimension(1028, 1209));
     * homePage = new HomePage(driver);
     * }
     * 
     * @And("User buys the ticket")
     * public void user_buys_the_ticket() {
     * ticketDetailsPage.clickBuyTicketButton();
     * }
     * 
     * @When("I select the same origin and destination")
     * public void i_select_the_same_origin_and_destination() {
     * homePage.selectOrigin("Aveiro");
     * homePage.selectDestination("Aveiro");
     * }
     * 
     * @When("I click the search button")
     * public void i_click_the_search_button() {
     * searchResultPage = homePage.searchForTrips();
     * }
     * 
     * @Then("I should see a message indicating no trips are available")
     * public void i_should_see_a_message_indicating_no_trips_are_available() {
     * assertTrue(searchResultPage.isNoTripsMessageDisplayed());
     * }
     * 
     * @After
     * public void tearDown() {
     * if (driver != null) {
     * driver.quit();
     * }
     * }
     * }
     */



    @Given("User is on the homepage")
    public void user_is_on_the_homepage() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePage.navigateTo();
    }

    @When("User selects Aveiro as origin and Mirandela as destination")
    public void user_selects_origin_and_destination() {
        homePage.selectOrigin("Aveiro");
        homePage.selectDestination("Mirandela");
        homePage.clickSearchButton();
    }

    @Then("User should see available trips")
    public void user_should_see_available_trips() {
        tripSelectionPage = new TripSelectionPage(driver);
    }

    @When("User selects a trip")
    public void user_selects_a_trip() {
        tripSelectionPage.selectTrip();
    }

    @And("User selects a seat")
    public void user_selects_a_seat() {
        ticketDetailsPage = new TicketDetailsPage(driver);
        ticketDetailsPage.selectSeat();
    }

    @And("User enters name and email")
    public void user_enters_name_and_email() {
        ticketDetailsPage.enterName("Daniel Madureira");
        ticketDetailsPage.enterEmail("daniel.madureira@ua.pt");
    }

    @Then("User buys the ticket")
    public void user_buys_the_ticket() {
        ticketDetailsPage.clickCheckoutButton();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}