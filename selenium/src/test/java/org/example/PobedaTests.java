package org.example;

import org.example.config.FirefoxDriverOptions;
import org.example.config.WebDriverFactory;
import org.example.pages.InfoDropdown;
import org.example.pages.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.example.pages.MainPageAssert.checkMainPage;
import static org.example.pages.InfoDropdownAssert.checkInfoDropdown;

public class PobedaTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private InfoDropdown infoDropdown;

    @BeforeEach
    void init() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\ALibaur\\Desktop\\geckodriver.exe");
        driver = WebDriverFactory.createDriver(FirefoxDriverOptions.getOptions());
        wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        mainPage = new MainPage(driver);
        infoDropdown = new InfoDropdown(driver);
    }

    @Test
    public void checkInfoDropdownAppears() {

        String expectedTitle = "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками";

        driver.get("https://www.flypobeda.ru/");

        checkMainPage(mainPage).mainPageIsLoaded(expectedTitle);
        mainPage.hoverInfoTab();
        checkInfoDropdown(infoDropdown)
                .checkInfoFlightTabIsVisible()
                .checkInfoUsefulTabIsVisible()
                .checkInfoAboutCompanyTabIsVisible();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
