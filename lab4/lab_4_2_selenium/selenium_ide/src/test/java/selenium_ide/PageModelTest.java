package selenium_ide;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

 class PageModelTest {
    
    private WebDriver driver;
    private HomePage homePage;
    private ReservePage reservePage;
    private ConfirmationPage confirmationPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1028, 1089));
        homePage = new HomePage(driver);
        reservePage = new ReservePage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void buyATrip() {
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
