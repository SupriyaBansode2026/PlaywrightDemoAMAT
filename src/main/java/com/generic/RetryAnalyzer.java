package com.generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private int maxRetryCount;
    private final Pojo objPojo = new Pojo();
    public static int globalRetryCount = 0;

    public RetryAnalyzer() {
        Properties config = new Properties();
        int defaultLimit = 1; // default retry limit if config file missing

        try {
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            config.load(new FileInputStream(configPath));
            maxRetryCount = Integer.parseInt(config.getProperty("RetryLimit", String.valueOf(defaultLimit)));
        } catch (IOException e) {
            e.printStackTrace();
            maxRetryCount = defaultLimit;
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        System.out.println("Retry attempt: " + (retryCount + 1) + " for test: " + result.getName());

        if (!result.isSuccess() && retryCount < maxRetryCount) {
            retryCount++;
            globalRetryCount++;
            objPojo.setObjectId(""); // reset context before retry
            result.setStatus(ITestResult.FAILURE);
            return true; // ask TestNG to retry
        }

        // Stop retrying after reaching limit
        result.setStatus(result.isSuccess() ? ITestResult.SUCCESS : ITestResult.FAILURE);
        return false;
    }
}
