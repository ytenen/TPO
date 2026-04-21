package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrollHelper {

    public static void scrollToNews(WebDriver driver, WebDriverWait wait) {

        By newsButton = By.xpath("//button[contains(.,'К новостям')]");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(newsButton)
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

    }
}