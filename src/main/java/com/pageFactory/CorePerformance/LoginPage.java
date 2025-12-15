package com.pageFactory.CorePerformance;

import java.util.Properties;
import com.microsoft.playwright.*;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class LoginPage {
	
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	boolean bResult = false;
	private Page page;

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
		objUtilities.logReporter("LOGIN CHK :: Check Login logo Displayed : isLoginPageLogoDisplay()", objWrapperFunctions.checkElementDisplyed(logintext), false);
	}

	public void enterUsername(String user) {
		objUtilities.logReporter("LOGIN :: Enter value of username: enterUsername()", objWrapperFunctions.setText(uname, user), false);
	}
	
	public void enterPassword(String pswd) {
		objUtilities.logReporter("LOGIN :: Enter value of password for login: enterPassword() ", objWrapperFunctions.setText(paswd, pswd), false);
	}

	public void clickOnLoginButton() {
		objUtilities.logReporter(
			"LOGIN :: Click on login: enterPassword()",
			objWrapperFunctions.click(login),
			false
		);
		objUtilities.logReporter(
			"Check URL contains dashboard keyword : enterPassword() ",
			objUtilities.getCurrentURL().contains("dashboard"),
			false
		);
	}
}