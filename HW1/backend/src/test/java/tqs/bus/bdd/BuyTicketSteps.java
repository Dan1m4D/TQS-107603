package tqs.bus.bdd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BuyTicketSteps {
    private WebDriver driver;
    private HomePage homePage;
    private TicketDetailsPage ticketDetailsPage;
    private TripSelectionPage tripSelectionPage;


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