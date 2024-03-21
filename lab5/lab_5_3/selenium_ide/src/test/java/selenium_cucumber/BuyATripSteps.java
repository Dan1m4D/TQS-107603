package selenium_cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

public class BuyATripSteps {
    private WebDriver driver;

    @Given("I am on the BlazeDemo homepage")
    public void i_am_on_the_blazedemo_homepage() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1028, 1089));
        driver.get("https://blazedemo.com/");
    }

    @When("I click on the {string} button")
    public void i_click_on_the_button(String buttonText) {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @When("I select the first trip")
    public void i_select_the_first_trip() {
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
    }

    @Then("I should see the confirmation page with the correct details")
    public void i_should_see_the_confirmation_page_with_the_correct_details() {
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(2)")).getText(), is("555 USD"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2)")).getText(), is("888888"));
    }
}
