package com.generic;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class AllureUtils {

    private AllureUtils() {}

    public static void attachScreenshot(Page page, String name) {
        byte[] screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true));

        Allure.addAttachment(
                name,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }
}
