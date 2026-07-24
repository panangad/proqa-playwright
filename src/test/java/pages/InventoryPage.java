package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final By TABLE = By.cssSelector("table");
    private static final By STATUS_DROPDOWN = By.cssSelector("select");
    private static final By SEARCH_BOX = By.cssSelector("input[placeholder='Search by Order Code, Customer Name, Customer Phone...']");
    private static final By PRINT_SELECTED_BUTTON = By.xpath("//button[contains(text(),'Print Selected')]");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement table() {
        return waitFor(TABLE);
    }

    public List<WebElement> rows() {
        waitLoaded();
        return table().findElements(By.cssSelector("tbody tr"));
    }

    private void waitLoaded() {
        wait.until(d -> {
            List<WebElement> rs = table().findElements(By.cssSelector("tbody tr"));
            return !rs.isEmpty() && !rs.get(0).getText().toLowerCase().contains("loading");
        });
    }

    public void filterByStatus(String label) {
        WebElement dropdown = waitFor(STATUS_DROPDOWN);
        new Select(dropdown).selectByVisibleText(label);
    }

    public void searchByCustomer(String query) {
        WebElement box = waitFor(SEARCH_BOX);
        box.clear();
        box.sendKeys(query);
    }

    public WebElement printSelectedButton() {
        return waitFor(PRINT_SELECTED_BUTTON);
    }

    public WebElement firstRowSelectButton() {
        List<WebElement> rows = rows();
        return rows.get(0).findElements(By.cssSelector("button")).get(0);
    }
}
