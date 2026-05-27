package base;

import config.ConfigReader;
import drivers.DriverManager;
import factory.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.SafeActions;

public class BaseTest {

    public static WebDriver driver;
    protected SafeActions safe;

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        DriverFactory.initDriver();
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("baseUrl"));
        driver.manage().window().maximize();
        safe = new SafeActions(driver);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {

        try {
            if (DriverManager.getDriver() != null) {
                DriverManager.getDriver().manage().deleteAllCookies();
                DriverManager.getDriver().quit();
                DriverManager.unload();
            }
        } catch (Exception ignored) {
        }
    }
}