package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EmployeeManagementPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    private Page page;
    boolean bResult = false;

    String url = "https://kajal6-trials718.orangehrmlive.com/client/#/pim/employees";
    private String empMngmntTabSelector = "(//li//span[text()='Employee Management'])[1]";
    private String addEmpSelector = "//i[text()='add']";
    private String floatingAddMsgSelector = "//div[@class='fixed-action-btn floating-add-btn tooltipped']";
    private String empDetailsWindowSelector = "//label[text()='Employee Full Name']";
    private String fnameSelector = "//input[@placeholder='First Name']";
    private String lnameSelector = "//input[@placeholder='Last Name']";
    private String mnameSelector = "//input[@placeholder='Middle Name']";
    private String autoGenerateIdToggleSelector = "//input[contains(@class, 'custom-control-input') and contains(@id, 'autoGenerateEmployeeId')]";

    private Locator autoGenerateIdToggle;

    public EmployeeManagementPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = new WrapperFunctions(objPojo);
        objConfig = objPojo.getObjConfig();
        page = objPojo.getPage();
        autoGenerateIdToggle = page.locator(autoGenerateIdToggleSelector);
    }

    public void clickOnEmployeeMngmntElement() {
        objWrapperFunctions.click(page.locator(empMngmntTabSelector));
    }

    public void clickOnAddEmpIcon() {
        objWrapperFunctions.waitForElementToBeClickable(addEmpSelector);
        objWrapperFunctions.click(page.locator(addEmpSelector));
        objWrapperFunctions.waitForElementPresence(empDetailsWindowSelector);
    }

    public void checkFloatingAddMsgDisplay() {
        objWrapperFunctions.checkElementDisplyed(page.locator(floatingAddMsgSelector));
    }

    public void enterEmployeeDetails(String firstName, String middleName, String lastName) {
        objWrapperFunctions.setText(page.locator(fnameSelector), firstName);
        objWrapperFunctions.setText(page.locator(mnameSelector), middleName);
        objWrapperFunctions.setText(page.locator(lnameSelector), lastName);
    }

    public boolean checkAutoGenerateIDToggleIsSelected() {
        String autoId = autoGenerateIdToggle.getAttribute("class");
        return autoId != null && autoId.contains("ng-not-empty");
    }
}