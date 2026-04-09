package pages;

import config.Cookie;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    private final By emailInput = By.xpath("//input[@name='login']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By submitButton = By.xpath("//button[@type='submit']");
    private final By usernameLabel = By.xpath("//div[@id='common_menu']/div/div/div/a/div/span");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void acceptCookies() {
        Cookie.acceptCookies(driver, wait);
    }

    public void fillEmail(String email) {
        WebElement loginField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        );
        loginField.clear();
        loginField.sendKeys(email);
    }

    public void fillPassword(String password) {
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        );
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void submit() {
        WebElement submit = wait.until(
                ExpectedConditions.presenceOfElementLocated(submitButton)
        );
        js.executeScript("arguments[0].click();", submit);
    }

    public String getLoggedInUsername() {
        WebElement username = wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameLabel)
        );
        return username.getText();
    }
}