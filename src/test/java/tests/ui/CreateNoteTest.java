package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;
import utils.ExcelUtils;

import java.util.UUID;

public class CreateNoteTest extends BaseTest {

    @Test
    public void verifyCreateNote() {
        String filePath = "src/test/resources/testdata/RegisterData.xlsx";

        String email =
                ExcelUtils.getCellData(filePath, "Register", 1, 2);

        String password =
                ExcelUtils.getCellData(
                        filePath, "Register", 1, 3);

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();
        loginPage.clickLoginHere();
        loginPage.login(email, password);

        String title = "TestNote_" + UUID.randomUUID().toString().substring(0, 5);
        notesPage.createNote("Home", title, "This is test description");
        Assert.assertTrue(notesPage.isNotePresent(title), "Note was not created");

        System.out.println("Create note via UI successful");
    }
}