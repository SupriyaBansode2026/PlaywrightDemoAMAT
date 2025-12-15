package com.scripts.CorePerformance;

import java.util.Properties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Pojo;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;

public class DashboardSubTabVisibilityTest extends BaseTest {

	Properties objCCBProperties;
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
	public void validateDashboardPage_TC2() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForDashboard.checkSubTabVisibilty(objCCBProperties.getProperty("subTabList"));
		objViewForDashboard.compareListOfElement(objCCBProperties.getProperty("expectedWidgetList"));
		objViewForDashboard.checkLeftTabVisibilty(objCCBProperties.getProperty("leftMenuTab"));
	}
	
	@AfterClass
	public void tearDownTest() {
		tearDownWebEnvironment();
		objPojo = null;
		objViewForLogin = null;
		objCCBProperties = null;
		objViewForDashboard = null;
	}
}