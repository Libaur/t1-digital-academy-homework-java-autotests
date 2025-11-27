package org.example.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class InfoDropdownAssert {

    private final WebDriver driver;
    private final InfoDropdown actual;

    private InfoDropdownAssert(InfoDropdown actual) {
        this.actual = actual;
        this.driver = actual.driver;
    }

    public static InfoDropdownAssert checkInfoDropdown(InfoDropdown actual) {
        return new InfoDropdownAssert(actual);
    }

    public InfoDropdownAssert checkInfoFlightTabIsVisible() {
        Assertions.assertTrue(actual.infoFlightTab.isDisplayed());
        return this;
    }

    public InfoDropdownAssert checkInfoUsefulTabIsVisible() {
        Assertions.assertTrue(actual.infoUsefulTab.isDisplayed());
        return this;
    }

    public InfoDropdownAssert checkInfoAboutCompanyTabIsVisible() {
        Assertions.assertTrue(actual.infoAboutCompanyTab.isDisplayed());
        return this;
    }
}
