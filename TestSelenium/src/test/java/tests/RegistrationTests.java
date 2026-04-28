package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test
    public void registrationWithoutErrors(){

        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        register.enterFirstName("John");
        register.enterLastName("Doe");
        register.enterEmail(email);
        //user2@gmail.com
        register.enterTelephone("1234567890");
        register.enterPassword("12345");
        register.confirmPassword("12345");

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);

        Assert.assertTrue(account.isRegistrationSuccessful());
    }

}