package selenium_ide;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {

    private final WebDriver driver;

    @FindBy(css = "tr:nth-child(1) .btn")
    private WebElement selectButton;

    public ReservePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectFlight() {
        selectButton.click();
    }

    public void clickPurchaseButton() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }
}

