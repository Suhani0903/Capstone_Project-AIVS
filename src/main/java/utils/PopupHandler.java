package utils;

import org.openqa.selenium.*;
import java.util.Arrays;
import java.util.List;

public class PopupHandler {
    private WebDriver driver;
    public PopupHandler(WebDriver driver) {
        this.driver = driver;
    }

    public void closeAdsIfPresent() {
        List<By> popupLocators = Arrays.asList(
                By.cssSelector(".close"),
                By.cssSelector(".close-btn"),
                By.cssSelector(".modal-close"),
                By.xpath("//button[contains(text(),'Close')]"),
                By.xpath("//button[contains(text(),'×')]"),
                By.xpath("//div[contains(@class,'popup')]//button"),
                By.xpath("//iframe"));

        for (By locator : popupLocators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    elements.get(0).click();
                    System.out.println("Popup closed using: " + locator);
                    break;
                }

            } catch (Exception ignored) {
            }
        }
    }
}