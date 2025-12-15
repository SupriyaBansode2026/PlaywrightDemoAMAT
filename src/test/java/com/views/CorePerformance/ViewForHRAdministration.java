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
	
	public void clickMainMenu(String str) {
		objHRAdministrationPage.clickOnMenu();
		objHRAdministrationPage.clickTopToolTipMenu(str);
	}
	
	public void selectTypeDropdown(String str) {

		objHRAdministrationPage.clickPlusIcon();
		objHRAdministrationPage.fill_addUserRoleForm(str);
	}
	
	public void checkOnEmployeeActionsCheckbox(String str) {

		objHRAdministrationPage.checkEmployeeActions(str);
	}
	public void checkOnWorkflowMnmgtCheckboxes(String str) {

		objHRAdministrationPage.workflowManagement(str);
	}
//		objHRAdministrationPage.check_checkboxes_dataGroupPermissions("Training");
//		objHRAdministrationPage.check_checkboxes_dataGroupPermissions("Attendance");
//		objHRAdministrationPage.check_checkboxes_dataGroupPermissions("Leave");
//		objHRAdministrationPage.check_checkboxes_dataGroupPermissions("Goals");
	
	public void checkListOfCheckboxes(String str) {

		objHRAdministrationPage.check_checkboxes_dataGroupPermissions(str);
	}
	public void clickSave(String str) {

		objHRAdministrationPage.clickOnSubmitButton(str);
	}
	public void validate_GridView(String str) {

		objHRAdministrationPage.verifygridview(str);
	}

//	public void validateHRAdministration_Users(String empname) { //this method not used
//		objHRAdministrationPage.clickOnMenu();
//		objHRAdministrationPage.clickTopToolTipMenu("Users");
//		objHRAdministrationPage.clickOnFilterIcon();
//		objHRAdministrationPage.enterEmployeenameInFilterUsers(empname);
//	}

}
