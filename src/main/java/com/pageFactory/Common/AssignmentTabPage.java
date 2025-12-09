package com.pageFactory.Common;

import java.util.List;
import com.microsoft.playwright.Locator;
import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

public class AssignmentTabPage {

	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;

	private Locator btnPopUpReassign;

	public AssignmentTabPage(Pojo objPojo) {
		this.objPojo = objPojo;
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		btnPopUpReassign = objPojo.getPage().locator("//h4[text()='Reassignment' and @class='modal-title']/ancestor::div[@class='modal-content']//input[@value='Reassign']");
	}

	public void expandAssignmentTabHeaders(String sHeader) {
		int iCounter = 0;
		boolean bResult = false;
		String headerXpath = "//div[@id='accordion']//a[contains(text(),'" + sHeader + "')]";
		Locator objHeader = objPojo.getPage().locator(headerXpath);
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementToBeClickable(headerXpath), false);

		if (objWrapperFunctions.getAttributeValue(headerXpath, "aria-expanded").equalsIgnoreCase("false")) {
			do {
				objWrapperFunctions.click(headerXpath);
				if (objWrapperFunctions.getAttributeValue(headerXpath, "aria-expanded").equalsIgnoreCase("true")) {
					bResult = true;
					break;
				}
				iCounter++;
			} while (objWrapperFunctions.getAttributeValue(headerXpath, "aria-expanded").equalsIgnoreCase("false") || iCounter < 5);
			objUtilities.logReporter("Expand assignment tab header '" + sHeader + "' in method 'expandAssignmentTabHeaders'.", bResult, false);
		} else {
			objUtilities.logReporter("Already Expanded tooling request assignment tab header '" + sHeader + "' in method 'expandAssignmentTabHeaders'.", true, false);
		}
	}

	public void clickReassignInAssignmentsTable(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn, String sExpAssignedUser) {
		int iCounter = 0;
		sTableInSection = sTableInSection.replace(" ", "");
		String tableXpath = "//div[@id='" + sTableInSection + "']//table";
		objUtilities.logReporter("Waiting for element to be clickable : clickReassignInAssignmentsTable() ", objWrapperFunctions.waitForElementPresence(tableXpath), false);
		Locator objWETable = objPojo.getPage().locator(tableXpath);
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No Column found in table : clickReassignInAssignmentsTable()", false, false);
		int iRow = objWrapperFunctions.getTableRowWithExactname(objWETable, iCol, sTableValue);

		Locator objWEUser = null;
		if (iRow != 0) {
			do {
				objWEUser = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
						objWrapperFunctions.getTableColumn(objWETable, "User"), "");
				if (objWrapperFunctions.getText(objWEUser, "text").equalsIgnoreCase(sExpAssignedUser))
					iRow = iRow + 1;
				else
					break;
			} while (objWrapperFunctions.getTableRowCount(objWETable) >= iRow);

			Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
					objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
			objUtilities.waitFor(5);
			objUtilities.logReporter("Click on Reassign button ", objWrapperFunctions.click(objWEAction), false);
			objUtilities.waitFor(5);
		}
		objUtilities.logReporter(
				"Click Reassign button against value " + sTableValue + " in method 'clickReassignInAssignmentsTable'",
				objWrapperFunctions.checkElementDisplyed(btnPopUpReassign), false);
	}

	public int clickReassignInAssignmentsTable1(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn) {
		int iCounter = 0;
		sTableInSection = sTableInSection.replace(" ", "");
		String tableXpath = "//div[@id='" + sTableInSection + "']//table";
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementPresence(tableXpath), false);
		Locator objWETable = objPojo.getPage().locator(tableXpath);
		int iRow = 8;
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
				objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
		do {
			objWrapperFunctions.click(objWEAction);
			if (objWrapperFunctions.checkElementDisplyed(btnPopUpReassign))
				break;
			iCounter++;
		} while (!objWrapperFunctions.checkElementDisplyed(btnPopUpReassign) || iCounter < 5);
		objUtilities.logReporter("Click Reassign button against value " + sTableValue + " in method 'clickReassignInAssignmentsTable'",
				objWrapperFunctions.checkElementDisplyed(btnPopUpReassign), false);
		return iRow;
	}

	public void clickReassignInAssignmentsTable_BuUser(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn, String sBUUserName) {
		boolean bResult = false;
		int iCounter = 0;
		sTableInSection = sTableInSection.replace(" ", "");
		String tableXpath = "//div[@id='" + sTableInSection + "']//table";
		objUtilities.logReporter("Waiting for element to be clickable : clickReassignInAssignmentsTable_BuUser()", objWrapperFunctions.waitForElementPresence(tableXpath), false);
		Locator objWETable = objPojo.getPage().locator(tableXpath);
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRow = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sTableValue);
		int iRowForBUUser = 0;
		for (int i = iRow; i <= iTotalRowCount; i++) {
			String userXpath = "//div[@id='" + sTableInSection + "']//table//tr["+i+"]//td["+iColForUser+"]//a";
			Locator objWEUserName = objPojo.getPage().locator(userXpath);
			boolean bUser = objWrapperFunctions.getText(objWEUserName, "text").contains(sBUUserName);
			if (bUser) {
				iRowForBUUser = i;
				break;
			}
		}
		if (iRowForBUUser == 0) {
			objUtilities.logReporter(
					"The expected user '"+sBUUserName+"' not found: clickReassignInAssignmentsTable_BuUser()", false, false);
		}
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRowForBUUser,
				objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
		do {
			objWrapperFunctions.click(objWEAction);
			if (objWrapperFunctions.checkElementDisplyed(btnPopUpReassign)) {
				bResult = true;
				break;
			}
			iCounter++;
		} while (!objWrapperFunctions.checkElementDisplyed(btnPopUpReassign) || iCounter < 5);
		if (objWrapperFunctions.checkElementDisplyed(btnPopUpReassign))
			bResult = true;
		objUtilities.logReporter(
				"Click Reassign button against value " + sTableValue + " in method 'clickReassignInAssignmentsTable'",
				bResult, false);
	}

	public void verifyReassignButtonOnPopUpDisplayed() {
		objUtilities.logReporter("Reassign button present on Reassignment pop up in method 'verifyReassignButtonOnPopUpDisplayed'",
				objWrapperFunctions.checkElementDisplyed(btnPopUpReassign), false);
	}

	public void setReassignToOnPopUp(String sReassignTo) {
		if (sReassignTo.equals("")) {
			sReassignTo = objWrapperFunctions.GetUserName();
		}
		String userNameTextFieldXpath = "//*[text()='Search by UserName/UserID']/parent::div//following-sibling::input[@id='userDet']";
		Locator userNameTextFieldOnReassignment = objPojo.getPage().locator(userNameTextFieldXpath);
		objUtilities.logReporter("Set User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ",
				objWrapperFunctions.setText(userNameTextFieldOnReassignment, sReassignTo.trim()), false);

		String listXpathValue = "//ul/li[contains(text(),'"+sReassignTo+"')]";
		Locator listLocator = objPojo.getPage().locator(listXpathValue);

		if (!objWrapperFunctions.checkElementDisplyed(listLocator))
			listLocator = objPojo.getPage().locator("//ul/li/a[contains(text(),'"+sReassignTo+"')]");

		objUtilities.waitFor(4);
		if (objWrapperFunctions.waitForElementPresence(listLocator))
			objUtilities.logReporter("Select User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ", objWrapperFunctions.click(listLocator), false);
		else {
			objWrapperFunctions.ActionKeyBoardOperations("downkey");
			objUtilities.waitFor(2);
			objWrapperFunctions.ActionKeyBoardOperations("tab");
		}
		objUtilities.waitTillPageLoad(null);
	}

	public void clickReassignButtonOnPopUp() {
		objUtilities.logReporter("Click Reassign button present on Reassignment pop up in method clickReassignButtonOnPopUp",
				objWrapperFunctions.click(btnPopUpReassign), false);
		objUtilities.waitTillPageLoad(null);
	}

	public void verifyValueInAssignmentsTable(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn, String sValueToVerify) {
		if (sValueToVerify.equals("")) {
			sValueToVerify = objWrapperFunctions.GetUserName();
		}
		String tableXpath = "//div[@id='" + sTableInSection.replace(" ", "") + "']//table";
		Locator objWETable = objPojo.getPage().locator(tableXpath);
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No COlumn found in table", false, false);
		int iRow = objWrapperFunctions.getTableRow(objWETable, iCol, sTableValue);

		Locator actionLocator = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
				objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "");
		objUtilities.logReporter("Verify value in table " + sValueToVerify + " in method verifyValueInAssignmentsTable",
				objWrapperFunctions.getText(actionLocator, "Text").contains(sValueToVerify), false);
	}

	public void verifyPopUpFields(String sPopUpHeader, String sFieldLabel) {
		String objPopUpFieldXpath = "//h4[text()='" + sPopUpHeader + "' and @class='modal-title']/ancestor::div[@class='modal-content']//label[contains(text(), '"
				+ sFieldLabel + "')]/following-sibling::div/*[contains(@class,'form-control')]";
		Locator objPopUpField = objPojo.getPage().locator(objPopUpFieldXpath);
		if (!objWrapperFunctions.checkElementDisplyed(objPopUpField))
			objPopUpField = objPojo.getPage().locator("//h4[text()='" + sPopUpHeader + "' and @class='modal-title']/ancestor::div[@class='modal-content']//label[contains(text(), '"
					+ sFieldLabel + "')]/following-sibling::div/*[contains(@class,'input-group')]");
		objUtilities.logReporter("Field " + sFieldLabel + " present on popup with header " + sPopUpHeader + " in method verifyPopUpFields",
				objWrapperFunctions.checkElementDisplyed(objPopUpField), false);
	}

	public void verifyPopUpIsDisplayed(String sPopUpHeader) {
		String objPopUpXpath = "//h4[text()='" + sPopUpHeader + "' and @class='modal-title']/ancestor::div[@class='modal-content']";
		Locator objPopUp = objPojo.getPage().locator(objPopUpXpath);
		objUtilities.logReporter("Verify pop up displayed with header " + sPopUpHeader + " in method verifyPopUpIsDisplayed",
				objWrapperFunctions.checkElementDisplyed(objPopUp), false);
	}

	public void verifyMessageTextForAssignmentDrivers(String sAssignmentTxt) {
		Locator objAssignmentTxt = objPojo.getPage().locator("//div[@id='mainpageDiv']//div[contains(text(),'The Assignment Drivers are not yet populated.')]");
		objUtilities.logReporter("Verify message text for Assignment drivers " + sAssignmentTxt + "",
				objWrapperFunctions.checkElementDisplyed(objAssignmentTxt), false);
	}

	public void verifyMessageTextForBUCustomer(String sBUCustomerTxt) {
		Locator objBUCustomerTxt = objPojo.getPage().locator("//div[@class='box-body table-responsive scroll-style no-padding']");
		objUtilities.logReporter("Verify message text for BU Customer " + sBUCustomerTxt + "",
				objWrapperFunctions.getAttributeValue(objBUCustomerTxt, "innerText").contains(sBUCustomerTxt), false);
	}

	public void setReassignToOnPopUp_1(String sReassignTo) {
		if (sReassignTo.equals(""))
			sReassignTo = objWrapperFunctions.GetUserName();

		Locator lblReassignForm = objPojo.getPage().locator("//input[@id='EmployeeName']");
		String strReassignForm = objWrapperFunctions.getAttributeValue(lblReassignForm, "value");
		if (!strReassignForm.trim().equals(sReassignTo)) {
			String userNameTextFieldXpath = "//*[text()='Search by UserName/UserID']/parent::div//following-sibling::input[@id='userDet']";
			Locator userNameTextFieldOnReassignment = objPojo.getPage().locator(userNameTextFieldXpath);
			objUtilities.logReporter("Set User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ",
					objWrapperFunctions.setText(userNameTextFieldOnReassignment, sReassignTo.trim()), false);

			String listXpathValue = "//ul/li[contains(text(),'"+sReassignTo+"')]";
			Locator listLocator = objPojo.getPage().locator(listXpathValue);
			if (!objWrapperFunctions.checkElementDisplyed(listLocator))
				listLocator = objPojo.getPage().locator("//ul/li/a[contains(text(),'"+sReassignTo+"')]");

			objUtilities.waitFor(2);
			objUtilities.logReporter("Select User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ",
					objWrapperFunctions.click(listLocator), false);
			this.clickReassignButtonOnPopUp();
		} else {
			Locator btnClose = objPojo.getPage().locator("//h4[text()='Reassignment']/preceding::button[@class='close'][1]");
			objUtilities.logReporter("Click Close button present on Reassignment pop up in method :setReassignToOnPopUp_1()",
					objWrapperFunctions.click(btnClose), false);
		}
	}

	public void clickOnRemoveRevisedItem() {
		Locator btnRemoveRevisedItem = objPojo.getPage().locator("//a[text()='Remove Revised Item']");
		objUtilities.logReporter("Click on Remove Revised Item button : clickOnRemoveRevisedItem()",
				objWrapperFunctions.click(btnRemoveRevisedItem), false);
	}

	public void selectApproverInAssignmentsTable_BuUser(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn, String sBUUserName) {
		sTableInSection = sTableInSection.replace(" ", "");
		String tableXpath = "//div[@id='"+sTableInSection+"']//table";
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(tableXpath), false);
		Locator objWETable = objPojo.getPage().locator(tableXpath);
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("The expected column not found: selectApproverInAssignmentsTable_BuUser()", false, false);

		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRowForRole = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sTableValue);

		int iRowForBUUser = 0;
		for (int i = iRowForRole; i <= iTotalRowCount; i++) {
			String userXpath = "//div[@id='" + sTableInSection + "']//table//tr["+i+"]//td["+iColForUser+"]//a";
			Locator objWEUserName = objPojo.getPage().locator(userXpath);
			boolean bUser = objWrapperFunctions.getText(objWEUserName, "text").contains(sBUUserName);
			if (bUser) {
				iRowForBUUser = i;
				break;
			}
		}
		if (iRowForBUUser == 0) {
			objUtilities.logReporter("The expected row with user not found: selectApproverInAssignmentsTable_BuUser()", false, false);
		}
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRowForBUUser,
				objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn) + 1, "checkbox");
		objUtilities.logReporter("Click checkbox of the user "+sBUUserName+" value " + sTableValue + " in method selectApproverInAssignmentsTable_BuUser()", objWrapperFunctions.selectCheckBox(objWEAction, true), false);
	}

	public void clickOnRemoveBtnUnderAssignmentTab() {
		Locator btnRemove = objPojo.getPage().locator("//a[@id='Remove_8']");
		if (!objWrapperFunctions.checkElementDisplyed(btnRemove))
			btnRemove = objPojo.getPage().locator("//a[@id='Remove_1']");
		objUtilities.logReporter("Click on Remove button under Approvers:  clickOnRemoveBtnUnderAssignmentTab()", objWrapperFunctions.click(btnRemove), false);
	}

	public void verifyApproverIsRemoved(String sRoleValue, String sBUUserName) {
		boolean bResult = false;
		Locator objTable = objPojo.getPage().locator("//div[@id='Approvers']//table");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Role");
		if (iCol == 0)
			objUtilities.logReporter("The expected column not found: verifyApproverIsRemoved()", false, false);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRowForRole = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sRoleValue);

		if (iRowForRole > 0) {
			for (int i = iRowForRole; i <= iTotalRowCount; i++) {
				if (objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sRoleValue) > 0) {
					Locator objWEUserName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i, iColForUser, "button");
					boolean bUser = objWrapperFunctions.getText(objWEUserName, "text").contains(sBUUserName);
					if (bUser) {
						bResult = false;
						break;
					} else {
						bResult = true;
					}
				} else {
					break;
				}
			}
			objUtilities.logReporter("verify  Role "+sRoleValue+" with User "+sBUUserName+" is Removed successfully. : verifyApproverIsRemoved()", bResult, false);
		} else {
			objUtilities.logReporter("verify  Role "+sRoleValue+" with User "+sBUUserName+" is Removed successfully. : verifyApproverIsRemoved()", true, false);
		}
	}

	public void verifyCheckBOxForApproverIsPresent(String sTableInSection, String sTableValue, String sBUUserName) {
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Role");
		if (iCol == 0)
			objUtilities.logReporter("The expected column not found: verifyCheckBOxForApproverIsPresent()", false, false);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRowForRole = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sTableValue);

		int iRowForBUUser = 0;
		for (int i = iRowForRole; i <= iTotalRowCount; i++) {
			Locator objWEUserName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i, iColForUser, "button");
			boolean bUser = objWrapperFunctions.getText(objWEUserName, "text").contains(sBUUserName);
			if (bUser) {
				iRowForBUUser = i;
				break;
			}
		}
		if (iRowForBUUser == 0) {
			objUtilities.logReporter("The expected row with user not found: selectApproverInAssignmentsTable_BuUser()", false, false);
		}
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRowForBUUser,
				objWrapperFunctions.getTableColumn(objWETable, "Action") + 1, "checkbox");
		objUtilities.logReporter("verify  checkbox of the user "+sBUUserName+" and Role " + sTableValue + " is present in method selectApproverInAssignmentsTable_BuUser()", objWrapperFunctions.checkElementDisplyed(objWEAction), false);
	}

	public void verifyRoleAndRespectiveUserIsPresent(String sTableInSection, String sRoleValue, String sBUUserName) {
		boolean bResult = false;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Role");
		if (iCol == 0)
			objUtilities.logReporter("The expected column not found: verifyRoleAndRespectiveUserIsPresent()", false, false);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRowForRole = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sRoleValue);

		for (int i = iRowForRole; i <= iTotalRowCount; i++) {
			Locator objWEUserName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i, iColForUser, "button");
			boolean bUser = objWrapperFunctions.getText(objWEUserName, "text").contains(sBUUserName);
			if (bUser) {
				bResult = true;
				break;
			}
		}
		objUtilities.logReporter("verify  role "+sRoleValue+" and User "+sBUUserName+" is present under "+sTableInSection+" verifyRoleAndRespectiveUserIsPresent()", bResult, false);
	}

	public void closeReassignPopUp() {
		Locator btnClose = objPojo.getPage().locator("//h4[text()='Reassignment']/preceding::button[@class='close'][1]");
		objUtilities.logReporter("Click Close button present on Reassignment pop up in method :setReassignToOnPopUp_1()",
				objWrapperFunctions.click(btnClose), false);
	}

	public void verifyTableHeadersInAssignmentSection(String sSection, String sTableHeader) {
		String[] headerList = sTableHeader.split("~");
		for (String header : headerList) {
			Locator objTableHeader = objPojo.getPage().locator("//div[@id='" + sSection.replace(" ", "") + "']//table//tr/th[contains(text(),'" + header + "')]");
			objUtilities.logReporter("Verified " + header + " is displayed under " + sSection + " section", objWrapperFunctions.checkElementDisplyed(objTableHeader),
					false);			
		}
	}

	public void verifySections(String sHeaderSection) {
		String[] headerList = sHeaderSection.split("~");
		for (String header : headerList) {
			Locator objHeader = objPojo.getPage().locator("//a[contains(text(),'" + header + "')]");
			objUtilities.logReporter("Verify " + header + " section is displayed on Assignment Tab: verifySections()",
					objWrapperFunctions.checkElementDisplyed(objHeader), false);
		}
	}

	public void verifyDataInAssignmentDriversTable(String sTableHeader, String svalues) {
		String[] headerList = sTableHeader.split("~");
		String[] valueList = svalues.split("~");
		Locator objTable = objPojo.getPage().locator("//div[@id='tab_1']//table[@class='table table-striped table-hover'][1]");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		for (int i = 0; i < valueList.length; i++) {
			int iCol = objWrapperFunctions.getTableColumn(objWETable, headerList[i]);
			int iRow = objWrapperFunctions.getTableRow(objWETable, iCol, valueList[i]);
			Locator cellLocator = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
					objWrapperFunctions.getTableColumn(objWETable, headerList[i]), "");
			objUtilities.logReporter("Verify value in table " + valueList[i] + " under column " + headerList[i]
				+ " : verifyDataInAssignmentDriversTable()",
				objWrapperFunctions.getText(cellLocator, "Text").equalsIgnoreCase(valueList[i]), false);
		}
	}

	public boolean verifyRoleIsPresent(String sTableInSection, String sRoleValue) {
		boolean bResult = false;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		objWrapperFunctions.waitForElementPresence(objTable);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Role");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		for (int i = 1; i <= iTotalRowCount; i++) {
			Locator objWERoleName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i,iCol, "role");
			boolean bUser = objWrapperFunctions.getText(objWERoleName, "text").contains(sRoleValue);
			if (bUser) {
				bResult = true;
				break;
			}
		}
		return bResult;
	}

	public void verifyAddButtonOnPopUpDisplayed() {
		Locator addButton = objPojo.getPage().locator("//h4[text()='Add Assignment' and @class='modal-title']/ancestor::div[@class='modal-content']//following-sibling::div//input[@value='Add']");
		objUtilities.logReporter("Verify Add button is present on Assignment pop up : verifyAddButtonOnPopUpDisplayed",
				objWrapperFunctions.checkElementDisplyed(addButton), false);
	}

	public void clickOnPageButton(String sText) {
		objUtilities.waitFor(4);
		Locator Btn = objPojo.getPage().locator("//input[@value='"+sText+"']");
		if (!objWrapperFunctions.checkElementDisplyed(Btn))
			Btn = objPojo.getPage().locator("//a[text()='"+sText+"']");
		objUtilities.logReporter("Click on "+sText+" Button : clickOnPageButton", objWrapperFunctions.click(Btn), false);
	}

	public void setValueOnPopUp(String labelName, String value) {
		Locator cmbRole = objPojo.getPage().locator(
			"//h4[text()='Add Assignment' and @class='modal-title']/ancestor::div[@class='modal-content']//label[contains(text(), '"+labelName+"')]/following-sibling::div/*[contains(@class,'form-control')]");
		objUtilities.logReporter("Click on Assignment Dropdown",objWrapperFunctions.click(cmbRole),false);
		objUtilities.logReporter("Set value in "+labelName+" as " + value + " in method setValueOnPopUp",
			objWrapperFunctions.selectDropDownOptionByContains(cmbRole, value), false);
	}

	public void clickAddButtonOnPopUpDisplayed() {
		Locator addButton = objPojo.getPage().locator("//h4[text()='Add Assignment' and @class='modal-title']/ancestor::div[@class='modal-content']//following-sibling::div//input[@value='Add']");
		objUtilities.logReporter("Click on Add button on Assignment pop up : 'clickAddButtonOnPopUpDisplayed'",
			objWrapperFunctions.click(addButton), false);
	}

	public void clickReassignInAssignmentsTableForExactName(String sTableInSection, String sTableColumn, String sTableValue, String sTableActionColumn) {
		int iCounter = 0;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sTableInSection + "']//table");
		objUtilities.logReporter("Waiting for element to dispaly", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumnWithExactName(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No column found in table", false, false);
		int iRow = objWrapperFunctions.getTableRow(objWETable, iCol, sTableValue);
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
			objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
		if (iRow == 0)
			objUtilities.logReporter(sTableValue + " value is not diplayed under "+sTableColumn+" column in  "+sTableInSection+" Section in method 'clickReassignInAssignmentsTable'",
				false, false);
		else
		do {
			objWrapperFunctions.click(objWEAction);
			if (objWrapperFunctions.checkElementDisplyed(btnPopUpReassign))
				break;
			iCounter++;
		} while (!objWrapperFunctions.checkElementDisplyed(btnPopUpReassign) || iCounter < 5);
		objUtilities.logReporter(
			"Click Reassign button against value " + sTableValue + " in method 'clickReassignInAssignmentsTable'",
			objWrapperFunctions.checkElementDisplyed(btnPopUpReassign), false);
	}

	public void clicktoAddBUCustomer() {
		Locator AddBuCustomerbtn = objPojo.getPage().locator("//input[@value='Add BU Customer']");
		objUtilities.logReporter("Click to Add BU Customer Button:clicktoAddBUCustomer()", objWrapperFunctions.click(AddBuCustomerbtn), false);
	}

	public void VerifyAddBuCustomerPopField(String sfield) {
		Locator verifyxpath = objPojo.getPage().locator("//h4[@id='myModalLabel' and text()='Add BU Customer']");
		objUtilities.logReporter("Waiting for element to dispaly", objWrapperFunctions.waitForElementPresence(verifyxpath), false);
		objUtilities.logReporter("Verify "+sfield+"is displayed successfully:VerifyAddBuCustomerPopFiedls()", objWrapperFunctions.getText(verifyxpath, "text").contains(sfield), false);
	}

	public void VerifyPBG_NameDispalyedinAddBuCustomer(String sStringverify) {
		Locator verifyxpath = objPojo.getPage().locator("//h4[@id='myModalLabel' and text()='Add BU Customer']/following::div[@class='modal-body']//label[contains(text(),'PBG_Name')]");
		objUtilities.logReporter("Waiting for element to dispaly", objWrapperFunctions.waitForElementPresence(verifyxpath), false);
		objUtilities.logReporter("Verify "+sStringverify+"is displayed VerifyPBG_NameDispalyedinAddBuCustomer()", objWrapperFunctions.getText(verifyxpath, "text").contains(sStringverify), false);
	}

	public void VerifyAddbtnDispalyedinAddBuCustomer() {
		Locator Addbtn = objPojo.getPage().locator("//h4[@id='myModalLabel' and text()='Add BU Customer']/following::div[@class='modal-body']//input[@value='Add']");
		objUtilities.logReporter("Verify Add Button is displayed VerifyAddbtnDispalyedinAddBuCustomer()", objWrapperFunctions.checkElementDisplyed(Addbtn), false);
	}

	public void selectAddBucustomerdropdownValue(String sDropdownvalue) {
		Locator Addbtn = objPojo.getPage().locator("//h4[@id='myModalLabel' and text()='Add BU Customer']/following::div[@class='modal-body']//input[@value='Add']");
		Locator selectvalue = objPojo.getPage().locator("//h4[@id='myModalLabel' and text()='Add BU Customer']/following::select[@id='PBG_Name']");
		objUtilities.logReporter("Select value as:"+sDropdownvalue+"in selectAddBucustomerdropdownValue()", objWrapperFunctions.selectDropDownOption(selectvalue, sDropdownvalue, "text"),false);
		objUtilities.logReporter("Click on Add button : selectAddBucustomerdropdownValue()", objWrapperFunctions.click(Addbtn),false);
	}

	public void VerifyRemoveAllbtn() {
		Locator Removeallbtn = objPojo.getPage().locator("//div[@class='box-body table-responsive scroll-style no-padding']//table/thead/tr/th/a[@id='Removeall']");
		objUtilities.logReporter("Verify Removeall button is displayed: VerifyRemoveAllandRemovebtn()", objWrapperFunctions.checkElementDisplyed(Removeallbtn), false);
	}

	public void VerifyRemovebtn() {
		Locator Removebtn = objPojo.getPage().locator("//div[@class='box-body table-responsive scroll-style no-padding']//table/tbody/tr/td/a[@id='removeRecord']");
		objWrapperFunctions.waitForElementPresence(Removebtn);
		objUtilities.logReporter("Verify Remove button is displayed: VerifyRemoveAllandRemovebtn()", objWrapperFunctions.checkElementDisplyed(Removebtn), false);
	}

	public void checkHeaderIsexpanded(String sHeader) {
		Locator objHeader = objPojo.getPage().locator("//div[@id='accordion']//div[@id='"+sHeader+"']//thead//th[text()='User']");
		objUtilities.logReporter(sHeader+" tab header is already expanded : checkHeaderIsexpanded()", objWrapperFunctions.checkElementDisplyed(objHeader), false);
	}

	public void verifyAssignmentTabRessignButtonNotDisplayed(String strSection) {
		String str = strSection.replaceAll("\\s", "");
		Locator btnRessign = objPojo.getPage().locator("//div[@id='"+str+"']//input");
		objUtilities.logReporter("Verify Ressign button not displayed under"+  strSection + "' : verifyQualGPEAssignmentTabRessignButtonNotDisplayed()",
				!objWrapperFunctions.checkElementDisplyed(btnRessign), false);
	}

	public String getBUsernameFromApproversSection(String sTableInSection, String sTableColumn, String sTableValue,String sTableActionColumn) {
		String bUsername = null;
		String unName = objWrapperFunctions.GetUserName();
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sTableInSection + "']//table");
		objUtilities.logReporter("Waiting for element to be dispalyed", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No column found in table", false, false);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRow = objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sTableValue);

		for (int i = iRow; i <= iTotalRowCount; i++) {
			Locator objWEUser = objPojo.getPage().locator("//div[@id='" + sTableInSection + "']//table//tr["+i+"]//td["+iColForUser+"]//a");
			bUsername = objWrapperFunctions.getText(objWEUser, "text");
			if (!bUsername.equals(objWrapperFunctions.GetUserName())) {
				unName = bUsername ;
			}
		}
		return unName;
	}

	public void verifyFiistsection(String sectionName) {
		Locator header = objPojo.getPage().locator("//*[@id='dot']//table/thead/tr/th[contains(text(),'"+sectionName+"')]");
		objUtilities.logReporter("verify header "+sectionName+" in assignment tab ", objWrapperFunctions.checkElementDisplyed(header), false);
	}

	public void verifyFirstsectionValue(String sectionValue) {
		Locator header = objPojo.getPage().locator("//table/tbody/tr/td[contains(text(),'"+sectionValue+"')]");
		objUtilities.logReporter("verify "+sectionValue+" header in assignment tab ", objWrapperFunctions.checkElementDisplyed(header), false);
	}

	public void verifymsg() {
		Locator greenTickMark = objPojo.getPage().locator("//*[@id='dot']//div[@class='col-md-3']/label[contains(text(),' Approved in the last review.')]/img");
		objUtilities.logReporter("", objWrapperFunctions.checkElementDisplyed(greenTickMark), false);
		Locator redTickMark = objPojo.getPage().locator("//*[@id='dot']//div[@class='col-md-3']/label[contains(text(),' Disapproved in the last review.')]/img");
		objUtilities.logReporter("", objWrapperFunctions.checkElementDisplyed(redTickMark), false);
	}

	public void ClickToAddApproverInAssignmentTab() {
		Locator sAddbtn = objPojo.getPage().locator("//h4[text()='Add Assignment']/ancestor::div[@id='reassignModalContent']//input[@type='submit' and @value='Add']");
		if (!objWrapperFunctions.checkElementDisplyed(sAddbtn))
			sAddbtn = objPojo.getPage().locator("//input[@type='button' and @value='Add Approver']");
		objUtilities.logReporter(
				"Click Add button present on Add Approver pop up in method: clickAddButtonOnaddApproverPopUp()",
				objWrapperFunctions.click(sAddbtn), false);
		objUtilities.waitTillPageLoad(null);
	}

	public void SelectRoleAndUserInAddApproverPopupInAssignmentTab(String sRoleName, String sApproverName) {
		Locator Role = objPojo.getPage().locator("//h4[text()='Add Assignment']/following::div//label[contains(text(),'Role')]/following-sibling::div/select[@id='Role']");
		Locator Username = objPojo.getPage().locator("//h4[text()='Add Assignment']/following::div//label[contains(text(),'User Name')]/following-sibling::div/select[@id='UserName']");
		objUtilities.logReporter("Set value for Role in Add approver as " + sRoleName + "in method: SelectRoleAndUserInAddApproverPopupInAssignmentTab()",
			objWrapperFunctions.selectDropDownOption(Role, sRoleName, "Text"), false);

		if (sApproverName.equals("")) {
			sApproverName = objWrapperFunctions.GetUserName();
		}
		Locator userNameTextFieldOnReassignment = objPojo.getPage().locator("//*[text()='Search by UserName/UserID']/parent::div//following-sibling::input[@id='userDet']");
		objUtilities.logReporter("Set User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ",
			objWrapperFunctions.setText(userNameTextFieldOnReassignment, sApproverName.trim()), false);

		Locator listLocator = objPojo.getPage().locator("//ul/li[contains(text(),'"+sApproverName+"')]");
		if (!objWrapperFunctions.checkElementDisplyed(listLocator))
			listLocator = objPojo.getPage().locator("//ul/li/a[contains(text(),'"+sApproverName+"')]");

		objUtilities.waitFor(2);
		objUtilities.logReporter("Select User Name on Reassignment Pop-up :setUserNameOnReassignmet()  ",
			objWrapperFunctions.click(listLocator), false);

		objUtilities.waitTillPageLoad(null);
	}

	public void clickAddButtonOnaddApproverPopUp() {
		Locator sAddbtn = objPojo.getPage().locator("//h4[text()='Add Assignment']/ancestor::div[@id='reassignModalContent']//input[@type='submit' and @value='Add']");
		if (!objWrapperFunctions.checkElementDisplyed(sAddbtn))
			sAddbtn = objPojo.getPage().locator("//input[@type='button' and @value='Add Approver']");
		objUtilities.logReporter(
				"Click Add button present on Add Approver pop up in method: clickAddButtonOnaddApproverPopUp()",
				objWrapperFunctions.click(sAddbtn), false);
		objUtilities.waitTillPageLoad(null);
	}

	public String getRoleName() {
		Locator RoleName = objPojo.getPage().locator("//div[@id='lblRole']");
		objUtilities.logReporter("Verified Role Not Null :getRoleName()", objWrapperFunctions.getText(RoleName, "text") != null, false);
		return objWrapperFunctions.getText(RoleName, "text").trim();
	}

	public void verifyFYINotificationsMessageText() {
		Locator objFYINotificationTxt = objPojo.getPage().locator("//*[@id='FYINotifications']/div[contains(text(),'The Assignments are not yet populated')]");
		objUtilities.logReporter("Verify message text for FYI Notification The Assignments are not yet populated",
				objWrapperFunctions.checkElementDisplyed(objFYINotificationTxt), false);
	}

	public void expandAssignmentTabHeadersTab(String sHeader) {
		int iCounter = 0;
		boolean bResult = false;
		Locator objHeader = objPojo.getPage().locator("//*[@id='collapsed_4']");
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementToBeClickable("//*[@id='collapsed_4']"), false);
		if (objWrapperFunctions.getAttributeValue(objHeader, "aria-expanded").equalsIgnoreCase("false")) {
			do {
				objWrapperFunctions.click(objHeader);
				if (objWrapperFunctions.getAttributeValue(objHeader, "aria-expanded").equalsIgnoreCase("true")) {
					bResult = true;
					break;
				}
				iCounter++;
			} while (objWrapperFunctions.getAttributeValue(objHeader, "aria-expanded").equalsIgnoreCase("false") || iCounter < 5);
			objUtilities.logReporter("Expand assignment tab header '" + sHeader + "' in method 'expandAssignmentTabHeaders'.", bResult, false);
		} else {
			objUtilities.logReporter("Already Expanded tooling request assignment tab header " + sHeader + " in method expandAssignmentTabHeaders", true, false);
		}
	}

	public void clickOnRemoveButtonnUnderAssignmentTab() {
		Locator btnRemove = objPojo.getPage().locator("//a[@id='Remove_1']");
		objUtilities.logReporter("Click on Remove button under Approvers:  clickOnRemoveBtnUnderAssignmentTab()", objWrapperFunctions.click(btnRemove), false);
	}

	public void verifyReassignButtonAvailableInAssignment(String sRole) {
		Locator reassignBtn = objPojo.getPage().locator("//*[@id='Approvers']//tbody//td[contains(text(),'" + sRole + "')]//following-sibling::td//input[@value='Reassign']");
		objUtilities.logReporter("Successfully verified Reassign button is display against "+sRole+" in Assignmnet section:  verifyReassignButtonAvailableInAssignment()", objWrapperFunctions.checkElementDisplyed(reassignBtn), false);
	}

	public void ExpandSections(String sSectionName) {
		Locator section = objPojo.getPage().locator("//a[@href='#"+sSectionName+"']");
		String areaExpanded = objWrapperFunctions.getAttributeValue(section, "class");
		if (areaExpanded.contains("collapsed")) {
			objUtilities.logReporter("Click on " + sSectionName + " to expand :ExpandSections()",
					objWrapperFunctions.click(section), false);
		} else {
			objUtilities.logReporter(" " + sSectionName + " is already expanded :ExpandSections()", true, false);
		}
		objUtilities.waitFor(5);
	}

	public String getRolesListAccordingtoSection(String sSecID) {
		String sRoles = null;
		this.ExpandSections(sSecID);
		Locator TableHeader = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table");
		int iCol = objWrapperFunctions.getTableColumn(TableHeader, "Role");
		Locator TableData = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table/tbody");
		int iRowCount = objWrapperFunctions.getTableRowCount(TableData);
		for(int i = 1; i <= iRowCount; i++) {
			Locator sXpath = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table/tbody/tr["+i+"]/td["+iCol+"]");
			if(sRoles == null)
				sRoles = objWrapperFunctions.getText(sXpath, "text");
			else
				sRoles = sRoles+"~"+objWrapperFunctions.getText(sXpath, "text");
			objUtilities.logReporter("Get Role "+sRoles+" : getRolesListAccordingtoSection()", true , false);
		}
		if(sRoles==null)
			objUtilities.logReporter("Unable to get Role  "+sRoles+" : getRolesListAccordingtoSection()", false , false);
		return sRoles;
	}

	public void verifyReassignButtonInAssignmentTabsSection(String sSection, String sRole) {
		expandAssignmentTabHeaders(sSection);
		Locator table = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table");
		Locator weTable = table;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColAction = objWrapperFunctions.getTableColumn(weTable, "Action");
		int iRow = objWrapperFunctions.getTableRow(weTable, iColRole, sRole);
		Locator reassignBtn = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColAction+"]/input[@value='Reassign']");
		objUtilities.logReporter("verify Reassign Button In Assignment Tabs Section "+sSection+" at role "+sRole+" :verifyReassignButtonInAssignmentTabsSection()", objWrapperFunctions.checkElementDisplyed(reassignBtn) , false);
	}

	public void verifyOfferButtonInAssignmentTabsSection(String sSection, String sRole) {
		expandAssignmentTabHeaders(sSection);
		Locator table = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table");
		Locator weTable = table;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColAction = objWrapperFunctions.getTableColumn(weTable, "Action");
		int iRow = objWrapperFunctions.getTableRow(weTable, iColRole, sRole);
		Locator reassignBtn = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColAction+"]/input[@value='Offer']");
		objUtilities.logReporter("verify Reassign Button In Assignment Tabs Section "+sSection+" at role "+sRole+" : verifyOfferButtonInAssignmentTabsSection()", objWrapperFunctions.checkElementDisplyed(reassignBtn) , false);
	}

	public void clickOfferInAssignmentsTable(String sSection, String sTableColumn, String sTableValue, String sTableActionColumn) {
		boolean bResult = false;
		int iCounter = 0;
		Locator popupHeader = objPojo.getPage().locator("//h4[text()='Offer' and @class='modal-title']/ancestor::div[@class='modal-content']");
		sSection = sSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sSection + "']//table");
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No Column found in table", false, false);
		int iRow = objWrapperFunctions.getTableRowWithExactname(objWETable, iCol, sTableValue);
		Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
				objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
		if (iRow == 0)
			objUtilities.logReporter(sTableValue + " value is not diplayed under "+sTableColumn+" column in  "+sSection+" Section in method clickReassignInAssignmentsTable",
				false, false);												
		else
			do {
				objWrapperFunctions.click(objWEAction);
				if (objWrapperFunctions.checkElementDisplyed(popupHeader)) {
					bResult = true;
					break;
				}
				iCounter++;
			} while (iCounter < 5);
		objUtilities.logReporter(
			"Click Reassign button against value " + sTableValue + " in method clickReassignInAssignmentsTable",
			bResult, false);
	}

	public void clickOfferButtonOnPopUp() {
		Locator offerBtn = objPojo.getPage().locator("//h4[text()='Offer' and @class='modal-title']/ancestor::div[@class='modal-content']//input[@value='Offer']");
		objUtilities.logReporter("Click Offer button present on Offer pop up in method clickOfferButtonOnPopUp",
				objWrapperFunctions.click(offerBtn), false);
		objUtilities.waitTillPageLoad(null);
	}

	public void clickCancelOfferedInAssignmentsTable(String sSection, String sTableColumn, String sTableValue, String sTableActionColumn) {
		sSection = sSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sSection + "']//table");
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No Column found in table", false, false);
		int iRow = objWrapperFunctions.getTableRowWithExactname(objWETable, iCol, sTableValue);
		if (iRow == 0)
			objUtilities.logReporter(sTableValue + " value is not diplayed under "+sTableColumn+" column in  "+sSection+" Section in method clickReassignInAssignmentsTable",
				false, false);																		
		else{
			Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
					objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "button");
			objUtilities.logReporter(
					"Click Reassign button against value " + sTableValue + " in method clickReassignInAssignmentsTable",
					objWrapperFunctions.click(objWEAction), false);
			objUtilities.waitTillPageLoad(null);
		}
	}

	public void verifyOfferButtonInAssignmentsTable(String sSection, String sTableColumn, String sTableValue, String sTableActionColumn) {
		sSection = sSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sSection + "']//table");
		objUtilities.logReporter("Waiting for element to be clickable", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No Column found in table", false, false);
		int iRow = objWrapperFunctions.getTableRowWithExactname(objWETable, iCol, sTableValue);
		if (iRow == 0)
			objUtilities.logReporter(sTableValue + " value is not diplayed under "+sTableColumn+" column in  "+sSection+" Section in method clickReassignInAssignmentsTable",
				false, false);																		
		else{
			Locator objWEAction = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, iRow,
					objWrapperFunctions.getTableColumn(objWETable, sTableActionColumn), "checkbox");
			objUtilities.logReporter(
					"Click Reassign button against value " + sTableValue + " in method 'clickReassignInAssignmentsTable'",
					objWrapperFunctions.checkElementDisplyed(objWEAction), false);
		}
	}

	public void verifyReassignButtonNotAvailableInAssignment(String sRole) {
		Locator reassignBtn = objPojo.getPage().locator("//*[@id='Approvers']//tbody//td[contains(text(),'"+sRole+"')]//following-sibling::td//input[@value='Reassign']");
		objUtilities.logReporter("Successfully verified Reassign button is not display against "+sRole+" in Assignmnet section:  verifyReassignButtonNotAvailableInAssignment()",!objWrapperFunctions.checkElementDisplyed(reassignBtn), false);	
	}

	public void verifyAcceptDeclineButtonInAssignmentTabsSection(String sSection, String sRole, String sOfferedTo){
		expandAssignmentTabHeaders(sSection);
		Locator table = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table");
		Locator weTable = table;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColAction = objWrapperFunctions.getTableColumn(weTable, "Action");
		int iColOfferedTo = objWrapperFunctions.getTableColumn(weTable, "Offered To");
		int iRow = objWrapperFunctions.getTableRow(weTable, iColRole, sRole);
		int iRowOfferedTo = objWrapperFunctions.getTableRow(weTable, iColOfferedTo, sOfferedTo);
		objUtilities.logReporter("Verify offered to user as "+sOfferedTo+" : verifyAcceptDeclineButtonInAssignmentTabsSection()", iRowOfferedTo > 0, false);
		Locator acceptBtn = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColAction+"]/a[contains(text(),'Accept')]");
		objUtilities.logReporter("verify accept Button In Assignment Tabs Section "+sSection+" at role "+sRole+" : "
			+ "verifyAcceptDeclineButtonInAssignmentTabsSection()", objWrapperFunctions.checkElementDisplyed(acceptBtn) , false);
		Locator declineBtn = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColAction+"]/a[contains(text(),'Decline')]");
		objUtilities.logReporter("verify decline Button In Assignment Tabs Section "+sSection+" at role "+sRole+" :verifyAcceptDeclineButtonInAssignmentTabsSection()", objWrapperFunctions.checkElementDisplyed(declineBtn) , false);
	}

	public String getUserNameFromAssignmentSectionTableOfaRole(String sSection, String sRole) {
		expandAssignmentTabHeaders(sSection);
		Locator table = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table");
		Locator weTable = table;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColUser = objWrapperFunctions.getTableColumn(weTable, "User");
		int iRow = objWrapperFunctions.getTableRow(weTable, iColRole, sRole);
		Locator user = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColUser+"]/a");
		String sUserName = objWrapperFunctions.getText(user, "text");
		return sUserName != null ? sUserName : "";
	}

	public void verifyReassignmentPopUp(String sSection, String sRole) {
		expandAssignmentTabHeaders(sSection);
		clickReassignInAssignmentsTable(sSection, "Role", sRole, "Action");
		verifyPopUpIsDisplayed("Reassignment");
		Locator PageElement = objPojo.getPage().locator("//label[contains(text(),'Reassign To')]/following-sibling::div/select");
		objUtilities.logReporter("verify reassign to field is present on Resaaignment popup : verifyReassignmentPopUp",
			objWrapperFunctions.checkElementDisplyed(PageElement), false);
		String[] propertyArr = {"Role", "Reassign From"};
		for (String sProperty : propertyArr) {
			PageElement = objPojo.getPage().locator("//label[contains(text(),'"+sProperty+"')]/following-sibling::div/input");
			objUtilities.logReporter("verify "+sProperty+" is present on Resaaignment popup : verifyReassignmentPopUp",
				objWrapperFunctions.checkElementDisplyed(PageElement), false);
			objUtilities.logReporter("verify "+sProperty+" is readonly on Resaaignment popup : verifyReassignmentPopUp",
				!objWrapperFunctions.setText(PageElement, "ab"), false);
		}
		verifyReassignButtonOnPopUpDisplayed();
		PageElement = objPojo.getPage().locator("//label[contains(text(),'Search by UserName/UserID')]/following-sibling::div/div/input");
		objUtilities.logReporter("verify Search by UserName/UserID is present on Resaaignment popup : verifyReassignmentPopUp",
			objWrapperFunctions.checkElementDisplyed(PageElement), false);
	}

	public void clickReassignInAssignmentsTable(String sSection, String string, String sRole, String string2) {
	}

	public boolean verifyDelegatedUserIsPresent(String sTableInSection, String UserName) {
		boolean bResult = false;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Delegated to");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		for (int i = 1; i <= iTotalRowCount; i++) {
			Locator objWERoleName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i,iCol, "Delegatedto");
			boolean bUser = objWrapperFunctions.getText(objWERoleName, "text").contains(UserName);
			if (bUser) {
				bResult = true;
				break;
			}
		}
		return bResult;
	}

	public void verifyOfferedToUserInAssignmentTabsSection(String sSection, String sRole, String sOfferedToUser) {
		expandAssignmentTabHeaders(sSection);
		Locator table = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table");
		Locator weTable = table;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColOfferedTo = objWrapperFunctions.getTableColumn(weTable, "Offered To");
		int iRow = objWrapperFunctions.getTableRow(weTable, iColRole, sRole);
		Locator reassignBtn = objPojo.getPage().locator("//a[contains(text(),'"+sSection+"')]/ancestor::div[1]/following-sibling::div//table/tbody/tr["+iRow+"]/td["+iColOfferedTo+"]/a[contains(text(),'"+sOfferedToUser+"')]");
		objUtilities.logReporter("verify Reassign Button In Assignment Tabs Section "+sSection+" at role "+sRole+" : "
			+ "verifyOfferButtonInAssignmentTabsSection()", objWrapperFunctions.checkElementDisplyed(reassignBtn) , false);
	}

	public void verifyNonExistanceOfReassignButton(String sTableInSection, String sRoleValue) {
		expandAssignmentTabHeaders(sTableInSection);
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		Locator weTable = objTable;
		int iColRole = objWrapperFunctions.getTableColumn(weTable, "Role");
		int iColAction = objWrapperFunctions.getTableColumn(weTable, "Action");
		int iRowRole = objWrapperFunctions.getTableRow(weTable, iColRole, sRoleValue);
		Locator reassignBtn = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table/tbody/tr["+iRowRole+"]/td["+iColAction+"]/input[@value='Reassign']");
		objUtilities.logReporter("verify non existance of Reassign Button In Assignment Tabs Section "+sTableInSection+" at role "+sRoleValue+" : "
			+ "verifyNonExistanceOfReassignButton()", !objWrapperFunctions.checkElementDisplyed(reassignBtn) , false);
	}

	public void verifyOnlyOneRoleIsPresentUnderSection(String sTableInSection, String sRoleValue) {
		boolean bResult = false;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='"+sTableInSection+"']//table");
		objUtilities.logReporter("Waiting for element to present ", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, "Role");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		objUtilities.logReporter("verify only one role is present under section "+sTableInSection+" : verifyOnlyOneRoleIsPresentUnderSection()",
			iTotalRowCount == 1, false);
		for (int i = 1; i <= iTotalRowCount; i++) {
			Locator objWERoleName = objWrapperFunctions.getWebElementBasedOnActionInTableCell(objWETable, i,iCol, "role");
			boolean bUser = objWrapperFunctions.getText(objWERoleName, "text").contains(sRoleValue);
			if (bUser) {
				bResult = true;
				break;
			}
		}
		objUtilities.logReporter("verify only one role "+sRoleValue+" is present under section "+sTableInSection+" : verifyOnlyOneRoleIsPresentUnderSection()",
			bResult, false);
	}

	public void clickOnRemoveButtonInAssignmentTab() {
		Locator btnRemove = objPojo.getPage().locator("//table[@id='Tab_8']//tr/th/a[contains(text(),'Remove')]");
		objUtilities.logReporter("Click on Remove button under Approvers:  clickOnRemoveBtnUnderAssignmentTab()", objWrapperFunctions.click(btnRemove), false);	
	}

	public void reassignAllUsingRole(String sRole,String section) {
		String userName = "";
		Locator role = objPojo.getPage().locator("//td[text()='"+sRole+"']/parent::tr");
		List<Locator> l1 = objWrapperFunctions.getWebElementList("//td[text()='"+sRole+"']/parent::tr");
		for(Locator loc : l1) {  
			String rowCnt = objWrapperFunctions.getAttributeValue(loc, "rowIndex");
			userName = userName+"~"+objWrapperFunctions.getAttributeValue(objPojo.getPage().locator("//div[@id='"+section+"']//tbody/tr["+rowCnt+"]/td[1]/a"), "text");
		}
		String[] name = userName.split("~");
		for(int i=1; i<name.length; i++) {
			if(!name[i].equals(objWrapperFunctions.GetUserName())) {
				this.ExpandSections(section);
				this.clickReassignInAssignmentsTable_BuUser(section, "Role", sRole, "Action", name[i]);
				this.verifyPopUpIsDisplayed("Reassignment");
				this.setReassignToOnPopUp("");
				this.clickReassignButtonOnPopUp();
			}
		}
	}

	public void verifyRemoveButtonNotAvailableInAssignment(String Section, String sRole) {
		Locator reassignBtn = objPojo.getPage().locator("//*[@id='"+Section+"']//tbody//td[contains(text(),'"+sRole+"')]//following-sibling::td//input[@type='checkbox']");
		objUtilities.logReporter("Successfully verified remove  button is  not display against "+sRole+" in Assignmnet section:  verifyRemoveButtonNotAvailableInAssignment()",!objWrapperFunctions.checkElementDisplyed(reassignBtn), false);	
	}

	public void VerifyRoleInAddApproverPopupInAssignmentTab(String sRoleName) {
		String[] aRoles = sRoleName.split("~");
		for(String sRole : aRoles){
			Locator Role = objPojo.getPage().locator("//h4[text()='Add Assignment']/following::div//label[contains(text(),'Role')]/following-sibling::div/select[@id='Role']");
			objUtilities.logReporter("verified value for Role in Add approver as " + sRoleName + "in method: VerifyRoleInAddApproverPopupInAssignmentTab()",
				objWrapperFunctions.verifyDropDownOptionValues(Role, sRoleName), false);
		}
		Locator Closebutton= objPojo.getPage().locator("//*[@id='reassignModalContent']/div/button");
		objWrapperFunctions.click(Closebutton);
		objUtilities.waitFor(8);
	}

	public String getBUsernameFromApproversSectionInAssigment(String sTableInSection, String sTableColumn, String sTableValue,String sTableActionColumn) {
		String bUsername = null;
		sTableInSection = sTableInSection.replace(" ", "");
		Locator objTable = objPojo.getPage().locator("//div[@id='" + sTableInSection + "']//table");
		objUtilities.logReporter("Waiting for element to be dispalyed", objWrapperFunctions.waitForElementPresence(objTable), false);
		Locator objWETable = objTable;
		int iCol = objWrapperFunctions.getTableColumn(objWETable, sTableColumn);
		if (iCol == 0)
			objUtilities.logReporter("No column found in table", false, false);
		int iColForUser = objWrapperFunctions.getTableColumn(objWETable, "User");
		int iTotalRowCount = objWrapperFunctions.getTableRowCount(objWETable);
		int iRow =  objWrapperFunctions.getTableRowWithExactValue(objWETable, iCol, sTableValue);
		Locator tablecell = objPojo.getPage().locator("//div[@id='Approvers']//table//tbody//tr["+iRow+"]/td["+iColForUser+"]/a");
		bUsername = objWrapperFunctions.getText(tablecell, "text");
		if(bUsername == null)
			objUtilities.logReporter("Fail to get approver name : getBUsernameFromApproversSectionInAssigment()", false, false);
		return bUsername;
	}

	public String getSectionName(String sRole) {
		Locator section = objPojo.getPage().locator("//tbody//td[contains(text(),'"+sRole+"')]/ancestor::div[contains(@class,'panel-collapse')]/parent::div//a[@aria-expanded]");
		String sSection = objWrapperFunctions.getText(section, "text").trim();
		String[] sec = sSection.split("\n");
		return sec[0];
	}

	public void setReassignToOnPopUp_2(String sReassignTo) {
		if (sReassignTo.equals(""))
			sReassignTo = objWrapperFunctions.GetUserName();
		Locator userNameTextFieldOnReassignment = objPojo.getPage().locator("//*[text()='Search by UserName/UserID']/parent::div//following-sibling::input[@id='userDet']");
		objUtilities.logReporter("Set 'User Name on Reassignment Pop-up :setReassignToOnPopUp_2()  ",
			objWrapperFunctions.setText(userNameTextFieldOnReassignment, sReassignTo.trim()), false);
		Locator listLocator = objPojo.getPage().locator("//ul/li[contains(text(),'"+sReassignTo+"')]");
		if (!objWrapperFunctions.checkElementDisplyed(listLocator))
			listLocator = objPojo.getPage().locator("//ul/li/a[contains(text(),'"+sReassignTo+"')]");
		objUtilities.waitFor(2);
		objUtilities.logReporter("Select 'User Name on Reassignment Pop-up :setReassignToOnPopUp_2()  ",
			objWrapperFunctions.click(listLocator), false);
		Locator AddBtnXpath = objPojo.getPage().locator("//*[@id='addlateapprover']//input[@value='Add']");
		objUtilities.logReporter("click on Add button :setReassignToOnPopUp_2()  ",
			objWrapperFunctions.click(AddBtnXpath), false);
		objUtilities.waitFor(10);
	}

	public void reassignAllUsingRoleExt(String sRole,String section,String sUserName) {
		String userName = "";
		Locator role = objPojo.getPage().locator("//td[text()='"+sRole+"']/parent::tr");
		List<Locator> l1 = objWrapperFunctions.getWebElementList("//td[text()='"+sRole+"']/parent::tr");
		for(Locator loc : l1) {  
			String rowCnt = objWrapperFunctions.getAttributeValue(loc, "rowIndex");
			userName = userName+"~"+objWrapperFunctions.getAttributeValue(objPojo.getPage().locator("//div[@id='"+section+"']//tbody/tr["+rowCnt+"]/td[1]/a"), "text");
		}
		String[] name = userName.split("~");
		for(int i=1; i<name.length; i++) {
			if(!name[i].equals(objWrapperFunctions.GetUserName())) {
				this.ExpandSections(section);
				this.clickReassignInAssignmentsTable_BuUser(section, "Role", sRole, "Action", name[i]);
				this.verifyPopUpIsDisplayed("Reassignment");
				this.setReassignToOnPopUp(sUserName);
				this.clickReassignButtonOnPopUp();
			}
		}
	}

	public void verifyMessageTextForAssignmentPage(String sAssignmentTxt) {
		Locator objAssignmentTxt = objPojo.getPage().locator("//*[@id='mainpageDiv']//label[contains(text(),'"+sAssignmentTxt+"')]");
		objUtilities.logReporter("Verify message text for Assignment page " + sAssignmentTxt + "",
			objWrapperFunctions.checkElementDisplyed(objAssignmentTxt), false);
	}

	public String getRolesAndUserNameListAccordingtoSection(String sSecID) {
		String sRoles = null;
		this.ExpandSections(sSecID);
		Locator TableHeader = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table");
		int iCol = objWrapperFunctions.getTableColumn(TableHeader, "Role");
		int iCol1 = objWrapperFunctions.getTableColumn(TableHeader, "User");
		Locator TableData = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table/tbody");
		int iRowCount = objWrapperFunctions.getTableRowCount(TableData);
		for(int i = 1; i <= iRowCount; i++) {
			Locator sXpath = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table/tbody/tr["+i+"]/td["+iCol+"]");
			Locator sXpath1 = objPojo.getPage().locator("//div[@id='"+sSecID+"']//table/tbody/tr["+i+"]/td["+iCol1+"]");
			if(sRoles == null) {
				String sRol = objWrapperFunctions.getText(sXpath, "text");
				String sUser = objWrapperFunctions.getAttributeValue(sXpath1, "innerText");
				sRoles = sRol+","+sUser;
			} else {
				String sRol = objWrapperFunctions.getText(sXpath, "text");
				String sUser = objWrapperFunctions.getAttributeValue(sXpath1, "innerText");
				String sRole = sRol+","+sUser;
				sRoles = sRoles+"~"+sRole;
			}
			objUtilities.logReporter("Get Role : "+sRoles+" : getRolesAndUserNameListAccordingtoSection()", true , false);
		}
		if(sRoles==null)
			objUtilities.logReporter("Unable to get Role : "+sRoles+" : getRolesAndUserNameListAccordingtoSection()", false , false);
		return sRoles;
	}

	public int reassignAllUsingSection(String section) {
		section = section.replace(" ", "");
		String roles = this.getRolesAndUserNameListAccordingtoSection(section);
		String[] rolAndUser = roles.split("~");
		String userName = "";
		Locator role = objPojo.getPage().locator("//div[@id='"+section+"']//table/tbody//td/parent::tr");
		List<Locator> l1 = objWrapperFunctions.getWebElementList("//div[@id='"+section+"']//table/tbody//td/parent::tr");
		for(Locator loc : l1) {  
			String rowCnt = objWrapperFunctions.getAttributeValue(loc, "rowIndex");
			userName = userName+"~"+objWrapperFunctions.getAttributeValue(objPojo.getPage().locator("//div[@id='"+section+"']//tbody/tr["+rowCnt+"]/td[1]/a"), "text");
		}
		String[] name = userName.split("~");
		for(int i=1; i<name.length; i++) {
			if(!name[i].equals(objWrapperFunctions.GetUserName())) {
				this.ExpandSections(section);
				String[] rol = rolAndUser[i-1].split(",");
				this.clickReassignInAssignmentsTable_BuUser(section, "Role", rol[0], "Action", rol[1]);
				this.setReassignToOnPopUp("");
				this.clickReassignButtonOnPopUp();
			}
		}
		objUtilities.refreshWebPage();
		objUtilities.waitFor(10);
		List<Locator> l2 = objWrapperFunctions.getWebElementList("//div[@id='"+section+"']//table/tbody//td/parent::tr");
		String rolesNew = this.getRolesListAccordingtoSection(section);
		String[] rolnew = rolesNew.split("~");
		return l2.size();
	}

}