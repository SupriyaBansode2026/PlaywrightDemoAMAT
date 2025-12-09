package com.views.CorePerformance;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.OrangehrmPage;




public class ViewForOrangehrm {

	private OrangehrmPage objOrangehrm;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;

	
	public ViewForOrangehrm(Pojo objPojo) {

		objOrangehrm = new OrangehrmPage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}

	public boolean loginPageValidation() {
		return objOrangehrm.isLoginPageLogoDisplay();
	}
	
	
	public String getCurrentPageUrl() {
		return objOrangehrm.getPageUrl();
	}
	
	
	public void login(String user, String password) {
		objOrangehrm.enterUsername(user);
		objOrangehrm.enterPassword(password);
		objOrangehrm.clickOnLoginButton();
	}
	
	
	public boolean clickonAnnounceOption() {
		return objOrangehrm.clickonAnnounceOption();
	}
//	
//	public boolean verifyDropdownValues(String locatortype, String locatorvalue) {
//		return objOrangehrm.verifyDropdownValues(locatortype, locatorvalue);
//	}

	
	public void clickOnDocuments() {
		objOrangehrm.clickOnDocuments();
	}
	
//	 public void verifydropdownvalues() {
//		 objOrangehrm.verifydropdownvalues();
//	 }

//	 public String verifydropdownvalues() {
//		return objOrangehrm.verifydropdownvalues();
//	 }
//	 public String verifydropdownvaluess() {
//		return objOrangehrm.verifydropdownvaluess();
//	 }
	 
	 
//	 public List<String> returnListOfElement() {
//			return objOrangehrm.getElementsList();
//		}

	 
	 
	
}