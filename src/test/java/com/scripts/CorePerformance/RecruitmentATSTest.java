package com.scripts.CorePerformance;
 

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
import com.generic.BaseTest;
import com.generic.Pojo;
import com.views.CorePerformance.ViewForLogin;
import com.views.CorePerformance.ViewForRecruitmentATS;
 
public class RecruitmentATSTest extends BaseTest {
	Properties objCCBProperties;
	Pojo objPojo;
	private ViewForLogin objViewForLogin;
	private ViewForRecruitmentATS objViewForRecruitmentATS;
	@BeforeClass
	public void setUpTest() {
		System.out.println("STEP - Launch Browser");
		initializeWebEnvironment();
		objViewForLogin = new ViewForLogin(this);
		objCCBProperties = this.getObjConfig();
		objViewForRecruitmentATS = new ViewForRecruitmentATS(this);
	}
	@Test
	public void verifyRecruitmentTabAddCandidateFunctionality_TC6() {
		objCCBProperties = this.loadConfigPropertiesForModules("CorePerformance");
		objViewForLogin.login(objCCBProperties.getProperty("user"), objCCBProperties.getProperty("password"));
		objViewForRecruitmentATS.clickOnRecruitmentATSTab();
		objViewForRecruitmentATS.clickOnAddCandidateButton();
		objViewForRecruitmentATS.enterCandidateFullName(objCCBProperties.getProperty("candidateFirstName"), objCCBProperties.getProperty("candidateLastName"), objCCBProperties.getProperty("emailID"));
		objViewForRecruitmentATS.clickOnSaveButton();
		objViewForRecruitmentATS.checkStatusOfNewAddedCandidate(objCCBProperties.getProperty("expectedStageStatus"));
		objViewForRecruitmentATS.verifyFloatingSuccessMsgDisplaying();
	}
	
	@AfterClass
	public void tearDownTest() {
		tearDownWebEnvironment();
		objPojo = null;
		objViewForLogin = null;
		objCCBProperties = null;
		objViewForRecruitmentATS = null;
	}
}