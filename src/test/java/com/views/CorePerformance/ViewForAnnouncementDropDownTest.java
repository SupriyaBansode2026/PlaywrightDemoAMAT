package com.views.CorePerformance;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.AnnouncementDropDownPage;

public class ViewForAnnouncementDropDownTest {

	private AnnouncementDropDownPage objOrangehrm;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;

	
	public ViewForAnnouncementDropDownTest(Pojo objPojo) {

		objOrangehrm = new AnnouncementDropDownPage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}

	public void clickOnAnnouncementElement() {
		objOrangehrm.clickOnAnnouncement();
	}
	
	public void getTheAnnouncementDropdownEle() {
		objOrangehrm.getDropdownValues();
	}
	
	public void verifyAnnouncementDropdownEle(String ele1, String ele2) {
		objOrangehrm.verifyDropdownElement(ele1, ele2);
	}
	
	public void clickOnDocumentsOption() {
		objOrangehrm.clickOnDocuments();
	}
	
	public void verifyDocumentsPageUrl(String textToValidate) {
		objOrangehrm.verifyDocsPageUrl(textToValidate);
	}
	
}