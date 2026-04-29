package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TabletsPage;

public class TabletsLinkTest extends BaseTest {

    @Test
    public void testTabletsPageBreadcrumbAndSidebarHighlight() {

        // Step 1 – Log in
        login();

        // Step 2 – Click "Tablets" in the navbar
        HomePage homePage = new HomePage(driver);
        homePage.goToTablets();

        TabletsPage tabletsPage = new TabletsPage(driver);

        // Step 3 – Assert the last breadcrumb item is "Tablets"
        String lastBreadcrumb = tabletsPage.getLastBreadcrumbText();
        Assert.assertEquals(lastBreadcrumb, "Tablets",
                "Last breadcrumb should be 'Tablets'");

        // Step 4 – Assert the highlighted sidebar link is "Tablets"
        String activeSidebar = tabletsPage.getActiveSidebarLinkText();
        Assert.assertEquals(activeSidebar, "Tablets",
                "Active sidebar link should be 'Tablets'");

        // Step 5 – Log out
        logout();
    }
}