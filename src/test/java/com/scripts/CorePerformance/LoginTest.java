package com.scripts.CorePerformance;

import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

import com.generic.BaseTest;
import com.generic.TimeWatch;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForCorePerformance;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForLogin;

public class LoginTest extends BaseTest {

	private Utilities objUtilities;
	Properties objCCBProperties;
	private ViewForDashboard objViewForDashboard;
	private ViewForLogin objViewForLogin;

	@BeforeMethod
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment();
		objUtilities = this.getObjUtilities();
		objViewForLogin = new ViewForLogin(this);
		objViewForDashboard = new ViewForDashboard(this);
	}

	@Story("Login_Page_Validation")
	@Description("Validate dashboard page loading.")
	@Test
	public void verifyLoginFunctionality_TC1() {
		objViewForLogin.loginPageValidation();
		objCCBProperties = this.getObjConfig();
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
	}

	@AfterMethod
	public void tearDownTest() {
		// tearDownWebEnvironment();
		objUtilities = null;
		objViewForLogin = null;
	}
}
