package com.views.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.LeavePage;

public class ViewForLeave {
	
	private CorePerformancePage objCorePerformancePage;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;
	private LeavePage objLeavePage;

	
	public ViewForLeave(Pojo objPojo) {
		objCorePerformancePage = new CorePerformancePage(objPojo);
		objLeavePage=new LeavePage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}
	
	public void verfiyAssignLeaveOptionsIsWorking(String assignLeaveOption, String assignLeaveBtn) {
		objLeavePage.verifyAssignLeave(assignLeaveOption,assignLeaveBtn);
	}
	
	public void verfiyLeaveListOptionsIsWorking(String leaveListOption, String leaveListBtn) {
		objLeavePage.verifyLeaveList(leaveListOption,leaveListBtn);
	}
	
	public void verfiyLeaveCalnedarOptionsIsWorking(String leaveCalendarOption, String leaveCalendarBtn) {
		objLeavePage.verifyLeaveCalender(leaveCalendarOption,leaveCalendarBtn);
	}
	
	public void verfiyApplyLeaveOptionsIsWorking(String applyLeaveOption, String applyLeaveBtn) {
		objLeavePage.verifyApplyLeave(applyLeaveOption,applyLeaveBtn);
	}
	
	public void verfiyMyLeaveOptionsIsWorking(String myLeaveOption, String myLeaveBtn) {
		objLeavePage.verifyMyLeave(myLeaveOption,myLeaveBtn);
	}
}