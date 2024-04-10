package tqs.bus.bdd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketsPage {

    private final WebDriver driver;

    public TicketsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isInTicketPage() {
        return driver.getCurrentUrl() == "http://localhost:3000/mytickets";
    }

    public boolean isTicketsPageDisplayed() {
        return driver.findElement(By.cssSelector(".text-4xl")).getText().equals("My Tickets");
    }

    public boolean isTicketInfoDisplayed(String name, String email) {
        return driver.findElement(By.cssSelector(".flex:nth-child(3) > .text-2xl")).getText().equals(name)
            && driver.findElement(By.cssSelector(".flex:nth-child(4) > .text-2xl")).getText().equals(email);
    }

}
