package com.scripts.CorePerformance;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.Pojo;
import com.views.CorePerformance.ViewForDashboard;
import com.views.CorePerformance.ViewForRecruitmentATS;

public class RecruitmentATSTest extends BaseTest {
	
	private static final String String = null;
	Properties objCCBProperties;
	private ViewForRecruitmentATS objViewForRecruitmentATS;
	

	@BeforeClass
	public void initPageObjects() {
		objViewForRecruitmentATS = new ViewForRecruitmentATS(this);
		objCCBProperties = this.getObjConfig();
	}
	
	@Test
	public void verifyRecruitmentTab_TC6() {
		System.out.println("STEP - Click on 'Recruitment(ATS)' tab.");
		objViewForRecruitmentATS.clickOnRecruitmentATSTab();
		System.out.println("STEP - Click on 'Add Candidate' button.");
		objViewForRecruitmentATS.clickOnAddCandidateButton();
		System.out.println("STEP - Enter candidate 'First Name' 'Last Name' and 'Email'.");
		objViewForRecruitmentATS.enterCandidateFullName(objCCBProperties.getProperty("candidateFirstName"), objCCBProperties.getProperty("candidateLastName"), objCCBProperties.getProperty("emailID"));
	//	System.out.println("STEP - Select Vacancy option from dropdown.");
		System.out.println("STEP - Click on 'Save' button.");
		objViewForRecruitmentATS.clickOnSaveButton();
		System.out.println("VERIFY - Stage status of newly added candidate should be 'Application Received'.");
		String status = "Application Received";
		Assert.assertEquals(objViewForRecruitmentATS.checkStatusOfNewAddedCandidate(), status);
	}
}