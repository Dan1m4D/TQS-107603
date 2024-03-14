package selenium_ide;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static io.github.bonigarcia.seljup.BrowserType.FIREFOX;
import static io.github.bonigarcia.seljup.BrowserType.OPERA;
import static io.github.bonigarcia.seljup.BrowserType.SAFARI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class DockerTest {

    private HomePage homePage;
    private ReservePage reservePage;
    private ConfirmationPage confirmationPage;


    @Test
    void testOpera(@DockerBrowser(type = FIREFOX) WebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    @Test
    void buyATripOnOpera(@DockerBrowser(type = FIREFOX) WebDriver driver) {

        driver.manage().window().setSize(new Dimension(1028, 1089));
        homePage = new HomePage(driver);
        reservePage = new ReservePage(driver);
        confirmationPage = new ConfirmationPage(driver);

        // Open home page
        homePage.open();

        // Click on reserve button
        homePage.clickReserveButton();

        // Verify title of reserve page
        assertThat(driver.getTitle(), is("BlazeDemo - reserve"));

        // Select flight and proceed
        reservePage.selectFlight();
        reservePage.clickPurchaseButton();

        // Verify details on confirmation page
        assertThat(confirmationPage.getTotalPrice(), is("555 USD"));
        assertThat(confirmationPage.getAccountNumber(), is("888888"));

        // Verify title of confirmation page
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
    }

}
