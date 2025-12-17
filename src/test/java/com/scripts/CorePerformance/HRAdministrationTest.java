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
import com.views.CorePerformance.ViewForHRAdministration;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForOrganization;

public class HRAdministrationTest extends BaseTest {

	Properties objCCBProperties;
	private Utilities objUtilities;

	private ViewForLogin objViewForLogin;
	private ViewForHRAdministration objViewForHRAdministration;


	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment(); 
		objUtilities = this.getObjUtilities();
		objViewForLogin = new ViewForLogin(this);
		objViewForHRAdministration=new ViewForHRAdministration(this);
	}

	@Story("HR Administration_Page_Validation")
	@Description("Validate HR Administration page loading.")
	@Test()
	public void verifyHRAdminstartionFunctionality_TC2() {
		objUtilities = this.getObjUtilities();
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForHRAdministration.clickMainMenu(objCCBProperties.getProperty("menuName"));
		objViewForHRAdministration.selectTypeDropdown(objCCBProperties.getProperty("typedropdown"));
		objViewForHRAdministration.checkOnEmployeeActionsCheckbox(objCCBProperties.getProperty("empAction"));
		objViewForHRAdministration.checkOnWorkflowMnmgtCheckboxes(objCCBProperties.getProperty("checkbox1"));
		objViewForHRAdministration.checkListOfCheckboxes(objCCBProperties.getProperty("listCheckbox"));
		objViewForHRAdministration.clickSave(objCCBProperties.getProperty("save"));
		objViewForHRAdministration.validate_GridView(objCCBProperties.getProperty("typedropdown"));



//		objViewForHRAdministration.validateHRAdministration_Users(objCCBProperties.getProperty("empname"));
	
	}
	
	@AfterClass
	public void tearDownTest() {
		objUtilities=null;
		objViewForLogin=null;
		objViewForHRAdministration=null;
		objCCBProperties=null;
		 tearDownWebEnvironment();
	}
}
