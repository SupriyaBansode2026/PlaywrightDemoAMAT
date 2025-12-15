package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.microsoft.playwright.*;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

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
        Page page = objPojo.getPage();
        loc_DashboardBtn = page.locator("(//a[@ui-sref='dashboard'])[1]");
        objCorePerformancePage = new CorePerformancePage(objPojo);
    }

    public void verifyAssignLeave(String assignLeaveOption, String assignLeaveBtn) {
        Page page = objPojo.getPage();
        Locator loc_AssignLeave = page.locator("//div[text()='" + assignLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = page.locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + assignLeaveBtn + "')]");
        objUtilities.logReporter("Mange User Role gridview :verifygridview()", objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"), false);

        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyLeaveList(String leaveListOption, String leaveListBtn) {
        Page page = objPojo.getPage();
        Locator loc_AssignLeave = page.locator("//div[text()='" + leaveListOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = page.locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + leaveListBtn + "')]");
        objUtilities.logReporter("Mange User Role gridview :verifygridview()", objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"), false);

        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyLeaveCalender(String leaveCalendarOption, String leaveCalendarBtn) {
        Page page = objPojo.getPage();
        Locator loc_AssignLeave = page.locator("//div[text()='" + leaveCalendarOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = page.locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + leaveCalendarBtn + "')]");
        objUtilities.logReporter("Mange User Role gridview :verifygridview()", objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"), false);

        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyApplyLeave(String applyLeaveOption, String applyLeaveBtn) {
        Page page = objPojo.getPage();
        Locator loc_AssignLeave = page.locator("//div[text()='" + applyLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = page.locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + applyLeaveBtn + "')]");
        objUtilities.logReporter("Mange User Role gridview :verifygridview()", objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"), false);

        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }

    public void verifyMyLeave(String myLeaveOption, String myLeaveBtn) {
        Page page = objPojo.getPage();
        Locator loc_AssignLeave = page.locator("//div[text()='" + myLeaveOption + "']/parent::div/preceding-sibling::div[@class='quick-access-button']");
        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_AssignLeave), false);

        Locator loc_AssignLeaveText = page.locator("//a[@ng-if='!!sref && !menuTooltip' and contains(text(), '" + myLeaveBtn + "')]");
        objUtilities.logReporter("Mange User Role gridview :verifygridview()", objWrapperFunctions.getText(loc_AssignLeaveText, "text").contains("Assign"), false);

        objUtilities.logReporter("Click Assign Leave:clickAssignLeave()", objWrapperFunctions.click(loc_DashboardBtn), false);
    }
}