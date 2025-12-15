package com.views.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.pageFactory.CorePerformance.LogoutPage;

public class ViewForLogout {
	
	private Utilities objUtilities;
	private Pojo objPojo;
	private LogoutPage objLogoutPage;


	public ViewForLogout(Pojo objPojo) {
		objLogoutPage = new LogoutPage(objPojo);
		objUtilities = objPojo.getObjUtilities();
	}

	public void logoutTheApplication() {
		objLogoutPage.performLogout();
	}
}
