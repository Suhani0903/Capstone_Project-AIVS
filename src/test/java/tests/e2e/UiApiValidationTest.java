package tests.e2e;

import base.BaseTest;
import pages.DashboardPage;
import drivers.DriverManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;
import pages.RegisterPage;
import tests.api.AuthManager;
import tests.api.NotesService;
import java.util.UUID;

public class UiApiValidationTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(UiApiValidationTest.class);

    @Test(dataProvider = "e2eData", dataProviderClass = utils.DataProviders.class)
    public void validateUiWithApi(String email,String password,String category,String title,String description) {

        logger.info("Starting UI ↔ API validation flow");
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();
        registerPage.clickSignup();
        registerPage.register(email, password,"Test User", password);

        if (registerPage.isUserAlreadyExists()) {
            logger.warn("User already exists");
            registerPage.clickLoginHere();
            loginPage.login(email, password);
        } else {
            logger.info("New user registered");
            registerPage.clickLoginHere();
            loginPage.login(email, password);
        }

        String finalTitle = title + "_" + UUID.randomUUID().toString().substring(0, 8);
        notesPage.createNote(category, finalTitle, description);
        Assert.assertTrue(notesPage.isNotePresent(finalTitle), "Note not visible in UI");

        logger.info("Note created successfully: {}", finalTitle);
        String token = AuthManager.getToken(email, password);
        Response response = NotesService.getAllNotes(token);
        Assert.assertTrue(response.getTime() < 2000, "API response exceeded 2 seconds");

        logger.info("API response time: {} ms", response.getTime());

        String noteId = response.jsonPath()
                .getString("data.find { it.title == '" + finalTitle + "' }.id");

        Assert.assertNotNull(noteId, "Note ID not found in API");

        Assert.assertEquals(
                response.jsonPath().getString(
                        "data.find { it.title == '" + finalTitle + "' }.title"), finalTitle);

        Assert.assertEquals(
                response.jsonPath().getString(
                        "data.find { it.title == '" + finalTitle + "' }.description"
                ), description);

        Assert.assertEquals(
                response.jsonPath().getString(
                        "data.find { it.title == '" + finalTitle + "' }.category"
                ), category);

        logger.info("API validation successful");
        Response deleteResponse = NotesService.deleteNote(token, noteId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
        logger.info("Note deleted successfully via API");
        Response afterDelete = NotesService.getAllNotes(token);

        String deletedNote = afterDelete.jsonPath()
                .getString("data.find { it.id == '" + noteId + "' }");

        Assert.assertNull(deletedNote);
        logger.info("Verified note deletion from API");

        DriverManager.getDriver().navigate().refresh();

        Assert.assertFalse(
                notesPage.isNotePresent(finalTitle),
                "Note still visible in UI after deletion");

        logger.info("UI ↔ API DELETE FLOW SUCCESSFUL");

        Assert.assertFalse(
                notesPage.isNotePresent(finalTitle), "Note still visible in UI after deletion");

        logger.info("UI ↔ API DELETE FLOW SUCCESSFUL");

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.logout();
    }
}