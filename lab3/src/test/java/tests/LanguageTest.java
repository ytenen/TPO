package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import utils.CookieHelper;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LanguageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void languageSwitchTest() {

        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1200, 900));

        CookieHelper.acceptCookies(driver, wait);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        By tournaments = By.xpath("//a[contains(@href,'/tournaments')]");
        By regionButton = By.cssSelector(".change-region");
        By languagesLocator = By.xpath("//a[contains(@href,'/set_language/')]");

        String currentText = wait.until(
                ExpectedConditions.presenceOfElementLocated(tournaments)
        ).getText();

        for (int i = 0; i < 20; i++) {

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            WebElement regionBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(regionButton)
            );

            js.executeScript("arguments[0].click();", regionBtn);

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(languagesLocator));

            List<WebElement> languages = driver.findElements(languagesLocator);

            WebElement lang = languages.get(i);

            String langName = lang.getText();
            System.out.println("Checking language: " + langName);

            WebElement oldElement = driver.findElement(tournaments);

            js.executeScript("arguments[0].click();", lang);

            wait.until(ExpectedConditions.stalenessOf(oldElement));

            wait.until(ExpectedConditions.presenceOfElementLocated(tournaments));

            String newText = driver.findElement(tournaments).getText();

            System.out.println("Old: " + currentText + " | New: " + newText);

            assertNotEquals(currentText, newText);

            currentText = newText;
        }
    }
}