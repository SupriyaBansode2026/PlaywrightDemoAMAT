package com.scripts.AWC;

import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.TestContext;
import com.generic.Utilities;
import com.views.AWC.ViewForAWC;
import io.qameta.allure.*;

public class AW_Launch_Login extends BaseTest {

	private Utilities objUtilities;
	Properties objAWCProperties;
	private ViewForAWC objviewForAWC;

	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment();
		objUtilities = this.getObjUtilities();
		objviewForAWC = new ViewForAWC(this);
	}

	@BeforeMethod
	public void setUp(ITestResult result) {

		String testName = result.getMethod().getMethodName();
		String className = result.getTestClass().getRealClass().getSimpleName();

		TestContext.setTestName(className + "_" + testName);
	}

	@Story("AW_Launch_Login")
	@Description("Verify that user with correct credentials able to login to AW environment")
	@Test
	public void CreateProjectAndPCR() {
		objUtilities = this.getObjUtilities();
		objAWCProperties = this.loadConfigPropertiesForModules("AWC");

		objviewForAWC.verifyLoginPageDisplay();
		objviewForAWC.enterUsername(objAWCProperties.getProperty("user"));
		objviewForAWC.enterPassword(objAWCProperties.getProperty("password"));
		objviewForAWC.clickOnLoginButton();
		objviewForAWC.clickOnAvtarIcon();
		objviewForAWC.verifyAvtarUserInfoPanel(objAWCProperties.getProperty("sAvtarOption"));
		objviewForAWC.clickOnSignOutBtn();
		objviewForAWC.verifyLoginPageDisplay();

		objUtilities.logReporter("TC Completed", true, false);
	}

	@AfterClass
	public void tearDownTest() {
		tearDownWebEnvironment();
		objUtilities = null;
		objviewForAWC = null;
	}

}