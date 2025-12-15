package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

public class LogoutPage {

	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Page page;
	private Properties objConfig;
	boolean bResult = false;

	private String logoutSelector = "//i[text()='oxd_logout_round']";

	public LogoutPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();
	}

	public void performLogout() {
		objUtilities.logReporter(
			"clicked on 'Logout' button:",
			objWrapperFunctions.click(logoutSelector),
			false
		);
	}
}