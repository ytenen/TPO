package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private final By emailInput = By.xpath("//input[@name='login']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By submitButton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver, TestConfig.EXPLICIT_WAIT);
    }

    public LoginPage waitForLoginForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        return this;
    }

    public LoginPage enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        field.clear();
        field.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        field.clear();
        field.sendKeys(password);
        return this;
    }

    public LoginPage clickSubmit() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        button.click();
        return this;
    }

    public LoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        return this;
    }

    public boolean isLoginStillVisible() {
        return !driver.findElements(emailInput).isEmpty();
    }
}