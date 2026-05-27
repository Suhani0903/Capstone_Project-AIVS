package tests.ui;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;

public class InvalidEmailFormatTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(InvalidEmailFormatTest.class);

    @Test
    public void invalidEmailFormatValidationTest() {
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        logger.info("Opening Signup Page");
        registerPage.clickSignup();
        logger.info("Navigating to Login Page");
        registerPage.clickLoginHere();
        logger.info("Entering Invalid Email Format");

        loginPage.login("abc123", "Test@123");
        logger.info("Verifying Invalid Email Validation Message");

        Assert.assertTrue(loginPage.isValidationMessageDisplayed(),
                "Invalid email validation message not displayed");
        logger.info("Invalid Email Format Validation Successful");
    }
}