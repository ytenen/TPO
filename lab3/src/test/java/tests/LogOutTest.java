package tests;

import static org.hamcrest.CoreMatchers.is;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.LoginPage;
import utils.CookieHelper;
import utils.ScrollHelper;

class LogOutTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Map<String, Object> vars;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void logOutTest() {
        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1267, 842));

        CookieHelper.acceptCookies(driver, wait);
        waitForCookieOverlayToDisappear();

        ScrollHelper.scrollToNews(driver, wait);

        openLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.acceptCookies();
        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();

        openUserMenu();

        By logoutLink = By.xpath("//a[contains(text(),'Выйти')]");
        WebElement logoutElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutLink)
        );

        js.executeScript("arguments[0].click();", logoutElement);

        wait.until(driver ->
                "complete".equals(js.executeScript("return document.readyState"))
        );

        String loginText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Войти')]")
        )).getText();

        MatcherAssert.assertThat(loginText, is("Войти"));
    }

    private void openLoginPage() {
        By loginButton = By.xpath("//a[contains(text(),'Войти')]");
        By loginInput = By.xpath("//input[@name='login']");

        String originalWindow = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        wait.until(driver -> {
            boolean secondWindowOpened = driver.getWindowHandles().size() > 1;
            boolean authUrlOpened = driver.getCurrentUrl().contains("/auth/")
                    || driver.getCurrentUrl().contains("/id/")
                    || driver.getCurrentUrl().contains("login");
            boolean loginFieldExists = !driver.findElements(loginInput).isEmpty();

            return secondWindowOpened || authUrlOpened || loginFieldExists;
        });

        switchToNewWindowIfOpened(originalWindow);

        wait.until(driver ->
                "complete".equals(js.executeScript("return document.readyState"))
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));
    }

    private void switchToNewWindowIfOpened(String originalWindow) {
        Set<String> windowHandles = driver.getWindowHandles();

        if (windowHandles.size() > 1) {
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    return;
                }
            }
        }
    }

    private void openUserMenu() {
        By bell = By.xpath("//div[@id='common_menu']//a[contains(@href,'notifications') or contains(@class,'notification') or contains(@class,'bell')]");
        By arrow = By.xpath("//div[@id='common_menu']/div/div/div/a/div");
        By logoutLink = By.xpath("//a[contains(text(),'Выйти')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(bell));

        for (int i = 0; i < 2; i++) {
            try {
                WebElement arrowElement = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(arrow)
                );

                new Actions(driver)
                        .moveToElement(arrowElement)
                        .pause(Duration.ofMillis(500))
                        .click()
                        .pause(Duration.ofMillis(500))
                        .perform();

                wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
                return;

            } catch (TimeoutException e) {
                // повторная попытка
            }
        }

        throw new TimeoutException("Пункт 'Выйти' не появился после открытия меню");
    }

    private void waitForCookieOverlayToDisappear() {
        By overlay = By.xpath("//div[contains(@class,'onetrust-pc-dark-filter')]");

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (TimeoutException e) {
            js.executeScript(
                    "document.querySelectorAll('.onetrust-pc-dark-filter').forEach(el => el.remove());"
            );
        }
    }
}