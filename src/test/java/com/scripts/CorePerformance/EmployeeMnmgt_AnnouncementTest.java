package com.scripts.CorePerformance;
//import java.util.Hashtable;
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
import com.pageFactory.CorePerformance.OrangehrmPage;
import com.views.CorePerformance.ViewForCorePerformance;
import com.views.CorePerformance.ViewForHRAdministration;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForOrangehrm;

public class EmployeeMnmgt_AnnouncementTest extends BaseTest { 
		
		    private Utilities objUtilities;
		    Properties objCCBProperties;
		    //private ViewForCorePerformance objViewForCorePerformance = new ViewForCorePerformance(this);
		    private  ViewForOrangehrm objorangehrmtest = new ViewForOrangehrm(this);
			@BeforeMethod
			public void setUpTest() {
				System.out.println("STEP - Launch Browser");
				initializeWebEnvironment(); 
				ViewForLogin objViewForLogin = new ViewForLogin(this);
				objorangehrmtest = new ViewForOrangehrm(this);
			}
		 
		   


		    @Story("Complete Login and Dashboard Flow")
		    @Description("Covers Login, Dashboard, Dropdown, Document Navigation, Logout")
		    @Test
		    public void verifyCompleteLoginFlow_TC01() {
		        
		        System.out.println("STEP 1 - Verify Orangehrm URL after login");	       
		        
		        //objViewForCorePerformance.navigateToDocuments();
		        //String documentsUrl = objViewForCorePerformance.getCurrentPageUrl();
		        String pageURL = objorangehrmtest.getCurrentPageUrl();
		        Assert.assertTrue(pageURL.contains("orangehrmlive"), "page URL not loaded.");
		        
		        
		        System.out.println("STEP 2 - Enter credentials and click on login");
		        objUtilities = this.getObjUtilities();
				objCCBProperties = this.getObjConfig();
		        objorangehrmtest.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
				
		        System.out.println("STEP 3 - verify dashboard url");
		        pageURL = objorangehrmtest.getCurrentPageUrl();
		        Assert.assertTrue(pageURL.contains("dashboard"), "dashboard URL not loaded.");
		        
		        System.out.println("STEP 4  - click Announcement");
		        Assert.assertTrue(objorangehrmtest.clickonAnnounceOption(), "fail to click on announcement.");
		        //Assert.assertTrue(objorangehrmtest.verifyAnnouncementDropDownValue("News"), "News option is not available.");
		        System.out.println("STEP 5 - verify Documents");
		       
//		        Assert.assertTrue(objorangehrmtest.verifydropdownvalues().contains("Documents"), "Documents' option not display in dropdown.");
		        
		        
		        System.out.println("STEP 6 - verify News");
		        
//		        Assert.assertTrue(objorangehrmtest.verifydropdownvaluess().contains("News"), "Documents' option not display in dropdown.");
		        
		        
		        System.out.println("STEP 7 - Click on Documents option");		        
		        objorangehrmtest.clickOnDocuments();
		        
		        pageURL = objorangehrmtest.getCurrentPageUrl();
		        Assert.assertTrue(pageURL.contains("viewDocuments"), "view documents URL not loaded.");
		        
		        
		        
		    } 
		


	}