package com.generic;

import com.microsoft.playwright.*;

public class AppiumManager {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public void startPlaywright() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        System.out.println("Playwright started and browser launched.");
    }

    public Page getPage() {
        return page;
    }

    public void stopPlaywright() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("Playwright stopped.");
    }
}
