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

class CheckingProfileTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private void openProfileMenu(WebDriver driver, WebDriverWait wait) {
        By bell = By.xpath("//span[contains(.,'notification')]");
        By arrow = By.xpath("//div[@id='common_menu']/div/div/div/a/div");
        By profileLink = By.xpath("//div[4]/ul/li/a");

        wait.until(ExpectedConditions.visibilityOfElementLocated(bell));

        for (int i = 0; i < 2; i++) {
            try {
                WebElement arrowElement = wait.until(ExpectedConditions.visibilityOfElementLocated(arrow));

                new Actions(driver)
                        .moveToElement(arrowElement)
                        .pause(Duration.ofMillis(500))
                        .click()
                        .pause(Duration.ofMillis(500))
                        .perform();

                wait.until(ExpectedConditions.visibilityOfElementLocated(profileLink));
                return;
            } catch (TimeoutException e) {

            }
        }

        throw new TimeoutException("Пункт профиля не появился после открытия меню");
    }


    @Test
    void checkingProfileTest() {
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

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.acceptCookies();
        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();

        openProfileMenu(driver, wait);

        WebElement profile = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/ul/li/a"))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profile);


        wait.until(driver -> Objects.equals(((JavascriptExecutor) driver)
                .executeScript("return document.readyState"), "complete"));

        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1/span")
        )).getText();

        MatcherAssert.assertThat(title, is("ЛИЧНЫЙ КАБИНЕТ"));
    }
}