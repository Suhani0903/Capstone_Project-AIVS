package tests.ui;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.NotesPage;
import pages.RegisterPage;
import utils.ExcelUtils;

public class EndToEndUserJourneyTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(EndToEndUserJourneyTest.class);

    @Test
    public void fullUserFlow() {
        String registerPath = "src/test/resources/testdata/RegisterData.xlsx";
        String notesPath = "src/test/resources/testdata/NotesData.xlsx";
        logger.info("Reading Register Test Data");

        String firstName =
                ExcelUtils.getCellData(registerPath, "Register", 1, 0);

        String lastName =
                ExcelUtils.getCellData(registerPath, "Register", 1, 1);

        String email =
                ExcelUtils.getCellData(registerPath, "Register", 1, 2);

        String password =
                ExcelUtils.getCellData(registerPath, "Register", 1, 3);

        logger.info("Reading Notes Test Data");
        String category =
                ExcelUtils.getCellData(notesPath, "Notes", 1, 0);

        String baseTitle =
                ExcelUtils.getCellData(notesPath, "Notes", 1, 1);

        String noteTitle = baseTitle + "_" + System.currentTimeMillis();

        String noteDesc =
                ExcelUtils.getCellData(notesPath, "Notes", 1, 2);

        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        NotesPage notesPage = new NotesPage();
        logger.info("Opening Signup Page");
        registerPage.clickSignup();
        logger.info("Registering User");
        registerPage.register(email, password, firstName + " " + lastName, password);

        if (registerPage.isUserAlreadyExists()) {
            logger.warn("User already exists");
            registerPage.clickLoginHere();
            logger.info("Logging in existing user");
            loginPage.login(email, password);
        } else {
            logger.info("New User Registered");
            registerPage.clickLoginHere();
            logger.info("Logging in newly registered user");
            loginPage.login(email, password);
        }

        logger.info("Waiting for Dashboard");
        dashboardPage.waitForDashboard();
        logger.info("Creating Note");

        notesPage.createNote(category, noteTitle, noteDesc);
        logger.info("Validating Created Note");

        Assert.assertTrue(notesPage.isNotePresent(noteTitle), "Note was not created successfully");
        logger.info("FULL UI USER FLOW COMPLETED SUCCESSFULLY");
    }
}