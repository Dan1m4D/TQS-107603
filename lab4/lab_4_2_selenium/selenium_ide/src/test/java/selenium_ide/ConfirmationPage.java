package selenium_ide;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    private final WebDriver driver;

    @FindBy(css = "tr:nth-child(3) > td:nth-child(2)")
    private WebElement totalPriceElement;

    @FindBy(css = "tr:nth-child(6) > td:nth-child(2)")
    private WebElement accountNumberElement;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTotalPrice() {
        return totalPriceElement.getText();
    }

    public String getAccountNumber() {
        return accountNumberElement.getText();
    }
}
