package com.views.CorePerformance;
 
import com.generic.Pojo;
import com.generic.Utilities;
import com.pageFactory.CorePerformance.RecruitmentATSPage;
 
public class ViewForRecruitmentATS {
	
	private Utilities objUtilities;
	private Pojo objPojo;
	private RecruitmentATSPage objRecruitmentATSPage;
 
	public ViewForRecruitmentATS(Pojo objPojo) {
		this.objPojo = objPojo;
		objRecruitmentATSPage = new RecruitmentATSPage(objPojo);
		objUtilities = objPojo.getObjUtilities();
	}
	public void clickOnRecruitmentATSTab() {
		objRecruitmentATSPage.clickOnRecruitmentATS();
	}
	public void clickOnAddCandidateButton() {
		objRecruitmentATSPage.clickOnAddCandidate();
	}
	public void enterCandidateFullName(String fname, String lname, String email) {
		objRecruitmentATSPage.enterCandidateDetails(fname, lname, email);
	}
	public void clickOnSaveButton() {
		objRecruitmentATSPage.clickOnSave();
	}
	public void checkStatusOfNewAddedCandidate(String expectedStatus) {
		objRecruitmentATSPage.checkStageStatus(expectedStatus);
	}
	
	public void verifyFloatingSuccessMsgDisplaying() {
		objRecruitmentATSPage.verifyToastMsg();
	}
}