package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.ExcelDataReader;

public class RegistrationTests extends BaseTest {

    @DataProvider(name = "validRegistrationData")
    public Object[][] validRegistrationData() {
        return ExcelDataReader.getTestData("ValidRegistration");
    }

    @Test(dataProvider = "validRegistrationData")
    public void validRegister(String firstName, String lastName,
                              String telephone, String password,
                              String confirmPassword, String expectedSuccessMessage) {
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        String email = "user" + System.currentTimeMillis() + "@mail.com";

        register.enterFirstName(firstName);
        register.enterLastName(lastName);
        register.enterEmail(email);
        register.enterTelephone(telephone);
        register.enterPassword(password);
        register.confirmPassword(confirmPassword);

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);
        Assert.assertTrue(account.isRegistrationSuccessful(),
                "Registration success message not displayed");
        Assert.assertTrue(account.isLogoutDisplayed(),
                "Logout link is not displayed in My Account menu");

        account.logout();
    }

    @DataProvider(name = "registrationValidationData")
    public Object[][] registrationValidationData() {
        return ExcelDataReader.getTestData("RegistrationValidationErrors");
    }

    @Test(dataProvider = "registrationValidationData")
    public void registrationWithFieldValidationErrors(
            String firstName, String lastName, String email,
            String telephone, String password, String confirmPassword,
            String expectedEmailError, String expectedTelephoneError,
            String expectedPasswordError) {

        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.enterFirstName(firstName);
        register.enterLastName(lastName);
        if (!email.isEmpty()) register.enterEmail(email);
        if (!telephone.isEmpty()) register.enterTelephone(telephone);
        if (!password.isEmpty()) register.enterPassword(password);
        if (!confirmPassword.isEmpty()) register.confirmPassword(confirmPassword);

        register.clickContinue();

        // Assert error messages exactly as provided from Excel
        Assert.assertEquals(register.getEmailError(), expectedEmailError,
                "Email error message mismatch");
        Assert.assertEquals(register.getTelephoneError(), expectedTelephoneError,
                "Telephone error message mismatch");
        Assert.assertEquals(register.getPasswordError(), expectedPasswordError,
                "Password error message mismatch");
    }
}