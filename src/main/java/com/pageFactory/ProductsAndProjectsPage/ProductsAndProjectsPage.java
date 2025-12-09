package com.pageFactory.ProductsAndProjectsPage;

import java.util.List;
import com.microsoft.playwright.*;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class ProductsAndProjectsPage {
    private WrapperFunctions objWrapperFunctions;
    private Utilities objUtilities;
    private Page page;

    public ProductsAndProjectsPage(Pojo objPojo) {
        objUtilities = objPojo.getObjUtilities();
        objWrapperFunctions = objPojo.getObjWrapperFunctions();
        page = objPojo.getPage();
    }

    private Locator exportBtnn() {
        return page.locator("//a[@id='btnExportToExcel']");
    }
    private Locator cmbBusinessUnit() {
        return page.locator("//div[text()='Business Unit']/following-sibling::div/select[@id='Business']");
    }
    private Locator cmbProduct() {
        return page.locator("//div[text()='Product']/following-sibling::div/select[@id='Product']/parent::div//button");
    }
    private Locator cmbKPU() {
        return page.locator("//div[text()='KPU (Legacy Core Change)']/following-sibling::div/select[@id='Kpu']");
    }
    private Locator SaveNSRBtn() {
        return page.locator("//button[@id='btnSave'][@type='button']");
    }
    private Locator cmbStatus() {
        return page.locator("//div[text()='Status']/following-sibling::div/select[@id='Status']");
    }
    private Locator cmbBUCustomer() {
        return page.locator("//div[text()='BU Customer']/following-sibling::div/select[@id='BUCustomer']");
    }
    private Locator btnExportToExcel() {
        return page.locator("a#btnExportToExcel");
    }
    private Locator btnLoad() {
        return page.locator("//a[contains(text(), 'Load')]");
    }
    private Locator btnCreateProject() {
        return page.locator("//input[@id='projectForm']");
    }
    private Locator tblFilterResult() {
        return page.locator("//div[@id='filterdGridResult']//table[@id='example-basic']");
    }
    private Locator txtFolderTitle() {
        return page.locator("//label[contains(text(), 'Folder Title')]/following-sibling::input[@id='folderTitle']");
    }
    private Locator txtGeneralUseStatement() {
        return page.locator("//label[contains(text(), 'General Use Statement')]/following-sibling::textarea[@id='userStatement']");
    }
    private Locator cmbReasonCode() {
        return page.locator("//label[contains(text(), 'Reason Code')]/following-sibling::select[@id='ReasonCode']");
    }
    private Locator btnCreateFolder() {
        return page.locator("//span[contains(text(), 'Create Folder') and @id='btn_CreateFolder']");
    }
    private Locator txtSubFolderTitle() {
        return page.locator("//label[contains(text(), 'Sub Folder Title')]/following-sibling::input[@id='folderTitle']");
    }
    private Locator btnCreateSubFolder() {
        return page.locator("//span[contains(text(), 'Create Sub Folder') and @id='btn_CreateFolder']");
    }
    private Locator btnUpdateSubFolder() {
        return page.locator("//span[contains(text(), 'Update Sub Folder') and @id='btn_CreateFolder']");
    }
    private Locator txtFieldProjectName() {
        return page.locator("//input[@id='projectName']");
    }
    private Locator txtFieldScope() {
        return page.locator("//textarea[@id='PScope']");
    }
    private Locator txtFieldProjectOwner() {
        return page.locator("//input[@id='Proj_Owner_Name']");
    }
    private Locator dropDownStatus() {
        return page.locator("//select[@id='pcrStatus']");
    }
    private Locator dropDownPriority() {
        return page.locator("//select[@id='ec_Priority']");
    }
    private Locator dropDownProjectComplexity() {
        return page.locator("//select[@id='projectComplexity']");
    }
    private Locator dropDownProjectType() {
        return page.locator("//select[@id='projectType']");
    }
    private Locator dropDownBusinessUnit() {
        return page.locator("//select[@id='BUnitList']");
    }
    private Locator dropDownProduct() {
        return page.locator("//select[@id='product']");
    }
    private Locator dropDownNeedDate() {
        return page.locator("//input[@id='needDate']");
    }
    private Locator dropDownStartDate() {
        return page.locator("//input[@id='startDate']");
    }
    private Locator dropDownEstimatedCompletionDate() {
        return page.locator("//input[@id='endDate']");
    }
    private Locator txtField1stSlotImpacted() {
        return page.locator("//input[@id='txtSlotImpacted']");
    }
    private Locator txtFieldProjectRank() {
        return page.locator("//input[@id='txtProjectRanking']");
    }
    private Locator dropDownEstDesignCompletioDate() {
        return page.locator("//input[@id='designCompletionDate']");
    }
    private Locator txtFieldbupriority() {
        return page.locator("//input[@id='txtBUPriority']");
    }
    private Locator btnSaveOnProjectDetails() {
        return page.locator("//span[@id='savepageicon']");
    }
    private Locator productandproject_productdropdown() {
        return page.locator("//span[@class='multiselect-selected-text']");
    }
    private Locator selectAllProducts() {
        return page.locator("//label[text()='Triad-Enabled-Products']//ancestor::div/ul//following-sibling::li/a/label/input[@type='checkbox']");
    }
    private Locator txtAreaNewTitle() {
        return page.locator("//*[@id='txtchange']");
    }
    private Locator btnSaveForRenameTitle() {
        return page.locator("//*[@id='btnSaveRename']");
    }
    private Locator historyTabOnProjectPage() {
        return page.locator("//*[@id='History']/a");
    }
    private Locator attributesBasedHistoryRadioBtn() {
        return page.locator("//input[@value='Attribute']");
    }
    private Locator expandProjectName() {
        return page.locator("//*[@id='FYIPeepPanel']//a[contains(text(),'Name')]");
    }
    private Locator assignmentsTabPCR() {
        return page.locator("//li[@id='Assign']//a[text()='Assignments']");
    }
    private Locator AuthorsandOwneinPCR() {
        return page.locator("//a[contains(text(),'Authors and Owners')]");
    }
    private Locator TriadParticipantsinPCR() {
        return page.locator("//a[contains(text(),'Triad Participants')]");
    }
    private Locator CCBinPCR() {
        return page.locator("//a[contains(text(),'Triad Participants')]");
    }
    private Locator ApproversinPCR() {
        return page.locator("//a[contains(text(),'Approvers')]");
    }
    private Locator CIBinPCR() {
        return page.locator("//a[contains(text(),'CIB')]");
    }
    private Locator costApproversinPCR() {
        return page.locator("//a[contains(text(),'Cost Approvers')]");
    }
    private Locator CIBOthersInPCR() {
        return page.locator("//a[contains(text(),'CIB Others')]");
    }
    private Locator FYINotificationsinPCR() {
        return page.locator("//a[contains(text(),'FYI Notifications')]");
    }
    private Locator authorsandOwnersButtonPCR() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'Authors and Owners')]");
    }
    private Locator triadParticipantsButtonPCR() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'Triad Participants')]");
    }
    private Locator fyiNotificationsButtonPCR() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'FYI Notifications')]");
    }
    private Locator UserInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'User')]");
    }
    private Locator RoleInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'Role')]");
    }
    private Locator DelegatedToInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'Delegated to')]");
    }
    private Locator OfferedToInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'Offered To')]");
    }
    private Locator SkipToInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'Skip')]");
    }
    private Locator ActionInPCR() {
        return page.locator("//*[@id='Tab_1']/thead/tr/th[contains(text(),'Action')]");
    }
    private Locator ReassignBtnInAuthorsandOwnersTab() {
        return page.locator("//div[@id='Approvers']//tr[2]//input[@value='Reassign']");
    }
    private Locator CancelButtonForReassignment() {
        return page.locator("//button[@class='close']");
    }
    private Locator commentsTabInPCR() {
        return page.locator("//li[@id='Index']//a[text()='Comments']");
    }
    private Locator systemComments() {
        return page.locator("//*[contains(text(),'System Comments')]");
    }
    private Locator otherActionComments() {
        return page.locator("//*[contains(text(),'Other Action Comments')]");
    }
    private Locator sortByDropDownOnCommensTab() {
        return page.locator("//*[@id=\"ddlSortComments\"]");
    }
    private Locator noCommentsMessage() {
        return page.locator("//*[@id='collapseFour']//following-sibling::div//div[contains(text(),'There are no comments for this EC.')]");
    }
    private Locator addCommentsButton() {
        return page.locator("//*[text()='Add Comment']");
    }
    private Locator replyButtonOnCommentsTab() {
        return page.locator("//i[@class='fa fa-reply']//parent::a");
    }
    private Locator participantsOnAddCommentsPopup() {
        return page.locator("//a[ contains(text(),'Participants')]");
    }
    private Locator fyiPeopleOnAddCommentsPopup() {
        return page.locator("//*[@id='acollapseSix' and contains(text(),'FYI People')]");
    }
    private Locator adhocUsersOnAddCommentsPopup() {
        return page.locator("//a[contains(text(),'Adhoc Users')]");
    }
    private Locator addCommentsTextAreaOnAddCommentsPopup() {
        return page.locator("//textarea[@id='txtComments']");
    }
    private Locator saveCommentsOnAddCommentsPopup() {
        return page.locator("//button[@id='btnSave' and contains(text(),'Save Comment')]");
    }
    private Locator cancelOnAddCommentsPopup() {
        return page.locator("//button[contains(text(),'Cancel')]");
    }
    private Locator commentsRadioButtOnAddCommentsPopup() {
        return page.locator("//*[@id='rdComment']");
    }
    private Locator issueRadioButtOnAddCommentsPopup() {
        return page.locator("//*[@id='rdIssue']");
    }
    private Locator messageAfterExpandingParticipants() {
        return page.locator("//div[@id='gridParticipant']");
    }
    private Locator messageAfterExpandingFYIPeople() {
        return page.locator("//div[@id='gridFYIPeeps']");
    }
    private Locator titleOnAddCommentsPopup() {
        return page.locator("//span[@id='ui-id-2' and contains(text(),'Add comments or issue')]");
    }
    private Locator errorMessageAddCommentsPopup() {
        return page.locator("//*[@id='lblCommentMessage']");
    }
    private Locator commentsTextField() {
        return page.locator("//textarea[@id='txtComments']");
    }
    private Locator productsAndProjectsTableforColumn() {
        return page.locator("//div[@id='filterdGridResult']//table[@id='example-basic']");
    }
    private Locator productsAndProjectsTableforRow() {
        return page.locator("//div[@id='filterdGridResult']//table[@id='example-basic']//tbody");
    }
    private Locator quickSearchLink() {
        return page.locator("//*[@id='txtEC_Number']");
    }
    private Locator quickSearchLinkIcon() {
        return page.locator("//*[@class='fa fa-search']");
    }
    private Locator currentTask() {
        return page.locator("//*[@id='Ptask']");
    }
    private Locator projectNameForProjectPage() {
        return page.locator("//input[@id='projectName']");
    }
    private Locator currentTaskDescription() {
        return page.locator("//*[@id='PtaskDesc']");
    }
    private Locator txtStatusInProjectDetails() {
        return page.locator("//*[@id='PtaskDesc']");
    }
    private Locator ownerInInProjectDetails() {
        return page.locator("//input[@name='adhocuserList_input']");
    }
    private Locator historyTab() {
        return page.locator("//*[@id='ProjectHistory']/a");
    }
    private Locator dateBasedHistoryRadioButton() {
        return page.locator("//*[@id='Date']");
    }
    private Locator currentDateHeaderExpand() {
        return page.locator("//a[text()='Current Date']");
    }
    private Locator lastWeekHeaderExpand() {
        return page.locator("//a[text()='Last Week']");
    }
    private Locator lastMonthHeaderExpand() {
        return page.locator("//a[text()='Last Month']");
    }
    private Locator dateRangeHeaderExpand() {
        return page.locator("//a[text()='Date Range']");
    }
    private Locator txtStartDate() {
        return page.locator("//*[@id='PDCStartDate']");
    }
    private Locator txtEndDate() {
        return page.locator("//*[@id='PDCEndDate']");
    }
    private Locator clickButtonOnDateRange() {
        return page.locator("//*[@id='GetDateRang']");
    }
    private Locator projectTeamTable() {
        return page.locator("//*[@id='autoValueHidden']//parent::div//table");
    }
    private Locator editButtonOnProjectTeam() {
        return page.locator("");
    }
    private Locator projectDecision() {
        return page.locator("//div[@id='cmLoad']//a[contains(text(),' Project Decisions')]");
    }
    private Locator createProject() {
        return page.locator("//*[@value='Create Project']");
    }
    private Locator projectName() {
        return page.locator("//div[@id='ModelProjectFormShow']//input[@id='projectName']");
    }
    private Locator projectOwner() {
        return page.locator("//div[@id='ModelProjectFormShow']//select[@id='ProjOwnPM']/following::input[1]");
    }
    private Locator status() {
        return page.locator("//select[@id='pcrStatus']");
    }
    private Locator priority() {
        return page.locator("//select[@id='ec_Priority']");
    }
    private Locator projectOwnerDropDownButton() {
        return page.locator("//span[@aria-controls='ProjOwnPM_listbox']");
    }
    private Locator projectComplexity() {
        return page.locator("//select[@id='projectComplexity']");
    }
    Locator NeedDate=page.locator("//span[@aria-controls='needDate_dateview']");
    
    Locator startDate=page.locator("//span[@aria-controls='startDate_dateview']");
    
    private Locator endDate() {
        return page.locator("//span[@aria-controls='endDate_dateview']");
    }
    private Locator product() {
        return page.locator("//select[@id='product']");
    }
    private Locator createProjectButton() {
        return page.locator("//input[@id='btn_Createproject']");
    }
    private Locator getProjectNumber() {
        return page.locator("//*[@id='lblEC_Number']");
    }
    private Locator projectType() {
        return page.locator("//select[@id='projectType']");
    }
    private Locator pdaProgram() {
        return page.locator("//select[@id='plc_sPLC']");
    }
    private Locator productSelect() {
        return page.locator("//select[@id='Product']");
    }
    private Locator kpuSelect() {
        return page.locator("//select[@id='Kpu']");
    }
    private Locator statusSelect() {
        return page.locator("//select[@id='Status']");
    }
    private Locator buCustomerSelect() {
        return page.locator("//select[@id='BUCustomer']");
    }
    private Locator businessUnitSelect() {
        return page.locator("//select[@id='Business']");
    }
    private Locator exportToExcelProductAndProjectButton() {
        return page.locator("//a[@id='btnExportToExcel']");
    }
    private Locator loadButton() {
        return page.locator("//a[contains(text(),' Load')]");
    }
    private Locator ECRProductDropdown() {
        return page.locator("//*[@id='product']");
    }
    private Locator pSymbolButton() {
        return page.locator("//*[@id=\"example-basic\"]//tr[@data-tt-id='2']//span[@class='img-icon']");
    }
    private Locator validatebutton() {
        return page.locator("//td[contains(text(),'Suraj Rathod')]/preceding-sibling::td[1]//a");
    }
    private Locator clickOnFolder() {
        return page.locator("//*[@id=\"example-basic\"]//tr[@data-tt-id='3.1']//i");
    }
    private Locator createECROption() {
        return page.locator("//td[contains(text(),'1500010')]/preceding-sibling::td[1]//a[contains(text(),'Create ECR')]");
    }
    private Locator SetSubStatus() {
        return page.locator("//*[@id='example-basic']//a[contains(text(),'Set Sub Status')]");
    }
    private Locator ViewEdit() {
        return page.locator("//*[@id='example-basic']//a[contains(text(),'View/Edit')]");
    }
    private Locator CreateSubFolder() {
        return page.locator("//*[@id='example-basic']//a[contains(text(),'Create Sub Folder')]");
    }
    private Locator validateRename() {
        return page.locator("//*[@id='example-basic']//a[contains(text(),'Rename')]");
    }
    private Locator quickInfoOption() {
        return page.locator("//*[@id='example-basic']//a[contains(text(),'Quick Info')]");
    }
    private Locator btnCancel() {
        return page.locator("//span[contains(text(), 'Cancel') and @id='btn_Cancelproject']");
    }
    private Locator btnUpdate() {
        return page.locator("//span[contains(text(), 'Update Folder') and @id='btn_CreateFolder']");
    }
    private Locator getNumber() {
        return page.locator("//*[@id='lblEC_Number']");
    }
    private Locator ProjectIDLabel() {
        return page.locator("//label[text()='Project ID: ']");
    }
    private Locator ProjectTitleLabel() {
        return page.locator("//label[text()='Project Title: ']");
    }
    private Locator FolderTitleLabel() {
        return page.locator("//label[contains(text(),'Folder Title:')]");
    }
    private Locator GeneralUseStatementLabel() {
        return page.locator("//label[text()='General Use Statement: ']");
    }
    private Locator ReasonCodeLabel() {
        return page.locator("//label[text()='Reason Code: ']");
    }
    private Locator ProjectIDValue() {
        return page.locator("//label[@for='projectID']");
    }
    private Locator ProjectTitleValue() {
        return page.locator("//label[@for='projectTitle']");
    }
    private Locator FolderIDLabel() {
        return page.locator("//*[contains(text(),'Folder ID: ')]");
    }
    private Locator SubFolderTitleLabel() {
        return page.locator("//label[contains(text(),'Sub Folder Title:')]");
    }
    private Locator AddToExplotoryProject() {
        return page.locator("//*[@id='btnExploratory']");
    }
    private Locator AuthorsandOwneDD() {
        return page.locator("//a[contains(text(),'Authors and Owners')]");
    }
    private Locator TriadParticipantsDD() {
        return page.locator("//a[contains(text(),'Triad Participants')]");
    }
    private Locator ApproversDD() {
        return page.locator("//a[contains(text(),'Approvers')]");
    }
    private Locator FYINotificationsDD() {
        return page.locator("//a[contains(text(),'FYI Notifications')]");
    }
    private Locator authorsandOwnersButton() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'Authors and Owners')]");
    }
    private Locator triadParticipantsButton() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'Triad Participants')]");
    }
    private Locator fyiNotificationsButton() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'FYI Notifications')]");
    }
    private Locator userNameTextFieldOnReassignment() {
        return page.locator("//*[text()='Search by UserName/UserID']/parent::div//following-sibling::input[@id='userDet']");
    }
    private Locator okButtonForReassignment() {
        return page.locator("//*[@id='btnOk']");
    }
    private Locator reassignButtonForReassignment() {
        return page.locator("//*[@id=\"reassign\"]//following-sibling::input[@value='Reassign']");
    }
    private Locator approversButtonPCR() {
        return page.locator("//h4[@class='panel-title']//a[contains(text(),'Approvers')]");
    }
    private Locator UserColumn() {
        return page.locator("//table//tr//th[contains(text(),'User')]");
    }
    private Locator RoleColumn() {
        return page.locator("//table//tr//th[contains(text(),'Role')]");
    }
    private Locator DelegatedToColumn() {
        return page.locator("//table//tr//th[contains(text(),'Delegated to')]");
    }
    private Locator OfferedToColumn() {
        return page.locator("//table//tr//th[contains(text(),'Offered To')]");
    }
    private Locator SkipToColumn() {
        return page.locator("//table//tr//th[contains(text(),'Skip')]");
    }
    private Locator ActionColumn() {
        return page.locator("//table//tr//th[contains(text(),'Action')]");
    }
    private Locator ReassignButtonInAuthorsandOwnersTab() {
        return page.locator("//table[@id='Tab_2']//input[@value='Reassign']");
    }
    private Locator CancelButtonForReassignmentinReassignmentTab() {
        return page.locator("//div[@id='reassignModalContent']/div//button[@class='close']");
    }
    private Locator dropDownpmtcrSubmitDate() {
        return page.locator("//input[@id='pmtcrSubmitDate']");
    }
    private Locator objtableXpathECR() {
        return page.locator("//*[@id='gridFinalECR']/div[2]/table/tbody/tr/td[2]/a");
    }
    private Locator restrictWriteAccessYesBtn() {
        return page.locator("//*[@id='rdRestrictWriteAccess']");
    }
    private Locator warningYesBtn() {
        return page.locator("//*[@id='dynamicBody']//following-sibling::div/button[text()='Yes']");
    }
    private Locator newQuestionnaireBtn() {
        return page.locator("//*[@id=\"btnNewQuestionnaireTemplate\"]");
    }
    private Locator newQuestionnaireSaveBtn() {
        return page.locator("//*[@id='newQuestionnaireHeader']/parent::div//following::div[@class='modal-footer']//button[contains(text(),'Save')]");
    }
    private Locator newSubTaskBtn() {
        return page.locator("//*[@id='btnNewSubTaskTemplate']");
    }
    private Locator newSubTaskSaveBtn() {
        return page.locator("//*[@id='addSubTask']//following::div[@class='modal-footer']//button[contains(text(),'Save')]");
    }
    private Locator txtFieldProjectTag() {
        return page.locator("//input[@aria-controls='ddlFormTagNames_listbox']");
    }
    private Locator btnmoveechistory() {
        return page.locator("//button[@id='btnP2MoveECHistoryAssocChanges']");
    }
    private Locator MilestoneName() {
        return page.locator("//span[contains(text(),'Milestone Name')]/following-sibling::span//input[@id='txtMilestoneName']");
    }
    private Locator MilestoneNameBU() {
        return page.locator("//select[@id='Business']");
    }
    private Locator MilestoneNameCreatedBy() {
        return page.locator("//select[@id='UserList']");
    }
    private Locator resetbtn() {
        return page.locator("//a[@id='btnSearch']");
    }
    private Locator searchbtn() {
        return page.locator("//span[@id='btnsch']");
    }
    private Locator addnewmilestonebtn() {
        return page.locator("//button[@class='btn btn-primary btn-xs']");
    }
    private Locator DesignCompletion() {
        return page.locator("//div[contains(text(),'Design Completion')]/following-sibling::div[contains(text(),'Design Completion Cycle')]");
    }
    private Locator NSRReadinessCycle() {
        return page.locator("//div[contains(text(),'NSR Readiness')]/following-sibling::div[contains(text(),'NSR Readiness Cycle')]");
    }
    private Locator ConversionCompletionCycle() {
        return page.locator("//div[contains(text(),'Conversion Completion')]/following-sibling::div[contains(text(),'Conversion Completion Cycle')]");
    }
    private Locator fileToUploadButton() {
        return page.locator("//input[@id='btnfileupload']");
    }
    private Locator attachementDescriptionTextField() {
        return page.locator("//input[@id='Attachment_Description']");
    }
    private Locator taskFileUploadButton() {
        return page.locator("//button[@id='fileupload']");
    }

    public void expandSectionOnProjectScreen(String sSection) {
        Locator objSection = page.locator("//*[@id='accordion2']/div[1]");
        objWrapperFunctions.scrollTillElement(objSection);
        objWrapperFunctions.getText(objSection, "text");
        String ariaExpanded = objSection.getAttribute("aria-expanded");
        if (ariaExpanded == null || ariaExpanded.equalsIgnoreCase("false")) {
            objUtilities.logReporter(
                "Expand section '" + sSection + "' on project screen : expandSectionOnProjectScreen()",
                objWrapperFunctions.click(objSection), false);
        } else {
            objUtilities.logReporter("Section '" + sSection + "' already expanded : expandSectionOnProjectScreen()",
                true, false);
        }
    }

    public void EnableorDisableProjectAddsValueFieldCheckBox(boolean bFlag) {
        Locator sCheckedValue = page.locator("//*[@id='applicableForValueCaptureYes']");
        Locator sUncheckedValue = page.locator("//*[@id='applicableForValueCaptureNo']");
        if(!objWrapperFunctions.isCheckBoxSelected(sCheckedValue, true) && !objWrapperFunctions.isCheckBoxSelected(sUncheckedValue, true)) {
            if(objWrapperFunctions.checkElementDisplyed(sCheckedValue)) {
                if(bFlag == true) {
                    sCheckedValue = page.locator("//*[@id='applicableForValueCaptureYes']");
                    if(objWrapperFunctions.isCheckBoxSelected(sCheckedValue, true))
                        objUtilities.logReporter("already Selected option " + bFlag + " for Value added Project : EnableorDisableProjectAddsValueFieldCheckBox()", true, false);
                    else    
                        objUtilities.logReporter("Select option " + bFlag + " for Value added Project : EnableorDisableProjectAddsValueFieldCheckBox()", objWrapperFunctions.click(sCheckedValue), false);
                }else{
                    sUncheckedValue = page.locator("//*[@id='applicableForValueCaptureNo']");
                    if(!objWrapperFunctions.isCheckBoxSelected(sUncheckedValue, true)) {
                        if(objWrapperFunctions.isCheckBoxSelected(sUncheckedValue, true))
                            objUtilities.logReporter("already Selected option " + bFlag + " for Value added Project : EnableorDisableProjectAddsValueFieldCheckBox()", true, false);
                        else    
                            objUtilities.logReporter("Select option " + bFlag + " for Value added Project : EnableorDisableProjectAddsValueFieldCheckBox()", objWrapperFunctions.click(sUncheckedValue), false);
                    }
                }
            }
        }
    }

    public void selectReadyToAcceptNSROption(String sOption) {
        boolean bResult = false;
        Locator rbtnReadyToAcceptNSR = page.locator("input#IsReadytoAcceptNSR");
        objWrapperFunctions.waitForElementPresence("input#IsReadytoAcceptNSR");
        List<Locator> rbtnWEReadyToAcceptNSROption = rbtnReadyToAcceptNSR.all();
        for (Locator rbtnWE : rbtnWEReadyToAcceptNSROption) {
            String value = rbtnWE.getAttribute("value");
            if (value != null && value.toLowerCase().contains(sOption.toLowerCase())) {
                objWrapperFunctions.click(rbtnWE);
                bResult = true;
                break;
            }
        }
        objUtilities.logReporter(
            "Select option '" + sOption + "' for Ready to Accept NSR : selectReadyToAcceptNSROption()", bResult, false);
    }

    public void verifyNSRCommentSectionOpen() {
        Locator sCommentPop = page.locator("//*[@id='commonPopup']");
        Locator ComnmentArea = page.locator("//textarea[@id='ReadyNSRComments']");
        if (objWrapperFunctions.checkElementDisplyed(sCommentPop)) {
            objUtilities.logReporter("verify task comment section open : verifyNSRCommentSectionOpen()", objWrapperFunctions.checkElementDisplyed(sCommentPop), false);
            objUtilities.waitFor(5);
            objUtilities.logReporter("Set Comment Test :  setCommentInNSRAcceptPopup()", objWrapperFunctions.setText(ComnmentArea, "Test"), false);
            Locator Savebttn = page.locator("//button[@id='btnSaveNSRComments']");
            objWrapperFunctions.scrollTillElement(Savebttn);
            objUtilities.logReporter("Click on Save Comment button", objWrapperFunctions.click(Savebttn), false);
        }
        else {
            objUtilities.logReporter("verify task comment section is not displayed : verifyNSRCommentSectionOpen()", true, false);
        }
    }

    public void setNSRReadyForecastDate() {
        Locator ReadyForecastDate = page.locator("//input[@id='txtNSR_ReadyForecastDate']");
        objUtilities.logReporter(
            "Set Ready Forecast Date to " + objUtilities.getRequiredDate(1, "dd MMM yyy", "") + " :setNSRReadyForecastDate()",
            objWrapperFunctions.setText(ReadyForecastDate,
                objUtilities.getRequiredDate(1, "dd MMM yyy", "")),
            false);
        objUtilities.logReporter("press enter:reassignTaskToCurrentUser()", objWrapperFunctions.ActionKeyBoardOperations("enter"), false);
        objUtilities.waitFor(4);
    }

    public void saveDetailsOnProductsAndProjectsPage() {
        Locator SaveButton = page.locator("//i[@class='fa fa-save']");
        objUtilities.waitFor(10);
        objWrapperFunctions.waitForElementToBeClickable("//i[@class='fa fa-save']");
        objUtilities.logReporter(
            "Wait for Save project Details button after editing them on products and projects Page :saveDeatilsOnP2Page()",
            objWrapperFunctions.waitForElementToBeClickable("//i[@class='fa fa-save']"), false);
        objUtilities.logReporter(
            "Save project Details after editing them on products and projects Page :saveDeatilsOnP2Page()",
            objWrapperFunctions.click(SaveButton), false);
    }

    public void setProjectName(String strTitle) {
        Locator projectName = page.locator("//div[@id='ModelProjectFormShow']//input[@id='projectName']");
        objWrapperFunctions.waitForElementToBeClickable("//div[@id='ModelProjectFormShow']//input[@id='projectName']");
        objUtilities.logReporter("Set Project Name'" + strTitle + "': setProjectName()", objWrapperFunctions.setText(projectName, strTitle), false);
    }

    public void setProjectOwner(String sOwner) {
        if (sOwner.contains(",") || sOwner.toLowerCase().contains("x0")) {
            String[] split1 = sOwner.split(", ");
            if (split1.length > 1) {
                sOwner = split1[1].split(" ")[0] + " " + split1[0];
            }
        }
        Locator cmbProjectOwner = page.locator("//label[contains(text(), 'Project Owner / PM')]/following-sibling::input");
        objUtilities.logReporter("Sucessfully clicked on project owner DD", objWrapperFunctions.click(cmbProjectOwner), false);
        cmbProjectOwner.press("Control+a");
        objUtilities.logReporter("Sucessfully cleared on project owner text box", objWrapperFunctions.ActionKeyBoardOperations("backspace"), false);
        objUtilities.logReporter("Sucessfully clicked on button '" + sOwner + "'.", objWrapperFunctions.setTextCharByChar(cmbProjectOwner, sOwner), false);
        cmbProjectOwner.press("ArrowDown");
        cmbProjectOwner.press("Enter");
        cmbProjectOwner.press("Tab");
        objUtilities.logReporter("Sucessfully Selected value '" + sOwner + "'. :setProjectOwner()", cmbProjectOwner.inputValue().contains(sOwner), false);
    }

    public void setStatus(String sStatus) {
        Locator statusDD = page.locator("//select[@id='pcrStatus']");
        objUtilities.logReporter("Select status'" + sStatus + "':setStatus()", objWrapperFunctions.selectDropDownOption(statusDD, sStatus), false);
        objUtilities.logReporter("click on tab : setStatus()", objWrapperFunctions.ActionKeyBoardOperations("Tab"), false);
    }

    public void setPriority(String sPriority) {
        Locator priority = page.locator("//select[@id='ec_Priority']");
        boolean flag = false;
        for (int i = 1; i <= 5; i++) {
            flag = objWrapperFunctions.selectDropDownOption(priority, sPriority, "text");
            if (flag) {
                break;
            }
        }
        objUtilities.logReporter("Select priority " + sPriority + " :setPriority()", flag, false);
    }

    public void setComplexity(String sComplexity) {
        Locator complexity = page.locator("//select[@id='projectComplexity']");
        objUtilities.logReporter("Select status'" + sComplexity + "':setComplexity()", objWrapperFunctions.selectDropDownOption(complexity, sComplexity), false);
    }

    public void setScope(String strScope) {
        Locator txtScope = page.locator("//textArea[@id='PScope']");
        objUtilities.logReporter("Set Project Scope", objWrapperFunctions.setText(txtScope, strScope), false);
    }

    public void setDeliverable(String strDeliverable) {
        Locator txtDeliverable = page.locator("//*[@id='Deliverable']");
        objUtilities.logReporter("Set Deliverable", objWrapperFunctions.setText(txtDeliverable, strDeliverable), false);
    }

    public void setBusinessBenefits(String strBusinessBenefits) {
        Locator txtBusinessBenefits = page.locator("//*[@id='BusinessBenefits']");
        objUtilities.logReporter("Set Project Scope", objWrapperFunctions.setText(txtBusinessBenefits, strBusinessBenefits), false);
    }

    public void setProjectType(String sType) {
        switch (sType.toLowerCase()) {
            case "safety compliance":
            case "customer request":
            case "cip":
            case "obsolescence":
            case "ces":
            case "margin improvement":
            case "design cost reduction":
            case "reliability/quality improvement":
            case "others":
                Locator CIPType = page.locator("//select[@id='ddlCIPSubstatus']");
                objUtilities.logReporter("Select status'CIP':setProjectType()", objWrapperFunctions.selectDropDownOption(projectType(), "CIP"), false);
                if (sType.equalsIgnoreCase("CIP")) {
                    objUtilities.logReporter("Select CIP Type as 'CES':setProjectType()", objWrapperFunctions.selectDropDownOption(CIPType, "CES"), false);
                } else {
                    objUtilities.logReporter("Select CIP Type as '" + sType + "':setProjectType()", objWrapperFunctions.selectDropDownOption(CIPType, sType), false);
                }
                break;
            default:
                objUtilities.logReporter("Select status'" + sType + "':setProjectType()", objWrapperFunctions.selectDropDownOption(projectType(), sType), false);
        }
    }

    public void setNeedDate(String sNeedDate, int iDayIncrementor) {
        Locator dtNeedDate = page.locator("input#needDate");
        if (!sNeedDate.isEmpty()) {
            sNeedDate = objUtilities.getFormatedDate(sNeedDate, "dd MMM yyyy", "dd MMM yyyy");
        } else {
            sNeedDate = objUtilities.getRequiredDate(iDayIncrementor, "dd MMM yyyy", "");
        }
        page.evaluate("document.getElementById('needDate').removeAttribute('readonly', 0);");
        objUtilities.logReporter("Select Date From Calendar '" + sNeedDate + "': setNeedDate()", objWrapperFunctions.setText(dtNeedDate, sNeedDate), false);
    }

    public void NeedDate() {
        this.setNeedDate("", 1);
    }

    public void setStartDate(String sStartDate, int iDayIncrementor) {
        Locator dtStartDate = page.locator("input#startDate");
        if (!sStartDate.isEmpty()) {
            sStartDate = objUtilities.getFormatedDate(sStartDate, "dd MMM yyyy", "dd MMM yyyy");
        } else {
            sStartDate = objUtilities.getRequiredDate(iDayIncrementor, "dd MMM yyyy", "");
        }
        page.evaluate("document.getElementById('startDate').removeAttribute('readonly', 0);");
        objUtilities.logReporter("Select Date From Calendar " + sStartDate + ": setStartDate()", objWrapperFunctions.setText(dtStartDate, sStartDate), false);
    }

    public void startDate() {
        this.setStartDate("", 0);
    }

    public void selectBusinessUnitOnCreateProjectPopUp(String sBusinessUnit) {
        Locator cmbCreateProjectBU = page.locator("//label[contains(text(),'Business Unit')]/following-sibling::select[@id='BUnit']");
        objUtilities.logReporter(
            "Select BU " + sBusinessUnit + " on create project pop up : selectBusinessUnitOnCreateProjectPopUp",
            objWrapperFunctions.selectDropDownOption(cmbCreateProjectBU, sBusinessUnit, "text"), false);
    }

    public void SelectECRProduct(String product) {
        Locator ECRProductDropdown = page.locator("//*[@id='product']");
        objUtilities.logReporter("Select ECR Product:SelectECRProduct()", objWrapperFunctions.selectDropDownOption(ECRProductDropdown, product), false);
    }

    public void clickOnCreateProjectButton() {
        Locator btnCreateProject = page.locator("//h4[text()='Project']/ancestor::div[@class='modal-content']//input[@value='Create Project']");
        objWrapperFunctions.scrollTillElement(btnCreateProject);
        objWrapperFunctions.waitForElementToBeClickable("//h4[text()='Project']/ancestor::div[@class='modal-content']//input[@value='Create Project']");
        objUtilities.logReporter("Click on create project button : clickOnCreateProjectButton()", objWrapperFunctions.click(btnCreateProject), false);
        // Equivalent for waitTillPageLoad(null) can be a small wait if needed
    }

    public void HandlecreateTeamsChannelModal() {
        Locator XPath = page.locator("//h5[@id='createTeamsChannelModalLabel']");
        Locator NoButton = page.locator("//*[@id='createTeamsChannelModal']//button[text()='Yes']");
        if (objWrapperFunctions.checkElementDisplyed(XPath)) {
            objUtilities.logReporter("verified Create Teams Channel  popup : HandlecreateTeamsChannelModal()", objWrapperFunctions.checkElementDisplyed(XPath), false);
            objUtilities.logReporter("click on Yes button on Create Teams Channel  popup : HandlecreateTeamsChannelModal()", objWrapperFunctions.click(NoButton), false);
        }
        else {
            objUtilities.logReporter("verified Create Teams Channel  popup is not displayed : HandlecreateTeamsChannelModal()", true, false);
        }
    }

    public void handlecreateTeamChannelConfiguration() {
        Locator XPath = page.locator("//h5[@id='teamsConfigurationErrorModalLabel']");
        Locator CreateProjectButton = page.locator("//*[@id='teamsConfigurationErrorModal']//button[text()='Create Project without Teams']");
        if (objWrapperFunctions.checkElementDisplyed(XPath)) {
            objUtilities.logReporter("verified Create Teams Channel Configuration  popup : handlecreateTeamChannelConfiguration()", objWrapperFunctions.checkElementDisplyed(XPath), false);
            objUtilities.logReporter("click on Yes button on Create Teams Channel Configuration  popup : handlecreateTeamChannelConfiguration()", objWrapperFunctions.click(CreateProjectButton), false);
        }
        else {
            objUtilities.logReporter("verified Create Teams Channel Configuration popup is not displayed : handlecreateTeamChannelConfiguration()", true, false);
        }
    }

    public void clickOnCreateProjectCancelButton() {
        Locator btnCreateProjectCancel = page.locator("//span[@id='btn_Cancelproject']");
        objWrapperFunctions.scrollTillElement(btnCreateProjectCancel);
        objUtilities.logReporter("Click on create project Cancel button : clickOnCreateProjectCancelButton()", objWrapperFunctions.click(btnCreateProjectCancel), false);
    }

    public void selectProductsAndProjectDropDownValue(String sDropDownName, String sDropDownValue) {
        Locator objWEDropDown = null;
        switch (sDropDownName.toLowerCase()) {
            case "business unit":
                objWEDropDown = cmbBusinessUnit();
                break;
            case "product":
                objUtilities.logReporter(
                    "Select value " + sDropDownValue + " in dropdown " + sDropDownName + " in method :selectProductsAndProjectDropDownValue",
                    this.selectValueFromProductDropDown(sDropDownValue, true), false);
                break;
            case "kpu":
                objWEDropDown = cmbKPU();
                break;
            case "status":
                objWEDropDown = cmbStatus();
                break;
            case "bu customer":
                objWEDropDown = cmbBUCustomer();
                break;
            default:
                objUtilities.logReporter("Wrong drop down name passed as " + sDropDownName, false, false);
        }
        if (!sDropDownName.equalsIgnoreCase("Product")) {
            objUtilities.logReporter(
                "Select value " + sDropDownValue + " in dropdown " + sDropDownName + " in method :selectProductsAndProjectDropDownValue",
                objWrapperFunctions.selectDropDownOption(objWEDropDown, sDropDownValue, "text"), false);
        }
    }

    public boolean selectValueFromProductDropDown(String sProduct, boolean bSelect) {
        boolean bResult = false;
        Locator objProduct = page.locator("//div[text()='Product']/following-sibling::div/select[@id='Product']/parent::div//ul/li//*[b or label]/*");
        if (!getValueInProductsAndProjectPageComboBox("Product").toLowerCase().contains(sProduct.toLowerCase())) {
            objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
            List<Locator> objWEProduct = objProduct.all();
            if (sProduct.equalsIgnoreCase("All")) {
                for (Locator objWEProd : objWEProduct) {
                    Locator chkWEProduct = objWEProd.locator("xpath=.//preceding-sibling::input");
                    if (!objWrapperFunctions.selectCheckBox(chkWEProduct, false)) {
                        bResult = true;
                        break;
                    }
                }
            } else if (sProduct.contains("~")) {
                String[] arr = sProduct.split("~");
                for (int i = 0; i < arr.length; i++) {
                    int iLoopCount = 0;
                    while (bResult == false && iLoopCount < 3) {
                        if (iLoopCount > 0) {
                            objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
                            objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
                            objWEProduct = objProduct.all();
                        }
                        for (Locator objWEProd : objWEProduct) {
                            String abc = objWrapperFunctions.getText(objWEProd, "Text");
                            if (abc.equalsIgnoreCase(arr[i])) {
                                Locator chkWEProduct = objWEProd.locator("xpath=.//preceding-sibling::input");
                                if (objWrapperFunctions.selectCheckBox(chkWEProduct, bSelect)) {
                                    bResult = true;
                                    break;
                                }
                            }
                        }
                        iLoopCount++;
                    }
                }
            } else {
                int iLoopCount = 0;
                while (bResult == false && iLoopCount < 3) {
                    if (iLoopCount > 0) {
                        objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
                        objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
                        objWEProduct = objProduct.all();
                    }
                    for (Locator objWEProd : objWEProduct) {
                        if (objWrapperFunctions.getText(objWEProd, "Text").equalsIgnoreCase(sProduct)) {
                            Locator chkWEProduct = objWEProd.locator("xpath=.//preceding-sibling::input");
                            if (objWrapperFunctions.selectCheckBox(chkWEProduct, bSelect)) {
                                bResult = true;
                                break;
                            }
                        }
                    }
                    iLoopCount++;
                }
            }
        } else {
            bResult = true;
        }
        objUtilities.logReporter("Click to product dropdown value:selectValueFromProductDropDown()", objWrapperFunctions.click(cmbProduct()), false);
        return bResult;
    }

    public String getValueInProductsAndProjectPageComboBox(String sFieldName) {
        Locator objWEField = null;
        switch (sFieldName.toLowerCase()) {
            case "business unit":
                objWEField = cmbBusinessUnit();
                break;
            case "product":
                return objWrapperFunctions.getText(cmbProduct(), "Text");
            case "kpu":
                objWEField = cmbKPU();
                break;
            case "status":
                objWEField = cmbStatus();
                break;
            case "bu customer":
                objWEField = cmbBUCustomer();
                break;
            default:
                objUtilities.logReporter("Wrong drop down name passed as " + sFieldName, false, false);
        }
        Locator objSelected = objWEField.locator(".//option[@selected='selected']").last();
        return objWrapperFunctions.getText(objSelected, "text");
    }

    public void clickOnProductAndProjectButton(String sButtonName) {
        Locator objWEButton = null;
        switch (sButtonName.toLowerCase()) {
            case "load":
                objWEButton = btnLoad();
                objUtilities.logReporter("Click on Export to Excel button : clickOnProductAndProjectButton", objWrapperFunctions.click(btnExportToExcel()), false);
                break;
            case "create project":
                objWEButton = btnCreateProject();
                break;
            case "move ec history":
                objWEButton = btnmoveechistory();
                break;
            case "export to excel":
                objWEButton = btnExportToExcel();
                objUtilities.logReporter("Click on Export to Excel button : clickOnProductAndProjectButton", objWrapperFunctions.click(btnExportToExcel()), false);
                break;
            default:
                objUtilities.logReporter("Wrong button passed to click as " + sButtonName + " in method :clickOnProductAndProjectButton", false, false);
        }
        if (!sButtonName.equalsIgnoreCase("Export to Excel")) {
            objUtilities.logReporter(
                "Click on Product and Project button " + sButtonName + " in method :clickOnProductAndProjectButton",
                objWrapperFunctions.click(objWEButton), false);
        }
        if (sButtonName.equalsIgnoreCase("Create Project")) {
            page.waitForSelector("a#loader", new Page.WaitForSelectorOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.DETACHED));
        }
        if (sButtonName.equalsIgnoreCase("load")) {
            // Loader selector might need to be updated according to actual loader.
            page.waitForSelector("", new Page.WaitForSelectorOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.DETACHED));
        }
        else if (sButtonName.equalsIgnoreCase("Export to Excel")) {
            objUtilities.waitFor(150);
        }
    }
}