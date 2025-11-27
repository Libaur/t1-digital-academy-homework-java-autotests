package org.example.pages;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageAssert {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MainPage actual;

    private MainPageAssert(MainPage actual) {
        this.actual = actual;
        this.driver = actual.driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    }

    public static MainPageAssert checkMainPage(MainPage actual) {
        return new MainPageAssert(actual);
    }

    public MainPageAssert mainPageIsLoaded(String expectedTitle) {
        this.mainLogoIsLoaded();
        this.titleIsEqualTo(expectedTitle);
        return this;
    }

    public MainPageAssert titleIsEqualTo(String expectedTitle) {
        Assertions.assertThat(actual.getMainTitle())
                .isEqualTo(expectedTitle);
        return this;
    }

    public MainPageAssert mainLogoIsLoaded() {
        wait.until(ExpectedConditions.visibilityOf(actual.mainLogo));
        return this;
    }
}
