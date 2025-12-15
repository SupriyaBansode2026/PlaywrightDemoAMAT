package com.pageFactory.CorePerformance;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RecruitmentATSPage {
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Pojo objPojo;
    private Page page;
    boolean bResult = false;

    public RecruitmentATSPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        page = objPojo.getPage();
    }

    private String recruitmentTab = "(//li//span[text()='Recruitment (ATS)'])[1]";
    private String addCandidate = "//button[@toolTip='Add Candidate']";
    private String firstName = "//input[@id='addCandidateForm_firstName']";
    private String lastName = "//input[@id='addCandidateForm_lastName']";
    private String email = "//input[@id='addCandidateForm_email']";
    private String save = "//div[text()='Save']";
    private String stage = "(//div[@class='oxd-table-body']//div[@class='oxd-table-card --loaded oxd-row-highlight--success']//div[@role='cell'])[7]//div[@class='selected-content']";
    private String toastMsg = "//p[text()='Successfully saved']";
    private String uploadFile = "//div[text()='No file chosen']";

    String path = "C:\\Users\\ctaware\\Downloads\\UAN Allotment.pdf";

    public void clickOnRecruitmentATS() {
        objUtilities.logReporter("Clicked on 'Recruitment(ATS)' tab : clickOnRecruitmentATS()",
            objWrapperFunctions.click(recruitmentTab), false);
    }

    public void clickOnAddCandidate() {
        objUtilities.logReporter("Clicked on 'Add Candidate' button : clickOnAddCandidate()",
            objWrapperFunctions.click(addCandidate), false);
    }

    public void enterCandidateDetails(String fName, String lName, String emailID) {
        objUtilities.logReporter("Enter First Name : enterCandidateDetails()",
            objWrapperFunctions.setText(firstName, fName), false);
        objUtilities.logReporter("Enter Last Name : enterCandidateDetails()",
            objWrapperFunctions.setText(lastName, lName), false);
        objUtilities.logReporter("Enter Email ID : enterCandidateDetails()",
            objWrapperFunctions.setText(email, emailID), false);
    }

    public void clickOnSave(){
        objUtilities.logReporter("Clicked on 'Add Candidate' button : clickOnSave()",
            objWrapperFunctions.click(save), false);
    }

    public void checkStageStatus(String expectedStatus) {
        String text = objWrapperFunctions.getText(stage, "text");
        objUtilities.logReporter("Stage status of newly added candidate should be 'Application Received' : checkStageStatus()",
            text != null && text.equals(expectedStatus), false);
    }

    public void verifyToastMsg() {
        objUtilities.logReporter("Verify 'Successfully saved' toast message displayed : verifyToastMsg()",
            objWrapperFunctions.checkElementDisplyed(toastMsg), false);
    }
}