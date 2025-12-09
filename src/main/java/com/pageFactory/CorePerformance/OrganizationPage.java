package com.pageFactory.CorePerformance;

import java.util.Properties;
import java.util.Random;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OrganizationPage {

	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	private CorePerformancePage objCorePerformancePage;
	private Page page;

	public Locator loc_btn_Organization;
	public Locator loc_submenu_GeneralInformation;
	public Locator loc_inputbox_organizationname;
	public Locator loc_inputbox_note;
	public Locator loc_btn_submit;
	public Locator loc_submenu_Structure;
	public Locator loc_structuregridrecord;
	public Locator loc_structuremodule_editbtn;
	public Locator loc_structuregridrecord_3dots;
	public Locator loc_structuregridrecord_3dots_delete;
	public Locator loc_confirmation_popup_delete;
	public Locator loc_structuremodule_donebtn;

	public OrganizationPage(Pojo objPojo) {
		this.objPojo = objPojo;
		this.page = objPojo.getPage();
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = new WrapperFunctions(objPojo);
		objConfig = objPojo.getObjConfig();
		objCorePerformancePage = new CorePerformancePage(objPojo);

		loc_btn_Organization = page.locator("//div[@id='top-ribbon-menu']/div[2]/child::top-level-menu-item/div/a[contains(text(),'Organization')]");
		loc_submenu_GeneralInformation = page.locator("//div[@id='top-ribbon-menu']//child::top-level-menu-item[4]/div/sub-menu-container//child::a[contains(text(),'General Information')]");
		loc_inputbox_organizationname = page.locator("(//form[@name='contactDetailsForm']/materializecss-decorator[1]//child::input[1])[1]");
		loc_inputbox_note = page.locator("//materializecss-decorator[2]/div/sf-decorator//textarea");
		loc_btn_submit = page.locator("//materializecss-decorator[4]//button[@type='submit']");
		loc_submenu_Structure = page.locator("//div[@id='top-ribbon-menu']//child::top-level-menu-item[3]/div/a[contains(text(),'Structure')]");
		loc_structuregridrecord = page.locator("(//span[@class='org_info'])[2]");
		loc_structuremodule_editbtn = page.locator("//div[@class='org_structure']/div[1][text()='Edit']");
		loc_structuregridrecord_3dots = page.locator("(//span[@class=\"menu\"]/i)[2]");
		loc_structuregridrecord_3dots_delete = page.locator("//a[@rel='2'][text()='Delete']");
		loc_confirmation_popup_delete = page.locator("//div[@class='modal-footer']/a[text()='Yes, Delete']");
		loc_structuremodule_donebtn = page.locator("//ui-view/div[contains(@class,'structure')]/div[1][text()='Done']");
	}

	public void clickTopToolTipMenu(Locator ele) {
		boolean menu_manageUserRoles = objWrapperFunctions.click(ele);
		objUtilities.logReporter("Click Top Tool Tip Menu", menu_manageUserRoles, false);
		if (!menu_manageUserRoles) {
			throw new AssertionError("Top Tool Tip Menu click failed");
		}
	}

	public void clicksubmenu() {
		boolean submenu = objWrapperFunctions.click(loc_submenu_GeneralInformation);
		objUtilities.logReporter("Click submenu General Information", submenu, false);
		if (!submenu) {
			throw new AssertionError("Submenu General Information click failed");
		}
	}

	public void fill_GeneralInformationForm() {
		objWrapperFunctions.waitTillElementEnabled(loc_inputbox_organizationname);

		StringBuilder str = new StringBuilder("Branch_");
		Random rand = new Random();
		int randomNumber = 100 + rand.nextInt(900);
		str.append(randomNumber);

		objWrapperFunctions.setText(loc_inputbox_organizationname, str.toString());
	}

	public void setNote() {
		objWrapperFunctions.setText(loc_inputbox_note, "delete s/w");
	}

//	public void clickSubmit() {
//		boolean clickType = objWrapperFunctions.click(loc_btn_submit);
//		objUtilities.logReporter("Click Submit", clickType, false);
//		if (!clickType) {
//			throw new AssertionError("Submit button click failed");
//		}
//	}

	public void clickDone(Locator ele) {
		objWrapperFunctions.checkElementDisplyed(ele);
		boolean submenu = objWrapperFunctions.click(ele);
		objUtilities.logReporter("Click Done button", submenu, false);
		if (!submenu) {
			throw new AssertionError("Done button click failed");
		}
	}
}