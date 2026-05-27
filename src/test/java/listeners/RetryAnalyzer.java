package listeners;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import drivers.DriverManager;

import java.time.Duration;

import static base.BaseTest.driver;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxTry) {
            count++;
            System.out.println("Retrying Test: " + result.getName() + " | Attempt " + count);

            try {
                if (DriverManager.getDriver() != null) {
                    DriverManager.getDriver().navigate().refresh();
                    new WebDriverWait(driver, Duration.ofSeconds(2))
                            .until(d -> true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}