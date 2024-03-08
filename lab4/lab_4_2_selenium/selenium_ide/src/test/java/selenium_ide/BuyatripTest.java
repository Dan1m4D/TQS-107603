package selenium_ide;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BuyatripTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void buyatrip() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1028, 1089));
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.getTitle(), is("BlazeDemo - reserve"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(3)")).getText(), is("43"));
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(2)")).getText(), is("555 USD"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2)")).getText(), is("888888"));
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
    }
}
