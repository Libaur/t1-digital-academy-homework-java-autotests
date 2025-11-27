package org.example.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AuthModal {

    private final WebDriver driver;
    private final By authModal = By.className("auth-modal");
    private final By authHeader = By.className("auth__header");
    private final By loginInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By signInButton = By.cssSelector("[type='submit']");
    private final By authErrorSpan = By.cssSelector(".auth__error auth__error_top");

    public AuthModal(WebDriver driver) {
        this.driver = driver;
    }

    public AuthModal checkModalDisplayed(String expectedTitle) {
        Assertions.assertTrue(findAuthModal().isDisplayed());
        Assertions.assertEquals(expectedTitle, findAuthModal().findElement(authHeader).getText());
        return this;
    }

    public AuthModal checkLoginInputDisplayed(String expectedPlaceholder) {
        Assertions.assertTrue(driver.findElement(loginInput).isDisplayed());
        Assertions.assertEquals(expectedPlaceholder,
                driver.findElement(loginInput).getAttribute("placeholder"));
        return this;
    }

    public AuthModal checkPasswordInputDisplayed(String expectedPlaceholder) {
        Assertions.assertTrue(driver.findElement(passwordInput).isDisplayed());
        Assertions.assertEquals(expectedPlaceholder,
                driver.findElement(passwordInput).getAttribute("placeholder"));
        return this;
    }

    public AuthModal checkSignInButtonDisplayed(String expectedText) {
        Assertions.assertTrue(driver.findElement(signInButton).isDisplayed());
        Assertions.assertEquals(expectedText,
                driver.findElement(signInButton).getText());
        return this;
    }

    public AuthModal checkAuthErrorSpanDisplayed(String expectedText) {
        Assertions.assertTrue(findAuthModal().findElement(authErrorSpan).isDisplayed());
        Assertions.assertEquals(expectedText,
                findAuthModal().findElement(authErrorSpan).getText());
        return this;
    }

    public AuthModal enterLogin(String login) {
        findAuthModal().findElement(loginInput).sendKeys(login);
        return this;
    }

    public AuthModal enterPassword(String password) {
        findAuthModal().findElement(passwordInput).sendKeys(password);
        return this;
    }

    public AuthModal clickSignInButton() {
        findAuthModal().findElement(signInButton).click();
        return this;
    }

    private WebElement findAuthModal() {
        return driver.findElement(authModal);
    }
}
