package com.pageFactory.CorePerformance;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objCCBProperties;
    boolean bResult = false;
    private Page page;

    private Locator widgetList;
    private Locator buzz;
    private Locator leaveToAprv;
    private Locator ukAttendence;
    private Locator generalReqToAprv;
    private String myactionTabLinks = "//span[text()='%s']";
    private Locator homeIcon;

    private Locator leavePagination;
    private Locator generalPagination;
    private Locator ukAttendenceApproval;

    List<String> expectedWidgetList = new ArrayList<>();

    int leaveRequestCount = 0;
    int ukAttendanceCount = 0;
    int generalRequestCount = 0;

    public DashboardPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = new WrapperFunctions(objPojo);
        objCCBProperties = objPojo.getObjConfig();
        page = objPojo.getPage();
        widgetList = page.locator("//div[@class=\"row\"]/div//span[@class=\"widget-content-container\"]//div[@class=\"dashboard-widget-container\"]//span[@class=\"widget-header-text\" or @style='margin-left: 0.75rem; text-align: left']");
        buzz = page.locator("//div[@class='top-level-menu-item-container']/a[contains(text(),'Buzz')]");
        leaveToAprv = page.locator("//span[text()='Leave Requests to Approve']/preceding-sibling::span");
        ukAttendence = page.locator("//span[text()='Attendance Sheets to Approve']/preceding-sibling::span");
        generalReqToAprv = page.locator("//span[text()='General Requests to Approve']/preceding-sibling::span");
        homeIcon = page.locator("//i[text()='oxd_home_menu']");
        leavePagination = page.locator("//li[@class='summary']");
        generalPagination = page.locator("//li[@class='summary']");
        ukAttendenceApproval = page.locator("//span[@class='available-count']");
    }

    public void verifyDashboardPageUrl() {
        objUtilities.logReporter("Dashboard page loaded : verifyDashboardPageUrl()",
                objUtilities.getCurrentURL().contains("dashboard"), false);
    }

    public String getPageTitle() {
        return objUtilities.getPageTitle();
    }

    public List<Locator> getWebElementsList() {
        return objWrapperFunctions.getWebElementList("//div[@class=\"row\"]/div//span[@class=\"widget-content-container\"]//div[@class=\"dashboard-widget-container\"]//span[@class=\"widget-header-text\" or @style='margin-left: 0.75rem; text-align: left']");
    }

    public boolean getElementsList(String expectedWidgetList) {
        List<Locator> elelist = objWrapperFunctions.getWebElementList("//div[@class=\"row\"]/div//span[@class=\"widget-content-container\"]//div[@class=\"dashboard-widget-container\"]//span[@class=\"widget-header-text\" or @style='margin-left: 0.75rem; text-align: left']");
        List<String> widgetListActual = new ArrayList<String>();
        for (Locator ele : elelist) {
            widgetListActual.add(ele.textContent().trim());
        }
        String[] liArr = expectedWidgetList.split("~");
        for (String str : liArr) {
            if (widgetListActual.contains(str))
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
        String subTabXpath = "//div[@class='top-level-menu-item-container']/a[contains(text(),'" + elementToBeCheck + "')]";
        Locator subTabToBeCheck = page.locator(subTabXpath);
        objWrapperFunctions.waitForElementPresence(subTabToBeCheck, "5");
        objUtilities.logReporter(
                elementToBeCheck + " Sub-tab displayed on dashboard page : checkSubTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.checkElementDisplyed(subTabToBeCheck), false);
    }

    public void checkLeftMenuTabVisibiltyOnDashboardPage(String menu) {
        String menuTabXpath = "(//li//span[text()='" + menu + "'])[1]";
        Locator menuTabToBeCheck = page.locator(menuTabXpath);

        objWrapperFunctions.waitForElementPresence(menuTabToBeCheck, "5");
        objUtilities.logReporter("Scroll till Left Menu Tab " + menu + " : checkLeftMenuTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.scrollTillElement(menuTabXpath), false);

        objWrapperFunctions.waitForElementPresence(menuTabToBeCheck, "5");
        objUtilities.logReporter(
                menu + " Left Menu Tab displayed on dashboard page : checkLeftMenuTabVisibiltyOnDashboardPage()",
                objWrapperFunctions.checkElementDisplyed(menuTabToBeCheck), false);
    }

    public void clickOnBuzz() {
        objWrapperFunctions.waitForElementPresence(buzz, "5");
        objUtilities.logReporter("Clicked on Buzz option: clickOnBuzz()", objWrapperFunctions.click(buzz), false);
    }

    public void requestCount(String option) {
        int count = 0;
        if (option.equals(objCCBProperties.getProperty("leaveReqAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(leaveToAprv, "5");
            String txt = objWrapperFunctions.getText(leaveToAprv, "text");
            count = extractText(txt);
            leaveRequestCount = count;
            System.out.println("Total Leave Requests to Approve count is - " + leaveRequestCount);
        } else if (option.equals(objCCBProperties.getProperty("ukAttendenceAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(ukAttendence, "5");
            String txt = objWrapperFunctions.getText(ukAttendence, "text");
            count = extractText(txt);
            ukAttendanceCount = count;
            System.out.println("Total UK Attendance sheets to Approve count is - " + ukAttendanceCount);
        } else if (option.equals(objCCBProperties.getProperty("generalReqAprvlLink"))) {
            objWrapperFunctions.waitForElementPresence(generalReqToAprv, "5");
            String txt = objWrapperFunctions.getText(generalReqToAprv, "text");
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
        Locator ele = page.locator(locatorvalue);
        objWrapperFunctions.waitForElementPresence(ele, "5");
        return objWrapperFunctions.getText(ele, "text");
    }

    public String getText(String locatorvalue) {
        Locator ele = page.locator(locatorvalue);
        objWrapperFunctions.waitForElementPresence(ele, "5");
        return objWrapperFunctions.getText(ele, "text");
    }

    public void clickOnLink(String text) {
        String locator = String.format(myactionTabLinks, text);
        Locator eleToClick = page.locator(locator);
        objWrapperFunctions.waitForElementPresence(eleToClick, "5");
        objUtilities.logReporter("Click on '" + text + " ' link : clickOnLink()", objWrapperFunctions.click(eleToClick),
                false);
    }

    public void verifyUrl(String urlText) {
        objUtilities.logReporter("Link contains '" + urlText + "' : verifyUrl()",
                objWrapperFunctions.waitTillUrlContains(urlText), false);
    }

    public void clickOnIcon() {
        objWrapperFunctions.waitForElementPresence(homeIcon, "5");
        objUtilities.logReporter("clicked on Home Icon: clickOnIcon()", objWrapperFunctions.click(homeIcon), false);
    }

    public void getLeavePaginationText(String val) {
        objWrapperFunctions.waitForElementPresence(leavePagination, "5");
        String str = objWrapperFunctions.getText(leavePagination, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
                "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget : getLeavePaginationText()",
                leaveRequestCount == count, false);
    }

    public void getGeneralRequestPaginationText(String val) {
        objWrapperFunctions.waitForElementPresence(generalPagination, "5");
        String str = objWrapperFunctions.getText(generalPagination, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
                "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget : getGeneralRequestPaginationText()",
                ukAttendanceCount == count, false);
    }

    public void getUKAttendenceApprovalText(String val) {
        objWrapperFunctions.waitForElementPresence(ukAttendenceApproval, "5");
        String str = objWrapperFunctions.getText(ukAttendenceApproval, val);
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