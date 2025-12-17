package com.scripts.CorePerformance;

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Pojo;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;

public class DashboardToSwitchToNewWindowBuzzTest extends BaseTest{
	
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
		objViewForDashboard = new ViewForDashboard(this);
	}

	@Test			
	public void verifyBuzzSubTab_TC5() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForDashboard.clickOnBuzzElement();
		objViewForDashboard.switchToNewWindow(objCCBProperties.getProperty("titleTextOfBuzzPage"), objCCBProperties.getProperty("buzzPageUrl"));
		objViewForDashboard.validatePageUrl(objCCBProperties.getProperty("expectedUrlFragmentOfBuzzPage"));
		objViewForDashboard.switchBackToParentWindow(0);		
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
