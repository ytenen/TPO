package config;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cookie {
    public static void acceptCookies(WebDriver driver, WebDriverWait wait) {
        WebElement cookie = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",cookie);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".onetrust-pc-dark-filter")));
    }
}

