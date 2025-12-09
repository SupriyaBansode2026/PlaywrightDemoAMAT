package com.views.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.DashboardPage;
import com.pageFactory.CorePerformance.LoginPage;

public class ViewForLogin {
	
	private Utilities objUtilities;
	private Pojo objPojo;
	private LoginPage objLoginPage;


	public ViewForLogin(Pojo objPojo) {
		objLoginPage = new LoginPage(objPojo);
		objUtilities = objPojo.getObjUtilities();
	}

	public void loginPageValidation() {
		 objLoginPage.isLoginPageLogoDisplay();
	}

	public void login(String user, String password) {
		objLoginPage.enterUsername(user);
		objLoginPage.enterPassword(password);
		objLoginPage.clickOnLoginButton();
	}
}