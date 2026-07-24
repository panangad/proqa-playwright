package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.LoginPage;
import support.Config;
import support.DriverFactory;
import support.ScreenshotOnFailureExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(ScreenshotOnFailureExtension.class)
public class NavigationTest {

    private static final By LOGOUT_BUTTON = By.xpath("//button[contains(text(),'Logout')]");
    private static final String[] PAGE_PATHS = { "/inventory" };

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
    void allConfiguredPagesAreReachable() {
        List<String> broken = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String path : PAGE_PATHS) {
            driver.get(Config.baseUrl() + path);
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON));
            } catch (Exception e) {
                broken.add(path + " (no logged-in marker / error state)");
            }
        }

        Assertions.assertTrue(broken.isEmpty(), "Broken pages: " + String.join(", ", broken));
    }
}
