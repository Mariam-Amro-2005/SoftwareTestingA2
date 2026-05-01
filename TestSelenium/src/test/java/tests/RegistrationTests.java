package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test
    public void validRegister(){
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        String email = "user" + System.currentTimeMillis() + "@mail.com";

        register.enterFirstName("firstName");
        register.enterLastName("lastName");
        register.enterEmail(email);
        register.enterTelephone("1234567890");
        register.enterPassword("password");
        register.confirmPassword("password");

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);
        Assert.assertTrue(account.isRegistrationSuccessful(),
                "Registration success message not displayed");

        Assert.assertTrue(account.isLogoutDisplayed(),
                "Logout link is not displayed in My Account menu");

        account.logout();
    }

    @Test
    public void registrationWithFieldValidationErrors() {
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        register.enterFirstName("Emily");
        register.enterLastName("test");

        register.clickContinue();

        Assert.assertEquals(register.getEmailError(),
                "E-Mail Address does not appear to be valid!",
                "Email error message mismatch");
        Assert.assertEquals(register.getTelephoneError(),
                "Telephone must be between 3 and 32 characters!",
                "Telephone error message mismatch");
        Assert.assertEquals(register.getPasswordError(),
                "Password must be between 4 and 20 characters!",
                "Password error message mismatch");

        register.enterEmail("valid@example.com");
        register.enterTelephone("1234567890");

        register.enterPassword("123");
        register.confirmPassword("123");

        register.clickContinue();

        Assert.assertEquals(register.getPasswordError(),
                "Password must be between 4 and 20 characters!",
                "Password error should be shown for short password");

        Assert.assertEquals(register.getEmailError(), "", "Email error should be gone");
        Assert.assertEquals(register.getTelephoneError(), "", "Telephone error should be gone");
    }

}