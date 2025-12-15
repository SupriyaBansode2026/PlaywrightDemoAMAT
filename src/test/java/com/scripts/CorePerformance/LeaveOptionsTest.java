package com.scripts.CorePerformance;

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForCorePerformance;
import com.views.CorePerformance.ViewForLeave;
import com.views.CorePerformance.ViewForLogin;

public class LeaveOptionsTest extends BaseTest {

	private Utilities objUtilities;
	Properties objCCBProperties;
	private ViewForCorePerformance objViewForCorePerformance;
	private ViewForLeave objViewForLeave;
	private ViewForLogin objViewForLogin;

	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objViewForLeave = new ViewForLeave(this);
	}

	@Test()
	public void verifyLeaveOptionsIsWorking() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForLeave.verfiyAssignLeaveOptionsIsWorking(objCCBProperties.getProperty("assignLeaveOption"),
				objCCBProperties.getProperty("assignLeaveBtn"));
		objViewForLeave.verfiyLeaveListOptionsIsWorking(objCCBProperties.getProperty("leaveListOption"),
				objCCBProperties.getProperty("leaveListBtn"));
		objViewForLeave.verfiyLeaveCalnedarOptionsIsWorking(objCCBProperties.getProperty("leaveCalendarOption"),
				objCCBProperties.getProperty("leaveCalendarBtn"));
		objViewForLeave.verfiyApplyLeaveOptionsIsWorking(objCCBProperties.getProperty("applyLeaveOption"),
				objCCBProperties.getProperty("applyLeaveBtn"));
		objViewForLeave.verfiyMyLeaveOptionsIsWorking(objCCBProperties.getProperty("myLeaveOption"),
				objCCBProperties.getProperty("myLeaveBtn"));
	}

	@AfterClass
	public void tearDownTest() {
		objViewForLogin = null;
		objCCBProperties = null;
		objViewForCorePerformance = null;
		objViewForLeave = null;
		tearDownWebEnvironment();
	}
}
