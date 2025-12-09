package com.views.NSR;
 
import java.util.Properties;
 
import com.generic.*;

import com.pageFactory.NSR.NSRPage;
 
public class ViewForNSR extends BaseTest {

	// Local variables

	private NSRPage objNSRPage;

	private Utilities objUtilities;

	private Properties objNSRModule;

	public static String strMsg;

	public static String slotID;

	public static String NSRNO;

	public static String sRollOnPage;

	public ViewForNSR(Pojo objPojo) {

		objUtilities = objPojo.getObjUtilities();

		objNSRPage = new NSRPage(objPojo);

		objNSRModule = loadConfigPropertiesForModules("NSR");

	}

	public void takeActionOnAddNewNSRPopUp(String sConfirmationMessage, String sActionButton) {

		objNSRPage.verifyAddNewNSRPopUpDisplayed();

		objNSRPage.verifyConfirmationMessageOnAddNewNSRPopUp(sConfirmationMessage);

		objNSRPage.takeActionOnAddNewNSRPopUp(sActionButton);

	}
 
	public void fillOrderInformationSectionBasic(String sBU, String sTechnology, String sPlatform, String sSO,

			String sSOLineItem) {

		objNSRPage.expandNSRSection("Order Information");

		if (sBU != "")

			objNSRPage.selectDDInOrderInformationSection_ChromeAndIE("BU", sBU);// 03062019 RupaliP to run method both

		if (sSO != "" && sSOLineItem != "") {
 
			sSO = objNSRModule.getProperty("SoNumber_QA");

			sSOLineItem = objNSRModule.getProperty("SoLineItemNumber_QA");
 
		} else if (sSO != "" && sSOLineItem == "") {

			sSO = objNSRModule.getProperty("SoNumber_QA");

		} else if (sSO == "" && sSOLineItem != "") {

			sSOLineItem = objNSRModule.getProperty("SoLineItemNumber_QA");

		} else if (sSO == "" && sSOLineItem == "") {

			// sO and so line item will be empty

		}
 
		objNSRPage.enterValuesInNSRDetailsTextbox("SO #", sSO);

		objNSRPage.selectDDInOrderInformationSection_ChromeAndIE("Technology", sTechnology);// 03062019 RupaliP to run

		objNSRPage.enterValuesInNSRDetailsTextbox("SO Line Item #", sSOLineItem);

		objNSRPage.selectDDInOrderInformationSection_ChromeAndIE("Platform", sPlatform);// 03062019 RupaliP to run
 
	}

	public void fillRequestorSectionBasic(String sTitle, String sDescription, String sPrimaryDriver,

			String sRepeatRequest, String sResponsePartNo, String sCategory, String sSubCategory) {

		objNSRPage.expandNSRSection("Requestor Section");

		objUtilities.waitTillPageLoad(null);

		if (sTitle != "")

			objNSRPage.enterValuesInNSRDetailsTextbox("Title", sTitle);

		if (sCategory != "")

			objNSRPage.selectDDInRequestorSection_ChromeandIE("  Category", sCategory);// 03062019 RupaliP to run method

		if (sDescription != "")

			objNSRPage.enterValuesInNSRDetailsTextbox("Description", sDescription);

		if (sSubCategory != "")

			objNSRPage.selectDDInRequestorSection_ChromeandIE("Sub Category", sSubCategory);// 03062019 RupaliP to run

		if (sRepeatRequest != "")

			objNSRPage.selectDDInRequestorSection_ChromeandIE("Repeat Request", sRepeatRequest);// 03062019 RupaliP to

		if (sResponsePartNo != "")

			objNSRPage.enterValuesInNSRDetailsTextbox("Response Part #", sResponsePartNo);

		if (sPrimaryDriver != "")

			objNSRPage.selectDDInRequestorSection_ChromeandIE("Primary Driver", sPrimaryDriver);// 03062019 RupaliP to

	}

	public void addExistingProjectInAssociatedCorePlusProjectSection(String sBU, String sProduct, String sProject,

			String sProjectID) {

		objNSRPage.clickOnAddToExistingProject();

		objUtilities.waitTillPageLoad(null);

		objNSRPage.verifyAddAssociatedProjectsPopUpDisplayed();

		if (sProjectID != "")

			objNSRPage.setProjectIDInAddAssociatedProjectPopUp(sProjectID);

		else {

			if (sBU != "")

				objNSRPage.selectDDValueInAddAssociatedProjectsPopUp("BU", sBU);

			if (sProduct != "")

				objNSRPage.selectDDValueInAddAssociatedProjectsPopUp("Product", sProduct);

			objNSRPage.clickAddAssociatedProjectsPopUpButton("Load");

			objNSRPage.selectCheckboxAgainstProjectTitleOnAddAssociatedProjects(sProject);

		}

		objNSRPage.clickAddAndCloseOnAddAssociatedProjectPopUpforaddindAgainProject();

		objUtilities.waitFor(3);
 
	}

	public void clickOnSave() {

		objNSRPage.clickOnSave();

		objUtilities.waitFor(10);

		objUtilities.waitTillPageLoad(null);

	}

	public void clickNSRDetailsHeaderButton(String sButtonName) {

		objUtilities.waitTillPageLoad(null);

		if(sButtonName.equalsIgnoreCase("Preliminary – Drive long lead items")

				|| sButtonName.equalsIgnoreCase("Intermediate - BOM / BOM updates") || sButtonName.equalsIgnoreCase("Final - BOM ready"))

		{

			objUtilities.waitTillPageLoad(null);

			objNSRPage.SelectNSRRiskAssessmentDropdwnwithoutNSRRiskReason("Low Risk");

			objNSRPage.SelectFinalBOMLateReason("Design Unavailable");

			objNSRPage.clickOnSave();

			objUtilities.waitTillPageLoad(null);

			objNSRPage.clickNSRDetailsHeaderButton(sButtonName);
 
		}

		else {

			objNSRPage.clickNSRDetailsHeaderButton(sButtonName);

		}

	}

	public void verifyNSRStatusOnNSRDetailsPage(String sExpStatus) {

		objNSRPage.verifyNSRStatusOnNSRDetailsPage(sExpStatus);

	}

	public void clickOnNSRDetailsHeaderButton(String sButtonName) {

		objNSRPage.clickNSRDetailsHeaderButton(sButtonName);
 
	}

	public void SelectNsrRiskAssesmentDD(String sRisk)

	{

		objNSRPage.SelectNsrRiskAssesmentDD(sRisk);

		objNSRPage.SelectFinalBOMLateReason("Design Unavailable");

	}

	public void selectNSRRiskReason(String sOption)

	{

		objNSRPage.selectNSRRiskReason(sOption);

	}

	public void verifyCRFAttributeONCRNPage() {

		objNSRPage.verifyCRFAttributeONCRNPage();

	}

	public void fillEstimatedCompletionInCRNHeader(String sEstCompletionPercent) {

		if (sEstCompletionPercent.contains(".0"))

			sEstCompletionPercent = sEstCompletionPercent.replace(".0", "");

		objNSRPage.enterEstimatedCompletionPercent(sEstCompletionPercent);

	}

	public void submitNSR() {

		objNSRPage.clickOnSubmitNSRBtn();

	}
 
	public String getCRFNumberONCRNPage() {

		return (objNSRPage.getCRFNumberONCRNPage());

	}

	public void ClickOnCRFLinkAttributeOnCRNPage() {

		objNSRPage.ClickOnCRFLinkAttributeOnCRNPage();

	}

	public void handleAndAcceptAlert(String sMesg) {

			objNSRPage.handleAndAcceptAlert(sMesg);

		}

	public void verifyCurrentURL(String sURL) {

		objNSRPage.verifyCurrentURL(sURL);

	}

	public void VerifyCRFAttributesPresentOnCRFPage(String AttributeName) {

		objNSRPage.VerifyCRFAttributesPresentOnCRFPage(AttributeName);

	}

	public void verifyLinksAreHyperlinkAndClickOnLink(String hyperlinks) {

		objNSRPage.verifyLinksAreHyperlinkAndClickOnLink(hyperlinks);
 
	}

	public void verifySourceAndvCModel(String source) {
 
		objNSRPage.expandNSRSection("Order Information");

		objUtilities.waitFor(5);

		objNSRPage.verifySourceAndvCModel(source);

	}

}