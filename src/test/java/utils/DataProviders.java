package utils;
import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "noteData")
    public Object[][] getNoteData() {
        String path = "src/test/resources/testdata/NotesData.xlsx";
        return new Object[][] {
                {
                        ExcelUtils.getCellData(path, "Notes", 1, 0),
                        ExcelUtils.getCellData(path, "Notes", 1, 1)
                },
                {
                        ExcelUtils.getCellData(path, "Notes", 2, 0),
                        ExcelUtils.getCellData(path, "Notes", 2, 1)
                }
        };
    }

    @DataProvider(name = "e2eData")
    public Object[][] getE2EData() {
        String registerPath = "src/test/resources/testdata/RegisterData.xlsx";
        String notesPath = "src/test/resources/testdata/NotesData.xlsx";

        return new Object[][] {
                {
                        ExcelUtils.getCellData(registerPath, "Register", 1, 2), // email
                        ExcelUtils.getCellData(registerPath, "Register", 1, 3), // password
                        ExcelUtils.getCellData(notesPath, "Notes", 1, 0),       // category
                        ExcelUtils.getCellData(notesPath, "Notes", 1, 1),       // title
                        ExcelUtils.getCellData(notesPath, "Notes", 1, 2)        // description
                },
                {
                        ExcelUtils.getCellData(registerPath, "Register", 2, 2),
                        ExcelUtils.getCellData(registerPath, "Register", 2, 3),
                        ExcelUtils.getCellData(notesPath, "Notes", 2, 0),
                        ExcelUtils.getCellData(notesPath, "Notes", 2, 1),
                        ExcelUtils.getCellData(notesPath, "Notes", 2, 2)
                }
        };
    }
}