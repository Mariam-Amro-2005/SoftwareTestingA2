package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TabletsPage;
import utils.CSVDataReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class TabletsLinkTest extends BaseTest {

    private static final String TEST_DATA_PATH =
            "src/test/resources/tablets_test_data.csv";

    @DataProvider(name = "tabletsData")
    public Object[][] getData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "tabletsData")
    public void testTabletsPageBreadcrumbAndSidebarHighlight(String[] data) {

        String testName = data[0];
        String expectedBreadcrumb = data[1];
        String expectedSidebar = data[2];

        System.out.println("Running test: " + testName);

        // Step 1 – Login
        login();

        // Step 2 – Go to Tablets page
        HomePage homePage = new HomePage(driver);
        homePage.goToTablets();

        TabletsPage tabletsPage = new TabletsPage(driver);

        // Step 3 – Validate breadcrumb
        String lastBreadcrumb = tabletsPage.getLastBreadcrumbText();
        Assert.assertEquals(lastBreadcrumb, expectedBreadcrumb,
                "Breadcrumb mismatch");

        // Step 4 – Validate sidebar active link
        String activeSidebar = tabletsPage.getActiveSidebarLinkText();
        Assert.assertEquals(activeSidebar, expectedSidebar,
                "Sidebar highlight mismatch");

        // Step 5 – Logout
        logout();
    }
}