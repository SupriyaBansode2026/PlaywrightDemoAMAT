package com.pageFactory.CorePerformance;

import java.util.List;
import java.util.Properties;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class AnnouncementDropDownPage {

	private AnnouncementDropDownPage objCorePerformancePage;
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	private Page page;
	boolean bResult = false;

	private String AnnouncementDDSelector = "//div[@class='top-level-menu-item-container']/a[contains(text(),'Announcements')]";
	private String announcementDropdownSelector = "//a[text()='Announcements ']/following-sibling::sub-menu-container/div//div";
	private String documentsSelector = "//a[text()=' Documents ']";
	
	List<Locator> list;

	public AnnouncementDropDownPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();
	}

	public void clickOnAnnouncement() {
		objUtilities.logReporter("Clicked on Announcement option: clickOnAnnouncement() ",
			objWrapperFunctions.click(AnnouncementDDSelector), false);
	}

	public void getDropdownValues() {
		list = objWrapperFunctions.getWebElementList(announcementDropdownSelector);
		for (Locator ele : list) {
			System.out.println(ele.textContent());
		}
	}
	
	public void verifyDropdownElement(String dropValue1, String dropValue2) {
		boolean containsDropValue1 = false;
		boolean containsDropValue2 = false;
		if (list != null) {
			for (Locator ele : list) {
				String text = ele.textContent();
				if (text != null && text.trim().equals(dropValue1)) {
					containsDropValue1 = true;
				}
				if (text != null && text.trim().equals(dropValue2)) {
					containsDropValue2 = true;
				}
			}
		}
		objUtilities.logReporter(
			"Announcement Dropdown contains Documents option: verifyDropdownElement()",
			containsDropValue1, false);
		objUtilities.logReporter(
			"Announcement Dropdown contains News option: verifyDropdownElement()",
			containsDropValue2, false);
	}
	
	public void clickOnDocuments() {
		objUtilities.logReporter(
			"Clicked on Documents option: clickOnDocuments()",
			objWrapperFunctions.click(documentsSelector), false);
	}
	
	public void verifyDocsPageUrl(String text) {
		objUtilities.logReporter(
			"Verify user navigated to documents page : verifyDocsPageUrl()",
			objUtilities.getCurrentURL().contains(text), false);
	}
}