package selenium_ide;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;

    @FindBy(css = ".btn-primary")
    private WebElement reserveButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://blazedemo.com/");
    }

    public void clickReserveButton() {
        reserveButton.click();
    }
    
}
