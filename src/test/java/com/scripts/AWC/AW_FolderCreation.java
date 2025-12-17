package com.scripts.AWC;

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Utilities;
import com.views.AWC.ViewForAWC;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

public class AW_FolderCreation extends BaseTest {

	private Utilities objUtilities;
	Properties objAWCProperties;
	private ViewForAWC objviewForAWC;

	@BeforeClass
	public void setUpTest() {
		initializeWebEnvironment();
		objUtilities = this.getObjUtilities();
		objviewForAWC = new ViewForAWC(this);
	}

	@Story("CORE_Exploratory_CCBModule_1")
	@Description("CCB page load performance � ECO PM finding")
	@Test
	public void CreateProjectAndPCR() {
		objUtilities = this.getObjUtilities();
		objAWCProperties = this.loadConfigPropertiesForModules("AWC");

		objviewForAWC.verifyLoginPageDisplay();
		objviewForAWC.enterUsername(objAWCProperties.getProperty("user"));
		objviewForAWC.enterPassword(objAWCProperties.getProperty("password"));
		objviewForAWC.clickOnLoginButton();
		objviewForAWC.clickOnExplorerIcon();
		objviewForAWC.verifySubOptionInFolderProperties(objAWCProperties.getProperty("sUserProperties"));
		objviewForAWC.clickOnMoreOption();
		objviewForAWC.clickOnAvtarIcon();
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