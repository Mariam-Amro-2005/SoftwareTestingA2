package tests;

import base.BaseTest;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.CSVDataReader;

import java.io.IOException;

public class SearchTests extends BaseTest {

    private static final String SEARCH_BY_NAME_CSV = "src/test/resources/search_by_name.csv";

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws IOException, CsvException {
        return CSVDataReader.getTestData(SEARCH_BY_NAME_CSV);
    }

    @Test(dataProvider = "searchData")
    public void searchByName(String[] data) {
        String testName = data[0];
        String keyword = data[1];

        System.out.println("Running test: " + testName);

        login();

        HomePage home = new HomePage(driver);
        home.search(keyword);

        SearchPage searchPage = new SearchPage(driver);
        Assert.assertTrue(
                searchPage.resultMAtching(keyword),
                "Search results do not match: " + keyword
        );

        logout();
    }
}