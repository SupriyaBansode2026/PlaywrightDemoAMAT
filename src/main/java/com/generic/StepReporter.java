package com.generic;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepReporter {

    private static final Logger logger =
            LoggerFactory.getLogger(StepReporter.class);

    private StepReporter() {}

    public static void stepWithScreenshot(Page page, String message) {
        logger.info(message);

        Allure.step(message, () -> {
            ScreenshotUtils.capture(
                    page,
                    TestContext.getTestName(),
                    message,
                    TestContext.nextStep()
            );
        });
    }
}
