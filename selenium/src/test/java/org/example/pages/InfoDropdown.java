package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InfoDropdown {

    WebDriver driver;

    @FindBy(css = "a[href='/information#flight']")
    WebElement infoFlightTab;

    @FindBy(css = "a[href='/information#useful']")
    WebElement infoUsefulTab;

    @FindBy(css = "a[href='/information#company']")
    WebElement infoAboutCompanyTab;

    public InfoDropdown(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}