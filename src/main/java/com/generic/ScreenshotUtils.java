package com.generic;


import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.*;

public class ScreenshotUtils {

    private static final String BASE_DIR = "screenshots";

    private ScreenshotUtils() {}

    public static void capture(Page page,
                               String testName,
                               String stepName,
                               int stepNumber) {

        try {
            Path testDir = Paths.get(BASE_DIR, testName);
            Files.createDirectories(testDir);

            String fileName = String.format(
                    "%02d_%s.png",
                    stepNumber,
                    stepName.replaceAll("[^a-zA-Z0-9]", "_")
            );

            Path screenshotPath = testDir.resolve(fileName);

            // Save locally
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(screenshotPath)
                            .setFullPage(true)
            );

            // Attach to Allure
            Allure.addAttachment(
                    stepName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
