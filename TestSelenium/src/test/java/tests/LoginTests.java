package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.ExcelDataReader;

public class LoginTests extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return ExcelDataReader.getTestData("ValidLogin");
    }

    @Test(dataProvider = "validLoginData")
    public void validLogin(String email, String password,
                           String expectedTitle, String expectedLogoutVisible) {
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
    public Object[][] invalidLoginData() {
        return ExcelDataReader.getTestData("InvalidLogin");
    }

    @Test(dataProvider = "invalidLoginData")
    public void invalidLogin(String email, String password,
                             String expectedErrorMessage, String expectedTitle) {
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