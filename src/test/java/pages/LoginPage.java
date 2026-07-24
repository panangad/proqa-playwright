package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = By.cssSelector("input[placeholder='Enter username']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[type=password]");
    private static final By LOGIN_BUTTON = By.xpath("//button[contains(text(),'Login')]");
    private static final By LOGOUT_BUTTON = By.xpath("//button[contains(text(),'Logout')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        waitFor(USERNAME_INPUT).sendKeys(username);
        waitFor(PASSWORD_INPUT).sendKeys(password);
        waitForClickable(LOGIN_BUTTON).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        waitFor(LOGOUT_BUTTON);
    }
}
