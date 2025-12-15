package com.scripts.CorePerformance;

import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.generic.BaseTest;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForLogout;

public class LogoutTest extends BaseTest {

	private Utilities objUtilities;
	Properties objCCBProperties;
	private ViewForDashboard objViewForDashboard;
	private ViewForLogin objViewForLogin;
	private ViewForLogout objViewForLogout;

	@BeforeMethod
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment();
		objUtilities = this.getObjUtilities();
		objViewForLogin = new ViewForLogin(this);
		objViewForLogout = new ViewForLogout(this);
	}
	
	@Test
	public void verifyLoginFunctionality() {
		objViewForLogin.loginPageValidation();
		objCCBProperties = this.getObjConfig();
	}
	
	@Test()
	public void verifyLogoutFunctionality() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForLogout.logoutTheApplication();
	}

	@AfterMethod
	public void tearDownTest() {
		tearDownWebEnvironment();
		objUtilities = null;
		objViewForLogin = null;
	}
}
