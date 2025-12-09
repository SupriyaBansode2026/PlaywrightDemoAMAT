package com.pageFactory.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.Properties;

public class LoginPage {
	
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	private Page page;
	boolean bResult = false;

	private Locator uname;
	private Locator paswd;
	private Locator logintext;
	private Locator login;

	public LoginPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();

		uname = page.locator("//input[@name='txtUsername']");
		paswd = page.locator("//input[@name='txtPassword']");
		logintext = page.locator("//div[text()='Login            ']");
		login = page.locator("//button[@type='submit']");
	}

	public void isLoginPageLogoDisplay() {
		objUtilities.logReporter("LOGIN CHK :: Check Login logo Displayed:: ", objWrapperFunctions.checkElementDisplyed(logintext), false);
	}

	public void enterUsername(String user) {
		objUtilities.logReporter("LOGIN :: Enter value of username:: ", objWrapperFunctions.setText(uname, user), false);
	}

	public void enterPassword(String pswd) {
		objUtilities.logReporter("LOGIN :: Enter value of password for login:: ", objWrapperFunctions.setText(paswd, pswd), false);
	}

	public void clickOnLoginButton() {
		objUtilities.logReporter("LOGIN :: Click on login:: ", objWrapperFunctions.click(login), false);
		objUtilities.logReporter("Check URL contains dashboard keyword :: :: ", objUtilities.getCurrentURL().contains("dashboard"), false);
	}
}