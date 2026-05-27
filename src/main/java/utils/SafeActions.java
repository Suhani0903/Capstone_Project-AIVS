package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SafeActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private PopupHandler popupHandler;

    public SafeActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.popupHandler = new PopupHandler(driver);
    }

    public void safeClick(By locator) {
        try {
            popupHandler.closeAdsIfPresent();
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Click intercepted → retrying with popup cleanup");
            popupHandler.closeAdsIfPresent();
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
            } catch (Exception ex) {
                System.out.println("Selenium click failed → using JS click");
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        }
    }

    public void safeType(By locator, String value) {
        popupHandler.closeAdsIfPresent();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }
}