package com.pageFactory.CorePerformance;

import java.util.Properties;
import java.util.Random;

import com.microsoft.playwright.Locator;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.pageFactory.CorePerformance.CorePerformancePage;

public class HRAdministrationPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;
    private CorePerformancePage objCorePerformancePage;

    // Locators
    private Locator loc_leftmenu_HRAdministration;
    private Locator loc_header_toptooltip_HRAdministration;
    public Locator loc_btn_ManageUserRoles;
    public Locator loc_btn_Users;
    private Locator loc_plusIcon;
    private Locator loc_typeDropdownAdmin;
    private Locator loc_btn_type;
    private Locator loc_userRoleName;
    private Locator loc_checkbox_addEmployee;
    private Locator loc_checkbox_Candidate;
    private Locator loc_checkbox_training;
    private Locator loc_checkbox_attendance;
    private Locator loc_checkbox_Leave;
    private Locator loc_checkbox_goals;
    private Locator loc_btn_submit;

    private Locator loc_filterIcon;
    private Locator loc_title_FilterPopup;
    private Locator loc_txtbox_employeename;
    private Locator loc_btn_filter_submit;

    public HRAdministrationPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        objCorePerformancePage = new CorePerformancePage(objPojo);

        // Initialize Playwright Locators
        loc_leftmenu_HRAdministration = objPojo.getPage().locator("(//div[@id='menu-content']/ul/li[2]/a/span)[1]");
        loc_header_toptooltip_HRAdministration = objPojo.getPage().locator("//div[@id=\"topbar\"]/ul/li[2]/div");
        loc_btn_ManageUserRoles = objPojo.getPage().locator("//div[@id='top-ribbon-menu']/div[2]/child::top-level-menu-item/div/a[contains(text(),'Manage User Roles')]");
        loc_btn_Users = objPojo.getPage().locator("//div[@id='top-ribbon-menu']/div[2]/child::top-level-menu-item/div/a[contains(text(),'Users')]");
        loc_plusIcon = objPojo.getPage().locator("//div[@id='UserRolesDiv']/div/a");
        loc_typeDropdownAdmin = objPojo.getPage().locator("//form[@name='userRoleForm']/div[1]//ul/li[1]");
        loc_btn_type = objPojo.getPage().locator("//input[@type='text'][@class='select-dropdown']");
        loc_userRoleName = objPojo.getPage().locator("//input[@type='text'][@id='user_role_name']");
        loc_checkbox_addEmployee = objPojo.getPage().locator("//label[@for='checkbox_employee_add']");
        loc_checkbox_Candidate = objPojo.getPage().locator("//div[@id='work_folw_management_div']/div/label[text()='Candidate']");
        loc_checkbox_training = objPojo.getPage().locator("//ul[@class=\"collapsible\"]/li/div/p/label[text()='Training']");
        loc_checkbox_attendance = objPojo.getPage().locator("//ul[@class=\"collapsible\"]/li/div/p/label[text()='Attendance']");
        loc_checkbox_Leave = objPojo.getPage().locator("//ul[@class=\"collapsible\"]/li/div/p/label[text()='Leave']");
        loc_checkbox_goals = objPojo.getPage().locator("//ul[@class=\"collapsible\"]/li/div/p/label[text()='Goals']");
        loc_btn_submit = objPojo.getPage().locator("//div[@class='right-align']/a[2]");

        loc_filterIcon = objPojo.getPage().locator("//ui-view[@name='navbar']/ul/li/a");
        loc_title_FilterPopup = objPojo.getPage().locator("//h4[text()='Filter Users']");
        loc_txtbox_employeename = objPojo.getPage().locator("//h4[text()='Filter Users']");
        loc_btn_filter_submit = objPojo.getPage().locator("div[@class='modal-footer']/a[text()='Search']");
    }

    //pooja code started
    public void clickTopToolTipMenu(Locator ele) {
        objUtilities.logReporter("Click Plus Icon:: ", objWrapperFunctions.click(ele), false);
    }

    public void clickPlusIcon() {
        objUtilities.logReporter("Click Plus Icon:: ", objWrapperFunctions.click(loc_plusIcon), false);
    }

    public void fill_addUserRoleForm() {
        objUtilities.logReporter("Add user role form:: ", objWrapperFunctions.click(loc_btn_type), false);
        objUtilities.logReporter("Add user role form:: ", objWrapperFunctions.click(loc_typeDropdownAdmin), false);
        objUtilities.logReporter("Add user role form:: ", objWrapperFunctions.waitTillElementEnabled(loc_userRoleName), false);

        StringBuilder str = new StringBuilder("demo");
        Random rand = new Random();
        int randomNumber = 100 + rand.nextInt(900);
        str.append(randomNumber);

        objUtilities.logReporter("Check employee actions:: ", objWrapperFunctions.setText(loc_userRoleName, str.toString()), false);
    }

    public void checkEmployeeActions() {
        objUtilities.logReporter("Check employee actions:: ", objWrapperFunctions.click(loc_checkbox_addEmployee), false);
    }

    public void workflowManagement() {
        objUtilities.logReporter("Work Flow management:: ", objWrapperFunctions.click(loc_checkbox_Candidate), false);
    }

    public void dataGroupPermissions() {
        objUtilities.logReporter("Data Group Permissions:: ", objWrapperFunctions.click(loc_checkbox_training), false);
        objUtilities.logReporter("Data Group Permissions:: ", objWrapperFunctions.click(loc_checkbox_attendance), false);
        objUtilities.logReporter("Data Group Permissions:: ", objWrapperFunctions.click(loc_checkbox_Leave), false);
        objUtilities.logReporter("Data Group Permissions:: ", objWrapperFunctions.click(loc_checkbox_goals), false);
    }

    public void clickOnButton() {
        objUtilities.logReporter("Click Type:: ", objWrapperFunctions.click(loc_btn_submit), false);
    }

    public void clickOnButton(Locator ele) {
        objUtilities.logReporter("Click Type:: ", objWrapperFunctions.click(ele), false);
    }

    public void clickOnMenu() {
        objUtilities.logReporter("VERIFY - Clicked on Left menu HR Administration:: ",objWrapperFunctions.click(loc_leftmenu_HRAdministration), false);
    }

    // Users menu
    public void clickOnFilterIcon() {
        objUtilities.logReporter("VERIFY - Filter record by clicking filter Icon:: ",objWrapperFunctions.click(loc_filterIcon), false);
    }

    public void enterEmployeenameInFilterUsers(String empname) {
        objUtilities.logReporter("Filter :: :: ",objWrapperFunctions.getText(loc_title_FilterPopup, "text").equalsIgnoreCase("Filter Users"), false);
        objUtilities.logReporter("Filter :: Enter employee name:: ",objWrapperFunctions.setText(loc_txtbox_employeename, empname), false);
        objUtilities.logReporter("VERIFY - Click on submit:: ", objWrapperFunctions.click(loc_btn_filter_submit),false);
    }

    //pooja code ended    
    public void chklogger() {
        objUtilities.logReporter("hi here", true, false);
    }

}