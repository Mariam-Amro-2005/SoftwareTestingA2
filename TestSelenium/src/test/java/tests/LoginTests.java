package tests;

import base.BaseTest;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void validLogin() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login("alice05@example.com", "test");

        Assert.assertEquals(driver.getTitle(), "My Account",
                "Page title should be 'My Account' after successful login");

        AccountPage account = new AccountPage(driver);
        Assert.assertTrue(account.isLogoutDisplayed(),
                "Logout link should be visible when user is logged in");

        account.logout();
    }

    @Test
    public void invalidLogin() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login("alice05@example.com", "wrongpassword");

        Assert.assertEquals(login.getErrorMessage(),
                "Warning: No match for E-Mail Address and/or Password.",
                "Error message should match exactly");

        Assert.assertEquals(driver.getTitle(), "Account Login",
                "Page should still be login page after failed attempt");

        AccountPage account = new AccountPage(driver);
        Assert.assertFalse(account.isLogoutDisplayed(),
                "Logout link should not be visible for failed login");
    }
}