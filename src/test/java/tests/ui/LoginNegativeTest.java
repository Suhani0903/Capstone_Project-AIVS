package tests.ui;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;

public class LoginNegativeTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginNegativeTest.class);

    @Test
    public void invalidLoginTest() {
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        logger.info("Opening Signup Page");
        registerPage.clickSignup();
        logger.info("Navigating to Login Page");
        registerPage.clickLoginHere();
        logger.info("Performing Invalid Login");
        loginPage.login("wrong@gmail.com", "wrongpassword");
        logger.info("Verifying Login Error Message");
        Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Error message not displayed");
        logger.info("Invalid Login Validation Successful");
    }
}