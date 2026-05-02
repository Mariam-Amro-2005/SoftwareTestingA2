package tests;

import base.BaseTest;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVDataReader;

import java.io.IOException;

public class LoginTests extends BaseTest {

    private static final String VALID_LOGIN_CSV = "src/test/resources/valid_login.csv";
    private static final String INVALID_LOGIN_CSV = "src/test/resources/invalid_login.csv";

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() throws IOException, CsvException {
        return CSVDataReader.getTestData(VALID_LOGIN_CSV);
    }

    @Test(dataProvider = "validLoginData")
    public void validLogin(String[] data) {
        String email = data[0];
        String password = data[1];
        String expectedTitle = data[2];
        String expectedLogoutVisible = data[3];

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertEquals(driver.getTitle(), expectedTitle,
                "Page title mismatch after successful login");

        AccountPage account = new AccountPage(driver);
        boolean logoutVisible = Boolean.parseBoolean(expectedLogoutVisible);
        Assert.assertEquals(account.isLogoutDisplayed(), logoutVisible,
                "Logout link visibility mismatch");

        if (logoutVisible) {
            account.logout();
        }
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() throws IOException, CsvException {
        return CSVDataReader.getTestData(INVALID_LOGIN_CSV);
    }

    @Test(dataProvider = "invalidLoginData")
    public void invalidLogin(String[] data) {
        String email = data[0];
        String password = data[1];
        String expectedErrorMessage = data[2];
        String expectedTitle = data[3];

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertEquals(login.getErrorMessage(), expectedErrorMessage,
                "Error message mismatch");
        Assert.assertEquals(driver.getTitle(), expectedTitle,
                "Page title should still be login page");

        AccountPage account = new AccountPage(driver);
        Assert.assertFalse(account.isLogoutDisplayed(),
                "Logout link should not be visible for failed login");
    }
}