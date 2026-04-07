package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By loginButton = By.xpath("//a[contains(@href,'auth/oid/new')]");

    public HomePage(WebDriver driver) {
        super(driver, TestConfig.EXPLICIT_WAIT);
    }

    public HomePage open() {
        driver.get(TestConfig.BASE_URL);
        return this;
    }

    public HomePage scrollDown() {
        scrollBy(800);
        return this;
    }

    public LoginPage clickLogin() {
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        scrollIntoView(login);
        login.click();
        return new LoginPage(driver);
    }
}