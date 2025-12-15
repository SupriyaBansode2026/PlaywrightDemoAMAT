package com.views.CorePerformance;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.pageFactory.CorePerformance.HRAdministrationPage;
import com.pageFactory.CorePerformance.OrganizationPage;


public class ViewForOrganization {

	private OrganizationPage objOrganizationPage;
	private HRAdministrationPage objHRAdministrationPage;



	public ViewForOrganization(Pojo objPojo) {

		new CorePerformancePage(objPojo);
		objOrganizationPage=new OrganizationPage(objPojo);
		objHRAdministrationPage=new HRAdministrationPage(objPojo);

	}
	
	public void validateOrganization_clickMenu() {
		objHRAdministrationPage.clickOnMenu();
	}
		public void organizationmenu(String str) {

		objOrganizationPage.clickOrganizationMenu(str);
		}
		public void generalInformationSubmenu() {

		objOrganizationPage.clickGeneralInformationSubMenu();
		objOrganizationPage.fill_GeneralInformationForm();
	}
		public void enterNote(String str) {

		objOrganizationPage.setNote(str);
		}
		public void performSubmit() {

		objOrganizationPage.clickSubmit();
		}
		//VERIFIED - Organization:General Information Created Successfully
		
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_submenu_Structure);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuremodule_editbtn);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord_3dots);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord_3dots_delete);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_confirmation_popup_delete);
		public void structureSubmenu() {

		objOrganizationPage.performActionsOnStructureSubmenu();
		}
		public void verifypopuptext(String str) {
			objOrganizationPage.verfiypopuptext(str);
		}
		public void clickDelete(String str) {
			objOrganizationPage.performDeleteActionOnPopup(str);
		}
		//further can add 
//		objWrapperFunctions.waitTillElementPresent(objOrganizationPage.loc_structuremodule_donebtn);
//		objWrapperFunctions.checkElementDisplyed(objOrganizationPage.loc_structuremodule_donebtn);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuremodule_donebtn);
//		objOrganizationPage.clickDone(objOrganizationPage.loc_structuremodule_donebtn);
	}	
