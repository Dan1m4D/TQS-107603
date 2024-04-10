package tqs.bus.bdd;

// TripSelectionPage.java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TripSelectionPage {
    private WebDriver driver;

    public TripSelectionPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isTripAvailable(String tripDetails) {
        return driver.findElement(By.cssSelector(".text-3xl")).getText().equals(tripDetails);
    }

    public void selectTrip() {
        driver.findElement(By.cssSelector(".flex-col:nth-child(1)")).click();
    }
}

