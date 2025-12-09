package com.pageFactory.CorePerformance;

import java.util.List;
import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;

public class OrangehrmPage {

    private OrangehrmPage objCorePerformancePage;
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    boolean bResult = false;

    // Playwright Locators
    private Locator loginpage;
    private Locator logo;
    private Locator uname;
    private Locator paswd;
    private Locator login;
    private Locator AnnouncementDD;
    private Locator DDoptionDocuments;
    private Locator views;

    public OrangehrmPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        loginpage = objPojo.getPage().locator("//div[@class=\"login-form shadow\"]");
        logo = objPojo.getPage().locator("//div[@class='orangehrm-logo']");
        uname = objPojo.getPage().locator("//input[@placeholder=\"Username\"]");
        paswd = objPojo.getPage().locator("//input[@placeholder=\"Password\"]");
        login = objPojo.getPage().locator("//button[text()=\"Login\"]");
        AnnouncementDD = objPojo.getPage().locator("//a[text()='Announcements ']"); 
        DDoptionDocuments = objPojo.getPage().locator("//a[contains(text(),'Documents')]");
        views = objPojo.getPage().locator("//div[@class=\"login-form shadow\"]");
    }

    public boolean isLoginPageLogoDisplay() {
        return objWrapperFunctions.checkElementDisplyed(logo);
    }

    public String getPageUrl() {
        return objPojo.getPage().url();
    }

    public void enterUsername(String user) {
        objWrapperFunctions.setText(uname, user);
    }

    public void enterPassword(String pswd) {
        objWrapperFunctions.setText(paswd, pswd);
    }

    public void clickOnLoginButton() {
        objWrapperFunctions.click(login);
    }

    public boolean clickonAnnounceOption() {
        return objWrapperFunctions.clickWithoutScroll("//a[text()='Announcements ']");
    }

    public void clickOnDocuments() {
        objWrapperFunctions.click(DDoptionDocuments);
    }

    // Add any required dropdown/text methods here as needed per project design.

}