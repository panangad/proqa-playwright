package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pages.InventoryPage;
import pages.LoginPage;
import support.Config;
import support.DriverFactory;
import support.ScreenshotOnFailureExtension;

import java.util.List;

@ExtendWith(ScreenshotOnFailureExtension.class)
public class InventoryFilterByStatusTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
        driver.get(Config.baseUrl());
        new LoginPage(driver).login(Config.username(), Config.password());
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quit();
    }

    @Test
    void filtersInventoryByDispatchedStatus() {
        driver.get(Config.baseUrl() + "/inventory");

        InventoryPage inventory = new InventoryPage(driver);
        Assertions.assertTrue(inventory.table().isDisplayed(), "Inventory table should be visible");

        inventory.filterByStatus("Dispatched");

        List<WebElement> rows = inventory.rows();
        Assertions.assertTrue(rows.get(0).isDisplayed(), "First row should be visible after filtering");
        Assertions.assertTrue(rows.size() > 0, "Filtered result should have at least one row");

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(org.openqa.selenium.By.cssSelector("td"));
            String statusText = cells.get(cells.size() - 1).getText().trim();
            Assertions.assertEquals("dispatched", statusText.toLowerCase(),
                    "Every visible row's status column should read 'Dispatched'");
        }
    }
}
