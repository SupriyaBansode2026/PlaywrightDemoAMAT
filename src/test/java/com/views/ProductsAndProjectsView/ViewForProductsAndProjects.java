package com.views.ProductsAndProjectsView;
 
import java.text.ParseException;

import java.util.List;

import java.util.Properties;
 
import com.generic.Pojo;

import com.generic.Utilities;

import com.generic.WrapperFunctions;

import com.pageFactory.ProductsAndProjectsPage.ProductsAndProjectsPage;
 
import io.qameta.allure.Description;
import io.qameta.allure.Story;
 
public class ViewForProductsAndProjects {

	// Local variables
 
	private ProductsAndProjectsPage objProductsAndProjectsPage;

	private Utilities objUtilities;

	private Pojo objPojo;

	private WrapperFunctions objWrapperFunctions;

	public static String sProjectNm;

	public static String sFolderName;

	public static String ProjectNumber;

	public static String sSubFolderName;

	public static String sNewProjectTitleNm;

	public static String projectID; // PPARKHI_19032018

	public static String strFolderTitle;

	private Properties objConfig;

	public Object clickOnPlusButton;
 
	public ViewForProductsAndProjects(Pojo objPojo) {

		this.objPojo = objPojo;

		objUtilities = objPojo.getObjUtilities();

		objWrapperFunctions = objPojo.getObjWrapperFunctions();

		objProductsAndProjectsPage = new ProductsAndProjectsPage(objPojo);

		objConfig = objPojo.getObjConfig();

	}
 
	public void updateReadyToAcceptNSRValueForProject(boolean bReadyToAcceptNSR) {

		objProductsAndProjectsPage = new ProductsAndProjectsPage(objPojo);

		objProductsAndProjectsPage.expandSectionOnProjectScreen("Project decisions");

		if (bReadyToAcceptNSR==true) {

			objProductsAndProjectsPage.EnableorDisableProjectAddsValueFieldCheckBox(false);

			objProductsAndProjectsPage.selectReadyToAcceptNSROption("True");

			objUtilities.waitTillPageLoad(null);

			objProductsAndProjectsPage.verifyNSRCommentSectionOpen();   

			objUtilities.waitFor(5);

			objProductsAndProjectsPage.setNSRReadyForecastDate();

			objUtilities.waitTillPageLoad(null);

			objProductsAndProjectsPage.saveDetailsOnProductsAndProjectsPage();       	

		} else

		{

			objProductsAndProjectsPage.selectReadyToAcceptNSROption("False");

			objUtilities.waitTillPageLoad(null);

		objProductsAndProjectsPage.EnableorDisableProjectAddsValueFieldCheckBox(false);

		objProductsAndProjectsPage.verifyNSRCommentSectionOpen();   

		objUtilities.waitFor(20);

		objUtilities.waitTillPageLoad(null);

	}

	}

		public void createProjectOnProductsAndProjectPage(String sProjectName, String sProjectOwner, String sProjectStatus,

				String sProjectPriority, String sProjectComplexity, String sProjectType, String sNeedDate,

				String sStartDate, String sEndDate, String sECRProduct, String sActionButton, String sBusinessUnit,

				String sScope) {

			objProductsAndProjectsPage = new ProductsAndProjectsPage(objPojo);

			objUtilities = new Utilities(objPojo);

			WrapperFunctions objWrapperFunctions = objPojo.getObjWrapperFunctions();

			sProjectNm = sProjectName + "_" + (int) (Math.random() * 9000 + 1000);

			objProductsAndProjectsPage.setProjectName(sProjectNm);

			if (sProjectOwner == "") // sz11062018

			{

				sProjectOwner = objWrapperFunctions.GetUserName();

				System.out.println("Project Owner is: " + sProjectOwner);

			}

			objProductsAndProjectsPage.setProjectOwner(sProjectOwner);

			objProductsAndProjectsPage.setStatus(sProjectStatus);

			if (sProjectPriority.equalsIgnoreCase("urgent")) {

				sProjectPriority = objConfig.getProperty("projectPriorityHigh");

				objProductsAndProjectsPage.setPriority(sProjectPriority);

			} else if (sProjectPriority.equalsIgnoreCase("routine")) {

				sProjectPriority = objConfig.getProperty("projectPriorityMedium");

				objProductsAndProjectsPage.setPriority(sProjectPriority);

			} else {

				sProjectPriority = objConfig.getProperty("projectPriorityLow");

				objProductsAndProjectsPage.setPriority(sProjectPriority);

			}

			objProductsAndProjectsPage.setScope("Project Scope");

			objProductsAndProjectsPage.setComplexity(sProjectComplexity);

			objProductsAndProjectsPage.setDeliverable("Project Deliverable");

			objProductsAndProjectsPage.setBusinessBenefits("Project BusinessBenefits");

			objProductsAndProjectsPage.setProjectType(sProjectType);

			if (sNeedDate == "")

				objProductsAndProjectsPage.NeedDate();

			else if(sNeedDate!="")

				objProductsAndProjectsPage.setNeedDate("", 1);		

			if (sStartDate == "")

				objProductsAndProjectsPage.startDate();

			if (sEndDate == "")

				if (sBusinessUnit != "")

					objProductsAndProjectsPage.selectBusinessUnitOnCreateProjectPopUp(sBusinessUnit);

			if (sECRProduct != "")

				objProductsAndProjectsPage.SelectECRProduct(sECRProduct);

			if (sActionButton.equalsIgnoreCase("Create Project")){

				objProductsAndProjectsPage.clickOnCreateProjectButton();

				objProductsAndProjectsPage.HandlecreateTeamsChannelModal();

				objProductsAndProjectsPage.handlecreateTeamChannelConfiguration();

			}

			else

				objProductsAndProjectsPage.clickOnCreateProjectCancelButton();
 
			objUtilities.waitTillPageLoad(null);

			objUtilities.waitTillPageLoad(null);

		}

		public void enterProductsAndProjectPageDataAndClickOnButton(String sBusinessUnit, String sProduct, String sKPU,

				String sStatus, String sBUCustomer, String sProductAndProjectButtonToClick) {

			objProductsAndProjectsPage = new ProductsAndProjectsPage(objPojo);

			if (sBusinessUnit != "")

				objProductsAndProjectsPage.selectProductsAndProjectDropDownValue("Business Unit", sBusinessUnit);

			if (sProduct != "")

				objProductsAndProjectsPage.selectProductsAndProjectDropDownValue("Product", sProduct);

			if (sKPU != "")

				objProductsAndProjectsPage.selectProductsAndProjectDropDownValue("KPU", sKPU);

			if (sStatus != "")

				objProductsAndProjectsPage.selectProductsAndProjectDropDownValue("Status", sStatus);

			if (sBUCustomer != "")

				objProductsAndProjectsPage.selectProductsAndProjectDropDownValue("BU Customer", sBUCustomer);

			if (sProductAndProjectButtonToClick != "")

				objProductsAndProjectsPage.clickOnProductAndProjectButton(sProductAndProjectButtonToClick);

		}
 
}
