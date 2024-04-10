package tqs.bus.bdd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get("http://localhost:3000/");
    }

    public void selectOrigin(String origin) {
        WebElement originDropdown = driver.findElement(By.cssSelector(".flex:nth-child(1) > .w-\\[20vw\\]"));
        originDropdown.click();
        driver.findElement(By.xpath("//option[text()='" + origin + "']")).click();
    }

    public void selectDestination(String destination) {
        WebElement destinationDropdown = driver.findElement(By.cssSelector(".flex:nth-child(2) > .w-\\[20vw\\]"));
        destinationDropdown.click();
        driver.findElement(By.xpath("//option[text()='" + destination + "']")).click();
    }

    public void clickSearchButton() {
        driver.findElement(By.cssSelector(".m-4")).click();
    }
}



