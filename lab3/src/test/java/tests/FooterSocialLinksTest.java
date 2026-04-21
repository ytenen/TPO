package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CookieHelper;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FooterSocialLinksTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void socialLinksTest() {

        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1200, 900));

        CookieHelper.acceptCookies(driver, wait);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        List<WebElement> socialLinks = driver.findElements(
                By.cssSelector(".footer_social-item")
        );

        for (int i = 0; i < driver.findElements(By.cssSelector(".footer_social-item")).size(); i++) {

            List<WebElement> links = driver.findElements(By.cssSelector(".footer_social-item"));
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(links.get(i)));

            String originalWindow = driver.getWindowHandle();
            int windowsBefore = driver.getWindowHandles().size();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", link);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

            wait.until(d -> d.getWindowHandles().size() > windowsBefore);

            Set<String> windows = driver.getWindowHandles();
            windows.remove(originalWindow);

            String newWindow = windows.iterator().next();
            driver.switchTo().window(newWindow);

            Assertions.assertFalse(driver.getCurrentUrl().isEmpty());

            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}