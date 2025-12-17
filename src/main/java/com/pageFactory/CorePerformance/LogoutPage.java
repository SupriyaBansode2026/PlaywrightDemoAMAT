package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;

public class LogoutPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;
    private Locator logout;

    public LogoutPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = new WrapperFunctions(objPojo);
        objConfig = objPojo.getObjConfig();
        logout = objPojo.getPage().locator("//i[text()='oxd_logout_round']");
    }

    public void performLogout() {
        objWrapperFunctions.waitForElementPresence(logout, "5");
        objUtilities.logReporter("clicked on 'Logout' button:", objWrapperFunctions.click(logout), false);
    }
}