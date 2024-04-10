package tqs.bus.bdd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TicketDetailsPage {

    private WebDriver driver;

    public TicketDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(email);
    }

    public void clickCheckoutButton() {
        driver.findElement(By.cssSelector(".btn")).click();
    }

    public String getSeatConfirmationMessage() {
        return driver.findElement(By.cssSelector(".flex:nth-child(1) > .flex > .text-2xl")).getText();
    }
    
    public void selectSeat() {
        driver.findElement(By.cssSelector(".w-1\\/2:nth-child(2) .btn")).click();
        driver.findElement(By.cssSelector(".w-12:nth-child(1) > .mx-1")).click();
    }
}
