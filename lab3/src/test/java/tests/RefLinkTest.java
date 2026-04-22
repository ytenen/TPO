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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import pages.LoginPage;
import utils.CookieHelper;

import java.time.Duration;
import java.util.*;

class RefLinkTest {
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
  void refLinkTest() throws InterruptedException {
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
      loginPage.submit();

      WebDriverWait waitAfterLogin = new WebDriverWait(driver, Duration.ofSeconds(15));

      WebElement refIcon = waitAfterLogin.until(
              d -> d.findElement(By.xpath("//div[@id='common_menu']/div/div/div[4]/a[2]/span"))
      );

      Actions builder = new Actions(driver);
      builder.moveToElement(refIcon).pause(Duration.ofMillis(500)).perform();

      WebElement arrow = waitAfterLogin.until(
              d -> d.findElement(By.xpath("//div[2]/div/ul/li[6]/div/div[2]"))
      );

      builder.moveToElement(arrow)
              .pause(Duration.ofMillis(300))
              .click()
              .pause(Duration.ofMillis(300))
              .perform();

      WebElement inviteLink = waitAfterLogin.until(
              d -> d.findElement(By.xpath("//a[contains(text(),'Пригласить друга')]"))
      );

      js.executeScript("arguments[0].click();", inviteLink);

      waitAfterLogin.until(d ->
              "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState"))
      );

      By textLocator = By.xpath("//section[@id='practice']/div[2]/div/div/div[3]/div/div/div[2]/div/div[3]");

      WebElement textBlock = waitAfterLogin.until(d -> d.findElement(textLocator));

      js.executeScript("arguments[0].scrollIntoView({block:'center'});", textBlock);
      js.executeScript("window.scrollBy(0, -200)");

      waitAfterLogin.until(d -> !d.findElement(textLocator).getText().isEmpty());

      MatcherAssert.assertThat(
              driver.findElement(textLocator).getText(),
              is("Минимальное количество боёв, которое должно быть у командира для доступа к отправке реферальных ссылок: 600.")
      );
  }
}
