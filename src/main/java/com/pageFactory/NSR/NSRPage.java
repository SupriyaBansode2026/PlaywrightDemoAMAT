package com.pageFactory.NSR;

import java.util.Properties;
import com.microsoft.playwright.Locator;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class NSRPage {
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Properties objConfig;
    private String browser;
    private Pojo objPojo;

    public NSRPage(Pojo objPojo) {
        this.objPojo = objPojo;
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        objConfig = objPojo.getObjConfig();
        browser = objPojo.getBrowser();
    }

    Locator commentsTabSystemCommentsError() { return objPojo.getPage().locator("//*[@id='LinkedNSRsGrid']/div"); }
    Locator CommentsTabReply() { return objPojo.getPage().locator("//table[@id='tblsystem']//span/a"); }
    Locator continueButton() { return objPojo.getPage().locator("//button[@id='btnContinue']"); }
    Locator createHyperLink() { return objPojo.getPage().locator("//span[text()='Create']"); }
    Locator SWPartFielddNsr() { return objPojo.getPage().locator("//input[@id='NSRInformation_NSRChangeRequestInformation_SWPartNumber']"); }
    Locator NewNSR() { return objPojo.getPage().locator("//a[contains(text(),'New NSR')]"); }
    Locator clickOnButton() { return objPojo.getPage().locator("//div[@id='nonsco']"); }
    Locator approveYesButton() { return objPojo.getPage().locator("//div[@role='dialog' and @aria-describedby='dissapprovedialog']//span[text()='Yes']"); }
    Locator NoSPSResolveCheckBox() { return objPojo.getPage().locator("//input[@id='noSpsresolve']"); }
    Locator AlertNoBtn() { return objPojo.getPage().locator("//span[@class='ui-button-text'][contains(text(),'No')]"); }
    Locator AlertYesBtn() { return objPojo.getPage().locator("//span[@class='ui-button-text'][contains(text(),'Yes')]"); }
    Locator approveNoButton() { return objPojo.getPage().locator("//div[@role='dialog' and @aria-describedby='dissapprovedialog']//span[text()='No']"); }
    Locator quickTextField() { return objPojo.getPage().locator("//input[@id='txtEC_Number']"); }
    Locator quickSearchIcon() { return objPojo.getPage().locator("//div[@id='searchScopeSelect']"); }
    Locator searchEditBox() { return objPojo.getPage().locator("//input[(@id='txtEC_Number')]"); }
    Locator primaryDriverDropDown() { return objPojo.getPage().locator("//select[contains(@id,'PrimaryDriver')]"); }
    Locator quoteNumberEditBox() { return objPojo.getPage().locator("//input[contains(@id,'QuoteNumber')]"); }
    Locator saveButton() { return objPojo.getPage().locator("//button[@id='btnSave']"); }
    Locator NSRFormValidationConfirmButton() { return objPojo.getPage().locator("//a[@id='btnuploadparts']"); }
    Locator NSROpenAssociateButton() { return objPojo.getPage().locator("//input[contains(@id,'openAssociateModal')]"); }
    Locator businessDropDown() { return objPojo.getPage().locator("//select[contains(@id,'Business')]"); }
    Locator productDropDown() { return objPojo.getPage().locator("//select[contains(@id,'Kpu')]"); }
    Locator loadButton() { return objPojo.getPage().locator("//a[contains(@id,'btnSearch')]"); }
    Locator addAssociatedAddAndCloseButton() { return objPojo.getPage().locator("//span[contains(text(),'Add & Close')]"); }
    Locator addAssociateCancelButton() { return objPojo.getPage().locator("//span[contains(text(),'Cancel)]"); }
    Locator firstInProgressCheckBox() { return objPojo.getPage().locator("//td[contains(text(),'In process')]//preceding-sibling::td/input[@type='checkbox']"); }
    Locator firstInProgressProjectTitle() { return objPojo.getPage().locator("//td[contains(text(),'In process')]//preceding-sibling::td[2]"); }
    Locator Repeatrequest() { return objPojo.getPage().locator("//label[contains(text(),'Repeat Request')]"); }
    Locator RepeatRequestDropDown() { return objPojo.getPage().locator("//select[contains(@id,'RepeatRequest')]"); }
    Locator RepeatRequestNumber() { return objPojo.getPage().locator("//input[contains(@id,'NSRInformation_NSRChangeRequestInformation_RepeatRequestNumber')]"); }
    Locator addCommentsPopup() { return objPojo.getPage().locator("//div[contains(@style,'display: block')]//span[contains(text(),'Add comments')]"); }
    Locator commentsEditBox() { return objPojo.getPage().locator("//textarea[(@id='txtComments')]"); }
    Locator LinkedNSRs() { return objPojo.getPage().locator("//a[contains(text(),'Linked NSRs')]"); }
    Locator TitleEditBox() { return objPojo.getPage().locator("//input[(@id='NSRInformation_NSRChangeRequestInformation_Title')]"); }
    Locator DescriptionEditBox() { return objPojo.getPage().locator("//textarea[(@id='NSRInformation_NSRChangeRequestInformation_Description')]"); }
    Locator selectBusinessUnitEditBox() { return objPojo.getPage().locator("//div/label[contains(text(),'BU')]//following-sibling::div/span//span[contains(text(),'Select')]"); }
    Locator selectTechnologyEditBox() { return objPojo.getPage().locator("//div/label[contains(text(),'Technology')]//following-sibling::div/span//span[contains(text(),'Select')]"); }
    Locator selectPlatformEditBox() { return objPojo.getPage().locator("//div/label[contains(text(),'Platform')]//following-sibling::div/span//span[contains(text(),'Select')]"); }
    Locator SoNumberEditBox() { return objPojo.getPage().locator("//input[(@id='NSRInformation_NSROrderInformation_SalesOrderNumber')]"); }
    Locator SoLineItemNumberEditBox() { return objPojo.getPage().locator("//input[(@id='NSRInformation_NSROrderInformation_SalesOrderLineItemNumber')]"); }
    Locator SaveNSRBtn() { return objPojo.getPage().locator("//button[@id='btnSave'][@type='button']"); }
    Locator SubmitNSRBtn() { return objPojo.getPage().locator("//input[@type='button'][@id='btnMovetoNext']"); }
    Locator filterIcon() { return objPojo.getPage().locator("//th[@data-title='Project/Folder Owner']/a/span"); }
    Locator textBox() { return objPojo.getPage().locator("//input[@class='k-input-inner' and @data-bind='value:filters[0].value']"); }
    Locator filterBtn() { return objPojo.getPage().locator("//div[@class='k-filter-menu-container']//button[@title='Filter']"); }
    Locator projectId() { return objPojo.getPage().locator("//div[@class='box-body']//table//tbody//tr[1]/td[1]"); }
    Locator selectCategoryEditBox() { return objPojo.getPage().locator("//div/label[contains(text(),'Category')]//following-sibling::div/span[contains(@aria-controls,'NSRInformation_NSRChangeRequestInformation_Category_listbox')]"); }
    Locator selectSubCategoryEditBox() { return objPojo.getPage().locator("//div/label[contains(text(),'Sub Category')]//following-sibling::div//span[@aria-controls='NSRInformation_NSRChangeRequestInformation_SubCategory_listbox']"); }
    Locator ApproveforEngineeringBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Approve and Move to Engg']"); }
    Locator ApprovePriceDirectBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Approve – Price Direct']"); }
    Locator RequestTriadApprovalBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Request Triad Approval']"); }
    Locator RequestInformationBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Request Information']"); }
    Locator ReturnNsrToInitiatorBtn() { return objPojo.getPage().locator("//*[contains(text(),'Return NSR to Initiator')]"); }
    Locator ReturnToGroupInboxBtn() { return objPojo.getPage().locator("//button[contains(text(),'Return to Group Inbox')]"); }
    Locator AssignmentsTab() { return objPojo.getPage().locator("//*[@id='Assign']"); }
    Locator approversButton() { return objPojo.getPage().locator("//h4[@class='panel-title']//a[contains(text(),'Approvers')]"); }
    Locator reassignBtnForApprovers() { return objPojo.getPage().locator("//input[@value='Reassign' and @data-id='1']"); }
    Locator userNameTextFieldOnReassignment() { return objPojo.getPage().locator("//select[@id='NewUser_Name']"); }
    Locator okButtonForReassignment() { return objPojo.getPage().locator("//*[@id='btnOk']"); }
    Locator reassignButtonForReassignment() { return objPojo.getPage().locator("//input[@class='btn btn-primary'][@type='submit']"); }
    Locator cancelBtn() { return objPojo.getPage().locator("//span[@id='btnCancel']"); }
    Locator PreliminaryDriveBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Preliminary – Drive long lead items']"); }
    Locator IntermediateBOMupdatesBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Intermediate - BOM / BOM updates']"); }
    Locator FinalBOMReadyBtn() { return objPojo.getPage().locator("//input[@name='responseBtn'][@value='Final - BOM ready']"); }
    Locator ResponseEnggRole() { return objPojo.getPage().locator("//div[@id='lblRole'][contains(text(),'Response Engineer')]"); }
    Locator reviewbyPlmRole() { return objPojo.getPage().locator("//div[@id='lblOpenAction'][contains(text(),'Review by Product Line Manager')]"); }
    Locator PrimaryPlmRole() { return objPojo.getPage().locator("//div[@id='lblRole'][contains(text(),'Primary Product Line Manager')]"); }
    Locator SearchIcon() { return objPojo.getPage().locator("//i[@class='fa fa-search']"); }
    Locator inpResponseDescription() { return objPojo.getPage().locator("//textarea[@id='NSRInformation_NSRChangeRequestInformation_ResponseDescription']"); }
    Locator inpResponsePart() { return objPojo.getPage().locator("//input[contains(@id,'ResponsePartNumber')]"); }
    Locator drpBomImpact() { return objPojo.getPage().locator("//select[contains(@id,'BOMImpact')]"); }
    Locator inpPCRNumber() { return objPojo.getPage().locator("//input[contains(@id,'RelatedPCRNumber')]"); }
    Locator inpEstimatedPrice() { return objPojo.getPage().locator("//input[@id='EstimatedPriceCESPricing']"); }
    Locator selectSubCategoryEditBoxDisable() { return objPojo.getPage().locator("//span[contains(@aria-controls,'SubCategory_listbox') and @aria-disabled='true']"); }
    Locator btnCreateTask() { return objPojo.getPage().locator("//div[@id='taskgrid']/div/button[contains(@class,'k-grid-add')]"); }
    Locator AssociatedTaskTable() { return objPojo.getPage().locator("//div[@id='taskgrid']//table"); }
    Locator CRNAnalyticsScrollableTable() { return objPojo.getPage().locator("//div[@id='AnalyticsData']//div[contains(@class,'auto-scrollable')]//table"); }
    Locator taskTitle() { return objPojo.getPage().locator("//input[@id='taskTitleText']"); }
    Locator taskDescription() { return objPojo.getPage().locator("//textarea[@id='taskDescText']"); }
    Locator taskOwnerListBox() { return objPojo.getPage().locator("//select[@id='TaskOwner']"); }
    Locator taskStatusListBox() { return objPojo.getPage().locator("//span[@aria-controls='TaskCurrentStatus_listbox']"); }
    Locator taskDueDate() { return objPojo.getPage().locator("//input[@id='DueDate']"); }
    Locator taskEndDate() { return objPojo.getPage().locator("//input[@id='TaskendDate']"); }
    Locator taskComment() { return objPojo.getPage().locator("//textarea[@id='taskCmtText']"); }
    Locator taskUpdateButton() { return objPojo.getPage().locator("//span[contains(text(),'Update')]"); }
    Locator taskCancelButton() { return objPojo.getPage().locator("//span[text()='Add/Edit Task']/ancestor::div[@class='k-widget k-window']//a[text()='Cancel']"); }
    Locator taskCurrentStatus() { return objPojo.getPage().locator("//span[@aria-controls='TaskCurrentStatus_listbox']/span/span"); }
    Locator lblTitleAlertMessage() { return objPojo.getPage().locator("//div[@id='Title_validationMessage']"); }
    Locator lblDescriptionAlertMessage() { return objPojo.getPage().locator("//div[@id='Description_validationMessage']"); }
    Locator lblTaskOwnerAlertMessage() { return objPojo.getPage().locator("//div[@id='TaskOwner_validationMessage']"); }
    Locator lblDueDateAlertMessage() { return objPojo.getPage().locator("//div[@id='DueDate_validationMessage']"); }
    Locator btnCRNExportToexcel() { return objPojo.getPage().locator("//button[@id='btnExportToExcelCorePlus']"); }
    Locator lblEndDateAlertMessage() { return objPojo.getPage().locator("//div[@id='TaskendDate_validationMessage']"); }
    Locator MilestonesTabAutomaticDatesSection() { return objPojo.getPage().locator("//div[contains(text(),'Automatic Dates - Milestones')]"); }
    Locator MilestonesTabProcessMetricsSection() { return objPojo.getPage().locator("//div[contains(text(),'Process Metrics')]"); }
    Locator MilestonesTabErrorStateCancellationExpiration() { return objPojo.getPage().locator("//span[contains(text(),'Error state')]"); }
    Locator MilestonesTabCompletedAction() { return objPojo.getPage().locator("//span[contains(text(),'Completed Action')]"); }
    Locator MilestonesTabCurrentAction() { return objPojo.getPage().locator("//span[contains(text(),'Current Action')]"); }
    Locator MilestonesTabFutureAction() { return objPojo.getPage().locator("//span[contains(text(),'Future Action')]"); }
    Locator MilestonesTabCreatedCycle() { return objPojo.getPage().locator("//div[contains(text(),'Create Cycle')]"); }
    Locator MilestonesTabProcessMetricsSTA() { return objPojo.getPage().locator("//div[contains(text(),'STA')]"); }
    Locator MilestonesTabProcessMetricsSTD() { return objPojo.getPage().locator("//div[contains(text(),'STD')]"); }
    Locator MilestonesTabProcessMetricsCTG() { return objPojo.getPage().locator("//div[contains(text(),'CTG')]"); }
    Locator MilestonesTabProcessMetricsSTANote() { return objPojo.getPage().locator("//b[contains(text(),'Submit to Approve')]/parent::div"); }
    Locator MilestonesTabProcessMetricsSTDNote() { return objPojo.getPage().locator("//b[contains(text(),'Submit to Done or Standard Cycle')]/parent::div"); }
    Locator MilestonesTabProcessMetricsCTGNote() { return objPojo.getPage().locator("//b[contains(text(),'Cradle to Grave')]/parent::div"); }
    Locator changeBUProductsModulesButton() { return objPojo.getPage().locator("//div[@id='divaddBUItems']//a[contains(text(),'Change BU-Products/Modules')]"); }
    Locator rightArrow() { return objPojo.getPage().locator("//a[@class='text-green' and @onclick='fnGetBUProductsList(1)']"); }
    Locator saveAndCloseButtonOnChangeBUPopup() { return objPojo.getPage().locator("//span[@class='btn btn-primary' and text()='Save and Close']"); }
    Locator changeRequestTabPCR() { return objPojo.getPage().locator("//li[@id='PCRChangeOrder']//a[text()='Change Request']"); }
    Locator titleTextFieldFillOutPCR() { return objPojo.getPage().locator("//*[@id='strPCRTitle']"); }
    Locator projectReasonForFillOutPCRForm() { return objPojo.getPage().locator("//*[@id='iPCRReasonId']"); }
    Locator problemStatementForFillOutPCRForm() { return objPojo.getPage().locator("//*[@id='strPCRProbStat']"); }
    Locator newRightArrow() { return objPojo.getPage().locator("//a[@class='text-green' and @onclick='fnGetBUProductsList()']"); }
    Locator ReturnNsrPlmbtn() { return objPojo.getPage().locator("//a[@id='btnReturnToPLM']"); }
    Locator btnPullBackNSR() { return objPojo.getPage().locator("//a[@id='btnPullBackNSR']"); }
    Locator AssociatedCRNTable() { return objPojo.getPage().locator("//div[@id='CRNgrid']/table"); }
    Locator approveAllRadioButton() { return objPojo.getPage().locator("//div[@class='modal-content']//div[@id='divApprovalModal']//input[@type='radio' and @value='Approve']"); }
    Locator rejectAllRadioButton() { return objPojo.getPage().locator("//div[@class='modal-content']//div[@id='divApprovalModal']//input[@type='radio' and @value='Reject']"); }
    Locator ItemAnalyticsTable() { return objPojo.getPage().locator("//*[@id='AnalyticsData']/div[@class='k-grid-header']"); }
    Locator CRNAnalyticsTable2() { return objPojo.getPage().locator("//*[@id='AnalyticsData']/div[@class='k-grid-content']"); }
    Locator CRNAnalyticsTable() { return objPojo.getPage().locator("//*[@id='AnalyticsData']"); }
    Locator fetchStatusFirstCheckbox() { return objPojo.getPage().locator("//td[contains(text(),'In process')]"); }
    Locator addAssociatedAddButton() { return objPojo.getPage().locator("//span[contains(text(),'Add')]"); }
    Locator addAssociatedCancelButton() { return objPojo.getPage().locator("//span[contains(text(),'Cancel') and @class='btn btn-social btn btn-danger']"); }
    Locator NSRCRNLink() { return objPojo.getPage().locator("(//*[@class='popover-content note-children-container']/span)[1]/a[1]"); }
    Locator NSRNoOfFeaturesActivated() { return objPojo.getPage().locator("//input[@id='NSRInformation_NSRChangeRequestInformation_FeaturesActivated']"); }
    Locator NSRNoOfToolsUpdated() { return objPojo.getPage().locator("//input[@id='NSRInformation_NSRChangeRequestInformation_ToolsUpdated']"); }

    public void clickOnSubmitNSRBtn() {
        Locator submitBtn = SubmitNSRBtn();
        objUtilities.logReporter("waiting for element to be displayed ",
            objWrapperFunctions.waitForElementPresence("//input[@type='button'][@id='btnMovetoNext']"), false);

        boolean bresult = objWrapperFunctions.waitForElementToBeClickable("//input[@type='button'][@id='btnMovetoNext']");
        if (bresult) {
            objUtilities.logReporter(
                "Sucessfully clicked on Submit NSR btn:clickOnSubmitNSRBtn()",
                objWrapperFunctions.click("//input[@type='button'][@id='btnMovetoNext']"), false);
        } else {
            objUtilities.logReporter(
                "Sucessfully verified 'Project number' is required to submit NSR:clickOnSubmitNSRBtn()",
                objWrapperFunctions.checkElementDisplyed("//*[@id='ibtnNoProject'][@data-content='Primary Product Line Manager role is defined by the Core+ Project, please enter the Core+ project to proceed.']"), false);
        }
    }

    public void expandNSRSection(String sSectionName) {
        String sectionXpath = "//div[@id='NsrOderDiv']//a[contains(text(), '" + sSectionName + "')]";
        objWrapperFunctions.scrollTillElement(sectionXpath);

        String expandedAttr = objWrapperFunctions.getAttributeValue(sectionXpath, "aria-expanded");
        if (expandedAttr == null || "false".equals(expandedAttr)) {
            objUtilities.logReporter("Expand NSR section :expandNSRSection()",
                objWrapperFunctions.javaScriptClick(objWrapperFunctions.getWebElement(sectionXpath)), false);
        } else {
            objUtilities.logReporter("Already expanded NSR section:expandNSRSection()" + sSectionName, true, false);
        }
    }

    public void clickOnAddToExistingProject() {
        String btnXpath = "//input[@value='Add to Existing Project']";
        objWrapperFunctions.scrollTillElement(btnXpath);
        objUtilities.logReporter(
            "Click on Add to existing project button on NSR details page : clickOnAddToExistingProject()",
            objWrapperFunctions.click(btnXpath), false);
    }

    public void verifyAddAssociatedProjectsPopUpDisplayed() {
        String popUpXpath = "//*[@id='associatedProjectModal']//h4[text()='Add Associated Projects']";
        objUtilities.logReporter(
            "Verify Add Associated Projects pop up displayed : verifyAddAssociatedProjectsPopUpDisplayed()",
            objWrapperFunctions.checkElementDisplyed(popUpXpath), false);
    }

    public void selectDDValueInAddAssociatedProjectsPopUp(String sDDLabel, String sDDValue) {
        String cmbXpath = "//h4[text()='Add Associated Projects']/ancestor::div[@class='modal-content']//label[text='" + sDDLabel + "']/following-sibling::div/select";
        if (!objWrapperFunctions.checkElementDisplyed(cmbXpath)) {
            cmbXpath = "//*[@id='addAssociatedProject']//label[text()='" + sDDLabel + "']/following-sibling::select";
        }
        if (!objWrapperFunctions.checkElementDisplyed(cmbXpath)) {
            cmbXpath = "//*[@id='associatedProjectModal']//div/label[contains(text(),'" + sDDLabel + "')]/following-sibling::select";
        }
        objUtilities.logReporter(
            "Select DD value " + sDDValue + " in Add Associated Projects pop up DD " + sDDLabel
            + ": selectDDValueInAddAssociatedProjectsPopUp()",
            objWrapperFunctions.selectDropDownOption(cmbXpath, sDDValue, "text"), false);
    }

    public void clickAddAssociatedProjectsPopUpButton(String sButtonName) {
        String btnXpath;
        if ("Load".equalsIgnoreCase(sButtonName)) {
            btnXpath = "//h4[text()='Add Associated Projects']/ancestor::div[@class='modal-content']//a[text()='Load']";
        } else {
            btnXpath = "//h4[text()='Add Associated Projects']/ancestor::div[@class='modal-content']//span[text='" + sButtonName + "']";
        }
        objUtilities.logReporter(
            "Click button " + sButtonName + " on Add Associated Projects pop up : clickAddAssociatedProjectsPopUpButton()",
            objWrapperFunctions.click(btnXpath), false);
    }

    public void selectCheckboxAgainstProjectTitleOnAddAssociatedProjects(String sProjectTitle) {
        String tblXpath = "//div[@id='filterdGridResult']";
        Locator tblLocator = objPojo.getPage().locator(tblXpath);
        int iColProjectTitle = objWrapperFunctions.getTableColumn(tblLocator, "Project Title");
        int iRow = objWrapperFunctions.getTableRow(tblLocator, iColProjectTitle, sProjectTitle);
        int iColCheckbox = objWrapperFunctions.getTableColumn(tblLocator, "Project ID");
        iColCheckbox = iColCheckbox - 1;
        Locator chkProject = objWrapperFunctions.getWebElementBasedOnActionInTableCell(tblLocator, iRow, iColCheckbox, "checkbox");
        objUtilities.logReporter(
            "Click on checkbox against project title " + sProjectTitle + " : selectCheckboxAgainstProjectTitleOnAddAssociatedProjects()",
            objWrapperFunctions.click(chkProject), false);
    }

    public void takeActionOnAddNewNSRPopUp(String sActionButton) {
        String btnActionXpath = "//h4[text() = 'Add new NSR']/ancestor::div[@class='modal-content']//a[text()='" + sActionButton + "']";
        objUtilities.logReporter("Click on Add new NSR pop up : takeActionOnAddNewNSRPopUp()",
            objWrapperFunctions.click(btnActionXpath), false);
        objUtilities.logReporter("waiting for element to be dispappeared ",
            objWrapperFunctions.waitTillElementGetDisAppeared(objWrapperFunctions.getWebElement(btnActionXpath)), false);
    }

    public void verifyAddNewNSRPopUpDisplayed() {
        String popUpXpath = "//h4[text() = 'Add new NSR']/ancestor::div[@class='modal-content']";
        objUtilities.logReporter("Verify Add New NSR pop up displayed : verifyAddNewNSRPopUpDisplayed()",
            objWrapperFunctions.checkElementDisplyed(popUpXpath), false);
    }

    public void verifyConfirmationMessageOnAddNewNSRPopUp(String sMessage) {
        String messageXpath = "//h4[text() = 'Add new NSR']/ancestor::div[@class='modal-content']//label[contains(text(),'" + sMessage + "')]";
        objUtilities.logReporter(
            "Verify confirmation message " + sMessage + " on Add New NSR pop up displayed : verifyConfirmationMessageOnAddNewNSRPopUp()",
            objWrapperFunctions.checkElementDisplyed(messageXpath), false);
    }

    public void enterValuesInNSRDetailsTextbox(String sTextboxLabel, String sTextboxValue) {
        String txtXpath = "//label[contains(text(),'" + sTextboxLabel + "')]/following-sibling::div/input";
        objWrapperFunctions.scrollTillElement(txtXpath);
        if (!objWrapperFunctions.checkElementDisplyed(txtXpath)) {
            txtXpath = "//label[text()='" + sTextboxLabel + "']/following-sibling::div/textarea";
            objWrapperFunctions.scrollTillElement(txtXpath);
        }
        objUtilities.logReporter(
            "Enter value " + sTextboxValue + " in textbox " + sTextboxLabel + " on NSR details page : enterValuesInNSRDetailsTextbox()",
            objWrapperFunctions.setText(txtXpath, sTextboxValue), false);
    }

    public void clickNSRDetailsHeaderButton(String sButtonName) {
        int iCounter = 0;
        String btnXpath = "//div[@id='workflowNV']//input[@value='" + sButtonName + "']";
        if (!objWrapperFunctions.checkElementDisplyed(btnXpath)) {
            btnXpath = "//div[@id='workflowNV']//a[contains(text(),'" + sButtonName + "')]";
        }
        do {
            if (objWrapperFunctions.checkElementDisplyed(btnXpath)) {
                objWrapperFunctions.waitForElementPresence(btnXpath);
                objUtilities.logReporter(
                    "Successfully Click on NSR details header button: " + sButtonName + " clickNSRDetailsHeaderButton()",
                    objWrapperFunctions.click(btnXpath), false);
                break;
            }
            iCounter++;
        } while (iCounter < 3);
        objUtilities.waitFor(15);
    }

    public void clickOnSave() {
        int iCounter = 0;
        String btnSaveXpath = "button#btnSave";
        objUtilities.logReporter("Click on Save button : clickOnSaveNSR()",
            objWrapperFunctions.scrollAndclick(btnSaveXpath), false);
        do {
            objUtilities.waitFor(5);
            String styleAttr = objWrapperFunctions.getAttributeValue(btnSaveXpath, "style");
            if (styleAttr == null || !styleAttr.contains("display: inline-block")) break;
            iCounter++;
        } while (iCounter < 30);
    }

    public void enterEstimatedCompletionPercent(String sEstCompletionPercent) {
        String txtXpath = "//label[contains(text(),'Estimated Completion %')]/following-sibling::div/input";
        objWrapperFunctions.waitForElementPresence(txtXpath);
        objUtilities.logReporter(
            "Enter estimated completion % value '" + sEstCompletionPercent + "' : enterEstimatedCompletionPercent()",
            objWrapperFunctions.setText(txtXpath, sEstCompletionPercent), false);
    }

    public void verifyNSRStatusOnNSRDetailsPage(String sExpStatus) {
        String lblXpath = "//div[@id='lblOpenAction' or @id='lblOpenAction' or @id='lblCRNFinalStatus' or @id='lblRole'][text()='" + sExpStatus + "']";
        objWrapperFunctions.scrollTillElement(lblXpath);
        objWrapperFunctions.waitForElementPresence(lblXpath);
        objUtilities.logReporter(
            "Verify NSR status " + sExpStatus + " on NSR details page : verifyNSRStatusOnNSRDetailsPage()",
            objWrapperFunctions.checkElementDisplyed(lblXpath), false);
    }

    public void setProjectIDInAddAssociatedProjectPopUp(String sProjectID) {
        int iCounter = 0;
        String projectDropdownClickXpath = "//ul[@id='projectNumber_listbox']//li/span[contains(text(),'" + sProjectID + "')]";
        String checkboxXpath = "//input[@type='checkbox']";
        String txtProjectNoXpath = "//label[text()='Project#']//parent::div//input[@id='projectNumber']";
        if (!objWrapperFunctions.checkElementDisplyed(txtProjectNoXpath)) {
            txtProjectNoXpath = "//label[text()='CorePlus Project#']//parent::div//input[@id='corePlusProjectNo']";
        }
        do {
            objUtilities.logReporter("Clear Project Text", objWrapperFunctions.clearText(txtProjectNoXpath), false);
            objUtilities.logReporter(
                "Set project ID as '" + sProjectID + "' : setProjectIDInAddAssociatedProjectPopUp()",
                objWrapperFunctions.setTextCharByChar(txtProjectNoXpath, sProjectID), false);
            objUtilities.waitFor(30);
            if (!objWrapperFunctions.checkElementDisplyed(projectDropdownClickXpath)) {
                projectDropdownClickXpath = "//ul[@id='corePlusProjectNo_listbox']//li[contains(text(),'" + sProjectID + "')]";
            }
            if (objConfig.getProperty("KendoUpgradeChanges").contains("true")) {
                projectDropdownClickXpath = "//ul[@id='projectNumber_listbox']//li/span[contains(text(),'" + sProjectID + "')]";
            }
            objUtilities.logReporter("Click to  project ID dropdown  : setProjectIDInAddAssociatedProjectPopUp()",
                objWrapperFunctions.click(projectDropdownClickXpath), false);
            objUtilities.waitFor(15);
            if (objWrapperFunctions.waitForElementPresence(checkboxXpath)) {
                break;
            }
            iCounter++;
        } while (iCounter < 5 || objWrapperFunctions.waitForElementPresence(checkboxXpath));
        if (!objWrapperFunctions.isCheckBoxSelected(checkboxXpath, true)) {
            objUtilities.logReporter("Click to  project Checkbox   : setProjectIDInAddAssociatedProjectPopUp()",
                objWrapperFunctions.click(checkboxXpath), false);
            if (!objWrapperFunctions.isCheckBoxSelected(checkboxXpath, true)) {
                objUtilities.logReporter("Click to  project Checkbox   : setProjectIDInAddAssociatedProjectPopUp()",
                    objWrapperFunctions.selectCheckBox(checkboxXpath, true), false);
            }
        }
    }

    public void selectDDInOrderInformationSection_ChromeAndIE(String sDDLabel, String sDDValue) {
        int iClickableCount = 0;
        boolean bClickable = false;

        if ("Chrome".equalsIgnoreCase(browser)) {
            String cmbXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div//span[@class='k-input-inner']";
            String attributeOrderInformationXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/span[@aria-controls='NSRInformation_NSROrderInformation_" + sDDLabel + "_listbox']";
            String attribute = objWrapperFunctions.getAttributeValue(attributeOrderInformationXpath, "aria-controls");
            String str = objWrapperFunctions.getText(cmbXpath, "text");
            if (!str.equals(sDDValue)) {
                do {
                    String drpOrderInformationXpath = "//ul[@id='" + attribute + "']//li/span[text()='" + sDDValue + "']";
                    objWrapperFunctions.waitFormobileElementToBeClickable(cmbXpath);
                    objUtilities.logReporter("click on order info", objWrapperFunctions.click(cmbXpath), false);
                    objUtilities.waitFor(1);
                    objWrapperFunctions.scrollTillElement(drpOrderInformationXpath);
                    bClickable = objWrapperFunctions.click(drpOrderInformationXpath);
                    if (bClickable) break;
                    iClickableCount++;
                } while (iClickableCount < 10);
                objUtilities.logReporter( "Select DD " + sDDLabel + " value " + sDDValue + " in order information section : selectDDInOrderInformationSection_ChromeAndIE()",
                    objWrapperFunctions.getText(cmbXpath, "text").contains(sDDValue), false);
            }
        } else if ("ie".equalsIgnoreCase(browser)) {
            String cmbOrderInformationXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div//span[@class='k-input']";
            objWrapperFunctions.sendKeys(cmbOrderInformationXpath, sDDValue);
            objUtilities.logReporter("Select DD " + sDDLabel + " value " + sDDValue + " in order information section : selectDDInOrderInformationSection_ChromeAndIE()",
                objWrapperFunctions.getText(cmbOrderInformationXpath, "text").contains(sDDValue), false);
        }
    }

    public void selectDDInRequestorSection_ChromeandIE(String sDDLabel, String sDDValue) {
        int iClickableCount = 0;
        boolean bClickable = false;

        if ("Chrome".equalsIgnoreCase(browser)) {
            String cmbRequestorSectionXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div//span[contains(@class,'k-input-value-text')]";
            if (objWrapperFunctions.checkElementDisplyed(cmbRequestorSectionXpath)) {
                String attributeRequestInformationXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/span[@aria-controls='NSRInformation_NSRChangeRequestInformation_" + sDDLabel.trim().replace(" ", "") + "_listbox']";
                if(!objWrapperFunctions.checkElementDisplyed(attributeRequestInformationXpath)) {
                    attributeRequestInformationXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/span[@aria-controls='" + sDDLabel.trim().replace(" ", "") + "_listbox']";
                }
                if(!objWrapperFunctions.checkElementDisplyed(attributeRequestInformationXpath)) {
                    attributeRequestInformationXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/span[@aria-controls='NSRInformation_NSRChangeRequestInformation_" + sDDLabel.trim().replace("/", "_") + "_listbox']";
                }
                String attribute = objWrapperFunctions.getAttributeValue(attributeRequestInformationXpath, "aria-controls");
                String str = objWrapperFunctions.getText(cmbRequestorSectionXpath, "text");
                if (!str.equals(sDDValue)) {
                    do {
                        String drpRequestInformationXpath = "//ul[@id='" + attribute + "']//li/span[contains(text(),'" + sDDValue + "')]";
                        objWrapperFunctions.waitFormobileElementToBeClickable(cmbRequestorSectionXpath);
                        objUtilities.logReporter("click " + sDDValue + " value from dropdown", objWrapperFunctions.click(cmbRequestorSectionXpath), false);
                        objUtilities.waitFor(1);
                        objWrapperFunctions.scrollTillElement(drpRequestInformationXpath);
                        bClickable = objWrapperFunctions.click(drpRequestInformationXpath);
                        if (bClickable) break;
                        iClickableCount++;
                    } while (iClickableCount < 10);
                    objUtilities.logReporter( "Select DD " + sDDLabel + " value " + sDDValue + " in Request information section : selectDDInRequestorSection_ChromeandIE()",
                        objWrapperFunctions.getText(cmbRequestorSectionXpath, "text").contains(sDDValue), false);
                }
            } else {
                cmbRequestorSectionXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/select";
                objUtilities.logReporter("Select DD value " + sDDValue + " in Requestor section DD " + sDDLabel
                    + " : selectDDInRequestorSection_ChromeandIE()",
                    objWrapperFunctions.selectDropDownOption(cmbRequestorSectionXpath, sDDValue, "text"), false);
            }
        } else if ("ie".equalsIgnoreCase(browser)) {
            String cmbRequestorSectionXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div/select";
            objWrapperFunctions.scrollAndclick(cmbRequestorSectionXpath);
            if (objWrapperFunctions.checkElementDisplyed(cmbRequestorSectionXpath)) {
                objUtilities.logReporter(
                    "Select DD value " + sDDValue + " in Requestor section DD " + sDDLabel
                    + " : selectDDInRequestorSection_ChromeandIE()",
                    objWrapperFunctions.selectDropDownOption(cmbRequestorSectionXpath, sDDValue, "text"), false);
            } else {
                cmbRequestorSectionXpath = "//label[contains(text(),'" + sDDLabel + "')]/following-sibling::div//span[@class='k-input']";
                objWrapperFunctions.scrollTillElement(cmbRequestorSectionXpath);
                objUtilities.logReporter("Set text " + sDDValue,
                    objWrapperFunctions.sendKeys(cmbRequestorSectionXpath, sDDValue), false);
                objUtilities.logReporter(
                    "Select DD " + sDDLabel + " value " + sDDValue
                    + " in Requestor section : selectDDInRequestorSection_ChromeandIE()",
                    objWrapperFunctions.getText(cmbRequestorSectionXpath, "text").contains(sDDValue), false);
            }
        }
    }

    public void verifySourceAndvCModel(String source) {
        String sourceXpath = "//*[@id='NSRInformation_NSROrderInformation_Source' and @value='" + source + "']";
        objUtilities.logReporter("successfully verified " + source + " is present :verifySourceAndvCModel()",
            objWrapperFunctions.checkElementDisplyed(sourceXpath), false);
        String VCModelXpath = "//*[@id='NSRInformation_NSROrderInformation_VCModel']";
        objUtilities.logReporter("Sucessfully verified vc model is non editable :verifySourceAndvCModel()",
            !objWrapperFunctions.setText(VCModelXpath, "abc"), false);
    }

    public void clickAddAndCloseOnAddAssociatedProjectPopUpforaddindAgainProject() {
        String btnAddCloseXpath = "//h4[text()='Add Associated Projects']/ancestor::div[@class='modal-content']//span[text()='Add & Close']";
        objUtilities.logReporter(
            "Click on button Add And Close on Add Associated Project pop up : clickAddAndCloseOnAddAssociatedProjectPopUp()",
            objWrapperFunctions.javaScriptClick(objWrapperFunctions.getWebElement(btnAddCloseXpath)), false);
    }

    public void ClickOnCRFLinkAttributeOnCRNPage() {
        String CRFAttributeXpath = "//label[contains(text(),'CRF #')]//parent::div//a[@class='ECAnchorStyle valign']";
        String CRFNumber = objWrapperFunctions.getText(CRFAttributeXpath, "text");
        objUtilities.logReporter("click to CRF " + CRFNumber + " on CRN ClickOnCRFLinkAttributeOnCRNPage()",
            objWrapperFunctions.click(CRFAttributeXpath), false);
    }

    public void verifyCRFAttributeONCRNPage() {
        String CRFAttributeXpath = "//label[contains(text(),'CRF #')]//parent::div//a[@class='ECAnchorStyle valign']";
        String CRFNumber = objWrapperFunctions.getText(CRFAttributeXpath, "text");
        objUtilities.logReporter(
            "Verify CRF as " + CRFNumber + " is got generated and is displayed on CRN page:verifyCRFAttributeONCRNPage()",
            objWrapperFunctions.checkElementDisplyed(CRFAttributeXpath), false);
    }

    public void verifyCurrentURL(String sURL) {
        String sCurrentURL = objUtilities.getCurrentURL();
        objUtilities.logReporter("Verify current URL of a Page : verifyCurrentURL()",
            sCurrentURL != null && sCurrentURL.equals(sURL) ? true : false, false);
    }

    public void verifyLinksAreHyperlinkAndClickOnLink(String Hyperlink) {
        String[] links = Hyperlink.split("~");
        for (String link : links) {
            String LinkXpath = "//*[@class='note-editable']//a[contains(text(),'" + link + "')]";
            objUtilities.logReporter(
                "Successfully verified " + link + "  number is hyperlinked on CRF page :  verifyCRNandNSRLinksAreHyperlink()",
                objWrapperFunctions.checkElementDisplyed(LinkXpath), false);
            objWrapperFunctions.click(LinkXpath);
            objUtilities.logReporter(
                "Successfully verified ' URL' Displayed after clicking on " + link + " number on CRF page:  verifyCRNandNSRLinksAreHyperlink()",
                objWrapperFunctions.checkElementDisplyed("//span[contains(@class,'popover-content')]/span/a[1]"), false);
        }
    }

    public void VerifyCRFAttributesPresentOnCRFPage(String sAttributes) {
        String CRFAttributeXpath = "//div[@id='CRFPageContents']//following::label[contains(text(),'" + sAttributes + "')]";
        String cRFAttribute = objWrapperFunctions.getText(CRFAttributeXpath, "text");
        objUtilities.logReporter(
            "Sucessfully verified attribute " + cRFAttribute + " is displayed on CRF Page : verifyAttributeOnCRFPage()",
            objWrapperFunctions.checkElementDisplyed(CRFAttributeXpath), false);
    }

    public String getCRFNumberONCRNPage() {
        String CRFAttributeXpath = "//label[contains(text(),'CRF #')]//parent::div//a[@class='ECAnchorStyle valign']";
        String CRFNumber = objWrapperFunctions.getText(CRFAttributeXpath, "text");
        objUtilities.logReporter("Verify CRF as " + CRFNumber + " is got generated and is displayed on CRN page : fetchCRFAttributeONCRNPage()", true, false);
        return CRFNumber;
    }

    public void SelectNsrRiskAssesmentDD(String sRisk) {
        String sNSRriskReasonXpath = "//select[contains(@id,'NSRRiskAssessmentId')]";
        objUtilities.logReporter("select " + sRisk + " from dropdown SelectNsrRiskAssesmentDD()",
            objWrapperFunctions.selectDropDownOption(sNSRriskReasonXpath, sRisk), false);
    }

    public void SelectNSRRiskAssessmentDropdwnwithoutNSRRiskReason(String sText) {
        String NSRRiskAsseementDropDownXpath = "//select[contains(@id,'NSRRiskAssessmentId')]";
        objUtilities.logReporter("select dropdown value " + sText + " for NSR Risk Assessment Dropdown",
            objWrapperFunctions.selectDropDownOption(NSRRiskAsseementDropDownXpath, sText), false);
    }

    public void selectNSRRiskReason(String sOption) {
        String sNSRRiskReasonDDXpath = "//select[@id='NSRInformation_NSRChangeRequestInformation_NSRRiskReasonId']";
        objUtilities.logReporter(
            "Sucessfully selected " + sOption + " drop down value for NSR Risk Reason : selectNSRRiskAssessment()",
            objWrapperFunctions.selectDropDownOption(sNSRRiskReasonDDXpath, sOption), false);
    }

    public void handleAndAcceptAlert(String sMesg) {
        objUtilities.logReporter("Successfully Handled Alert : handleAndAcceptAlert()",
            objWrapperFunctions.ValidateAndHandleAlert(sMesg), false);
    }

    public void SelectFinalBOMLateReason(String sText) {
        String FinalBOMLateReasonXpath = "//select[contains(@id,'NSRInformation_NSRChangeRequestInformation_FinalBOMLateReason')]";
        objUtilities.logReporter("select dropdown value " + sText + " for Final BOM Late Reason Dropdown",
            objWrapperFunctions.selectDropDownOption(FinalBOMLateReasonXpath, sText), false);
    }
}