package com.scripts.NSR;
 
import java.util.Hashtable;

import java.util.Properties;
 
import org.testng.ITestContext;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;
 
import com.generic.*;

import com.pageFactory.NSR.NSRPage;

import com.views.NSR.ViewForNSR;

//import com.views.SCO_Standard.ViewForSCO_Standard;
 
import io.qameta.allure.Description;

import io.qameta.allure.Story;
 
 
public class NSR_Core_16_1 extends BaseTest

{

	// Local Variables

	  private Utilities objUtilities;

	  private ViewForNSR objViewForNSR;

	  private Properties objConfig;
 
	  @BeforeClass

		public void setUpTest()

		{

			initializeWebEnvironment();

			objUtilities = this.getObjUtilities();

			objViewForNSR= new ViewForNSR(this);

			objConfig = this.getObjConfig();

		}

		@DataProvider(name = "TestDataProvider")

		public Object[][] getDataProvider(ITestContext context)

		{

			Object[][] testData = loadDataProvider("NSR_Core_16_1","NSR","Regression");

			return testData;

		}

		@Story("NSR_Core_16_1")

		@Description("Verify Order Information should be fetched from CRM/PST based on NSR source and shown in Core Plus NSR form in read-only format.")

		@Test(dataProvider = "TestDataProvider")

		public void validationOfNSRTab(String strRun, Hashtable<String, String> dataSetValue)

		{

			loadTestData(strRun, dataSetValue);

			objUtilities = this.getObjUtilities();

			Properties objNSRProperties = loadConfigPropertiesForModules("NSR");

			objUtilities.logReporter("*******************TEST CASE STARTED**********************", true, false);
 
			 objUtilities.clickOnContinue();

			 objUtilities.clickMenuOption(false, "Home", "");

			 if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))

			 {

				 objUtilities.selectSearchOption("", "40000208");//PST type NSR

				 objUtilities.waitTillPageLoad(null);

				 objViewForNSR.verifySourceAndvCModel(objNSRProperties.getProperty("sourcePST"));

			 }

			 else

			 {

				 objUtilities.selectSearchOption("", "40000208");//PST type NSR

				 objUtilities.waitTillPageLoad(null);

				 objViewForNSR.verifySourceAndvCModel(objNSRProperties.getProperty("sourcePST"));

			 }

			 objUtilities.logReporter("TC Completed", true, false);

			}

		@AfterClass

		public void tearDownTest()

		{

			tearDownWebEnvironment();

			objUtilities = null;

			objViewForNSR = null;

		}

}