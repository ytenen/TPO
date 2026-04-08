package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected void scrollBy(int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0, arguments[0]);", pixels);
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                element
        );
    }
}