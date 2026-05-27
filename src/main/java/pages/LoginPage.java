package pages;
import config.ConfigReader;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class LoginPage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(15));
    private final By loginHereButton = By.xpath("//a[text()='Login']");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By loginButton = By.xpath("//button[text()='Login']");
    private final By loginError = By.xpath("//*[contains(text(),'Incorrect email address or password')]");
    private final By validationMessage = By.xpath("//*[contains(text(),'invalid') or contains(text(),'required') or contains(text(),'Incorrect')]");

    public void clickLoginHere() {

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(0,0)");

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginHereButton));
        js.executeScript("arguments[0].click();", loginBtn);

        logger.info("Clicked Login Here button");
    }

    public void login(String userEmail, String userPassword) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(userEmail);
        DriverManager.getDriver().findElement(password).sendKeys(userPassword);
        DriverManager.getDriver().findElement(loginButton).click();
        js.executeScript("window.scrollBy(0,400)");
        logger.info("Login Successful");
    }
    public void openLoginPage() {
        DriverManager.getDriver().get(ConfigReader.getProperty("baseUrl"));
    }

    public boolean isLoginErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLoginButtonOnly() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", loginBtn);
        js.executeScript("arguments[0].click();", loginBtn);
        logger.info("Clicked Login button");
    }

    public boolean isValidationMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}