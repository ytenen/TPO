package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    void invalidLoginTest() {

        LoginPage loginPage = new HomePage(driver)
                .open()
                .scrollDown()
                .clickLogin()
                .waitForLoginForm();

        loginPage.login("bugugaga@imail.rus", "lol");

        Assertions.assertTrue(loginPage.isLoginStillVisible(),"Пользователю не удалось войти в систему"
        );
    }
}