package com.pageFactory.CorePerformance;

import java.util.List;
import java.util.Properties;

import com.microsoft.playwright.Locator;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class AnnouncementDropDownPage {

    private AnnouncementDropDownPage objCorePerformancePage;
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;

    private Locator AnnouncementDD;
    private Locator announcementDropdown;
    private Locator documents;

    List<Locator> list;

    public AnnouncementDropDownPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();

        // Initialize locators
        AnnouncementDD = objPojo.getPage().locator("//div[@class='top-level-menu-item-container']/a[contains(text(),'Announcements')]");
        announcementDropdown = objPojo.getPage().locator("//a[text()='Announcements ']/following-sibling::sub-menu-container/div//div");
        documents = objPojo.getPage().locator("//a[text()=' Documents ']");
    }

    public void clickOnAnnouncement() {
        objWrapperFunctions.waitForElementPresence(AnnouncementDD, "5");
        objUtilities.logReporter(
            "Clicked on Announcement option: clickOnAnnouncement() ",
            objWrapperFunctions.click(AnnouncementDD),
            false
        );
    }

    public void getDropdownValues() {
        objWrapperFunctions.waitForElementPresence(announcementDropdown, "5");
        list = objWrapperFunctions.getWebElementList("//a[text()='Announcements ']/following-sibling::sub-menu-container/div//div");
        for (Locator ele : list) {
            System.out.println(ele.textContent());
        }
    }

    public void verifyDropdownElement(String dropValue1, String dropValue2) {
        boolean hasDropValue1 = false;
        boolean hasDropValue2 = false;

        if (list != null) {
            for (Locator ele : list) {
                String text = ele.textContent();
                if (text != null && text.trim().equals(dropValue1)) {
                    hasDropValue1 = true;
                }
                if (text != null && text.trim().equals(dropValue2)) {
                    hasDropValue2 = true;
                }
            }
        }

        objUtilities.logReporter(
            "Announcement Dropdown contains Documents option: verifyDropdownElement()",
            hasDropValue1,
            false
        );
        objUtilities.logReporter(
            "Announcement Dropdown contains News option: verifyDropdownElement()",
            hasDropValue2,
            false
        );
    }

    public void clickOnDocuments() {
        objWrapperFunctions.waitForElementPresence(documents, "5");
        objUtilities.logReporter(
            "Clicked on Documents option: clickOnDocuments()",
            objWrapperFunctions.click(documents),
            false
        );
    }

    public void verifyDocsPageUrl(String text) {
        objUtilities.logReporter(
            "Verify user navigated to documents page : verifyDocsPageUrl()",
            objUtilities.getCurrentURL().contains(text),
            false
        );
    }
}