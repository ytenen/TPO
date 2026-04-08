package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage extends BasePage {

    public AccountPage(WebDriver driver) {
        super(driver, TestConfig.EXPLICIT_WAIT);
    }

    public boolean isUserLoggedIn(String username) {
        By userNameLocator = By.xpath("//*[contains(text(), '" + username + "')]");

        try {
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("/signin")
            ));

            wait.until(ExpectedConditions.presenceOfElementLocated(userNameLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}