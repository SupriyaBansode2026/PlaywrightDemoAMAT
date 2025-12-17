package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;

public class LeavePage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;
    private CorePerformancePage objCorePerformancePage;

    private Locator loc_DashboardBtn;

    public LeavePage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = new WrapperFunctions(objPojo);
        objConfig = objPojo.getObjConfig();
        objCorePerformancePage = new CorePerformancePage(objPojo);
        loc_DashboardBtn = objPojo.getPage().locator("(//a[@ui-sref='dashboard'])[1]").first();
    }

    public void verifyAssignLeave(String assignLeaveOption, String assignLeaveBtn) {
        Locator loc_AssignLeave = objPojo.getPage().locator("//div[text()='" + assignLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeave, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = objPojo.getPage().locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + assignLeaveBtn + "')]").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeaveText, "5");
        objUtilities.logReporter(
                "Mange User Role gridview :verifygridview() ",
                objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"),
                false
        );

        objWrapperFunctions.waitForElementPresence(loc_DashboardBtn, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyLeaveList(String leaveListOption, String leaveListBtn) {
        Locator loc_AssignLeave = objPojo.getPage().locator("//div[text()='" + leaveListOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeave, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = objPojo.getPage().locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + leaveListBtn + "')]").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeaveText, "5");
        objUtilities.logReporter(
                "Mange User Role gridview :verifygridview() ",
                objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"),
                false
        );

        objWrapperFunctions.waitForElementPresence(loc_DashboardBtn, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyLeaveCalender(String leaveCalendarOption, String leaveCalendarBtn) {
        Locator loc_AssignLeave = objPojo.getPage().locator("//div[text()='" + leaveCalendarOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeave, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = objPojo.getPage().locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + leaveCalendarBtn + "')]").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeaveText, "5");
        objUtilities.logReporter(
                "Mange User Role gridview :verifygridview() ",
                objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"),
                false
        );

        objWrapperFunctions.waitForElementPresence(loc_DashboardBtn, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyApplyLeave(String applyLeaveOption, String applyLeaveBtn) {
        Locator loc_AssignLeave = objPojo.getPage().locator("//div[text()='" + applyLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeave, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = objPojo.getPage().locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + applyLeaveBtn + "')]").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeaveText, "5");
        objUtilities.logReporter(
                "Mange User Role gridview :verifygridview() ",
                objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"),
                false
        );

        objWrapperFunctions.waitForElementPresence(loc_DashboardBtn, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyMyLeave(String myLeaveOption, String myLeaveBtn) {
        Locator loc_AssignLeave = objPojo.getPage().locator("//div[text()='" + myLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeave, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = objPojo.getPage().locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + myLeaveBtn + "')]").first();
        objWrapperFunctions.waitForElementPresence(loc_AssignLeaveText, "5");
        objUtilities.logReporter(
                "Mange User Role gridview :verifygridview() ",
                objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"),
                false
        );

        objWrapperFunctions.waitForElementPresence(loc_DashboardBtn, "5");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }
}