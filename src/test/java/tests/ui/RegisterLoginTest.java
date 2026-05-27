package tests.ui;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;
import utils.ExcelUtils;

public class RegisterLoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(RegisterLoginTest.class);

    @Test(priority = 1)
    public void registerAndLoginTest() {
        String filePath = "src/test/resources/testdata/RegisterData.xlsx";
        logger.info("Reading Register Test Data");
        String firstName =
                ExcelUtils.getCellData(filePath, "Register", 1, 0);

        String lastName =
                ExcelUtils.getCellData(filePath, "Register", 1, 1);

        String email =
                ExcelUtils.getCellData(filePath, "Register", 1, 2);

        String password =
                ExcelUtils.getCellData(filePath, "Register", 1, 3);

        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        logger.info("Opening Signup Page");
        registerPage.clickSignup();
        logger.info("Registering User");
        registerPage.register(email, password, firstName + " " + lastName, password);

        if (registerPage.isUserAlreadyExists()) {
            logger.warn("User already exists");
            registerPage.clickLoginHere();
            logger.info("Logging in Existing User");
            loginPage.login(email, password);
        } else {
            logger.info("New User Registered Successfully");
            registerPage.clickLoginHere();
            logger.info("Logging in Newly Registered User");
            loginPage.login(email, password);
        }
        logger.info("Register and Login Flow Completed Successfully");
    }
}