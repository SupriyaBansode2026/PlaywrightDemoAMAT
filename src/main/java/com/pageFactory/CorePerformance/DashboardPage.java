package com.pageFactory.CorePerformance;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.microsoft.playwright.*;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class DashboardPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;

    private String widgetListSelector = "//div[@class=\"row\"]/div//span[@class=\"widget-content-container\"]//div[@class=\"dashboard-widget-container\"]//span[@class=\"widget-header-text\" or @style='margin-left: 0.75rem; text-align: left']";
    private String empListSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Employee')]";
    private String myInfoSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'My')]";
    private String directorySelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Directory')]";
    private String buzzSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Buzz')]";
    private String announcmntSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Announcements')]";
    private String leaveToAprvSelector = "//span[text()='Leave Requests to Approve']/preceding-sibling::span";
    private String ukAttendenceSelector = "//span[text()='Attendance Sheets to Approve']/preceding-sibling::span";
    private String generalReqToAprvSelector = "//span[text()='General Requests to Approve']/preceding-sibling::span";
    private String myactionTabLinks = "//span[text()='%s']";
    private String homeIconSelector = "//i[text()='oxd_home_menu']";
    private String leavePaginationSelector = "//li[@class='summary']";
    private String generalPaginationSelector = "//li[@class='summary']";
    private String ukAttendenceApprovalSelector = "//span[@class='available-count']";
    private String mostLikedPostSelector = "//button[@class='oxd-icon-button back-to-dashboard-button']";
    List<String> expectedWidgetList = new ArrayList<>();

    int leaveRequestCount = 0;
    int ukAttendanceCount = 0;
    int generalRequestCount = 0;

    private Page page;

    public DashboardPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        page = objPojo.getPage();
    }

    public String verifyDashboardPageUrl() {
        return objUtilities.getCurrentURL();
    }

    public String getPageTitle() {
        return objUtilities.getPageTitle();
    }

    public List<Locator> getWebElementsList() {
        return objWrapperFunctions.getWebElementList(widgetListSelector);
    }

    public void widgetListOption() {
        expectedWidgetList.add("My Actions");
        expectedWidgetList.add("Quick Access");
        expectedWidgetList.add("Time At Work");
        expectedWidgetList.add("Employees on Leave Today");
        expectedWidgetList.add("Latest News");
        expectedWidgetList.add("Latest Documents");
        expectedWidgetList.add("Buzz Latest Posts");
        expectedWidgetList.add("Current Year's Leave Taken by Department");
        expectedWidgetList.add("Headcount by Location");
        expectedWidgetList.add("Annual Basic Payment by Location");
    }

    public void getElementsList() {
        List<Locator> elelist = objWrapperFunctions.getWebElementList(widgetListSelector);
        List<String> widgetList = new ArrayList<String>();
        for (Locator ele : elelist) {
            widgetList.add(ele.textContent() == null ? "" : ele.textContent().trim());
        }
        boolean present = widgetList.containsAll(expectedWidgetList);
        objUtilities.logReporter("Widget are available on dashboard page : ", present, false);
    }

    public void isEmployeeListElementDisplay() {
        objUtilities.logReporter(
            "Employee List sub-tab displayed on dashboard page : ",
            objWrapperFunctions.checkElementDisplyed(empListSelector),
            false
        );
    }

    public void isMyInfoElementDisplay() {
        objUtilities.logReporter(
            "My Info sub-tab displayed on dashboard page : ",
            objWrapperFunctions.checkElementDisplyed(myInfoSelector),
            false
        );
    }

    public void isDirectoryElementDisplay() {
        objUtilities.logReporter(
            "Directory sub-tab displayed on dashboard page : ",
            objWrapperFunctions.checkElementDisplyed(directorySelector),
            false
        );
    }

    public void isBuzzElementDisplay() {
        objUtilities.logReporter(
            "Buzz sub-tab displayed on dashboard page : ",
            objWrapperFunctions.checkElementDisplyed(buzzSelector),
            false
        );
    }

    public void clickOnBuzz() {
        objWrapperFunctions.click(buzzSelector);
    }

    public void isAnnouncementElementDisplay() {
        objUtilities.logReporter(
            "Announcement sub-tab displayed on dashboard page : ",
            objWrapperFunctions.checkElementDisplyed(announcmntSelector),
            false
        );
    }

    public void requestCount(String option) {
        int count = 0;
        if (option.equals("Leave request to Approve")) {
            objWrapperFunctions.waitForElementPresence(leaveToAprvSelector);
            String txt = objWrapperFunctions.getText(leaveToAprvSelector, "text");
            count = extractText(txt);
            leaveRequestCount = count;
            System.out.println("Total Leave Requests to Approve count is - " + leaveRequestCount);
        } else if (option.equals("Attendance Sheets to Approve")) {
            objWrapperFunctions.waitForElementPresence(ukAttendenceSelector);
            String txt = objWrapperFunctions.getText(ukAttendenceSelector, "text");
            count = extractText(txt);
            ukAttendanceCount = count;
            System.out.println("Total UK Attendance sheets to Approve count is - " + ukAttendanceCount);
        } else if (option.equals("General Requests to Approve")) {
            objWrapperFunctions.waitForElementPresence(generalReqToAprvSelector);
            String txt = objWrapperFunctions.getText(generalReqToAprvSelector, "text");
            count = extractText(txt);
            generalRequestCount = count;
            System.out.println("Total General Requests to Approve count is - " + generalRequestCount);
        }
    }

    public int extractText(String text) {
        if (text == null) throw new IllegalArgumentException("Text format is invalid: null");
        if (text.contains("(") && text.contains(")")) {
            String countText = text.substring(text.indexOf("(") + 1, text.indexOf(")"));
            return Integer.parseInt(countText.trim());
        } else {
            System.out.println(text);
            throw new IllegalArgumentException("Text format is invalid: " + text);
        }
    }

    public String getText(String locator, String locatorvalue, boolean applyWait) {
        Locator ele = page.locator("xpath=" + locatorvalue).first();
        return objWrapperFunctions.getText(ele, "text");
    }

    public String getText(String locatorvalue) {
        Locator ele = page.locator("xpath=" + locatorvalue).first();
        return objWrapperFunctions.getText(ele, "text");
    }

    public void clickOnLink(String text) {
        String locatorText = String.format(myactionTabLinks, text);
        Locator eleToClick = page.locator("xpath=" + locatorText).first();
        boolean result = objWrapperFunctions.click(eleToClick);
        objUtilities.logReporter("Click on '" + text + " ' link : ", result, false);
    }

    public void verifyUrl(String urlText) {
        objUtilities.logReporter(
            "Link contains '" + urlText + "' : ",
            objWrapperFunctions.waitTillUrlContains(urlText),
            false
        );
    }

    public void clickOnIcon() {
        objWrapperFunctions.waitForElementPresence(homeIconSelector);
        objWrapperFunctions.click(homeIconSelector);
    }

    public void getLeavePaginationText(String val) {
        Locator leavePaginationLocator = page.locator(leavePaginationSelector).first();
        String str = objWrapperFunctions.getText(leavePaginationLocator, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
            "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget",
            leaveRequestCount == count,
            false
        );
    }

    public void getGeneralRequestPaginationText(String val) {
        Locator generalPaginationLocator = page.locator(generalPaginationSelector).first();
        String str = objWrapperFunctions.getText(generalPaginationLocator, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
            "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget",
            leaveRequestCount == count,
            false
        );
    }

    public void getUKAttendenceApprovalText(String val) {
        Locator ukAttendenceApprovalLocator = page.locator(ukAttendenceApprovalSelector).first();
        String str = objWrapperFunctions.getText(ukAttendenceApprovalLocator, val);
        int count = getTheCount(str);
        objUtilities.logReporter(
            "Pagination count on Leave Page match the 'Leave Request Approval' count from My Actions widget",
            leaveRequestCount == count,
            false
        );
    }

    public int getTheCount(String text) {
        if (text == null) throw new IllegalArgumentException("Text format is invalid: null");
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

    public boolean switchingToWindow(String text, String url) {
        if (objUtilities.getCurrentURL().contains(url)) {
            return objWrapperFunctions.switchToWindowByContainsTitle(text);
        } else {
            return false;
        }
    }

    public void clickOnSearchField(String textToBeEnter) {
        objWrapperFunctions.click(mostLikedPostSelector);
    }

    public void clickOnMostLikedPostElement() {
        objWrapperFunctions.click(mostLikedPostSelector);
    }
}