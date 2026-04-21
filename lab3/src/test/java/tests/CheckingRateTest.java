package tests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.CookieHelper;
import utils.ScrollHelper;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class CheckingRateTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void checkingRateTest() {
        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1267, 842));

        CookieHelper.acceptCookies(driver, wait);
        ScrollHelper.scrollToNews(driver, wait);

        String originalWindow = driver.getWindowHandle();
        By loginInput = By.xpath("//input[@name='login']");

        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Войти')]")
        ));
        js.executeScript("arguments[0].click();", loginBtn);

        wait.until(driver ->
                driver.getWindowHandles().size() > 1
                        || driver.getCurrentUrl().contains("/auth/")
                        || !driver.findElements(loginInput).isEmpty()
        );

        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }

        wait.until(driver -> Objects.equals(
                ((JavascriptExecutor) driver).executeScript("return document.readyState"),
                "complete"
        ));

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.acceptCookies();
        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();

        By profileArrow = By.xpath("//div[2]/div/ul/li[2]/div/div[2]");
        By ratingsLink = By.xpath("//a[contains(text(),'Рейтинги')]");

        WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(profileArrow));

        new Actions(driver)
                .moveToElement(arrow)
                .pause(Duration.ofMillis(500))
                .click()
                .pause(Duration.ofMillis(300))
                .perform();

        WebElement ratings = wait.until(ExpectedConditions.visibilityOfElementLocated(ratingsLink));
        js.executeScript("arguments[0].click();", ratings);

        MatcherAssert.assertThat(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//a[contains(@href, '#')])[22]")
                )).getText(),
                is("СКРЫТЬ МОИ РЕЗУЛЬТАТЫ")
        );
    }
}