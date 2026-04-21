package tests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.hamcrest.CoreMatchers.is;

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

class BonusTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void bonusTest() {
        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1267, 842));

        CookieHelper.acceptCookies(driver, wait);
        ScrollHelper.scrollToNews(driver, wait);

        String originalWindow = driver.getWindowHandle();

        By loginArrow = By.xpath("//div[2]/div/ul/li[2]/div/div[2]");
        By bonusLink = By.xpath("//a[contains(text(),'Активировать бонус-код')]");

        WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(loginArrow));

        new Actions(driver)
                .moveToElement(arrow)
                .pause(Duration.ofMillis(500))
                .click()
                .pause(Duration.ofMillis(500))
                .perform();

        WebElement activateBonus = wait.until(ExpectedConditions.visibilityOfElementLocated(bonusLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activateBonus);

        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }

        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.acceptCookies();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='login']")
        ));

        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();

        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='app']/div/div/div/h1")
        ));

        MatcherAssert.assertThat(title.getText(), is("АКТИВИРОВАТЬ КОД WARGAMING"));
    }
}