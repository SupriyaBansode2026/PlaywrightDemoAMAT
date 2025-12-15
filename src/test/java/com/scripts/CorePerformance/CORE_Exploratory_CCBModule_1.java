package com.scripts.CorePerformance; 

import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

import com.generic.BaseTest;
import com.generic.TimeWatch;
import com.generic.Utilities;
import com.views.CorePerformance.ViewForCorePerformance;

public class CORE_Exploratory_CCBModule_1 extends BaseTest
{
	private Utilities objUtilities;
	Properties objCCBProperties;
	private ViewForCorePerformance objViewForCorePerformance;
	
	@BeforeClass
	public void setUpTest()
	{
		initializeWebEnvironment();
		objUtilities = this.getObjUtilities();
		objViewForCorePerformance = new ViewForCorePerformance(this);
	}
	
	@Story("CORE_Exploratory_CCBModule_1")
	@Description("CCB page load performance � ECO PM finding")
	@Test
	public void CreateProjectAndPCR() 
	{
		objUtilities = this.getObjUtilities();
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		
//		objUtilities.clickMenuOptionPerformace(true, "CCB Module", "");
//		
//		String StartTime = objUtilities.getCurrentDateTime();
//		objUtilities.waitTillPageLoadSCVPerf(By.xpath("//div[@class='panel-heading' and text()='SSG BUs']//parent::div//div[contains(text(),'" + objCCBProperties.getProperty("sMDPBU") + "')]"));
//		String EndTime = objUtilities.getCurrentDateTime();
//		objUtilities.logReporter("CCB Module Page"+"@~"+StartTime+"@~"+EndTime+"@~"+"NA",true,false);
//		
//		objViewForCorePerformance.selectBUOnCCBModulePage(objCCBProperties.getProperty("sMDPBU"));
//		objViewForCorePerformance.clickOnExportToExcelOnECOPMReview();
		
		objUtilities.logReporter("TC Completed", true, false);
	}

	@AfterClass
	public void tearDownTest()
	{
		tearDownWebEnvironment();
		objUtilities = null;
		objViewForCorePerformance = null;
	}
}