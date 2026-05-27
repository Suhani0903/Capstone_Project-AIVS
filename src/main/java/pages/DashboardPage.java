package pages;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {

    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    private final By addNewNote = By.xpath("//button[contains(text(),'Add Note')]");

    public void waitForDashboard() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addNewNote));
        wait.until(ExpectedConditions.elementToBeClickable(addNewNote));
    }

    private final By logoutButton = By.xpath("//button[text()='Logout']");

    public void logout() {
        try {
            WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logout.click();
        } catch (Exception e) {
            System.out.println("Logout not required or already logged out");
        }
    }

}