package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import utils.CookieHelper;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FooterLinksTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void footerLinksTest() {

        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1200, 900));

        CookieHelper.acceptCookies(driver, wait);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        List<WebElement> links = driver.findElements(By.cssSelector(".footer_link"));

        for (int i = 0; i < links.size(); i++) {

            links = driver.findElements(By.cssSelector(".footer_link"));
            WebElement link = links.get(i);

            String originalWindow = driver.getWindowHandle();
            String urlBefore = driver.getCurrentUrl();
            int windowsBefore = driver.getWindowHandles().size();

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", link);
            js.executeScript("arguments[0].click();", link);

            wait.until(driver ->
                    driver.getWindowHandles().size() > windowsBefore
                            || !driver.getCurrentUrl().equals(urlBefore)
            );

            if (driver.getWindowHandles().size() > windowsBefore) {

                Set<String> windows = driver.getWindowHandles();
                windows.remove(originalWindow);

                String newWindow = windows.iterator().next();
                driver.switchTo().window(newWindow);

                assertFalse(driver.getCurrentUrl().isEmpty());

                driver.close();
                driver.switchTo().window(originalWindow);

            } else {

                assertFalse(driver.getCurrentUrl().equals(urlBefore));

                driver.navigate().back();

                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".footer_link")
                ));
            }

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }
}