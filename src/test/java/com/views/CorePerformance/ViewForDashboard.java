package com.views.CorePerformance;

import java.util.Properties;
import com.generic.Pojo;
import com.generic.Utilities;

import com.pageFactory.CorePerformance.DashboardPage;


public class ViewForDashboard {

	private Utilities objUtilities;
	private Properties objCCBProperties;
	private Pojo objPojo;
	private DashboardPage objDashboardPage;

	public ViewForDashboard(Pojo objPojo) {
		this.objPojo = objPojo;
		this.objCCBProperties = objPojo.getObjConfig(); // Assuming this method exists
		this.objUtilities = objPojo.getObjUtilities();
		objDashboardPage = new DashboardPage(objPojo);
	}

	public void getCurrentPageUrl() {
		objDashboardPage.verifyDashboardPageUrl();
	}

	public String getCurrentPageTitle() {
		return objDashboardPage.getPageTitle();
	}

	public void compareListOfElement(String expectedWidgetList) {
		objDashboardPage.getElementsList(expectedWidgetList);
	}

	public void checkSubTabVisibilty(String subTabs) {
		String[] arr = subTabs.split("~");
		 for(String names : arr) {
			  if(names.contains("Employee")) {
				  objDashboardPage.checkSubTabVisibiltyOnDashboardPage(names);
			  }else if(names.contains("My")) {
				  objDashboardPage.checkSubTabVisibiltyOnDashboardPage(names);
			  }else if(names.contains("Directory")) {
				  objDashboardPage.checkSubTabVisibiltyOnDashboardPage(names);
			  }else if(names.contains("Buzz")) {
				  objDashboardPage.checkSubTabVisibiltyOnDashboardPage(names);
			  }else if(names.contains("Announcements")) {
				  objDashboardPage.checkSubTabVisibiltyOnDashboardPage(names);
			  }else {
				  System.out.println("Invalid sub tab");
			  }
			  
		}	
		
	}
	
	public void checkLeftTabVisibilty(String subTabs) {
		String[] arr = subTabs.split("~");
		 for(String name : arr) {
			 objDashboardPage.checkLeftMenuTabVisibiltyOnDashboardPage(name);
		}	
	}

	public void clickOnBuzzElement() {
		objDashboardPage.clickOnBuzz();
	}

	public void getTheApprovalCount(String option) {
		objDashboardPage.requestCount(option);
	}

	public void validatePageUrl(String text) {
		objDashboardPage.verifyUrl(text);
	}

	public void clickOnPageElementLink(String text) {
		objDashboardPage.clickOnLink(text);
	}

	public void clickOnElement() {
		objDashboardPage.clickOnIcon();
	}

	public void getLeavePaginationTextCount(String val) {
		objDashboardPage.getLeavePaginationText(val);
	}

	public void getGeneralRequestPaginationTextCount(String val) {
		objDashboardPage.getGeneralRequestPaginationText(val);
	}

	public void getUKAttendenceApprovalTextCount(String val) {
		objDashboardPage.getUKAttendenceApprovalText(val);
	}

	public void switchToNewWindow(String titleText, String url) {
		objDashboardPage.switchingToWindow(titleText, url);
	}

	public void switchBackToParentWindow(int index) {
		objDashboardPage.switchBack(index);
	}
}
