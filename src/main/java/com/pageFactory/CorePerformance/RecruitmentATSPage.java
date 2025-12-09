package com.pageFactory.CorePerformance;

import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RecruitmentATSPage {

    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Properties objConfig;
    private Page page;
    private boolean bResult = false;

    public RecruitmentATSPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        page = objPojo.getPage();
    }

    private Locator recruitmentTab() {
        return page.locator("(//li//span[text()='Recruitment (ATS)'])[1]");
    }
    private Locator addCandidate() {
        return page.locator("//button[@toolTip='Add Candidate']");
    }
    private Locator firstName() {
        return page.locator("//input[@id='addCandidateForm_firstName']");
    }
    private Locator lastName() {
        return page.locator("//input[@id='addCandidateForm_lastName']");
    }
    private Locator email() {
        return page.locator("//input[@id='addCandidateForm_email']");
    }
    private Locator save() {
        return page.locator("//div[text()='Save']");
    }
    private Locator stage() {
        return page.locator("(//div[@class='oxd-table-body']//div[@class='oxd-table-card --loaded oxd-row-highlight--success']//div[@role='cell'])[7]//div[@class='selected-content']");
    }

    public void clickOnRecruitmentATS() {
        objWrapperFunctions.click(recruitmentTab());
    }

    public void clickOnAddCandidate() {
        objWrapperFunctions.click(addCandidate());
    }

    public void enterCandidateDetails(String fName, String lName, String emailID) {
        objWrapperFunctions.setText(firstName(), fName);
        objWrapperFunctions.setText(lastName(), lName);
        objWrapperFunctions.setText(email(), emailID);
    }

    public void clickOnSave() {
        objWrapperFunctions.click(save());
    }

    public String checkStageStatus() {
        return objWrapperFunctions.getText(stage(), "text");
    }
}