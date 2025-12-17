package com.scripts.CorePerformance;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

import com.generic.BaseTest;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;

public class LoginTest extends BaseTest {

	Properties objCCBProperties;
	private ViewForLogin objViewForLogin;

	@BeforeMethod
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objCCBProperties = this.getObjConfig();
	}

	@Story("Login_Page_Validation")
	@Description("Validate dashboard page loading.")
	@Test
	public void verifyLoginFunctionality_TC1() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.loginPageValidation();
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
	}

	
	@AfterMethod
	public void tearDownTest() {
		tearDownWebEnvironment();
		objViewForLogin = null;
	}
}