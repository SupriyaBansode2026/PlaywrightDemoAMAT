package com.scripts.CorePerformance;

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
import com.generic.Pojo;
import com.generic.TimeWatch;
import com.generic.Utilities;
import com.pageFactory.CorePerformance.AnnouncementDropDownPage;
import com.views.CorePerformance.ViewForCorePerformance;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForHRAdministration;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForAnnouncementDropDownTest;

public class AnnouncementDropDownTest extends BaseTest {

	Properties objCCBProperties;
	private Utilities objUtilities;
	private ViewForDashboard objViewForDashboard;
	Pojo objPojo;
	private ViewForLogin objViewForLogin;
	private ViewForAnnouncementDropDownTest objorangehrmtest;

	@BeforeClass
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objCCBProperties = this.getObjConfig();
		objViewForDashboard = new ViewForDashboard(this);
		objorangehrmtest = new ViewForAnnouncementDropDownTest(this);
	}

	@Story("Complete Login and Dashboard Flow")
	@Description("Covers Login, Dashboard, Dropdown, Document Navigation, Logout")
	@Test
	public void verifyAnnouncementDropdownElements_TC01() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForDashboard.getCurrentPageUrl();
		objorangehrmtest.clickOnAnnouncementElement();
		objorangehrmtest.getTheAnnouncementDropdownEle();
		objorangehrmtest.verifyAnnouncementDropdownEle(objCCBProperties.getProperty("announcementDropdownEle1"), objCCBProperties.getProperty("announcementDropdownEle2"));
		objorangehrmtest.clickOnDocumentsOption();
		objorangehrmtest.verifyDocumentsPageUrl(objCCBProperties.getProperty("documentUrlFragment"));
	}
	
	@AfterClass
	public void tearDownTest() {
		tearDownWebEnvironment();
		objPojo = null;
		objViewForLogin = null;
		objCCBProperties = null;
		objorangehrmtest = null;
	}

}