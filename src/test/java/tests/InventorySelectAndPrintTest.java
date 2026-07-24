package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.InventoryPage;
import pages.LoginPage;
import support.Config;
import support.DriverFactory;
import support.ScreenshotOnFailureExtension;

import java.time.Duration;

@ExtendWith(ScreenshotOnFailureExtension.class)
public class InventorySelectAndPrintTest {

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
    void selectsOrderAndBulkPrintsInvoices() {
        driver.get(Config.baseUrl() + "/inventory");

        InventoryPage inventory = new InventoryPage(driver);
        Assertions.assertTrue(inventory.table().isDisplayed(), "Inventory table should be visible");

        WebElement printButton = inventory.printSelectedButton();
        Assertions.assertEquals("Print Selected (0)", printButton.getText().trim());
        Assertions.assertFalse(printButton.isEnabled(), "Print button should be disabled with no selection");

        WebElement firstRowSelect = inventory.firstRowSelectButton();
        firstRowSelect.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> inventory.printSelectedButton().getText().trim().equals("Print Selected (1)"));
        printButton = inventory.printSelectedButton();
        Assertions.assertEquals("Print Selected (1)", printButton.getText().trim());
        Assertions.assertTrue(printButton.isEnabled(), "Print button should be enabled after selecting a row");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Print Selected')]")))
                .click();

        wait.until(d -> !d.findElements(By.xpath("//*[contains(text(),'PROQA BOUTIQUE')]")).isEmpty());
        boolean invoicePresent = !driver.findElements(By.xpath("//*[contains(text(),'PROQA BOUTIQUE')]")).isEmpty();
        Assertions.assertTrue(invoicePresent, "Invoice content should be attached to the DOM after printing");
    }
}
