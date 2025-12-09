package com.views.CorePerformance;

import java.util.List;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.DashboardPage;
import com.pageFactory.CorePerformance.LoginPage;

public class ViewForDashboard {
	
	private Utilities objUtilities;
	private Pojo objPojo;
	private DashboardPage objDashboardPage;

	public ViewForDashboard(Pojo objPojo) {
		objDashboardPage = new DashboardPage(objPojo);
		objUtilities = objPojo.getObjUtilities();
	}
	
}
