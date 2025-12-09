package com.pageFactory.CorePerformance;

import java.util.List;
import java.util.Properties;

import com.generic.Pojo;
import com.generic.Utilities;
import com.generic.WrapperFunctions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Playwright Page Object for CorePerformancePage.
 */
public class CorePerformancePage {
	
	private WrapperFunctions objWrapperFunctions;
	private Utilities objUtilities;
	private Pojo objPojo;
	private Page page;
	private Properties objConfig;
	boolean bResult=false;
	
	private Locator cmbBusinessUnit;
	private Locator cmbStatus;
	private Locator btnLoad;

	public CorePerformancePage(Pojo objPojo) {
		objUtilities = objPojo.getObjUtilities();
		objWrapperFunctions = objPojo.getObjWrapperFunctions();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();

		cmbBusinessUnit = page.locator("//div[text()='Business Unit']/following-sibling::div/select[@id='Business']");
		cmbStatus = page.locator("//div[text()='Status']/following-sibling::div/select[@id='Status']");
		btnLoad = page.locator("//a[contains(text(), 'Load')]");
	}
	
	
}