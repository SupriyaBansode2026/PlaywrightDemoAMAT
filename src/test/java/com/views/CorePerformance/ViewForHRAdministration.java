package com.views.CorePerformance;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.HRAdministrationPage;

public class ViewForHRAdministration {

	private CorePerformancePage objCorePerformancePage;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;
	private HRAdministrationPage objHRAdministrationPage;


	public ViewForHRAdministration(Pojo objPojo) {

		objCorePerformancePage = new CorePerformancePage(objPojo);
		objHRAdministrationPage=new HRAdministrationPage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
	}

	
	
	//pooja code method starts
	public void validateHRAdministration_ManageUserRole() {
		objHRAdministrationPage.clickOnMenu();
		objHRAdministrationPage.clickTopToolTipMenu(objHRAdministrationPage.loc_btn_ManageUserRoles);
		objHRAdministrationPage.clickPlusIcon();
		objHRAdministrationPage.fill_addUserRoleForm();
		objHRAdministrationPage.checkEmployeeActions();
		objHRAdministrationPage.workflowManagement();
		objHRAdministrationPage.dataGroupPermissions();
		objHRAdministrationPage.clickOnButton();

		//VERIFIED - User Role Added Successfully
	}

	public void validateHRAdministration_Users(String empname) {
		objHRAdministrationPage.clickOnMenu();
		objHRAdministrationPage.clickTopToolTipMenu(objHRAdministrationPage.loc_btn_Users);
		objHRAdministrationPage.clickOnFilterIcon();
		objHRAdministrationPage.enterEmployeenameInFilterUsers(empname);
	}
	//pooja code ends


	public void chklog() {
		// TODO Auto-generated method stub
		objHRAdministrationPage.chklogger();
	}



	

}