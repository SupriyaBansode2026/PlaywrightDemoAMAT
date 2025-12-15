package com.pageFactory.CorePerformance;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.microsoft.playwright.Locator;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class DashboardPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objCCBProperties;
    boolean bResult = false;

    private String widgetListSelector = "//div[@class=\"row\"]/div//span[@class=\"widget-content-container\"]//div[@class=\"dashboard-widget-container\"]//span[@class=\"widget-header-text\" or @style='margin-left: 0.75rem; text-align: left']";
    private String buzzSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Buzz')]";
    private String leaveToAprvSelector = "//span[text()='Leave Requests to Approve']/preceding-sibling::span";
    private String ukAttendenceSelector = "//span[text()='Attendance Sheets to Approve']/preceding-sibling::span";
    private String generalReqToAprvSelector = "//span[text()='General Requests to Approve']/preceding-sibling::span";
    private String myactionTabLinks = "//span[text()='%s']";
    private String homeIconSelector = "//i[text()='oxd_home_menu']";

    private String leavePaginationSelector = "//li[@class='summary']";
    private String generalPaginationSelector = "//li[@class='summary']";
    private String ukAttendenceApprovalSelector = "//span[@class='available-count']";

    List<String> expectedWidgetList = new ArrayList<>();

    int leaveRequestCount = 0;
    int ukAttendanceCount = 0;
    int generalRequestCount = 0;

    public DashboardPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objCCBProperties = objPojo.getObjConfig();
    }

    public void verifyDashboardPageUrl() {
        objUtilities.logReporter("Dashboard page loaded : verifyDashboardPageUrl()",
                objUtilities.getCurrentURL().contains("dashboard"), false);
    }

    public String getPageTitle() {
        return objUtilities.getPageTitle();
    }

    public List<Locator> getWebElementsList() {
        return objWrapperFunctions.getWebElementList(widgetListSelector);
    }

    public boolean getElementsList(String expectedWidgetList) {
        List<Locator> elelist = objWrapperFunctions.getWebElementList(widgetListSelector);
        List<String> widgetTexts = new ArrayList<String>();
        for (Locator ele : elelist) {
            widgetTexts.add(ele.textContent() == null ? "" : ele.textContent().trim());
        }
        String[] liArr = expectedWidgetList.split("~");
        for (String str : liArr) {
            if (widgetTexts.contains(str))
                return true;
            else
                break;
        }
        return false;
    }

    public void verifyElementsList(String expectedWidgetList) {
        boolean flag = getElementsList(expectedWidgetList);
        objUtilities.logReporter("Widget are available on dashboard page : getElementsList()", flag, false);
    }

    public void checkSubTabVisibiltyOnDashboardPage(String elementToBeCheck) {
        String subTabToBeCheckSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'" + elementToBeCheck + "')]";
        objUtilities.logReporter(
                elementToBeCheck + " Sub-tab displayed on dashboard page : checkSubTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.checkElementDisplyed(subTabToBeCheckSelector), false);
    }

    public void checkLeftMenuTabVisibiltyOnDashboardPage(String menu) {
        String menuTabToBeCheckSelector = "(//li//span[text()='" + menu + "'])[1]";

        objUtilities.logReporter("Scroll till Left Menu Tab " + menu + " : checkLeftMenuTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.scrollTillElement(menuTabToBeCheckSelector), false);

        objUtilities.logReporter(
                menu + " Left Menu Tab displayed on dashboard page : checkLeftMenuTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.checkElementDisplyed(menuTabToBeCheckSelector), false);
    }

    public void clickOnBuzz() {
        objUtilities.logReporter("Clicked on Buzz option: clickOnBuzz()", objWrapperFunctions.click(buzzSelector), false);
    }

    public void requestCount(String option) {
        int count = 0;
        if (option.equals(objCCBProperties.getProperty("leaveReqAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(leaveToAprvSelector);
            String txt = objWrapperFunctions.getText(leaveToAprvSelector, "text");
            count = extractText(txt);
            leaveRequestCount = count;
            System.out.println("Total Leave Requests to Approve count is - " + leaveRequestCount);
        } else if (option.equals(objCCBProperties.getProperty("ukAttendenceAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(ukAttendenceSelector);
            String txt = objWrapperFunctions.getText(ukAttendenceSelector, "text");
            count = extractText(txt);
            ukAttendanceCount = count;
            System.out.println("Total UK Attendance sheets to Approve count is - " + ukAttendanceCount);
        } else if (option.equals(objCCBProperties.getProperty("generalReqAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(generalReqToAprvSelector);
            String txt = objWrapperFunctions.getText(generalReqToAprvSelector, "text");
            count = extractText(txt);
            generalRequestCount = count;
            System.out.println("Total General Requests to Approve count is - " + generalRequestCount);
        }
    }

    public int extractText(String text) {
        if (text.contains("(") && text.contains(")")) {
            String countText = text.substring(text.indexOf("(") + 1, text.indexOf(")"));
            return Integer.parseInt(countText.trim());
        } else {
            System.out.println(text);
            throw new IllegalArgumentException("Text format is invalid: " + text);
        }
    }

    public String getText(String locator, String locatorvalue, boolean applyWait) {
        String eleSelector = locatorvalue;
        return objWrapperFunctions.getText(eleSelector, "text");
    }

    public String getText(String locatorvalue) {
        String eleSelector = locatorvalue;
        return objWrapperFunctions.getText(eleSelector, "text");
    }

    public void clickOnLink(String text) {
        String locator = String.format(myactionTabLinks, text);
        objUtilities.logReporter("Click on '" + text + " ' link : clickOnLink()", objWrapperFunctions.click(locator), false);
    }

    public void verifyUrl(String urlText) {
        objUtilities.logReporter("Link contains '" + urlText + "' : verifyUrl()",
                objWrapperFunctions.waitTillUrlContains(urlText), false);
    }

    public void clickOnIcon() {
        objWrapperFunctions.waitForElementPresence(homeIconSelector);
        objUtilities.logReporter("clicked on Home Icon: clickOnIcon()", objWrapperFunctions.click(homeIconSelector), false);
    }

    public void getLeavePaginationText(String val) {
        String str = objWrapperFunctions.getText(leavePaginationSelector, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
                "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget : getLeavePaginationText()",
                leaveRequestCount == count, false);
    }

    public void getGeneralRequestPaginationText(String val) {
        String str = objWrapperFunctions.getText(generalPaginationSelector, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
                "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget : getGeneralRequestPaginationText()",
                ukAttendanceCount == count, false);
    }

    public void getUKAttendenceApprovalText(String val) {
        String str = objWrapperFunctions.getText(ukAttendenceApprovalSelector, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
                "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget : getUKAttendenceApprovalText()",
                generalRequestCount == count, false);
    }

    public int getTheCount(String text) {

        if (text.contains("of")) {
            String[] arr = text.split("of");
            String countText = arr[1];
            return Integer.parseInt(countText.trim());
        } else if (text.contains("(") && text.contains(")")) {
            return extractText(text);
        } else {
            System.out.println(text);
            throw new IllegalArgumentException("Text format is invalid: " + text);
        }
    }

    public void switchingToWindow(String text, String url) {
        if (objUtilities.getCurrentURL().contains(url))
            objUtilities.logReporter("Switched to new window open after clicked on Buzz: switchingToWindow()",
                    objWrapperFunctions.switchToWindowByContainsTitle(text), false);
    }

    public void switchBack(int index) {
        objUtilities.switchToTabBasedOnIndex(index);
    }
}