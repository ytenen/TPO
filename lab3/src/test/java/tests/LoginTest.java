package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    @Order(1)
    void invalidLoginTest() {

        LoginPage loginPage = new HomePage(driver)
                .open()
                .scrollDown()
                .clickLogin()
                .waitForLoginForm();

        loginPage.login("bugugaga@imail.rus", "lol").acceptCookiesIfPresent();

        Assertions.assertTrue(loginPage.isLoginStillVisible(),"Пользователю не удалось войти в систему"
        );
    }

    @Test
    @Order(2)
    void validLoginTest() {
        String username = "nagibator67_dikiy";

        LoginPage loginPage = new HomePage(driver)
                .open()
                .scrollDown()
                .clickLogin()
                .waitForLoginForm();

        loginPage.login("rosh.28@mail.ru", "Qfc12erty");

        AccountPage accountPage = new AccountPage(driver);

        Assertions.assertTrue(accountPage.isUserLoggedIn(username),"Пользователь должен успешно войти в систему");
    }
}