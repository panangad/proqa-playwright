package support;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotOnFailureExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            WebDriver driver = DriverFactory.current();
            if (driver == null) {
                return;
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dir = Path.of("target", "failure-screenshots");
            Files.createDirectories(dir);
            String className = context.getTestClass().map(Class::getSimpleName).orElse("UnknownClass");
            String methodName = context.getTestMethod().map(m -> m.getName()).orElse("unknownMethod");
            Path dest = dir.resolve(className + "." + methodName + ".png");
            Files.copy(screenshot.toPath(), dest);
        } catch (Exception ignored) {
            // never let screenshot capture mask the real test failure
        }
    }
}
