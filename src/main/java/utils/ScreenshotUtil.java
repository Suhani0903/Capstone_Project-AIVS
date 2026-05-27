package utils;

import drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String captureScreenshot(String testName) {

        File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        String path = "screenshots/" + testName + ".png";

        try {
            FileUtils.copyFile(src, new File(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}