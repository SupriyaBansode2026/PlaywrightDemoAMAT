package com.pageFactory.AWC;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AWCPage {
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Page page;
	private Properties objConfig;
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
		//objWrapperFunctions.waitForElementPresence(login, "5");
		objUtilities.logReporter("verify Login page display :verifyLoginPageDisplay()",
				objWrapperFunctions.checkElementDisplyed(login), false);
	}

	public void enterUsername(String user) {
		//objWrapperFunctions.waitForElementPresence(uname, "5");
		objUtilities.logReporter("Enter value of username: enterUsername()", objWrapperFunctions.setText(uname, user),
				false);
	}

	public void enterPassword(String pswd) {
		//objWrapperFunctions.waitForElementPresence(paswd, "5");
		objUtilities.logReporter("Enter value of password for login: enterPassword() ",
				objWrapperFunctions.setText(paswd, pswd), false);
	}

	public void clickOnLoginButton() {
		//objWrapperFunctions.waitForElementPresence(login, "5");
		objUtilities.logReporter("LOGIN Click on login: clickOnLoginButton()", objWrapperFunctions.click(login), false);
		Locator avtarIcon = page.locator("//div[contains(@class,'sessionControls-header')]//button[@aria-label='Your Profile']/span");
		objWrapperFunctions.waitForElementPresence(avtarIcon, "30");
	}

	public void clickOnAvtarIcon() {
		Locator avtarIcon = page.locator("//div[contains(@class,'sessionControls-header')]//button[@aria-label='Your Profile']/span");
		//objWrapperFunctions.waitForElementPresence(avtarIcon, "5");
		objUtilities.logReporter("click on Avatar icon :clickOnAvtarIcon()", objWrapperFunctions.click(avtarIcon), false);
	}

	public void verifyAvtarUserInfoPanel(String sOption) {
		Locator opt = page.locator("//span[contains(text(),'" + sOption + "')]");
		//objWrapperFunctions.waitForElementPresence(opt, "5");
		objUtilities.logReporter("verify " + sOption + " display on User information panel :verifyAvtarUserInfoPanel()",
				objWrapperFunctions.checkElementDisplyed(opt), false);
	}

	public void clickOnSignOutBtn() {
		Locator signOutBtn = page.locator("//div[contains(text(),'Sign Out')]/span[@iconid='cmdSignout']");
		//objWrapperFunctions.waitForElementPresence(signOutBtn, "5");
		objUtilities.logReporter("Clickon sign out Button :clickOnSignOutBtn()", objWrapperFunctions.click(signOutBtn),
				false);
	}

	public void verifyManageProfilePage(String sOption) {
		Locator opt = page.locator("//div[text()='" + sOption + "']");
		//objWrapperFunctions.waitForElementPresence(opt, "5");
		objUtilities.logReporter(
				"verify " + sOption + " display on manage user profile page :verifyManageProfilePage()",
				objWrapperFunctions.checkElementDisplyed(opt), false);
	}

	public void clickOnManageProfileLink() {
		Locator sMngPrfLink = page.locator("//a[@aria-label='Manage Profile']");
		//objWrapperFunctions.waitForElementPresence(sMngPrfLink, "5");
		objUtilities.logReporter("Clickon Manage profile link :clickOnManageProfileLink()",
				objWrapperFunctions.click(sMngPrfLink), false);
	}

	public void verifySubOption(String sOption, String sSubOptions) {
		Locator sOptSubOpt = page.locator("//*[@caption='" + sOption + "']//label/span[@class='sw-property-name' and text()='" + sSubOptions + "']");
		//objWrapperFunctions.waitForElementPresence(sOptSubOpt, "5");
		objUtilities.logReporter(
				"verify menu " + sOption + " has sub menu " + sSubOptions
						+ " in manage profile page :verifySubOption()",
				objWrapperFunctions.checkElementDisplyed(sOptSubOpt), false);
	}

	public void verifySubOptionInFolderProperties(String sSubOptions, String sVal) {
		Locator sOptSubOpt = page.locator("//*[@caption='']//label/span[contains(text(),'" + sSubOptions
				+ "')]/following-sibling::span[@title='" + sVal + "']");
		//objWrapperFunctions.waitForElementPresence(sOptSubOpt, "5");
		objUtilities.logReporter(
				"verify menu Properties has sub menu " + sSubOptions + " in manage profile page :verifySubOption()",
				objWrapperFunctions.checkElementDisplyed(sOptSubOpt), false);
	}

	public void clickOnExplorerIcon() {
		Locator explorerIcon = page.locator("//div[@title='Explorer']");
		//objWrapperFunctions.waitForElementPresence(explorerIcon, "5");
		objUtilities.logReporter("click on Explorere icon :clickOnExplorerIcon()",
				objWrapperFunctions.click(explorerIcon), false);
	}
	
	public void clickOnMoreOption() {
		Locator explorerIcon = page.locator("//button[@button-id='Awp0MoreGroup']/div/span[@iconid='cmdMore']");
		//objWrapperFunctions.waitForElementPresence(explorerIcon, "5");
		objUtilities.logReporter("click on More option icon from Explorere:clickOnMoreOption()",
				objWrapperFunctions.click(explorerIcon), false);
	}
	
}