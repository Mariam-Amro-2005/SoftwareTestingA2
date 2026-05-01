package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

public class ScreenshotUtil {
    /**
     * Captures a screenshot and attaches it to the Allure report.
     * @param driver   the current WebDriver instance
     * @param testName the name of the test (for the attachment title)
     */
    public static void captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) return;
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(testName + " - Screenshot", "image/png",
                new ByteArrayInputStream(screenshot), "png");
    }
}