package com.scripts.NSR;
 
import java.util.Hashtable;

import java.util.Properties;
 
import org.testng.ITestContext;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;
 
import com.generic.BaseTest;

import com.generic.Utilities;

import com.pageFactory.NSR.NSRPage;

import com.views.Common.ViewForAssignmentTab;

import com.views.NSR.ViewForNSR;
import com.views.ProductsAndProjectsView.ViewForProductsAndProjects;

import io.qameta.allure.Description;

import io.qameta.allure.Story;
 
public class CRN_CRF_1_7 extends BaseTest {
 
	private Properties objNSRProperties;

	private Utilities objUtilities;

	private ViewForNSR objViewForNSR;

	private ViewForAssignmentTab objViewForAssignmentTab;

	private ViewForProductsAndProjects objViewForProductsAndProjects;

	private NSRPage objNSRPage;

	private Properties objConfig;
 
	@BeforeClass

	public void setUpTest() {

		initializeWebEnvironment();

		objUtilities = this.getObjUtilities();

		objViewForNSR = new ViewForNSR(this);

		objViewForAssignmentTab = new ViewForAssignmentTab(this);

		objViewForProductsAndProjects = new ViewForProductsAndProjects(this);

		objNSRPage = new NSRPage(this);

	}
 
	@DataProvider(name = "TestDataProvider")

	public Object[][] getDataProvider(ITestContext context) {

		Object[][] testData = loadDataProvider("CRN_Change_Analyst_Pre_Requisite_ForSingleRTMFocalRole", "NSR",

				"Regression");

		return testData;

	}
 
	@Story("CRN_CRF_1_7")

	@Description("Verify CRF summary area with CRN & NSR info with hyperlink and url")

	@Test(dataProvider = "TestDataProvider")

	public void verifyCRNandNSRNumberHyperlinked(String strRun, Hashtable<String, String> dataSetValue) {

		loadTestData(strRun, dataSetValue);

		objUtilities = this.getObjUtilities();

		objNSRProperties = loadConfigPropertiesForModules("NSR");

		Properties objPCRProperties = this.loadConfigPropertiesForModules("createProjectAndPCR");

		objUtilities.logReporter("................ Test Case Started ................", true, false);
 
		// Create Project

		objUtilities.clickMenuOption(true, "Products and Project", "");

		objUtilities.verifyPageHeader("Products and Projects");

		objViewForProductsAndProjects.enterProductsAndProjectPageDataAndClickOnButton(

				objUtilities.dpString("sBusinessUnit"), objUtilities.dpString("sProduct"),

				objUtilities.dpString("sKPU"), objUtilities.dpString("sStatus"), objUtilities.dpString("sBUCustomer"),

				"Create Project");

		objViewForProductsAndProjects.createProjectOnProductsAndProjectPage(objUtilities.dpString("sProjectName"),

				objUtilities.dpString("sProjectOwner"), objUtilities.dpString("sProjectStatus"),

				objUtilities.dpString("sProjectPriority"), objUtilities.dpString("sProjectComplexity"),

				objUtilities.dpString("sProjectType"), objUtilities.dpString("sNeedDate"),

				objUtilities.dpString("sStartDate"), objUtilities.dpString("sEndDate"),

				objPCRProperties.getProperty("Squarer_Automation"), "Create Project", "", "");

		objUtilities.verifyPageHeader("Project");
 
		String sPrjID = objUtilities.getCurrentObjectID();

		objViewForProductsAndProjects.updateReadyToAcceptNSRValueForProject(true);

		System.out.println("Project" + sPrjID);
 
		// =========== Create NSR ===========================

		objUtilities.clickMenuOption(true, "Create", "New NSR");

		objViewForNSR.takeActionOnAddNewNSRPopUp(objNSRProperties.getProperty("sAddNSRConfirmationMessage"), "Yes");

		objViewForNSR.fillOrderInformationSectionBasic(objUtilities.dpString("sBU_new"),

				objUtilities.dpString("sTechnology"), objUtilities.dpString("sPlatform"), objUtilities.dpString("sSO"),

				objUtilities.dpString("sSOLineItem"));

		objViewForNSR.fillRequestorSectionBasic(objUtilities.dpString("sTitle"), objUtilities.dpString("sDescription"),

				objUtilities.dpString("sPrimaryDriver"), objUtilities.dpString("sRepeatRequest"), "9024-06747",

				objUtilities.dpString("sCategory2"), objUtilities.dpString("sSubCategory2"));
 
		String sNSRID = objUtilities.getCurrentObjectID().trim();

		System.out.print("NSR ID :" + sNSRID + ":");

		objViewForNSR.addExistingProjectInAssociatedCorePlusProjectSection("", "", "", sPrjID);

		objViewForNSR.clickOnSave();

		objUtilities.waitTillPageLoad(null);

		objViewForNSR.clickNSRDetailsHeaderButton("Submit NSR");

		objUtilities.waitTillPageLoad(null);
 
		objUtilities.activateTab("Assignments");

		objUtilities.waitTillPageLoad(null);

		objViewForAssignmentTab.clickReassignInAssignmentSectionTable(objUtilities.dpString("sAssignmentSection"),

				objUtilities.dpString("sTableColumn"), objUtilities.dpString("sTableValue"));

		objViewForAssignmentTab.reassignOnReassignmentPopupWithReassignFormHandle("");

		objUtilities.activateTab("NSR Detail");

		objViewForNSR.verifyNSRStatusOnNSRDetailsPage(objNSRProperties.getProperty("sReviewbyProductLineManager"));
 
		objViewForNSR.clickOnNSRDetailsHeaderButton(objNSRProperties.getProperty("ApproveandMovetoEngg"));

		objUtilities.waitTillPageLoad(null);
 
		/*if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))

		{

		objViewForNSR.enterValuesInNSRReasonfornotsubmittingFinalBOMResponseTextboxOnRequestSection(objNSRProperties.getProperty("FinalBOMHoldReason"),"Test final Reason");

		objViewForNSR.clickOnSave();

		objUtilities.waitTillPageLoad(null);


		objViewForNSR.clickOnNSRDetailsHeaderButton(objNSRProperties.getProperty("sPreliminaryDrivelongleaditems"));

		objUtilities.waitTillPageLoad(null);

		objViewForNSR.verifyCReviewandmodifyLateReasonfieldAlertPopUpAndtakeAction(objNSRProperties.getProperty("sPopupReviewandmodifyFinalBOMHoldReasonfield"), "No");

		}

		else

		{

		    objUtilities.waitTillPageLoad(null);

	        objViewForNSR.SelectNSRRiskAssessmentDropdwnDisplayed("Medium Risk");

	        objViewForNSR.clickOnSave();

	        objUtilities.waitTillPageLoad(null);

			objViewForNSR.clickOnNSRDetailsHeaderButton(objNSRProperties.getProperty("sPreliminaryDrivelongleaditems"));

			objUtilities.waitTillPageLoad(null);

		}

		objUtilities.waitTillPageLoad(null);

		objUtilities.verifyPageHeader("Change Release Notice");*/

objViewForNSR.clickNSRDetailsHeaderButton(objNSRProperties.getProperty("sPreliminaryDrivelongleaditems"));

		objUtilities.waitTillPageLoad(null);
 
 
		objViewForNSR.fillEstimatedCompletionInCRNHeader("60");

		objViewForNSR.clickOnSave();

		objUtilities.waitTillPageLoad(null);
 
		String sCRNID = objUtilities.getCurrentObjectID().trim();

		// to click on move to next

		objViewForNSR.submitNSR();

		objUtilities.waitTillPageLoad(null);
 
		// to verify CRN moved to change analyst page

		objViewForNSR.verifyNSRStatusOnNSRDetailsPage(objNSRProperties.getProperty("sChangeAnalystAUS4070"));
 
		// verify CRF attribute generated on CRN page

		objViewForNSR.verifyCRFAttributeONCRNPage();

		String CRFValue = objViewForNSR.getCRFNumberONCRNPage();
 
		// verify CRF number is hyperlinked

		objViewForNSR.ClickOnCRFLinkAttributeOnCRNPage();

		String CRFUrlValue = objNSRProperties.getProperty("sCRFURL") + CRFValue;

		objUtilities.switchToTabBasedOnIndex(1);

		objUtilities.refreshWebPage();

		objViewForNSR.handleAndAcceptAlert("something else other than 200 was returned");
 
		// verify URL of CRF tab

		objViewForNSR.verifyCurrentURL(CRFUrlValue);
 
		// verify 'Summary' attribute is present in CRF page

		objViewForNSR.VerifyCRFAttributesPresentOnCRFPage(objNSRProperties.getProperty("sCRFSummary"));
 
		String links = sNSRID + "~" + sCRNID;
 
		objViewForNSR.verifyLinksAreHyperlinkAndClickOnLink(links);
 
		objUtilities.logReporter("TC Completed", true, false);

	}
 
	@AfterClass

	public void tearDownTest() {

		tearDownWebEnvironment();

		objUtilities = null;

		objViewForNSR = null;

		objViewForAssignmentTab = null;

		objViewForProductsAndProjects = null;

		objNSRPage = null;

	}
 
}