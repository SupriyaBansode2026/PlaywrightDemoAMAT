package com.pageFactory.CorePerformance;

import java.util.Properties;
import java.util.Random;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HRAdministrationPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    private boolean bResult = false;
    private CorePerformancePage objCorePerformancePage;
    private Page page;
    private Locator loc_leftmenu_HRAdministration;
    private Locator loc_header_toptooltip_HRAdministration;
    private Locator loc_plusIcon;
    private Locator loc_btn_type;
    private Locator loc_userRoleName;
    private Locator loc_checkbox_addEmployee;
    private Locator loc_gridrecordstotal;
    private Locator loc_filterIcon;
    private Locator loc_title_FilterPopup;
    private Locator loc_txtbox_employeename;
    private Locator loc_btn_filter_submit;
    private String userroleName;

    public HRAdministrationPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        this.page = objPojo.getPage();
        loc_leftmenu_HRAdministration = page.locator("(//div[@id='menu-content']/ul/li[2]/a/span)[1]");
        loc_header_toptooltip_HRAdministration = page.locator("//div[@id=\"topbar\"]/ul/li[2]/div");
        loc_plusIcon = page.locator("//div[@id='UserRolesDiv']/div/a");
        loc_btn_type = page.locator("//input[@type='text'][@class='select-dropdown']");
        loc_userRoleName = page.locator("//input[@type='text'][@id='user_role_name']");
        loc_checkbox_addEmployee = page.locator("//label[@for='checkbox_employee_add']");
        loc_gridrecordstotal = page.locator("//table[contains(@class,'highlight bordered')]//thead/tr/th[@class='row-name sortable']/parent::tr/parent::thead/following::tbody[1]/tr");
        loc_filterIcon = page.locator("//ui-view[@name='navbar']/ul/li/a");
        loc_title_FilterPopup = page.locator("//h4[text()='Filter Users']");
        loc_txtbox_employeename = page.locator("//h4[text()='Filter Users']");
        loc_btn_filter_submit = page.locator("div[@class='modal-footer']/a[text()='Search']");
        objCorePerformancePage = new CorePerformancePage(objPojo);
    }

    public void clickTopToolTipMenu(String str_menu) {
        Locator loc = page.locator("//div[@id='top-ribbon-menu']/div[2]/child::top-level-menu-item/div/a[contains(text(),'" + str_menu + "')]");
        objWrapperFunctions.waitForElementPresence(loc, "5");
        objUtilities.logReporter("Click Top Tooltip Menu " + str_menu + " :clickTopToolTipMenu()", objWrapperFunctions.click(loc), false);
    }

    public void clickPlusIcon() {
        objWrapperFunctions.waitForElementPresence(loc_plusIcon, "5");
        objUtilities.logReporter("Click Plus Icon:clickPlusIcon()", objWrapperFunctions.click(loc_plusIcon), false);
    }

    public void fill_addUserRoleForm(String typedropdown) {
        objWrapperFunctions.waitForElementPresence(loc_btn_type, "5");
        objUtilities.logReporter("Click Type Dropdown:fill_addUserRoleForm()", objWrapperFunctions.click(loc_btn_type), false);
        Locator loc = page.locator("//ul[contains(@class,'select-dropdown')]/li/span[text()='" + typedropdown + "']");
        objWrapperFunctions.waitForElementPresence(loc, "5");
        objUtilities.logReporter("Select value from Type Dropdown:fill_addUserRoleForm()", objWrapperFunctions.click(loc), false);
        objWrapperFunctions.waitForElementPresence(loc_userRoleName, "5");
        objUtilities.logReporter("Field user role form-waitTillElementEnabled:fill_addUserRoleForm()", objWrapperFunctions.waitTillElementEnabled(loc_userRoleName), false);
        objWrapperFunctions.waitForElementPresence(loc_userRoleName, "5");
        objUtilities.logReporter("Enter value in User Role Name:fill_addUserRoleForm()", objWrapperFunctions.setText(loc_userRoleName, generaterandomString()), false);
    }

    public String generaterandomString() {
        StringBuilder str = new StringBuilder("demo");
        Random rand = new Random();
        int randomNumber = 100 + rand.nextInt(900);
        str.append(randomNumber);
        userroleName = str.toString();
        return str.toString();
    }

    public void checkEmployeeActions(String employeeaction) {
        Locator loc = page.locator("//div[@id='employee_actions_div']/child::div/label[contains(text(),'" + employeeaction + "')]");
        objWrapperFunctions.waitForElementPresence(loc, "5");
        objUtilities.logReporter("Select Checkbox of employee actions:checkEmployeeActions ", objWrapperFunctions.click(loc), false);
    }

    public void workflowManagement(String str) {
        Locator loc = page.locator("//div[@id='work_folw_management_div']/div/label[text()='" + str + "']");
        objWrapperFunctions.waitForElementPresence(loc, "5");
        objUtilities.logReporter("Work Flow management:: ", objWrapperFunctions.click(loc), false);
    }

    public void check_checkboxes_dataGroupPermissions(String checkboxList) {
        if (checkboxList != null) {
            String[] checkboxArray = checkboxList.split("~");
            for (String item : checkboxArray) {
                Locator loc = page.locator("//ul[@class='collapsible']/li/div/p/label[text()='" + item.trim() + "']");
                objWrapperFunctions.waitForElementPresence(loc, "5");
                objUtilities.logReporter("Data Group Permissions-Training:: ", objWrapperFunctions.click(loc), false);
            }
        } else {
            objUtilities.logReporter("Key listCheckbox not found in config.properties.", true, false);
        }
    }

    public void clickOnSubmitButton(String save) {
        Locator loc = page.locator("//div[@class='right-align']/a[text()='" + save + "']");
        objWrapperFunctions.waitForElementPresence(loc, "5");
        objUtilities.logReporter("Click Type:: ", objWrapperFunctions.click(loc), false);
    }

    public void clickOnButton(Locator ele) {
        objWrapperFunctions.waitForElementPresence(ele, "5");
        objUtilities.logReporter("Click Type:: ", objWrapperFunctions.click(ele), false);
    }

    public void clickOnMenu() {
        objWrapperFunctions.waitForElementPresence(loc_leftmenu_HRAdministration, "5");
        objUtilities.logReporter("VERIFY - Clicked on Left menu HR Administration:clickOnMenu() ", objWrapperFunctions.click(loc_leftmenu_HRAdministration), false);
    }

    public void verifygridview(String typedropdown) {
        int rowCount = objWrapperFunctions.getTableRowCount(loc_gridrecordstotal);
        for (int j = 1; j <= rowCount; j++) {
            Locator loc2 = page.locator("(//table[contains(@class,'highlight bordered')]//thead/tr/th[contains(@class,'sortable')]/parent::tr/parent::thead/following::tbody[1]/tr/td[2]//span)[" + j + "]");
            objWrapperFunctions.waitForElementPresence(loc2, "5");
            objUtilities.logReporter("Verify created record displayed on Grid :verifygridview() ", objWrapperFunctions.getText(loc2, "text").equalsIgnoreCase(userroleName), false);
            System.out.println("userroleName2" + userroleName);
            Locator loc3 = page.locator("//table[contains(@class,'highlight bordered')]//thead/tr/th[contains(@class,'sortable')]/parent::tr/parent::thead/following::tbody[1]/tr/td[3]//span[" + j + "]");
            objWrapperFunctions.waitForElementPresence(loc3, "5");
            objUtilities.logReporter("Verify Type of record displayed on Grid :verifygridview() ", objWrapperFunctions.getText(loc3, "text").equalsIgnoreCase(typedropdown), false);
        }
    }

    public void clickOnFilterIcon() {
        objWrapperFunctions.waitForElementPresence(loc_filterIcon, "5");
        objUtilities.logReporter("VERIFY - Filter record by clicking filter Icon:: ", objWrapperFunctions.click(loc_filterIcon), false);
    }

    public void enterEmployeenameInFilterUsers(String empname) {
        objWrapperFunctions.waitForElementPresence(loc_title_FilterPopup, "5");
        objUtilities.logReporter("Filter :: :: ", objWrapperFunctions.getText(loc_title_FilterPopup, "text").equalsIgnoreCase("Filter Users"), false);
        objWrapperFunctions.waitForElementPresence(loc_txtbox_employeename, "5");
        objUtilities.logReporter("Filter :: Enter employee name:: ", objWrapperFunctions.setText(loc_txtbox_employeename, empname), false);
        objWrapperFunctions.waitForElementPresence(loc_btn_filter_submit, "5");
        objUtilities.logReporter("VERIFY - Click on submit:: ", objWrapperFunctions.click(loc_btn_filter_submit), false);
    }
}