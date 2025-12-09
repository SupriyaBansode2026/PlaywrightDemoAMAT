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
import com.pageFactory.CorePerformance.HRAdministrationPage;
import com.views.CorePerformance.ViewForCorePerformance;
import com.views.CorePerformance.ViewForHRAdministration;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForOrganization;

public class HRAdministrationTest extends BaseTest {

	private Utilities objUtilities;
	Properties objCCBProperties;
	private ViewForCorePerformance objViewForCorePerformance;
	private ViewForHRAdministration objViewForHRAdministration;
	private ViewForOrganization objViewForOrganization;
	private ViewForLogin objViewForLogin;


	@BeforeClass
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment(); 
		objViewForLogin = new ViewForLogin(this);
		objViewForHRAdministration=new ViewForHRAdministration(this);
		objViewForOrganization=new ViewForOrganization(this);
	}
	
//	@Story("logger")
//	@Description("chk logger")
//	@Test(priority=1)
//	public void chklogger() {
////		System.out.println("VERIFY - Login page displaying by verifying login section.");
//		objViewForHRAdministration.chklog();
//	}

	@Story("Login_Page_Validation")
	@Description("Validate dashboard page loading.")
	@Test
	public void verifyLoginFunctionality_TC1() {
		objViewForLogin.loginPageValidation();
		objCCBProperties = this.getObjConfig();
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
	}
	
	

	//pooja code starts
	@Story("HR Administration_Page_Validation")
	@Description("Validate HR Administration page loading.")
	@Test(priority=2)
	public void verifyHRAdminstartionFunctionality_TC2() {
		objCCBProperties = this.getObjConfig();
		objViewForHRAdministration.validateHRAdministration_ManageUserRole();
//		objViewForHRAdministration.validateHRAdministration_Users(objCCBProperties.getProperty("empname"));
	
	}
	
	@Story("Organization_Page_Validation")
	@Description("Validate Organization page functionality:General Information tab and Structure.")
	@Test(priority=3)
	public void verifyOrganizationFunctionality_TC3() {
		//VERIFY -Test Organization started"
		objViewForOrganization.validateOrganization();
		//VERIFY - Organization- Test Ended
	}
	//pooja code ends
		
	
	@AfterClass
	public void tearDownTest() {
		// tearDownWebEnvironment();
		objUtilities = null;
		objViewForCorePerformance = null;
	}
}
