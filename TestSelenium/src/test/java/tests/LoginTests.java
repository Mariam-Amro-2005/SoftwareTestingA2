package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void invalidLogin(){

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);

        login.login("wrong@email.com","wrongpass");

        Assert.assertTrue(login.getErrorMessage()
                .contains("Warning: No match for E-Mail Address"));
    }
}