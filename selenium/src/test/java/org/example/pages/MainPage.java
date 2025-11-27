package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    WebDriver driver;
    Actions actions;

    @FindBy(css = "img[alt='«Авиакомпания «Победа», Группа «Аэрофлот»']")
    WebElement mainLogo;

    @FindBy(css = "a[href='/information']")
    WebElement infoTab;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void hoverInfoTab() {
        actions.moveToElement(infoTab).perform();
    }

    public String getMainTitle() {
        return driver.getTitle();
    }
}
