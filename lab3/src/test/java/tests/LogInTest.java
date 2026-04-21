package tests;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.CookieHelper;
import utils.ScrollHelper;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

class LogInTest {
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
    void logInTest() {
        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1267, 842));

        CookieHelper.acceptCookies(driver, wait);
        ScrollHelper.scrollToNews(driver, wait);

        String originalWindow = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Войти')]")
        )).click();

        wait.until(driver -> driver.getWindowHandles().size() >= 1);

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

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='login']")
        ));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.acceptCookies();
        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();

        MatcherAssert.assertThat(
                loginPage.getLoggedInUsername(),
                is("nagibator67_dikiy")
        );
    }
}