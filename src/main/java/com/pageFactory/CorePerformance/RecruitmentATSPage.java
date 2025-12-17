package com.pageFactory.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;

public class RecruitmentATSPage {
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    boolean bResult = false;

    private Locator recruitmentTab;
    private Locator addCandidate;
    private Locator firstName;
    private Locator lastName;
    private Locator email;
    private Locator save;
    private Locator stage;
    private Locator toastMsg;
    private Locator uploadFile;

    String path = "C:\\Users\\ctaware\\Downloads\\UAN Allotment.pdf";

    public RecruitmentATSPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = new WrapperFunctions(objPojo);

        recruitmentTab = objPojo.getPage().locator("(//li//span[text()='Recruitment (ATS)'])[1]");
        addCandidate = objPojo.getPage().locator("//button[@toolTip='Add Candidate']");
        firstName = objPojo.getPage().locator("//input[@id='addCandidateForm_firstName']");
        lastName = objPojo.getPage().locator("//input[@id='addCandidateForm_lastName']");
        email = objPojo.getPage().locator("//input[@id='addCandidateForm_email']");
        save = objPojo.getPage().locator("//div[text()='Save']");
        stage = objPojo.getPage().locator("(//div[@class='oxd-table-body']//div[@class='oxd-table-card --loaded oxd-row-highlight--success']//div[@role='cell'])[7]//div[@class='selected-content']");
        toastMsg = objPojo.getPage().locator("//p[text()='Successfully saved']");
        uploadFile = objPojo.getPage().locator("//div[text()='No file chosen']");
    }

    public void clickOnRecruitmentATS() {
        objWrapperFunctions.waitForElementPresence(recruitmentTab, "5");
        objUtilities.logReporter(
            "Clicked on 'Recruitment(ATS)' tab : clickOnRecruitmentATS()",
            objWrapperFunctions.click(recruitmentTab),
            false
        );
    }

    public void clickOnAddCandidate() {
        objWrapperFunctions.waitForElementPresence(addCandidate, "5");
        objUtilities.logReporter(
            "Clicked on 'Add Candidate' button : clickOnAddCandidate()",
            objWrapperFunctions.click(addCandidate),
            false
        );
    }

    public void enterCandidateDetails(String fName, String lName, String emailID) {
        objWrapperFunctions.waitForElementPresence(firstName, "5");
        objUtilities.logReporter(
            "Enter First Name : enterCandidateDetails()",
            objWrapperFunctions.setText(firstName, fName),
            false
        );

        objWrapperFunctions.waitForElementPresence(lastName, "5");
        objUtilities.logReporter(
            "Enter Last Name : enterCandidateDetails()",
            objWrapperFunctions.setText(lastName, lName),
            false
        );

        objWrapperFunctions.waitForElementPresence(email, "5");
        objUtilities.logReporter(
            "Enter Email ID : enterCandidateDetails()",
            objWrapperFunctions.setText(email, emailID),
            false
        );
    }

    public void clickOnSave() {
        objWrapperFunctions.waitForElementPresence(save, "5");
        objUtilities.logReporter(
            "Clicked on 'Add Candidate' button : clickOnSave()",
            objWrapperFunctions.click(save),
            false
        );
    }

    public void checkStageStatus(String expectedStatus) {
        objWrapperFunctions.waitForElementPresence(stage, "5");
        String text = objWrapperFunctions.getText(stage, "text");
        objUtilities.logReporter(
            "Stage status of newly added candidate should be 'Application Received' : checkStageStatus()",
            text.equals(expectedStatus),
            false
        );
    }

    public void verifyToastMsg() {
        objWrapperFunctions.waitForElementPresence(toastMsg, "5");
        objUtilities.logReporter(
            "Verify 'Successfully saved' toast message displayed : verifyToastMsg()",
            objWrapperFunctions.checkElementDisplyed(toastMsg),
            false
        );
    }
}