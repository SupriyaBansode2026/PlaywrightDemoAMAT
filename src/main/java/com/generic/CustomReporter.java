package com.generic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import org.testng.SkipException;

/**
 * @Description :	custom reporter 
 * @Author 		:	Harshvardhan Yadav (SQS) 
 */
public class CustomReporter extends CustomReporterHelper
{
	// Local Variables
	private Properties objCustomReporterConfig;
	private String printExcelReport = "true";
	private String printPDFReport = "";
	private String printHTMLReport = "";

	/** 
	 *  Start report as per configuration properties
	 * @param scriptName -  Running script name
	 */
	//public void startReport(String scriptName,String packageName)
	public void startReport(String scriptName,String packageName,String JiraID) // Namrata_10802021 - added JiraID parameter
	{
		try 
		{
			// load configuration properties file 
			//objCustomReporterConfig = loadCustomConfigFile(scriptName.substring(scriptName.lastIndexOf(".") + 1),packageName.substring(packageName.lastIndexOf(".") + 1));
			objCustomReporterConfig = loadCustomConfigFile(scriptName.substring(scriptName.lastIndexOf(".") + 1),
					packageName.substring(packageName.lastIndexOf(".") + 1),JiraID); // Namrata_10082021

			// get types of report to print
			printExcelReport = objCustomReporterConfig.getProperty("custom.reports.excel").trim();
			printPDFReport = objCustomReporterConfig.getProperty("custom.reports.pdf").trim();
			printHTMLReport = objCustomReporterConfig.getProperty("custom.reports.html").trim();
			startTxtReport();
			// start mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				startExcelReport();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	/**
	 * Add pass step
	 * @param stepDesc - Step description
	 * @param inputValue - Input value
	 * @param exceptedValue - Expected value (for verification point)
	 * @param actualValue - Actual value (for verification point)
	 */
 	public void pass(String stepDesc, String inputValue, String exceptedValue, String actualValue,String... stepText)
	{
		try
		{
			// add pass step for mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				addExcelPassStep(stepDesc, inputValue, exceptedValue, actualValue,stepText);
			if(printPDFReport.equalsIgnoreCase("true"))
				//addPDFPassStep(stepDesc, inputValue, exceptedValue, actualValue);
			if(printHTMLReport.equalsIgnoreCase("true"))
				System.out.println("add code after dummy run");
				//addHTMLPassStep(stepDesc, inputValue, exceptedValue, actualValue);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

 	/**
	 * Add fail step
	 * @param stepDesc - Step description
	 * @param inputValue - Input value
	 * @param exceptedValue - Expected value (for verification point)
	 * @param actualValue - Actual value (for verification point)
	 */
	public void fail(String stepDesc, String inputValue, String exceptedValue, String actualValue, String sFailureScreenshotName)
	{
		try
		{
			// add fail step for mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				addExcelFailStep(stepDesc, inputValue, exceptedValue, actualValue, sFailureScreenshotName);
			if(printPDFReport.equalsIgnoreCase("true"))
				addPDFFailStep(stepDesc, inputValue, exceptedValue, actualValue);
			if(printHTMLReport.equalsIgnoreCase("true"))
				addHTMLFailStep(stepDesc, inputValue, exceptedValue, actualValue);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	/**
	 * Add info step
	 * @param stepDesc - Step description
	 * @param infoMessage - Info message
	 */
	public void info(String stepDesc, String infoMessage)
	{
		try
		{
			// add info step for mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				addExcelInfoStep(stepDesc, infoMessage);
			if(printPDFReport.equalsIgnoreCase("true"))
				addPDFInfoStep(stepDesc, infoMessage);
			if(printHTMLReport.equalsIgnoreCase("true"))
				addHTMLInfoStep(stepDesc, infoMessage);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	/**
	 * end report
	 */
	public void endReport()
	{
		String userhome;
		try
		{
			// end mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				endExcelReport();
			RetryAnalyzer.globalRetryCount = 1;
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	
	public void endReport(String sObjectID)
	{
		String userhome;
		try
		{
			// end mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				endExcelReport(sObjectID);
			RetryAnalyzer.globalRetryCount = 1;
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public void StartBatchReport()
	{
		try
		{
			CreateBatchExcelReport();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public void ReadExecutionReport()
	{
		try
		{
			ReadExecutionExcelReport();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public void WriteBatchReport()
	{
		try
		{
			WriteExecutionExcelReport();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public void WriteFailBatchReport() 
	{
		try
		{
			HashMap <String,String> hm =ReadFailExecutionExcelReport();
			WriteFailExecutionExcelReport(hm);
			
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	//RPHADKE_18092018 - function to add step result for warning
 	public void warning(String stepDesc, String inputValue, String exceptedValue, String actualValue)
	{
		try
		{
			// add pass step for mentioned report
			if(printExcelReport.equalsIgnoreCase("true"))
				addExcelWarningStep(stepDesc, inputValue, exceptedValue, actualValue);
			if(printPDFReport.equalsIgnoreCase("true"))
				//addPDFPassStep(stepDesc, inputValue, exceptedValue, actualValue);
			if(printHTMLReport.equalsIgnoreCase("true"))
				System.out.println("add after");
				//addHTMLPassStep(stepDesc, inputValue, exceptedValue, actualValue);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	public void verifyTestCaseinDatabase(String scriptName,String packageName)
	{
 		try 
 		{
 			String sGroup = "";
			String sTestCaseID = "";
			String sFunctionality = "";
			if(packageName.contains("."))
			{
				sGroup = packageName.substring(0, packageName.lastIndexOf("."));
				sTestCaseID = scriptName.substring(scriptName.lastIndexOf(".") + 1);
				sFunctionality = packageName.substring(packageName.lastIndexOf(".") + 1);
			}
			objCustomReporterConfig = loadCustomConfigFile(scriptName.substring(scriptName.lastIndexOf(".") + 1),packageName.substring(packageName.lastIndexOf(".") + 1), sGroup);
		
 			boolean bReturn = checkTestCaseInDatabase(objCustomReporterConfig, sTestCaseID, sFunctionality, sGroup);
 			if (bReturn)
 			{
 				System.out.println("Test Case "+sTestCaseID+" is already present in the Database");
 				throw new SkipException("Test Case "+sTestCaseID+" is already present in the Database");
 			}
 			
 			
 		} catch (IOException e) {
			e.printStackTrace();
		}
		
 	}

	public boolean checkTestCaseInDatabase(Properties objCustomReporterConfig, String sTestCase, String sFunctionality, String sGroup)
	{
		System.out.println("Checking Testcase Inside Database");
		
		try
		{
			String sPMTReleaseName = "";
			String sActualFunctionality = "";
			String sTestCycle = "1";
			
			String sOPMTRelease = "";
			String sOTestCase = "";
			String sOResult = "";
			String sOGroup = "";
			String sOFunctionality = "";
			String sOTestCycle = "";
			if (System.getProperty("PMTReleaseName") != null) {
				sPMTReleaseName=System.getProperty("PMTReleaseName");
			} else {
				sPMTReleaseName=objCustomReporterConfig.getProperty("PMTRelease");
			}
			
			if(objCustomReporterConfig.getProperty(runningPackageName)!=null)
			{
				String[] array = objCustomReporterConfig.getProperty(sFunctionality).split("~");
				sActualFunctionality = array[0];
				sTestCycle = objCustomReporterConfig.getProperty("TestCycle");
			}
			
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			
			String sQuery = "SELECT * "+
							"FROM SQS_Automation "+
							"WHERE [PMT Release]='"+sPMTReleaseName+"' "+
							"and [Test Case]='"+sTestCase+"' "+
							"and [Result]='PASS' "+
							"and [Group]='"+sGroup+"' "+
							"and [Functionality]='"+sActualFunctionality+"' "+
							"and [TestCycle]='"+sTestCycle+"'";
			
			Statement objStMt = objCon.createStatement();
			System.out.println(sQuery);
			ResultSet rSet = objStMt.executeQuery(sQuery);
			
			while (rSet.next()) 
			{
				sOPMTRelease = rSet.getString("PMT Release");
				sOTestCase = rSet.getString("Test Case");
				sOResult = rSet.getString("Result");
				sOGroup = rSet.getString("Group");
				sOFunctionality = rSet.getString("Functionality");
				sOTestCycle = rSet.getString("TestCycle");
				if(sOPMTRelease.equals(sPMTReleaseName) && sOTestCase.equals(sTestCase) && sOResult.equals("PASS") && sOGroup.equals(sGroup) && sOFunctionality.equals(sFunctionality) && sOTestCycle.equals(sTestCycle))
				{
					System.out.println(sOPMTRelease);
					System.out.println(sOTestCase);
					System.out.println(sOResult);
					System.out.println(sOGroup);
					System.out.println(sOFunctionality);
					System.out.println(sOTestCycle);
					return true;
				}
				else
				{
					return false;
				}
			}
			return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
}
