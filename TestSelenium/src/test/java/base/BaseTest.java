package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.chrome.driver","chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://tutorialsninja.com/demo/index.php?route=common/home");
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

    @AfterMethod
    public void teardown() {

        driver.quit();
    }
}