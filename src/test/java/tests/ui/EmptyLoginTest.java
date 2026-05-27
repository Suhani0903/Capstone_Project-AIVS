package tests.ui;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;

public class EmptyLoginTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(EmptyLoginTest.class);

    @Test
    public void emptyLoginValidationTest() {
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        logger.info("Opening Signup Page");
        registerPage.clickSignup();
        logger.info("Navigating to Login Page");
        registerPage.clickLoginHere();
        logger.info("Clicking Login button without entering credentials");
        loginPage.clickLoginButtonOnly();
        logger.info("Verifying validation message");
        Assert.assertTrue(loginPage.isValidationMessageDisplayed(),
                "Validation message not displayed");
        logger.info("Empty Login Validation Successful");
    }
}