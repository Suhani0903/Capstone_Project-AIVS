package pages;

import drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class NotesPage {
    private static final Logger logger = LogManager.getLogger(NotesPage.class);

    WebDriverWait wait =
            new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

    private final By addNoteButton = By.xpath("//button[contains(text(),'Add Note')]");
    private final By titleField = By.name("title");
    private final By descriptionField = By.cssSelector("[data-testid='note-description']");
    private final By saveButton = By.xpath("//button[text()='Create']");
    private final By categoryDropdown = By.name("category");


    public void createNote(String category, String titleText, String descriptionText) {

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
        js.executeScript("arguments[0].click();", addBtn);
        logger.info("Clicked Add Note button");

        WebElement categoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryDropdown));
        Select select = new Select(categoryElement);
        select.selectByVisibleText(category);
        logger.info("Selected category: {}", category);

        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(titleField));
        titleInput.clear();
        titleInput.sendKeys(titleText);
        wait.until(ExpectedConditions.attributeToBe(titleField, "value", titleText));
        logger.info("Entered note title");

        wait.until(ExpectedConditions.presenceOfElementLocated(descriptionField));
        WebElement descInput = wait.until(
                ExpectedConditions.elementToBeClickable(descriptionField));

        descInput.clear();
        descInput.sendKeys(descriptionText);
        logger.info("Entered note description");

        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        js.executeScript("arguments[0].click();", saveBtn);
        logger.info("Clicked Create button");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(titleField));
        logger.info("Note created successfully");
    }

    public boolean isNotePresent(String noteTitle) {

        try {

            WebDriverWait wait =
                    new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'" + noteTitle + "')]")));

            logger.info("Note found in UI: {}", noteTitle);
            return true;
        } catch (Exception e) {
            logger.error("Note not found in UI: {}", noteTitle);
            return false;
        }
    }

    public boolean isNoteDisplayed(String title) {

        return DriverManager.getDriver().findElements(
                By.xpath("//h5[text()='" + title + "']")).size() > 0;
    }
}