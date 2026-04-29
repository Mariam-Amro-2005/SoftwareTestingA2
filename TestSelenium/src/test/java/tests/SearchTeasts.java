package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTeasts extends BaseTest {

    @Test
    public void searchByName(){

        // 1. Login
        HomePage home = new HomePage(driver);
        login();
        driver.get("http://tutorialsninja.com/demo/index.php?route=common/home");


        // 2. Search
        home.search("Mac");

        // 3. Validate results
        SearchPage searchPage = new SearchPage(driver);

        Assert.assertTrue(searchPage.resultMAtching("Mac"));

        // 4. Logout
        logout();
    }
}