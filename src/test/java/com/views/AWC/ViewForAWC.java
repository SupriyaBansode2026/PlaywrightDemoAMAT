package com.views.AWC;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.AWC.AWCPage;

public class ViewForAWC {

	private AWCPage objAWCPage;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;

	public ViewForAWC(Pojo objPojo) {

		objAWCPage = new AWCPage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}

	public void verifyLoginPageDisplay() {
		objAWCPage.verifyLoginPageDisplay();
	}

	public void enterUsername(String sUsername) {
		objAWCPage.enterUsername(sUsername);
	}

	public void enterPassword(String sPsw) {
		objAWCPage.enterPassword(sPsw);
	}

	public void clickOnLoginButton() {
		objAWCPage.clickOnLoginButton();
	}

	public void clickOnAvtarIcon() {
		objAWCPage.clickOnAvtarIcon();
	}

	public void verifyAvtarUserInfoPanel(String sValues) {
		String[] sVal = sValues.split("~");
		for (int i = 0; i < sVal.length; i++) {
			objAWCPage.verifyAvtarUserInfoPanel(sVal[i]);
		}
	}

	public void clickOnSignOutBtn() {
		objAWCPage.clickOnSignOutBtn();
	}
	public void verifyManageProfilePage(String sValues) {
		String[] sVal = sValues.split("~");
		for (int i = 0; i < sVal.length; i++) {
			objAWCPage.verifyManageProfilePage(sVal[i]);
		}
	}

	public void clickOnManageProfileLink() {
		objAWCPage.clickOnManageProfileLink();
	}
}
