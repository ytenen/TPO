package tests;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import pages.LoginPage;
import utils.CookieHelper;

import java.time.Duration;
import java.util.*;

class CheckingRateTest {
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
  void checkingRateTest() throws InterruptedException {
    driver.get("https://worldoftanks.eu/ru/");
      driver.manage().window().setSize(new Dimension(1267, 842));
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      CookieHelper.acceptCookies(driver, wait);

      js.executeScript("window.scrollBy(0,900)");
      Thread.sleep(3000);
      js.executeScript("window.scrollTo(0,0)");
      Thread.sleep(3000);

      driver.findElement(By.xpath("//a[contains(text(),'Войти')]")).click();

      String originalWindow = driver.getWindowHandle();

      for (String windowHandle : driver.getWindowHandles()) {
          if (!windowHandle.equals(originalWindow)) {
              driver.switchTo().window(windowHandle);
              break;
          }
      }

      LoginPage loginPage = new LoginPage(driver);
      loginPage.acceptCookies();
      loginPage.fillEmail("rosh.28@mail.ru");
      loginPage.fillPassword("Qfc12erty");
      loginPage.submit();
      Thread.sleep(3000);
    driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")).click();
      Thread.sleep(3000);

      driver.findElement(By.xpath("//a[contains(text(),\'Рейтинги\')]")).click();
      Thread.sleep(3000);

      MatcherAssert.assertThat(driver.findElement(By.xpath("(//a[contains(@href, \'#\')])[22]")).getText(), is("СКРЫТЬ МОИ РЕЗУЛЬТАТЫ"));
  }
}
