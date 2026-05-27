package listeners;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting Test: {}", result.getName());
        ExtentTestManager.setTest(ExtentManager.getExtent().createTest(result.getName()));
        ExtentTestManager.getTest().log(Status.INFO, "Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASSED: {}", result.getName());
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.error("TEST FAILED: {}", result.getName());
        String path = ScreenshotUtil.captureScreenshot(result.getName());
        try {
            Allure.addAttachment("Failure Screenshot", new FileInputStream(Paths.get(path).toFile()));
        } catch (IOException e) {
            logger.error("Failed to attach screenshot to Allure", e);
        }

        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
        try {
            ExtentTestManager.getTest().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Extent", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIPPED: {}", result.getName());
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Execution Started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Execution Finished: {}", context.getName());
        ExtentManager.getExtent().flush();
    }
}