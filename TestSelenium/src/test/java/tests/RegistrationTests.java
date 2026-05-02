package tests;

import base.BaseTest;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVDataReader;

import java.io.IOException;

public class RegistrationTests extends BaseTest {

    private static final String VALID_REG_CSV = "src/test/resources/valid_registration.csv";
    private static final String VALIDATION_CSV = "src/test/resources/registration_validation.csv";

    @DataProvider(name = "validRegistrationData")
    public Object[][] validRegistrationData() throws IOException, CsvException {
        return CSVDataReader.getTestData(VALID_REG_CSV);
    }

    @Test(dataProvider = "validRegistrationData")
    public void validRegister(String[] data) {
        String firstName = data[0];
        String lastName = data[1];
        String telephone = data[2];
        String password = data[3];
        String confirmPassword = data[4];
        // data[5] is expectedSuccessMessage, not used in assertions currently

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
    public Object[][] registrationValidationData() throws IOException, CsvException {
        return CSVDataReader.getTestData(VALIDATION_CSV);
    }

    @Test(dataProvider = "registrationValidationData")
    public void registrationWithFieldValidationErrors(String[] data) {
        String firstName = data[0];
        String lastName = data[1];
        String email = data[2];
        String telephone = data[3];
        String password = data[4];
        String confirmPassword = data[5];
        String expectedEmailError = data[6];
        String expectedTelephoneError = data[7];
        String expectedPasswordError = data[8];

        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.enterFirstName(firstName);
        register.enterLastName(lastName);
        if (!email.isEmpty()) register.enterEmail(email);
        if (!telephone.isEmpty()) register.enterTelephone(telephone);
        if (!password.isEmpty()) register.enterPassword(password);
        if (!confirmPassword.isEmpty()) register.confirmPassword(confirmPassword);

        register.acceptPolicy();
        register.clickContinue();

        Assert.assertEquals(register.getEmailError(), expectedEmailError,
                "Email error message mismatch");
        Assert.assertEquals(register.getTelephoneError(), expectedTelephoneError,
                "Telephone error message mismatch");
        Assert.assertEquals(register.getPasswordError(), expectedPasswordError,
                "Password error message mismatch");
    }
}