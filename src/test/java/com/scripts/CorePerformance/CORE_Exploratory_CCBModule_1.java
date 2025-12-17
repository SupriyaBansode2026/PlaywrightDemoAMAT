package com.scripts.CorePerformance; 

import java.util.Hashtable;
import java.util.Properties;

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