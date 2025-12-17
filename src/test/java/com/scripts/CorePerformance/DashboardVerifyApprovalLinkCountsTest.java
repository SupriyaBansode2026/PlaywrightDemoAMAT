package com.scripts.CorePerformance;

import java.util.Properties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Pojo;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;


public class DashboardVerifyApprovalLinkCountsTest extends BaseTest{
	
	Properties objCCBProperties;
	private Utilities objUtilities;
	private ViewForDashboard objViewForDashboard;
	Pojo objPojo;
	private ViewForLogin objViewForLogin;

	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objCCBProperties = this.getObjConfig();
	//	objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForDashboard = new ViewForDashboard(this);
	}

	@Test
	public void myActionsWidgetLinks_TC3() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForDashboard.getTheApprovalCount(objCCBProperties.getProperty("leaveReqAprvlLink"));
		objViewForDashboard.getTheApprovalCount(objCCBProperties.getProperty("ukAttendenceAprvlLink"));
		objViewForDashboard.getTheApprovalCount(objCCBProperties.getProperty("generalReqAprvlLink"));
	}

	@Test(dependsOnMethods = "myActionsWidgetLinks_TC3")
	public void verifyApproveCountOnMyActionsWidgetLinks_TC4() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForDashboard.clickOnPageElementLink("Leave Requests to Approve");
		objViewForDashboard.validatePageUrl(objCCBProperties.getProperty("expectedUrlFragmentOfLeavePage"));
		objViewForDashboard.getLeavePaginationTextCount(objCCBProperties.getProperty("getValueType"));
		objViewForDashboard.clickOnElement();
		objViewForDashboard.getCurrentPageUrl();
		
		objViewForDashboard.clickOnPageElementLink("Attendance Sheets to Approve");
		objViewForDashboard.validatePageUrl(objCCBProperties.getProperty("expectedUrlFragmentOfAttendence"));
		objViewForDashboard.getUKAttendenceApprovalTextCount(objCCBProperties.getProperty("getValueType"));
		objViewForDashboard.clickOnElement();
		objViewForDashboard.getCurrentPageUrl();
		
		objViewForDashboard.clickOnPageElementLink("General Requests to Approve");
		objViewForDashboard.validatePageUrl(objCCBProperties.getProperty("expectedUrlFragmentOfGeneral"));
		objViewForDashboard.getGeneralRequestPaginationTextCount(objCCBProperties.getProperty("getValueType"));
		objViewForDashboard.clickOnElement();
		objViewForDashboard.getCurrentPageUrl();	
	}	
	
	@AfterClass
	public void tearDownTest() {
		tearDownWebEnvironment();
		objPojo = null;
		objUtilities = null;
		objCCBProperties = null;
		objViewForLogin = null;
		objViewForDashboard = null;
	}
}
