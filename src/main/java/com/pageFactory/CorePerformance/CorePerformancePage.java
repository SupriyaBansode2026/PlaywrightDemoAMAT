package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CorePerformancePage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    private Page page;
    boolean bResult = false;

    public CorePerformancePage(Pojo objPojo) {

        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        page = objPojo.getPage();
    }

    // Add Playwright locators, methods, and logic here as needed for CorePerformancePage functionality.
}