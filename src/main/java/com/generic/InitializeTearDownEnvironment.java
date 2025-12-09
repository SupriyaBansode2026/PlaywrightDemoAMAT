package com.generic;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.microsoft.playwright.*;
import java.nio.file.Paths;
public class InitializeTearDownEnvironment 
{
	public static String browserVersion="";
	private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
	/**
	 * @Method		: 	setUpDesktopEnvironment
	 * @Description	:	This method initialize web driver for web application 
	 * 					by opening the browser and URL specified in config.properties file
	 * @author		: 	Harshvardhan Yadav (SQS)
	 */
	public Page initializeWebEnvironment(Properties objConfig) {
        try {
            String browserType = objConfig.getProperty("web.browser").trim().toLowerCase();
            String browserName = objConfig.getProperty("web.browser.name");

            playwright = Playwright.create();

            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false);

            // Handle Chrome Beta binary if specified
            if ("chrome".equals(browserType) && "beta".equalsIgnoreCase(browserName)) {
                File chromeBetaPath = new File("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
                if (!chromeBetaPath.exists()) {
                    String user = System.getProperty("user.name");
                    chromeBetaPath = new File("C:\\Users\\" + user + "\\AppData\\Local\\Google\\Chrome Beta\\Application\\chrome.exe");
                }
                launchOptions.setExecutablePath(chromeBetaPath.toPath());
            }

            // Launch browser based on type
            switch (browserType) {
                case "firefox":
                    browser = playwright.firefox().launch(launchOptions);
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(launchOptions);
                    break;
                case "chrome":
                case "chromium":
                default:
                    browser = playwright.chromium().launch(launchOptions);
                    break;
            }

            // Setup download path
            String downloadPath = System.getProperty("user.dir") + objConfig.getProperty("downloads.path");
            File downloadDir = new File(downloadPath);
            downloadDir.mkdirs();

            // Create browser context with download preferences
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setAcceptDownloads(true);
                // .setDownloadsPath(Paths.get(downloadPath));

            context = browser.newContext(contextOptions);
            page = context.newPage();

            // Log browser version
            browserVersion = browser.version();
            System.out.println("Browser Version is - " + browserVersion);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * @Method		: tearDownWebEnvironment
	 * @Description	: quit webdriver  
	 * @author		: Harshvardhan Yadav (SQS) 
	 */
	public void tearDownWebEnvironment(Properties objConfig) {
	    try {
	        // Close all contexts and pages
	        for (BrowserContext context : browser.contexts()) {
	            for (Page page : context.pages()) {
	                page.close();
	            }
	            context.close();
	        }

	        // Close the browser
	        browser.close();
	        playwright.close();
	    } catch (Exception exception) {
	        exception.printStackTrace();
	        String browserType = objConfig.getProperty("web.browser");
	        if ("IE".equalsIgnoreCase(browserType) || "Chrome".equalsIgnoreCase(browserType)) {
	            killBrowserAndDriver(objConfig);
	        }
	    }
	}

	/**
	 * @Method		: killBrowserAndDriver
	 * @Description	: this method close the web browser    
	 * @author		: Harshvardhan Yadav (SQS)  
	 */
	protected void killBrowserAndDriver(Properties objConfig) {
	    String browser = objConfig.getProperty("web.browser").trim();
	    String browserProcess = "";
	    String driverProcess = "";

	    if (!browser.isEmpty()) {
	        if (browser.equalsIgnoreCase("Chrome")) {
	            browserProcess = "chrome";
	            driverProcess = "chromedriver.exe"; // Only needed if used externally
	        } else if (browser.equalsIgnoreCase("IE")) {
	            browserProcess = "iexplore";
	            driverProcess = "IEDriverServer.exe"; // Not used by Playwright
	        } else if (browser.equalsIgnoreCase("Firefox")) {
	            browserProcess = "firefox";
	            driverProcess = "geckodriver.exe"; // Again, not used by Playwright
	        }
	    }

	    try {
	        if (!driverProcess.isEmpty()) {
	            Process procDriver = Runtime.getRuntime().exec("taskkill /F /T /IM " + driverProcess);
	            procDriver.waitFor();
	        }
	        if (!browserProcess.isEmpty()) {
	            Process procBrowser = Runtime.getRuntime().exec("taskkill /F /T /IM " + browserProcess + ".exe");
	            procBrowser.waitFor();
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	}

}
