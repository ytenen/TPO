//package tests;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import java.time.Duration;
//import java.util.*;
//
//public class GuestSuiteTest {
//  private WebDriver driver;
//  private Map<String, Object> vars;
//  JavascriptExecutor js;
//
//  @BeforeEach
//  public void setUp() {
//    driver = new FirefoxDriver();
//    js = (JavascriptExecutor) driver;
//    vars = new HashMap<String, Object>();
//  }
//
//  private void acceptCookiesV2() {
//    try {
//      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//      WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(
//          By.cssSelector("button#onetrust-accept-btn-handler")));
//      acceptBtn.click();
//      wait.until(ExpectedConditions.invisibilityOfElementLocated(
//          By.cssSelector("div.onetrust-pc-dark-filter")));
//    } catch (Exception e) {
//    }
//  }
//
//  private void sleep() {
//    try { Thread.sleep(800); } catch (InterruptedException ignored) {}
//  }
//
//  private void closePromoBanner() {
//    try {
//      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//      try {
//        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
//            By.cssSelector("div.promo-fullscreen_close, button.promo-close, .fullscreen-promo__close, .promo-close-btn")));
//        closeBtn.click();
//      } catch (Exception e1) {
//        try {
//          driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
//        } catch (Exception e2) {
//          js.executeScript("var banners = document.querySelectorAll('div.promo-fullscreen_content, div.promo-fullscreen, .promo-overlay, .modal-overlay'); banners.forEach(function(b) { b.remove(); });");
//        }
//      }
//
//      wait.until(ExpectedConditions.invisibilityOfElementLocated(
//          By.cssSelector("div.promo-fullscreen_content, div.promo-fullscreen")));
//    } catch (Exception e) {
//      try {
//        js.executeScript("var banners = document.querySelectorAll('div.promo-fullscreen_content, div.promo-fullscreen, .promo-overlay, .modal-overlay, .onetrust-pc-dark-filter'); banners.forEach(function(b) { b.remove(); });");
//      } catch (Exception ignored) {}
//    }
//  }
//
//  @AfterEach
//  public void tearDown() {
//    driver.quit();
//  }
//  @Test
//  public void participateTournament() {
//    driver.get("https://worldoftanks.eu/ru/");
//    acceptCookiesV2();
//    closePromoBanner();
//    driver.manage().window().setSize(new Dimension(1230, 724));
//    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    pageLoadWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
//    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    driver.findElement(By.xpath("//a[contains(text(),\'Турниры\')]")).click();
//    sleep();
//    js.executeScript("window.scrollTo(0,456)");
//    driver.findElement(By.xpath("//*[contains(text(),'УЧАСТВОВАТЬ') or contains(text(),'Участвовать')]")).click();
//    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
//    WebElement heading = wait2.until(ExpectedConditions.visibilityOfElementLocated(
//        By.cssSelector("h1, .layout_airy_wrapper_id h1, #layout_airy_wrapper_id h1")));
//    String headingText = heading.getText();
//    assertTrue(headingText.contains("ВХОД") || headingText.contains("вход"),
//        "Должна открыться страница входа, но найден: " + headingText);
//  }
//  @Test
//  public void siteScrolling() {
//    driver.get("https://worldoftanks.eu/ru/");
//    acceptCookiesV2();
//    closePromoBanner();
//    driver.manage().window().setSize(new Dimension(1470, 920));
//    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    pageLoadWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
//    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    try {
//      WebElement anyLink = driver.findElement(By.cssSelector("a[href*='wargaming.net'], .promo a, .banner a, main a"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(anyLink).perform();
//    } catch (Exception e) {
//      js.executeScript("window.scrollTo(0, 500)");
//    }
//    sleep();
//    {
//      WebElement element = driver.findElement(By.tagName("body"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element, 0, 0).perform();
//    }
//    sleep();
//    js.executeScript("window.scrollTo(0, 1000)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    sleep();
//    driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")).click();
//    sleep();
//    driver.findElement(By.xpath("//a[contains(text(),\'Новости\')]")).click();
//    sleep();
//    driver.findElement(By.xpath("//div[3]/div/a/span")).click();
//    sleep();
//    assertEquals("Отмечаем день рождения World of Tanks", driver.findElement(By.xpath("//div[@id='ctw_nav_sec_section']/div[2]/div/div[2]/h1")).getText());
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//div[2]/div/ul/li[3]/div/div[2]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    sleep();
//    driver.findElement(By.xpath("//div[2]/div/ul/li[3]/div/div[2]")).click();
//    sleep();
//    driver.findElement(By.xpath("//a[contains(text(),'Новичку')]")).click();
//    sleep();
//    driver.findElement(By.xpath("//li[3]/a/span[2]/span[2]")).click();
//    assertEquals("Тактические советы", driver.findElement(By.xpath("//h1[contains(.,'Тактические советы')]")).getText());
//  }
//  @Test
//  public void tanksSearchSystem() {
//    driver.get("https://worldoftanks.eu/ru/");
//    acceptCookiesV2();
//    closePromoBanner();
//    driver.manage().window().setSize(new Dimension(1470, 920));
//    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    pageLoadWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
//    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    try {
//      WebElement promoLink = driver.findElement(By.cssSelector("a[href*='wargaming.net'], .promo a, .banner a"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(promoLink).perform();
//    } catch (Exception e) {
//    }
//    {
//      WebElement element = driver.findElement(By.tagName("body"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element, 0, 0).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")).click();
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    WebElement tankopedia = wait.until(ExpectedConditions.elementToBeClickable(
//        By.xpath("//a[contains(text(),'Танковедение')]")));
//    js.executeScript("arguments[0].scrollIntoView(true);", tankopedia);
//    tankopedia.click();
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//header/div[2]/div/div/div/div/div"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    sleep();
//    driver.findElement(By.xpath("//span[contains(.,'СССР')]")).click();
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//header/div[2]/div/div/div/div/div[2]/span"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    sleep();
//    driver.findElement(By.xpath("//span[contains(.,'Лёгкие танки')]")).click();
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 400)");
//    sleep();
//    driver.findElement(By.xpath("//*[contains(text(),'Т-116')]")).click();
//    sleep();
//    assertEquals("ЛЁГКИЙ ТАНК", driver.findElement(By.xpath("//div[2]/div/span[2]/span/span[2]")).getText());
//    assertEquals("СССР", driver.findElement(By.xpath("//span[2]/span/span[2]")).getText());
//  }
//  @Test
//  public void tryingCodeActivation() {
//    driver.get("https://worldoftanks.eu/ru/");
//    acceptCookiesV2();
//    closePromoBanner();
//    driver.manage().window().setSize(new Dimension(1470, 920));
//    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    pageLoadWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
//    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")).click();
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    WebElement bonusCode = wait.until(ExpectedConditions.elementToBeClickable(
//        By.xpath("//a[contains(text(),'Активировать бонус-код')]")));
//    js.executeScript("arguments[0].scrollIntoView(true);", bonusCode);
//    bonusCode.click();
//    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
//    WebElement heading = wait2.until(ExpectedConditions.visibilityOfElementLocated(
//        By.cssSelector("h1, .layout_airy_wrapper_id h1, #layout_airy_wrapper_id h1")));
//    String headingText = heading.getText();
//    assertTrue(headingText.contains("ВХОД") || headingText.contains("вход"),
//        "Должна открыться страница входа, но найден: " + headingText);
//  }
//  @Test
//  public void downloadGame() {
//    driver.get("https://worldoftanks.eu/ru/");
//    acceptCookiesV2();
//    closePromoBanner();
//    driver.manage().window().setSize(new Dimension(1430, 837));
//    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    pageLoadWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? 'true' : null;"));
//    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    sleep();
//    js.executeScript("window.scrollTo(0, 0)");
//    sleep();
//    {
//      WebElement element = driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    driver.findElement(By.xpath("//div[2]/div/ul/li[2]/div/div[2]")).click();
//    {
//      WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Скачать')]"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//    WebElement download = wait.until(ExpectedConditions.elementToBeClickable(
//        By.xpath("//a[contains(text(),'Скачать')]")));
//    js.executeScript("arguments[0].scrollIntoView(true);", download);
//    download.click();
//    assertEquals("СКАЧАТЬ", driver.findElement(By.xpath("//div[2]/a[2]/span")).getText());
//  }
//}
