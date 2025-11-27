package org.example;

import org.example.config.FirefoxDriverOptions;
import org.example.pages.AuthModal;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PicabuTests {

    private WebDriver driver;
    private AuthModal authModal;
    private final String authValue = "Qwerty";

    @BeforeEach
    void init() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\ALibaur\\Desktop\\geckodriver.exe");
        driver = new FirefoxDriver(FirefoxDriverOptions.getOptions());
        authModal = new AuthModal(driver);
    }

    @Test
    public void checkAuthErrorAfterIncorrectCreds() {

        driver.get("https://pikabu.ru/");
        Assertions.
                assertEquals("Горячее – самые интересные и обсуждаемые посты | Пикабу", driver.getTitle());

        driver.findElement(By.cssSelector("[class$='login-button']")).click();

        authModal.checkModalDisplayed("Войти")
                .checkLoginInputDisplayed("Логин")
                .checkPasswordInputDisplayed("Пароль")
                .checkSignInButtonDisplayed("Войти")
                .enterLogin(authValue)
                .enterPassword(authValue)
                .clickSignInButton()
                .checkAuthErrorSpanDisplayed("Ошибка. Вы ввели неверные данные авторизации");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
