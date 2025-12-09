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

	private CorePerformancePage objCorePerformancePage;
	private Utilities objUtilities;
	private Pojo objPojo;
	private WrapperFunctions objWrapperFunctions;
	private OrganizationPage objOrganizationPage;
	private HRAdministrationPage objHRAdministrationPage;



	public ViewForOrganization(Pojo objPojo) {

		objCorePerformancePage = new CorePerformancePage(objPojo);
		objOrganizationPage=new OrganizationPage(objPojo);
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objUtilities = objPojo.getObjUtilities();
		objHRAdministrationPage=new HRAdministrationPage(objPojo);

	}
	
	//pooja code starts
	public void validateOrganization() {
//		objHRAdministrationPage.clickOnMenu();
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_btn_Organization);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_submenu_GeneralInformation);
		objOrganizationPage.fill_GeneralInformationForm();
		objOrganizationPage.setNote();
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_btn_submit);
		System.out.println("VERIFIED - Organization:General Information Created Successfully");
		
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_submenu_Structure);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuremodule_editbtn);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord_3dots);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuregridrecord_3dots_delete);
		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_confirmation_popup_delete);
//		objWrapperFunctions.waitTillElementPresent(objOrganizationPage.loc_structuremodule_donebtn);
//		objWrapperFunctions.checkElementDisplyed(objOrganizationPage.loc_structuremodule_donebtn);
//		objOrganizationPage.clickTopToolTipMenu(objOrganizationPage.loc_structuremodule_donebtn);
//		objOrganizationPage.clickDone(objOrganizationPage.loc_structuremodule_donebtn);
	}	
	//pooja code ends

}