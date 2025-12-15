package com.pageFactory.AWC;

import java.util.Properties;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class AWCPage {
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	private Page page;
	boolean bResult = false;

	private Locator uname;
	private Locator paswd;
	private Locator login;

	public AWCPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();
		uname = page.locator("//input[@name='username']");
		paswd = page.locator("//input[@name='password']");
		login = page.locator("//button[@type='submit']");
	}

	public void verifyLoginPageDisplay() {
		objWrapperFunctions.waitForElementPresence(login,"5");
		objUtilities.logReporter("verify Login page display :verifyLoginPageDisplay()",
				objWrapperFunctions.checkElementDisplyed(login), false);
	}

	public void enterUsername(String user) {
		objUtilities.logReporter("Enter value of username: enterUsername()", objWrapperFunctions.setText(uname, user),
				false);
	}

	public void enterPassword(String pswd) {
		objUtilities.logReporter("Enter value of password for login: enterPassword() ",
				objWrapperFunctions.setText(paswd, pswd), false);
	}

	public void clickOnLoginButton() {
		objUtilities.logReporter("LOGIN Click on login: clickOnLoginButton()", objWrapperFunctions.click(login), false);
		Locator avtarIcon = page.locator("//div[contains(@class,'sessionControls-header')]//button[@aria-label='Your Profile']/span");
		objWrapperFunctions.waitForElementPresence(avtarIcon,"50");

	}

	public void clickOnAvtarIcon() {
		Locator avtarIcon = page.locator("//div[contains(@class,'sessionControls-header')]//button[@aria-label='Your Profile']/span");
		objUtilities.logReporter("click on Avatar icon :clickOnAvtarIcon()", objWrapperFunctions.click(avtarIcon), false);
		objUtilities.waitFor(10);
	}

	public void verifyAvtarUserInfoPanel(String sOption) {
		Locator opt = page.locator("//span[contains(text(),'" + sOption + "')]");
		objUtilities.logReporter("verify " + sOption + " display on User information panel :verifyAvtarUserInfoPanel()",
				objWrapperFunctions.checkElementDisplyed(opt), false);
	}

	public void clickOnSignOutBtn() {
		Locator signOutBtn = page.locator("//div[contains(text(),'Sign Out')]/span[@iconid='cmdSignout']");
		objUtilities.logReporter("Clickon sign out Button :clickOnSignOutBtn()", objWrapperFunctions.click(signOutBtn), false);
	}

	public void verifyManageProfilePage(String sOption) {
		Locator opt = page.locator("//div[contains(text(),'" + sOption + "')]");
		objUtilities.logReporter("verify " + sOption + " display on manage user profile page :verifyManageProfilePage()",
				objWrapperFunctions.checkElementDisplyed(opt), false);
	}

	public void clickOnManageProfileLink() {
		Locator sMngPrfLink = page.locator("//a[@aria-label='Manage Profile']");
		objUtilities.logReporter("Clickon Manage profile link :clickOnManageProfileLink()", objWrapperFunctions.click(sMngPrfLink),
				false);
	}

	public void verifySubOption(String sOption, String sSubOptions) {
		// To implement when required, as Selenium code is not provided
	}
}