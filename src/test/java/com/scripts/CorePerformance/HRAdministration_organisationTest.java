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
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForOrganization;

public class HRAdministration_organisationTest extends BaseTest {

	Properties objCCBProperties;

	private ViewForOrganization objViewForOrganization;
	private ViewForLogin objViewForLogin;
	private Utilities objUtilities;


	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objViewForOrganization=new ViewForOrganization(this);
		objUtilities = this.getObjUtilities();
	}

	@Story("Organization_Page_Validation")
	@Description("Validate Organization page functionality:General Information tab and Structure.")
	@Test(priority=3)
	public void verifyOrganizationFunctionality_TC3() {
		objUtilities = this.getObjUtilities();
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForOrganization.validateOrganization_clickMenu();
		objViewForOrganization.organizationmenu(objCCBProperties.getProperty("orgsubmenu"));
		objViewForOrganization.generalInformationSubmenu();
		objViewForOrganization.enterNote(objCCBProperties.getProperty("enterNote"));
		objViewForOrganization.performSubmit();
		objViewForOrganization.structureSubmenu();
		objViewForOrganization.verifypopuptext(objCCBProperties.getProperty("confirmationtext"));
		objViewForOrganization.clickDelete(objCCBProperties.getProperty("acceptdelete"));
	}		
	
	@AfterClass
	public void tearDownTest() {
		objUtilities=null;
		objViewForLogin = null;
		objViewForOrganization = null;
		objCCBProperties = null;
		 tearDownWebEnvironment();
	}
}
