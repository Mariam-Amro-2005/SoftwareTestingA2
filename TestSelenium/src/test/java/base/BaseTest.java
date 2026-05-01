package base;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

import java.time.Duration;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.setDriver();
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(), result.getName());
        }
        DriverFactory.quitDriver();
    }

    protected void login() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login("user22222@gmail.com", "12345");
    }

    protected void logout() {
        AccountPage account = new AccountPage(driver);
        account.logout();
    }
}