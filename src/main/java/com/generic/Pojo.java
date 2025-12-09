package com.generic;
import java.util.Hashtable;
import java.util.Properties;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import com.microsoft.playwright.Page;

/**
 * @ScriptName    : Pojo
 * @Description   : This class will used to set common properties 
 * 					like webdriver and properties files.
 * @Author        : Vikram Bendre (SQS)
 */
public class Pojo 
{
	private Page page;
	private Properties objConfig;
	private Hashtable<String , String> dataPoolHashTable;
	private Utilities objUtilities;
	private WrapperFunctions objWrapperFunctions;
	private CustomReporter objReporter;
	private String runningScript = "";
	private String strTestDataFilePath = "";
	private String testCaseID = "";
	private String runID = "";
	private String runningPackage = "";
	private String sBrowser = "";
	//RPHADKE_08102018 - Added variable for object id
	private String sObjectID = "";
	private String sJiraID = "";
	private int defaultTimeout;
	private static String sBatchStartDateTime ;
	// Getter Setter for WebDriver object instance
	public void setPage(Page page) 
	{
		this.page = page; 
	}

	
	public Page getPage() {
		return page;
	}


	// Getter Setter for Test Data File Path
	public String getStrTestDataFilePath()
	{
		return strTestDataFilePath;
	}

	public void setStrTestDataFilePath(String strTestDataFilePath)
	{
		this.strTestDataFilePath = strTestDataFilePath;
	}

	public void setDefaultTimeout(int timeoutInMillis) {//setWebDriverWait
	    this.defaultTimeout = timeoutInMillis;
	    if (page != null) {
	        page.setDefaultTimeout(timeoutInMillis);
	    }
	}

	public int getDefaultTimeout() {//getWebDriverWait
	    return defaultTimeout;
	}

	// Getter Setter for Config object instance
	public void setObjConfig(Properties objConfig)
	{
		this.objConfig = objConfig;
	}

	public Properties getObjConfig()
	{
		return objConfig;
	}

	//  Getter Setter for DataPoolHashtable object instance
	public void setDataPoolHashTable(Hashtable<String, String> dataPoolHashTable)
	{
		this.dataPoolHashTable = dataPoolHashTable;
	}

	public Hashtable<String, String> getDataPoolHashTable()
	{
		return dataPoolHashTable;
	}

	// Getter Setter for Running Script Name
	public void setRunningScriptName(String scriptName)
	{
		this.runningScript = scriptName;
	}

	public String getRunningScriptName()
	{
		return runningScript;
	}
	// Getter Setter for Running Script Name
	public void setRunningPackageName(String scriptName)
	{
		this.runningPackage = scriptName;
	}

	public String getRunningPackageName()
	{
		return runningPackage;
	}

	// Getter Setter for Custom Reporter object instance
	public void setCustomeReporter(CustomReporter reporter)
	{
		this.objReporter = reporter;
	}

	public CustomReporter getCustomeReporter()
	{
		return objReporter;
	}

	// Getter Setter for Utilities object instance
	public Utilities getObjUtilities() 
	{
		return objUtilities;
	}

	public void setObjUtilities(Utilities objUtilities) 
	{
		this.objUtilities = objUtilities;
	}

	// Getter Setter for Wrapper function object instance
	public WrapperFunctions getObjWrapperFunctions() 
	{
		return objWrapperFunctions;
	}

	public void setObjWrapperFunctions(WrapperFunctions objWrapperFunctions)
	{
		this.objWrapperFunctions = objWrapperFunctions;
	}

	// Getter Setter for Test Case ID 
	public String getTestCaseID() 
	{
		return testCaseID;
	}

	public void setTestCaseID(String testCaseID) 
	{
		this.testCaseID = testCaseID;
	}

	// Getter Setter for RunID
	public String getRunID() 
	{
		return runID;
	}

	public void setRunID(String runID) 
	{
		this.runID = runID;
	}
	
	//RPHADKE_27082018 - set browser
	public void setBrowser(String sBrowser)
	{
		this.sBrowser = sBrowser;
	}
	
	//RPHADKE_27082018 - get browser
	public String getBrowser()
	{
		return sBrowser;
	}
	
	//RPHADKE_20092018 - set object id
	public void setObjectId(String sObjectId)
	{
		this.sObjectID = sObjectId;
	}
	
	//RPHADKE_20092018 - get object id
	public String getObjectId()
	{
		return sObjectID;
	}
	
	//Namrata_10082021 - set Jira ID
	public void setJiraTestId(String sJiraID)
	{
		this.sJiraID = sJiraID;
	}	
	
	//Namrata_10082021 - get Jira ID
	public String getJiraTestId()
	{
		return sJiraID;
	}
	public String getBatchStartDateTime()
	{
		return sBatchStartDateTime;
	}
}
