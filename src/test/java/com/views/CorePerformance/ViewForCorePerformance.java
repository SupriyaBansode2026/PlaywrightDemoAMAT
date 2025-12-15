package com.views.CorePerformance;

import java.awt.AWTException;
import java.io.IOException;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;

public class ViewForCorePerformance {
	
	private CorePerformancePage objCorePerformancePage;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;
	
	public ViewForCorePerformance(Pojo objPojo) {
		
		objCorePerformancePage = new CorePerformancePage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}
}