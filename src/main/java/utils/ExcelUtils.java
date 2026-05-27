package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static String getCellData(
            String filePath,
            String sheetName,
            int rowNumber,
            int cellNumber) {

        try {
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row row = sheet.getRow(rowNumber);
            Cell cell = row.getCell(cellNumber);
            return cell.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}