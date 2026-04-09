package tests;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.CookieHelper;

import java.time.Duration;
import java.util.*;

class BonusTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }


  @AfterEach
  public void tearDown() {
    driver.quit();
  }
    @Test
    void bonusTest() throws InterruptedException {
        driver.get("https://worldoftanks.eu/ru/");
        driver.manage().window().setSize(new Dimension(1267, 842));
        LoginPage loginPage = new LoginPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CookieHelper.acceptCookies(driver, wait);

        js.executeScript("window.scrollBy(0,900)");
        Thread.sleep(3000);
        js.executeScript("window.scrollTo(0,0)");
        Thread.sleep(3000);

        js.executeScript("document.querySelector('.promo-fullscreen_content')?.remove();");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div/ul/li[2]/div/div[2]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Активировать бонус-код')]"))).click();
        Thread.sleep(3000);

        loginPage.acceptCookies();
        loginPage.fillEmail("rosh.28@mail.ru");
        loginPage.fillPassword("Qfc12erty");
        loginPage.submit();
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']/div/div/div/h1")));
        MatcherAssert.assertThat(title.getText(), is("АКТИВИРОВАТЬ КОД WARGAMING"));
    }
}
