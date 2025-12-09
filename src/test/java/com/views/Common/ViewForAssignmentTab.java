package com.views.Common;
 
import com.generic.*;

import com.pageFactory.Common.AssignmentTabPage;
 
public class ViewForAssignmentTab {

	AssignmentTabPage objAssignmentTabPage;

	private Utilities objUtilities;

	private WrapperFunctions objWrapperFunctions;
 
	public ViewForAssignmentTab(Pojo objPojo) {

		objAssignmentTabPage = new AssignmentTabPage(objPojo);

		objUtilities = objPojo.getObjUtilities();

		objWrapperFunctions = new WrapperFunctions(objPojo);

	}
 
	// RP_18012018 - Verify reassignment pop up fields

	public void verifyReassignmentPopUpFields(String sReassignmentPopUpFields) {

		String[] sArrFields = sReassignmentPopUpFields.split("~");

		for (String sField : sArrFields) {

			objAssignmentTabPage.verifyPopUpFields("Reassignment", sField);

		}

		// verify reassignment button on pop up

		objAssignmentTabPage.verifyReassignButtonOnPopUpDisplayed();

	}
 
	// RP_18012018 - click Reassign in assignment tab section

	public void clickReassignInAssignmentSectionTable(String sAssignmentSection, String sTableColumn,

			String sTableValue) {

		objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

		// take action in assignment table

		objAssignmentTabPage.clickReassignInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "Action");

		// verify Reassignment pop up is displayed

		objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

	}
 
	// SZ: remove //sz05042018 - temp added code to reassign second time

	// RP_18012018 - click Reassign in assignment tab section

	public void clickReassignInAssignmentSectionTable1(String sAssignmentSection, String sTableColumn,

			String sTableValue) {

		objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

		// take action in assignment table

		objAssignmentTabPage.clickReassignInAssignmentsTable1(sAssignmentSection, sTableColumn, sTableValue, "Action");

		// verify Reassignment pop up is displayed

		objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

	}
 
	// SZ30052018: clicking on reassign depends on BU user name passed

	public void clickReassignInAssignmentsTable_UsingBUName(String sAssignmentSection, String sTableColumn,

			String sTableValue, String sBUUserName) {

		objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

		// take action in assignment table

		objAssignmentTabPage.clickReassignInAssignmentsTable_BuUser(sAssignmentSection, sTableColumn, sTableValue,

				"Action", sBUUserName);

		// verify Reassignment pop up is displayed

		objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

	}
 
	// RP_18012018 - enter values in reassignment

	public void reassignOnReassignmentPopup(String sReassignTo) {
 
		objAssignmentTabPage.setReassignToOnPopUp(sReassignTo);

		// click on Reassign button on pop up

		objAssignmentTabPage.clickReassignButtonOnPopUp();

		// verify value in assignment table

		// objAssignmentTabPage.verifyValueInAssignmentsTable(sExpandAssignmentHeader,

		// sTableColumn, sTableValue, "User", sReassignTo);

	}

	//sz25092018

	public void selectAndRemoveApproverInAssignmentsTable_BuUser(String sAssignmentSection, String sTableColumn,String sTableValue, String sBUUserName)

	{

		objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

		objAssignmentTabPage.selectApproverInAssignmentsTable_BuUser(sAssignmentSection, sTableColumn, sTableValue, "Action", sBUUserName);	//Here "Action" used to find the black coulmn number

		objAssignmentTabPage.clickOnRemoveBtnUnderAssignmentTab();

		/*objAssignmentTabPage.verifyApproverIsRemoved(sTableValue, sBUUserName);*/

	}
 
	//sz26092018

	public void verifyCheckBOxForApproverIsPresentUnderApprovers(String sRoleValuesAndsBUUserNames)

	{

		objAssignmentTabPage.expandAssignmentTabHeaders("Approvers");

		String[] aRoleValueAndsBUUserName = sRoleValuesAndsBUUserNames.split(";");

		for (String sRoleValuesAndUsers : aRoleValueAndsBUUserName)

		{

			String[] aRoleAndUser = sRoleValuesAndUsers.split("~");

			objAssignmentTabPage.verifyCheckBOxForApproverIsPresent("Approvers", aRoleAndUser[0], aRoleAndUser[1]);

		}

	}

	//sz26092018

	public void verifyRoleAndRespectiveUserIsPresent(String sTableInSection, String sRoleValuesAndsBUUserNames)

	{

		objAssignmentTabPage.expandAssignmentTabHeaders(sTableInSection);		

		String[] aRoleValueAndsBUUserName = sRoleValuesAndsBUUserNames.split(";");

		for (String sRoleValueAndsBUUserName : aRoleValueAndsBUUserName)

		{

			String[] sRolesAndNames = sRoleValueAndsBUUserName.split("~");

			objAssignmentTabPage.verifyRoleAndRespectiveUserIsPresent(sTableInSection, sRolesAndNames[0], sRolesAndNames[1]);

		}

	}

	// RP_18012018 - Verify value after reassign

	public void verifyReassignedValueInAssignmentsTable(String sAssignmentSection, String sTableColumn,

			String sTableValue, String sReassignTo) {

		objAssignmentTabPage.verifyValueInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "User",

				sReassignTo);

	}
 
	// RP_18012018 - click Reassign in assignment tab section

	public void clickReassignInAssignmentSectionTable(String sAssignmentSection, String sTableColumn,

			String sTableValue, String sExpAssignedUser) {

		objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

		// take action in assignment table

		objAssignmentTabPage.clickReassignInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "Action",

				sExpAssignedUser);

		// verify Reassignment pop up is displayed

		objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

	}
 
	public void verifyAssigmentMessageText(String sAssignmentTxt) {

		objAssignmentTabPage.verifyMessageTextForAssignmentDrivers(sAssignmentTxt);

	}
 
	public void verifyBUCustomerMessageText(String sBUCustomerTxt) {

		objAssignmentTabPage.verifyMessageTextForBUCustomer(sBUCustomerTxt);

	}
 
	// RPHADKE_04092018 - Reassign role

	public void reassignRole(String sAssignmentSection, String sRole, String sExpAssignedUser) {

		String Header=objUtilities.getPageHeader();

		if(Header.equals("Engineering Specification Waiver") && !sAssignmentSection.equalsIgnoreCase("Authors and Owners") )

			{

				objAssignmentTabPage.reassignAllUsingRoleExt(sRole, sAssignmentSection, sExpAssignedUser);

				objUtilities.waitFor(10);//to avoid sync issue

			}

		else

			{

				this.clickReassignInAssignmentSectionTable(sAssignmentSection, "Role", sRole, sExpAssignedUser);

				this.reassignOnReassignmentPopup(sExpAssignedUser);

			}	

	}
 
	// Dhirajkumar_19092018 - enter values in reassignment

	public void reassignOnReassignmentPopupWithReassignFormHandle(String sReassignTo) {

		objAssignmentTabPage.setReassignToOnPopUp_1(sReassignTo);

	}

	public void EmailVerifyID(String sReassignTo) {

		objAssignmentTabPage.setReassignToOnPopUp_1(sReassignTo);

	}


	// Namrata_15102018 - expand all assignment Tab Headers

	public void expandAllHeadersOnAssignmentTab(String sHeaders) {

		String[] headerList = sHeaders.split("~");
 
		for (String header : headerList) {

			objAssignmentTabPage.expandAssignmentTabHeaders(header);

		}
 
	}
 
	// Namrata_15102018 - Verify the table headers on assignment tab

	public void verifyTableHeadersOfAssignmentSection(String section, String header) {

		objAssignmentTabPage.verifyTableHeadersInAssignmentSection(section, header);

	}
 
	// Namrata_15102018 - Click on close icon after reassigning

	public void closeReassignPopUpWithoutReassigning(String sAssignmentSection, String sTableColumn, String sTableValue,

			String sReassignTo) {

		objAssignmentTabPage.clickReassignInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "Action");

		objAssignmentTabPage.setReassignToOnPopUp(sReassignTo);

		objAssignmentTabPage.closeReassignPopUp();

	}
	// Namrata_31102018 - Verify section headers on assignment tab

		public void verifySectionHeadersOfAssignmentSection(String sectionHeader) {

			objAssignmentTabPage.verifySections(sectionHeader);

		}
	 
		// Namrata_15102018 - Verify the table daa of Assigment Driver

		public void verifyAssignmentDriversTableData(String sectionHeader, String sectionValues) {

			objAssignmentTabPage.verifyDataInAssignmentDriversTable(sectionHeader, sectionValues);

		}

		public void expandHeaders(String sHeader) {

			// TODO Auto-generated method stub

			objAssignmentTabPage.expandAssignmentTabHeaders(sHeader);

		}

		//Namrata12272018 - verify role is present undee section

		public void verifyRoleIsPresentUnderSection(String sTableInSection, String sRoleValue)

		{

			objAssignmentTabPage.expandAssignmentTabHeaders(sTableInSection);		

			String[] aRoleValues = sRoleValue.split("~");

			for (String sRoleValueName : aRoleValues)

			{

				objUtilities.logReporter("verify  role  "+sRoleValueName+" is present under "+sTableInSection+" :verifyRoleIsPresentUnderSection()", objAssignmentTabPage.verifyRoleIsPresent(sTableInSection, sRoleValueName), false);

			}

		}

		//Namrata12272018 - verify role is not present undee section
	 
		

		// Namrata_28122018  - click n button

		public void clickOnPageButton(String butonName) {

			objAssignmentTabPage.clickOnPageButton(butonName);

		}

		// Namrata_31122018 - Verify assignment pop up fields

		public void verifyAssignmentPopUpFields(String sAssignmentPopUpFields) {

			String[] sArrFields = sAssignmentPopUpFields.split("~");

			objAssignmentTabPage.verifyPopUpIsDisplayed("Add Assignment");

			for (String sField : sArrFields) {

				objAssignmentTabPage.verifyPopUpFields("Add Assignment", sField);

			}

			objAssignmentTabPage.verifyAddButtonOnPopUpDisplayed();

		}

		// Namrata_31122018 - Assign role

		public void assignRole(String roleValue, String userValue) {

			objAssignmentTabPage.verifyPopUpIsDisplayed("Add Assignment");

			if(roleValue!="")

			objAssignmentTabPage.setValueOnPopUp("Role", roleValue); // set role

			objAssignmentTabPage.setValueOnPopUp("User Name", userValue); // set user

			objAssignmentTabPage.clickAddButtonOnPopUpDisplayed();


		}

		// Rupali_041219 - click Reassign in assignment tab section this choose exact name for assignment

			public void clickReassignInAssignmentSectionTableExactName(String sAssignmentSection, String sTableColumn,

					String sTableValue) {

				objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

				// take action in assignment table

				objAssignmentTabPage.clickReassignInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "Action");

				// verify Reassignment pop up is displayed

				objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

			}

			//Rupali_250419

			public void clicktoAddBUCustomerAndVerifyPopupfields(String sfield, String sStringverify,String sDropdownvalue) {

				objAssignmentTabPage.clicktoAddBUCustomer();

				objAssignmentTabPage.VerifyAddBuCustomerPopField(sfield);

				objAssignmentTabPage.VerifyPBG_NameDispalyedinAddBuCustomer(sStringverify);

				objAssignmentTabPage.VerifyAddbtnDispalyedinAddBuCustomer();

				objAssignmentTabPage.selectAddBucustomerdropdownValue(sDropdownvalue);

				objAssignmentTabPage.VerifyRemoveAllbtn();

				objAssignmentTabPage.VerifyRemovebtn();

			}

			// Nchavan_11122019 - verify if header is already expand on assignment tab

			public void verifyHeaderisAlreadyExpanded(String sHeader) {

				objAssignmentTabPage.checkHeaderIsexpanded(sHeader);

			}

			public void verifyAssignmentTabResignbuttonNotDisplayed(String strSection) {

				objAssignmentTabPage.expandAssignmentTabHeaders(strSection);

				objAssignmentTabPage.verifyAssignmentTabRessignButtonNotDisplayed(strSection);

			}
	 
			//Rupali

			public void verifyCheckBOxForApproverIsPresentUnder(String sRoleValuesAndsBUUserNames)

			{

				objAssignmentTabPage.expandAssignmentTabHeaders("CIB Others");

				String[] aRoleValueAndsBUUserName = sRoleValuesAndsBUUserNames.split(";");

				for (String sRoleValuesAndUsers : aRoleValueAndsBUUserName)

				{

					String[] aRoleAndUser = sRoleValuesAndUsers.split("~");

					objAssignmentTabPage.verifyCheckBOxForApproverIsPresent("CIB Others", aRoleAndUser[0], aRoleAndUser[1]);

				}

			}
	 
	//Rupali_072120

		public void ClickToAddApproverAndAddRoleInAssignmentTab(String sRoleName, String sApproverName) {

			objAssignmentTabPage.ClickToAddApproverInAssignmentTab();

			objAssignmentTabPage.SelectRoleAndUserInAddApproverPopupInAssignmentTab(sRoleName, sApproverName);

			objAssignmentTabPage.clickAddButtonOnaddApproverPopUp();

		}

		// RPHADKE_04092018 - Reassign role

		public void ReassignCurrentAction(String sAssignmentSection, String sRole, String sExpAssignedUser) {

			objUtilities.activateTab("Assignments");

			this.clickReassignInAssignmentSectionTable(sAssignmentSection, "Role", sRole, sExpAssignedUser);

			this.reassignOnReassignmentPopup("");

			objUtilities.waitTillPageLoad(null);

			objUtilities.activateTab("Change Order");

		}
	 
		//Roshan_16/12/2020

		public String getRoleName() {

			return objAssignmentTabPage.getRoleName();

		}

		public void verifyFYINotificationsMessageText() {

			objAssignmentTabPage.verifyFYINotificationsMessageText();

		}

		public void clickReassignInAssignmentSection(String sAssignmentSection, String sTableColumn,

				String sTableValue, String sExpAssignedUser) {

			objAssignmentTabPage.expandAssignmentTabHeadersTab(sAssignmentSection);

			// take action in assignment table

			objAssignmentTabPage.clickReassignInAssignmentsTable(sAssignmentSection, sTableColumn, sTableValue, "Action",

					sExpAssignedUser);

			// verify Reassignment pop up is displayed

			objAssignmentTabPage.verifyPopUpIsDisplayed("Reassignment");

		}
	 
		public void reassignRoleUser(String sAssignmentSection, String sRole, String sExpAssignedUser) {

			this.clickReassignInAssignmentSection(sAssignmentSection, "Role", sRole, sExpAssignedUser);

			this.reassignOnReassignmentPopup(sExpAssignedUser);

		}

		public void selectAndRemoveApproversInAssignmentsTable(String sAssignmentSection, String sTableColumn,String sTableValue, String sBUUserName)

		{

			objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

			objAssignmentTabPage.selectApproverInAssignmentsTable_BuUser(sAssignmentSection, sTableColumn, sTableValue, "Action", sBUUserName);	//Here "Action" used to find the black coulmn number

			objAssignmentTabPage.clickOnRemoveButtonnUnderAssignmentTab();	

		}

		public void verifyReassignButtonAvailableInAssignment(String sRole)

		{

			objAssignmentTabPage.verifyReassignButtonAvailableInAssignment(sRole);

		}

		public String getRolesListAccordingtoSection(String sSecID)

		{

			return objAssignmentTabPage.getRolesListAccordingtoSection(sSecID);

		}
		//Shyam_09032022

		public void verifyReassignButtonInAssignmentTabsSection(String sSection, String sRole){

			objAssignmentTabPage.verifyReassignButtonInAssignmentTabsSection(sSection, sRole);

		}

		//Shyam_14032022

		public void verifyOfferButtonInAssignmentTabsSection(String sSection, String sRole){

			objAssignmentTabPage.verifyOfferButtonInAssignmentTabsSection(sSection, sRole);

		}

		//Shyam_14032022

		public void clickOfferInAssignmentsTable(String sSection, String sTableColumn, String sTableValue) {

			objAssignmentTabPage.expandAssignmentTabHeaders(sSection);

			// take action in assignment table

			objAssignmentTabPage.clickOfferInAssignmentsTable(sSection, sTableColumn, sTableValue, "Action");

		}

		//Shyam_14032022

		public void reassignOnOfferPopup(String sReassignTo) {

			objAssignmentTabPage.setReassignToOnPopUp(sReassignTo);

			objAssignmentTabPage.clickOfferButtonOnPopUp();

		}

		//Shyam_14032022

		public void clickCancelOfferedInAssignmentsTable(String sSection, String sTableColumn, String sTableValue) {

			objAssignmentTabPage.expandAssignmentTabHeaders(sSection);

			objAssignmentTabPage.clickCancelOfferedInAssignmentsTable(sSection, sTableColumn, sTableValue, "Action");

		}

		public void verifyReassignButtonNotAvailableInAssignment(String sRole)

		{

			objAssignmentTabPage.verifyReassignButtonNotAvailableInAssignment(sRole);

		}

		//Shyam_14032022

		public void verifyAcceptDeclineButtonInAssignmentTabsSection(String sSection, String sRole, String sOfferedTo){

			objAssignmentTabPage.verifyAcceptDeclineButtonInAssignmentTabsSection(sSection, sRole, sOfferedTo);

		}

		//Shyam_24032022

		public String getUserNameFromAssignmentSectionTableOfaRole(String sSection, String sRole) {

			return objAssignmentTabPage.getUserNameFromAssignmentSectionTableOfaRole(sSection, sRole);

		}

		//Shyam_25032022

		public void verifyReassignmentPopUp(String sSection, String sRole) {

			objAssignmentTabPage.verifyReassignmentPopUp(sSection, sRole);

		}

		public boolean verifyDelegatedUserIsPresent(String sTableInSection, String UserName) 

		{

			return objAssignmentTabPage.verifyDelegatedUserIsPresent(sTableInSection, UserName);

		}

		//Shyam_25052022

		public void verifyOfferedToUserInAssignmentTabsSection(String sSection, String sRole, String sOfferedToUser){

			objAssignmentTabPage.verifyOfferedToUserInAssignmentTabsSection(sSection, sRole, sOfferedToUser);

		}

		//Shyam_23062022

		public void verifyNonExistanceOfReassignButton(String sTableInSection, String sRoleValue) 

		{

			objAssignmentTabPage.verifyNonExistanceOfReassignButton(sTableInSection, sRoleValue);

		}

		//Shyam_20072022	

		public void verifyOnlyOneRoleIsPresentUnderSection(String sTableInSection, String sRoleValue) {

			objAssignmentTabPage.expandAssignmentTabHeaders(sTableInSection);

			objAssignmentTabPage.verifyOnlyOneRoleIsPresentUnderSection(sTableInSection, sRoleValue);

		}

		public void selectAndRemoveApproversFromAssignmentsTable(String sAssignmentSection, String sTableColumn,String sTableValue, String sBUUserName)

		{

			objAssignmentTabPage.expandAssignmentTabHeaders(sAssignmentSection);

			objAssignmentTabPage.selectApproverInAssignmentsTable_BuUser(sAssignmentSection, sTableColumn, sTableValue, "Action", sBUUserName);	//Here "Action" used to find the black coulmn number

			objAssignmentTabPage.clickOnRemoveButtonInAssignmentTab();	

		}

		public void reassignAllUsingRole(String sRole,String section)

		{

			 objAssignmentTabPage.reassignAllUsingRole(sRole,section);

		}

		public void verifyRemoveButtonNotAvailableInAssignment(String section,String sRole)

		{

			objAssignmentTabPage.verifyRemoveButtonNotAvailableInAssignment(section,sRole);

		}

		public void verifyRoleIsNotPresentUnderSection(String sTableInSection, String sRoleValue)

		{

			objAssignmentTabPage.expandAssignmentTabHeaders(sTableInSection);		

			String[] aRoleValues = sRoleValue.split("~");

			for (String sRoleValueName : aRoleValues)

			{

				objUtilities.logReporter("verify  role : "+sRoleValue+" is present under "+sTableInSection+" verifyRoleIsPresent()'",! objAssignmentTabPage.verifyRoleIsPresent(sTableInSection, sRoleValueName), false);

			}

		}

		public void VerifyRoleInAddApproverPopupInAssignmentTab(String sRoleName) {

			objAssignmentTabPage.clickAddButtonOnaddApproverPopUp();

			objAssignmentTabPage.VerifyRoleInAddApproverPopupInAssignmentTab(sRoleName);

		}

		public String getBUsernameFromApproversSectionInAssigment(String sTableInSection, String sTableColumn, String sTableValue,String sTableActionColumn) {

			objUtilities.activateTab("Assignments");

			objAssignmentTabPage.expandAssignmentTabHeaders(sTableInSection);

			return objAssignmentTabPage.getBUsernameFromApproversSectionInAssigment(sTableInSection, sTableColumn, sTableValue,

					"Action");

		}

		public String getSectionName(String sRole)

		{

			return objAssignmentTabPage.getSectionName(sRole);

		}

		public void reassignAllUsingRoleExt(String sRole,String section,String sUserName)

		{

			objAssignmentTabPage.reassignAllUsingRoleExt(sRole, section, sUserName);

		}

		public void verifyMessageTextForAssignmentPage(String sAssignmentTxt) {

			objAssignmentTabPage.verifyMessageTextForAssignmentPage(sAssignmentTxt);

		}

		public int reassignAllUsingSection(String section)

		{

			objAssignmentTabPage.expandAssignmentTabHeaders(section);	

			return objAssignmentTabPage.reassignAllUsingSection(section);

		}
	 
}