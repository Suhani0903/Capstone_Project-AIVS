package pages;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class RegisterPage {

    private static final Logger logger = LogManager.getLogger(RegisterPage.class);

    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

    private final By signupButton = By.xpath("//a[@data-testid='open-register-view']");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By name = By.name("name");
    private final By confirmPassword = By.name("confirmPassword");
    private final By registerButton = By.xpath("//button[text()='Register']");
    private final By errorMessage = By.className("alert-danger");
    private final By loginHereButton = By.xpath("//a[@data-testid='login-view']");

    public void clickSignup() {

        WebElement signup = wait.until(ExpectedConditions.elementToBeClickable(signupButton));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", signup);
        logger.info("Clicked Create Account button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void clickLoginHere() {

        WebElement loginBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginHereButton));

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", loginBtn);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        js.executeScript("arguments[0].click();", loginBtn);
        logger.info("Clicked Login Here button");
    }

    public void register(String userEmail,String userPassword,String userName,String confirmPass) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(userEmail);
        DriverManager.getDriver().findElement(password).sendKeys(userPassword);
        DriverManager.getDriver().findElement(name).sendKeys(userName);
        DriverManager.getDriver().findElement(confirmPassword).sendKeys(confirmPass);
        DriverManager.getDriver().findElement(registerButton).click();
    }

    public boolean isUserAlreadyExists() {

        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}