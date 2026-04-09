package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieHelper {
    public static void acceptCookies(WebDriver driver, WebDriverWait wait) {
        WebElement cookie = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",cookie);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".onetrust-pc-dark-filter")));
    }
}