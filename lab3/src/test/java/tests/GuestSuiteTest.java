package tests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.CookieHelper;

import java.time.Duration;
import java.util.*;

public class GuestSuiteTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  private WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
    wait = new WebDriverWait(driver, Duration.ofSeconds(3));
  }

  private void acceptCookiesV2() {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(
          By.cssSelector("button#onetrust-accept-btn-handler")));
      acceptBtn.click();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(
          By.cssSelector("div.onetrust-pc-dark-filter")));
    } catch (Exception e) {
    }
  }

  private void sleep(){
      try {
          Thread.sleep(1000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
  }

  private void closePromoBanner() {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

      try {
        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("div.promo-fullscreen_close, button.promo-close, .fullscreen-promo__close, .promo-close-btn")));
        closeBtn.click();
      } catch (Exception e1) {
        try {
          driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
        } catch (Exception e2) {
          js.executeScript("var banners = document.querySelectorAll('div.promo-fullscreen_content, div.promo-fullscreen, .promo-overlay, .modal-overlay'); banners.forEach(function(b) { b.remove(); });");
        }
      }

      wait.until(ExpectedConditions.invisibilityOfElementLocated(
          By.cssSelector("div.promo-fullscreen_content, div.promo-fullscreen")));
    } catch (Exception e) {
      try {
        js.executeScript("var banners = document.querySelectorAll('div.promo-fullscreen_content, div.promo-fullscreen, .promo-overlay, .modal-overlay, .onetrust-pc-dark-filter'); banners.forEach(function(b) { b.remove(); });");
      } catch (Exception ignored) {}
    }
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  void participateTournament() {
    driver.get("https://worldoftanks.eu/ru/");
    driver.manage().window().setSize(new Dimension(1230, 724));

    CookieHelper.acceptCookies(driver, wait);
    closePromoBanner();
    NavigationHelper.waitForPageLoad(wait);
    NavigationHelper.scrollAndStabilize(js);

    WebElement tournamentsLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Турниры')]")));
    tournamentsLink.click();

    WebElement participateButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'УЧАСТВОВАТЬ') or contains(text(),'Участвовать')]")));
    participateButton.click();

    WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement heading = extendedWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1, .layout_airy_wrapper_id h1, #layout_airy_wrapper_id h1")));

    String headingText = heading.getText();
    assertTrue(headingText.contains("ВХОД") || headingText.contains("вход"),
            "Должна открыться страница входа, но найден: " + headingText);
  }

  @Test
  void tanksSearchSystem() {
    driver.get("https://worldoftanks.eu/ru/");
    driver.manage().window().setSize(new Dimension(1470, 920));

    CookieHelper.acceptCookies(driver, wait);
    closePromoBanner();
    NavigationHelper.waitForPageLoad(wait);
    NavigationHelper.scrollAndStabilize(js);

    openMainMenu();
    selectNationAndTypeFilters();
    verifyTankDetails();

    WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    assertEquals("ЛЁГКИЙ ТАНК", extendedWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/span[2]/span/span[2]"))).getText());
    assertEquals("СССР", extendedWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/span/span[2]"))).getText());
  }

  private void openMainMenu() {
    try {
      WebElement promoLink = wait.until(
              ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='wargaming.net'], .promo a, .banner a")));
      new Actions(driver).moveToElement(promoLink).perform();
    } catch (Exception e) {
      // Продолжаем выполнение, если промо-ссылка не найдена
    }

    wait.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));

    WebElement menuItem = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")));
    new Actions(driver).moveToElement(menuItem).perform();
    menuItem.click();

    WebElement tankopedia = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Танковедение')]")));
    ScrollHelper.scrollToElement(driver, js, tankopedia);
    tankopedia.click();
  }

  private void selectNationAndTypeFilters() {
    WebElement nationFilter = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//header/div[2]/div/div/div/div/div")));
    new Actions(driver).moveToElement(nationFilter).perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'СССР')]"))).click();

    WebElement typeFilter = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//header/div[2]/div/div/div/div/div[2]/span")));
    new Actions(driver).moveToElement(typeFilter).perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'Лёгкие танки')]"))).click();
  }

  private void verifyTankDetails() {
    ScrollHelper.scrollToTop(driver, js);
    js.executeScript("window.scrollTo(0, 400)");
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Т-116')]"))).click();
  }

  @Test
  void tryingCodeActivation() {
    driver.get("https://worldoftanks.eu/ru/");
    driver.manage().window().setSize(new Dimension(1470, 920));

    CookieHelper.acceptCookies(driver, wait);
    closePromoBanner();
    NavigationHelper.waitForPageLoad(wait);
    NavigationHelper.scrollAndStabilize(js);

    WebElement menuItem = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")));
    new Actions(driver).moveToElement(menuItem).perform();
    menuItem.click();

    WebElement bonusCode = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Активировать бонус-код')]")));
    ScrollHelper.scrollToElement(driver, js, bonusCode);
    bonusCode.click();
    WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement heading = extendedWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='layout_airy_wrapper_id']/h1")));

    String headingText = heading.getText();
    assertTrue(headingText.contains("ВХОД") || headingText.contains("вход"),
            "Должна открыться страница входа, но найден: " + headingText);
  }

  @Test
  void downloadGame() {
    driver.get("https://worldoftanks.eu/ru/");
    driver.manage().window().setSize(new Dimension(1430, 837));

    CookieHelper.acceptCookies(driver, wait);
    closePromoBanner();
    NavigationHelper.waitForPageLoad(wait);
    NavigationHelper.scrollAndStabilize(js);

    WebElement menuItem = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")));
    Actions builder = new Actions(driver);
    builder.moveToElement(menuItem).perform();
    menuItem.click();

    WebElement downloadLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Скачать')]")));
    builder.moveToElement(downloadLink).perform();

    WebElement download = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Скачать')]")));
    ScrollHelper.scrollToElement(driver, js, download);
    download.click();

    assertEquals("СКАЧАТЬ", wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/a[2]/span"))).getText());
  }

  @Test
  public void friendFinding() throws InterruptedException {
    driver.get("https://worldoftanks.eu/ru/");
    acceptCookiesV2();
    closePromoBanner();
    driver.manage().window().setSize(new Dimension(1430, 837));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

    js.executeScript("window.scrollTo(0, 1000)");
    sleep();
    js.executeScript("window.scrollTo(0, 0)");

    WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div/ul/li[6]/div/div[2]")));
    Actions actions = new Actions(driver);
    actions.moveToElement(menuItem).perform();
    menuItem.click();

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Поиск игроков')]"))).click();

    WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("account_table_search")));

    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("arguments[0].click();", searchInput);
    jsExecutor.executeScript("arguments[0].value = '';", searchInput);
    jsExecutor.executeScript("arguments[0].value = arguments[1];", searchInput, "kbshka_kormit"); // установка значения
    jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", searchInput); // триггерим событие input
    jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", searchInput); // триггерим событие change

    sleep();

    searchInput.sendKeys(Keys.ENTER);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/ru/community/accounts/725955610-kbshka_kormit/')]")));

    driver.findElement(By.xpath("//a[contains(@href, '/ru/community/accounts/725955610-kbshka_kormit/')]")).click();

    WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[2]/div[2]/div/div/div/div")));
    MatcherAssert.assertThat(resultElement.getText(), is("kbshka_kormit"));
  }

  public static class ScrollHelper {
    public static void scrollToTop(WebDriver driver, JavascriptExecutor js) {
      js.executeScript("window.scrollTo(0, 0)");
    }

    public static void scrollToBottom(JavascriptExecutor js) {
      js.executeScript("window.scrollTo(0, 1000)");
    }

    public static void scrollToElement(WebDriver driver, JavascriptExecutor js, WebElement element) {
      js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
  }

  public static class NavigationHelper {
    public static void waitForPageLoad(WebDriverWait wait) {
      wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
    }

    public static void scrollAndStabilize(JavascriptExecutor js) {
      scrollToBottom(js);
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
      scrollToTop(js);
    }

    private static void scrollToBottom(JavascriptExecutor js) {
      js.executeScript("window.scrollTo(0, 1000)");
    }

    private static void scrollToTop(JavascriptExecutor js) {
      js.executeScript("window.scrollTo(0, 0)");
    }
  }

  public static class MenuHelper {
    public static void openMenuItem(WebDriverWait wait, Actions builder, JavascriptExecutor js,
                                    String menuXpath, String submenuText) {
      WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXpath)));
      builder.moveToElement(menuItem).perform();
      menuItem.click();

      WebElement submenuItem = wait.until(ExpectedConditions.elementToBeClickable(
              By.xpath(String.format("//a[contains(text(),'%s')]", submenuText))));
      ScrollHelper.scrollToElement(null, js, submenuItem);
      submenuItem.click();
    }
  }
}
