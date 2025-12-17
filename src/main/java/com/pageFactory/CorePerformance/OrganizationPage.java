package com.pageFactory.CorePerformance;

import java.util.Properties;
import java.util.Random;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;

public class OrganizationPage {

	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Properties objConfig;
	private CorePerformancePage objCorePerformancePage;

	private Locator loc_submenu_GeneralInformation;
	private Locator loc_inputbox_organizationname;
	private Locator loc_inputbox_note;
	private Locator loc_btn_submit;
	private Locator loc_submenu_Structure;
	private Locator loc_structuregridrecord;
	private Locator loc_structuremodule_editbtn;
	private Locator loc_structuregridrecord_3dots;
	private Locator loc_structuregridrecord_3dots_delete;
	private Locator loc_confirmation_popup_delete;
	private Locator loc_structuremodule_donebtn;
	private Locator loc_confirmationpopup;

	public OrganizationPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = new WrapperFunctions(objPojo);
		objConfig = objPojo.getObjConfig();
		objCorePerformancePage = new CorePerformancePage(objPojo);

		loc_submenu_GeneralInformation = objPojo.getPage().locator("//div[@id='top-ribbon-menu']//child::top-level-menu-item[4]/div/sub-menu-container//child::a[contains(text(),'General Information')]");
		loc_inputbox_organizationname = objPojo.getPage().locator("(//form[@name='contactDetailsForm']/materializecss-decorator[1]//child::input[1])[1]");
		loc_inputbox_note = objPojo.getPage().locator("//materializecss-decorator[2]/div/sf-decorator//textarea");
		loc_btn_submit = objPojo.getPage().locator("//materializecss-decorator[4]//button[@type='submit']");
		loc_submenu_Structure = objPojo.getPage().locator("//div[@id='top-ribbon-menu']//child::top-level-menu-item[3]/div/a[contains(text(),'Structure')]");
		loc_structuregridrecord = objPojo.getPage().locator("(//span[@class='org_info'])[2]");
		loc_structuremodule_editbtn = objPojo.getPage().locator("//div[@class='org_structure']/div[1][text()='Edit']");
		loc_structuregridrecord_3dots = objPojo.getPage().locator("(//span[@class=\"menu\"]/i)[2]");
		loc_structuregridrecord_3dots_delete = objPojo.getPage().locator("//a[@rel='2'][text()='Delete']");
		loc_confirmation_popup_delete = objPojo.getPage().locator("//div[@class='modal-footer']/a[text()='Yes, Delete']");
		loc_structuremodule_donebtn = objPojo.getPage().locator("//ui-view/div[contains(@class,'structure')]/div[1][text()='Done']");
		loc_confirmationpopup = objPojo.getPage().locator("//div[@class='modal-content']/p");
	}

	public void clickOrganizationMenu(String str) {
		Locator loc = objPojo.getPage().locator("//div[@id='top-ribbon-menu']/div[2]/child::top-level-menu-item/div/a[contains(text(),'" + str + "')]");
		objWrapperFunctions.waitForElementPresence(loc, "5");
		objUtilities.logReporter("Click Tool Tip menu:clickOrganizationMenu() ", objWrapperFunctions.click(loc), false);
	}

	public void clickGeneralInformationSubMenu() {
		objWrapperFunctions.waitForElementPresence(loc_submenu_GeneralInformation, "5");
		objUtilities.logReporter("Click Tool Tip menu:clickGeneralInformationSubMenu() ", objWrapperFunctions.click(loc_submenu_GeneralInformation), false);
	}

	public void performActionsOnStructureSubmenu() {
		objWrapperFunctions.waitForElementPresence(loc_submenu_Structure, "5");
		objUtilities.logReporter("Click Tool Tip menu:performActionsOnStructureSubmenu() ", objWrapperFunctions.click(loc_submenu_Structure), false);

		objWrapperFunctions.waitForElementPresence(loc_structuregridrecord, "5");
		objUtilities.logReporter("Click Tool Tip menu:performActionsOnStructureSubmenu() ", objWrapperFunctions.click(loc_structuregridrecord), false);

		objWrapperFunctions.waitForElementPresence(loc_structuremodule_editbtn, "5");
		objUtilities.logReporter("Click Tool Tip menu:performActionsOnStructureSubmenu() ", objWrapperFunctions.click(loc_structuremodule_editbtn), false);

		objWrapperFunctions.waitForElementPresence(loc_structuregridrecord_3dots, "5");
		objUtilities.logReporter("Click Tool Tip menu:performActionsOnStructureSubmenu() ", objWrapperFunctions.click(loc_structuregridrecord_3dots), false);

		objWrapperFunctions.waitForElementPresence(loc_structuregridrecord_3dots_delete, "5");
		objUtilities.logReporter("Click Tool Tip menu:performActionsOnStructureSubmenu() ", objWrapperFunctions.click(loc_structuregridrecord_3dots_delete), false);
	}

	public void performDeleteActionOnPopup(String str) {
		Locator loc = objPojo.getPage().locator("//div[@class='modal-footer']/a[text()='" + str + "']");
		objWrapperFunctions.waitForElementPresence(loc, "5");
		objUtilities.logReporter("Click Tool Tip menu:performDeleteActionOnPopup() ", objWrapperFunctions.click(loc), false);
	}

	public void verfiypopuptext(String confirmationtext) {
		objWrapperFunctions.waitForElementPresence(loc_confirmationpopup, "5");
		objUtilities.logReporter("Verfiy popup text:verfiypopuptext() ", objWrapperFunctions.getText(loc_confirmationpopup, "text").trim().equalsIgnoreCase(confirmationtext.trim()), false);
	}

	public void clicksubmenu() {
		objWrapperFunctions.waitForElementPresence(loc_submenu_GeneralInformation, "5");
		objUtilities.logReporter("Click Sub menu:clicksubmenu() ", objWrapperFunctions.click(loc_submenu_GeneralInformation), false);
	}

	public void fill_GeneralInformationForm() {
		objWrapperFunctions.waitForElementPresence(loc_inputbox_organizationname, "5");
		objUtilities.logReporter("Fill General Information Form:fill_GeneralInformationForm() ", objWrapperFunctions.waitTillElementEnabled(loc_inputbox_organizationname), false);

		StringBuilder str = new StringBuilder("Branch_");
		Random rand = new Random();
		int randomNumber = 100 + rand.nextInt(900);
		str.append(randomNumber);

		objWrapperFunctions.waitForElementPresence(loc_inputbox_organizationname, "5");
		objUtilities.logReporter("Set text in Form:fill_GeneralInformationForm() ", objWrapperFunctions.setText(loc_inputbox_organizationname, str.toString()), false);
	}

	public void setNote(String str) {
		objWrapperFunctions.waitForElementPresence(loc_inputbox_note, "5");
		objUtilities.logReporter("Fill note in Form:setNote() ", objWrapperFunctions.setText(loc_inputbox_note, str), false);
	}

	public void clickSubmit() {
		objWrapperFunctions.waitForElementPresence(loc_btn_submit, "5");
		objUtilities.logReporter("Click submit button:clickSubmit()", objWrapperFunctions.click(loc_btn_submit), false);
	}

	public void clickDone(Locator ele) {
		objWrapperFunctions.waitForElementPresence(ele, "5");
		objUtilities.logReporter("Check element displayed :clickDone() ", objWrapperFunctions.checkElementDisplyed(ele), false);
		objWrapperFunctions.waitForElementPresence(ele, "5");
		objUtilities.logReporter("Click button:clickDone() ", objWrapperFunctions.click(ele), false);
	}
}