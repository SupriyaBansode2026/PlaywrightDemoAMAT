package com.generic;

import java.io.*;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FontUnderline;
public class CustomReporterHelper 
{
	// Local Variables
	private String runningScriptName = "Web Portal";
	protected String runningPackageName = "Web Portal";
	private String runningJiraID = "Web Portal";
	private Properties objCustomConfig;

	//Txt
	private String strTxtFilePath = "";
	// Excel
	private String strExcelFilePath = "";
	private XSSFWorkbook workbook;
	private XSSFSheet currentReportSheet;
	private XSSFSheet summarySheet;
	private FileInputStream ExcelFile;
	private XSSFRow summaryRow;
	private XSSFRow sheetRow;
	private int intRowCounter = 1;
	private int intSummaryRowCounter = 1;
	private int excelReportStepNumber = 1;
	private int intExcelReportNoOfFails = -1;
	private HashMap <Integer,BatchExcetionDeatils> Hm; 
	Date startDate;
	Date endDate;
	
	

	// PDF
	private String strPDFFilePath = "";
	private String strTempPDFFilePath = "";
	boolean exixtingPdf = false;
	private int numberOfPages;
	private int pdfReportStepNumber = 1;

	//HTML
	private String strHTMLFilePath = "";
	private String strTempHTMLFilePath = "";
	private String runningScriptDivName = "";
	boolean exixtingHTML = false;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	private String htmlPageOutput = "";
	private int htmlReportStepNumber = 1;
	private int intHTMLReportNoOfFails = 0;
	private int intTotalPass = 0;
	private int intTotalFail = 0;
	private int intTotalFailGraph = 0;
	private int intTotalPassGraph = 0;
    private boolean existingPdf;
    private Font fs12, fs12Bold, fs25Bold, fs12Red;
    
	 public Properties loadCustomConfigFile(String scriptName, String packageName, String jiraID) throws IOException {
	        runningScriptName = scriptName;
	        runningPackageName = packageName;
	        runningJiraID = jiraID;

	        objCustomConfig = new Properties();
	        String configPath = System.getProperty("user.dir") + "/src/test/resources/customReporter.properties";
	        try (FileInputStream fis = new FileInputStream(configPath)) {
	            objCustomConfig.load(fis);
	        }

	        return objCustomConfig;
	    }
	

	/**
	 * Start excel report with mentioned configuration properties
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void startExcelReport() throws IOException, InvalidFormatException
	{
		String reportSheet = "";
		Properties objConfig = new Properties();
		objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		
		//runningScriptName = "TestCase";
		// Excel allow sheet name long upto 31 characters. 
		// In excel report sheets are created by using running script name. 
		if(runningScriptName.length() > 30)
			reportSheet = runningScriptName.substring(0, 30);
		else
			reportSheet = runningScriptName;

		// Excel report file path
		strExcelFilePath = System.getProperty("user.dir") + "/target/custom-reports/Execution_Report.xlsx";
		File reportFile = new File(strExcelFilePath);

		// If file not exist create new with summary sheet and sheet for running script details
		if(!reportFile.exists())
		{
			new File(System.getProperty("user.dir") + "/target/custom-reports").mkdir();
			reportFile.createNewFile();
			workbook = new XSSFWorkbook();
			
			summarySheet = workbook.createSheet("TestSummary");
			summaryRow = summarySheet.createRow(0);
			this.createSummaryHeaderCell(0, "Test Case");
			this.createSummaryHeaderCell(1, "Executed By");
			this.createSummaryHeaderCell(2, "Status");
			this.createSummaryHeaderCell(3, "Start Time");
			this.createSummaryHeaderCell(4, "End Time");
			this.createSummaryHeaderCell(5, "Module Name");
			this.createSummaryHeaderCell(6, "Machine Name");
			this.createSummaryHeaderCell(7, "Iteration");
			this.createSummaryHeaderCell(8, "Failure Log");
			this.createSummaryHeaderCell(9, "Failure Screenshot Name");
			//RPHADKE_20092018 - added column for Object ID
			this.createSummaryHeaderCell(10, "Object ID");
			this.createSummaryHeaderCell(11, "Failure Type");
			this.createSummaryHeaderCell(12, "Jira Test Case Id"); //Namrata_10082021
			
			summaryRow = summarySheet.createRow(intSummaryRowCounter);
			
			this.createSummaryLinkCell(0, runningScriptName, reportSheet);
			this.createSummaryStepCell(1, System.getProperty("user.name"));
			this.createStartDateTimeCell(3);
			this.createSummaryStepCell(5, runningPackageName);
			this.createSummaryStepCell(6,InetAddress.getLocalHost().getHostName());
			this.createSummaryStepCell(7,"1");
			currentReportSheet = workbook.createSheet(runningScriptName);
			sheetRow = currentReportSheet.createRow(0);
		}
		else // If file exist update summary sheet and create sheet for running script details
		{
		
			workbook = (XSSFWorkbook)WorkbookFactory.create(new FileInputStream(strExcelFilePath));
			summarySheet = workbook.getSheet("TestSummary");
			intSummaryRowCounter = summarySheet.getLastRowNum() + 1;
			summaryRow = summarySheet.createRow(intSummaryRowCounter);
			this.createSummaryLinkCell(0, runningScriptName, reportSheet);
			this.createSummaryStepCell(1,  System.getProperty("user.name"));
			this.createStartDateTimeCell(3);
			this.createSummaryStepCell(5, runningPackageName);
			this.createSummaryStepCell(6,InetAddress.getLocalHost().getHostName());
			currentReportSheet = workbook.createSheet(runningScriptName);
			sheetRow = currentReportSheet.createRow(0);
		}

		// Add column header in sheet of running script details
		if(objConfig.getProperty("SCV_Environment").equalsIgnoreCase("true")) 
		{
			this.createSheetHeaderCell(0, "Step Description");
			this.createSheetHeaderCell(1, "Time Taken");
			this.createSheetHeaderCell(2, "Status");
			this.createSheetHeaderCell(3, "Start Time");
			this.createSheetHeaderCell(4, "End Time");
		}
		else
		{
			this.createSheetHeaderCell(0, "S.No");
			this.createSheetHeaderCell(1, "Step Description");
			this.createSheetHeaderCell(2, "TestCase");
			this.createSheetHeaderCell(3, "Expected Value");
			this.createSheetHeaderCell(4, "Actual Value");
			this.createSheetHeaderCell(5, "Status");
			this.createSheetHeaderCell(6, "Iteration");
			this.createSheetHeaderCell(7, "Log Timestamp");
			this.createSheetHeaderCell(8, "Failure Screenshot Name");
			this.createSheetHeaderCell(9, "Failure Type");
			this.createSheetHeaderCell(10, "Jira Step No");
		}
	}
	
	public void startHTMLReport() throws IOException
	{
		// HTML report file path
		strHTMLFilePath = System.getProperty("user.dir") + "/target/custom-reports/Html_Report/Execution_Report.html";

		// Add div for running script details
		runningScriptDivName = runningScriptName + "_"+ this.getRequiredDate(0, "yyyy_MM_dd_HHmmss", null);

		// If file not exist create new
		File reportFile = new File(strHTMLFilePath);
		if(!reportFile.exists())
		{
			exixtingHTML = false;
			// Create new directory and add required .css, .js and images files
			new File(System.getProperty("user.dir") + "/target/custom-reports/Html_Report").mkdirs();
			FileUtils.copyDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/externalResources/Custom_Design_Files"), new File(System.getProperty("user.dir") + "/target/custom-reports/Html_Report"));
			reportFile.createNewFile();

			// Assume default encoding.
			fileWriter = new FileWriter(strHTMLFilePath);

			// Wrap FileWriter in BufferedWriter.
			bufferedWriter = new BufferedWriter(fileWriter);

			// If header logo is available copy the same in images folder and add in report
			String hederLogoPath = objCustomConfig.getProperty("custom.proj.header.logo").trim();
			String hederLogoExtension = "";
			if(!hederLogoPath.equals("") && hederLogoPath.contains("."))
			{
				//hederLogoExtension = hederLogoPath.substring(hederLogoPath.lastIndexOf(".") + 1);
				//FileUtils.copyFile(new File(hederLogoPath),  new File(System.getProperty("user.dir") + "/target/custom-reports/Html_Report/IMG/headerlogo." + hederLogoExtension));
			}

			// Add html tags to page
			htmlPageOutput = " <!DOCTYPE html> \n" +
					" <head> \n"+ 
					" 	<link rel='stylesheet' type='text/css' href='./CSS/design.css'> \n"+ 
					"	<script type='text/javascript' src='./jquery/jquery-1.11.3.min.js'></script>"+
					"	<script src='./js/highcharts.js'></script>"+
					"	<script src='./js/highcharts-3d.js'></script>"+
					"	<script src='./js/modules/exporting.js'></script>"+
					" </head> \n"+ 
					" <body> \n"+ 
					" 	<table id='mainTable'> \n"+ 
					" 		<tbody> \n"+ 
					" 			<tr id='header'> \n"+ 
					" 				<td id='headertext'> \n"+ 
					" 					<b><u> <font color='#004080'>" + objCustomConfig.getProperty("custom.proj.description") + " </font></u></b> \n"; 

			if(!hederLogoPath.equals(""))
				htmlPageOutput = htmlPageOutput + " <div style='padding-right:20px;float:right'> <img src='./IMG/headerLogo." + hederLogoExtension + "' height='70' width='140' /> "+ " </i> "+" </div> \n"; 

			// Add summary table with list of test case with status 
			htmlPageOutput = htmlPageOutput + " 				</td> \n"+ 			
					" 			</tr> \n"+ 
					" 			<tr id='container'> \n"+ 
					" 				<td id='content'> \n"+ 
					" 					<div id='summary' class='info'> \n"+ 
					"				    	<table style='width: 100%;' align='center' > \n"+
					"							<tr > \n"+
					"								<td style='width: 70%;' align = 'left' valign='top'> \n"+
					" 									<table style='width: 100%;' class='expandCollpaseTable' align='center'> \n"+ 
					" 										<tr> \n"+ 
					" 											<th width='70%' align='center' class='expandCollpase'>Test Case</th> \n"+ 
					" 											<th width='30%' align='center' class='expandCollpase'>Status</th> \n"+ 
					" 										</tr> \n"+ 
					" 									</table> \n"+ 
					" 									<table style='width: 100%;' class='_customReportTestCaseTable' align='center'> \n"+ 
					" 										<tr> \n"+
					" 											<td width='70%' align='left' class='expandCollpase'> "+ " <a href='javascript:void(0)' onclick=\"document.getElementById('" + runningScriptDivName + "').style.display='block';document.getElementById('fade').style.display='block'\"> " + runningScriptName + "</a></td> \n"+ 
					" 											<td width='30%' align ='center' class='expandCollpase' id = '"+ runningScriptDivName +" _Status'>  </td> \n"+
					" 										</tr> \n"+ 
					" 									</table> \n"+ 
					"								</td > \n"+ 
					"								<td style='width: 30%;' align = 'left' valign='top'> \n"+
					"									<table width = '100%'><tr><td  align='center'><Select id='GraphType'>"+
					"										<option selected>3D Column</option> \n"+
					"										<option>Basic Column</option> \n"+
					"										<option>Pie</option> \n"+
					"										<option>Basic Bar</option> \n"+
					"									</Select> </td></tr><tr><td> \n"+
					"									<div id='_customReportGraph' name='_customReportGraph' style='width: 100%; margin: 0 auto'></div>"+
					"									</td></tr></table> \n"+
					"								</td > \n"+ 
					"							</tr> \n"+
					"						</table> \n"+
					" 					</div> \n"+ 
					" 				</td> \n"+ 
					" 			</tr> \n"+ 
					" 		</tbody> \n"+ 
					" 	</table> \n"+ 
					" 	<div style='display: none;' id='fade' class='_customReport_black_overlay'> </div> \n";

			// Add step details div with header to page 
			htmlPageOutput = htmlPageOutput + this.addScriptDetailsTable(runningScriptName, runningScriptDivName);
		}
		else // If file exist update existing  
		{
			exixtingHTML = true;
			// Add step details div with header to page
			htmlPageOutput = htmlPageOutput + this.addScriptDetailsTable(runningScriptName, runningScriptDivName);
		}
		
	}

	
	public void addExcelPassStep(String stepDesc, String inputValue, String expectedValue, String actualValue, String... stepText) throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream("src/test/resources/config.properties"));

        Row sheetRow = currentReportSheet.createRow(intRowCounter);

        if (stepDesc.contains("Performancevalidation")) {
            inputValue = stepDesc.replaceAll("[^0-9]", "");
            String timeTaken = inputValue + " Sec";
            String resultLog = stepDesc.replace(inputValue, "").replace("PASS:", "").replace("Performancevalidation:-", "").trim();

            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String machineName = InetAddress.getLocalHost().getHostName();
            String release = config.getProperty("EIT_Release");
            String env = config.getProperty("Environment");

            String insertValues = String.join("~", resultLog, release, env, runningPackageName, machineName, date, timeTaken);
            HashMap<Integer, BatchExcetionDeatils> objSqlData = new HashMap<Integer, BatchExcetionDeatils>(); objSqlData.put(1, new BatchExcetionDeatils(insertValues, "", "", "", "", "", "", "", "", "", "", "",InitializeTearDownEnvironment.browserVersion));
            this.insertRunResultInSQLForExploratoryTC(objSqlData,""); // Stub method
        }

        sheetRow.createCell(0).setCellValue(excelReportStepNumber);
        sheetRow.createCell(1).setCellValue(stepDesc);
        sheetRow.createCell(2).setCellValue(inputValue);
        sheetRow.createCell(3).setCellValue(expectedValue);
        sheetRow.createCell(4).setCellValue(actualValue);
        sheetRow.createCell(5).setCellValue("PASS");
        sheetRow.createCell(6).setCellValue("1"); // Retry count stub
        sheetRow.createCell(7).setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        if (stepText.length > 0) {
            sheetRow.createCell(10).setCellValue(stepText[0]);
        }

        intRowCounter++;
        excelReportStepNumber++;
    }

	
	public void addExcelFailStep(String stepDesc, String inputValue, String exceptedValue, String actualValue, String sFailureScreenshotName) throws IOException, InvalidFormatException
	{
		Properties objConfig = new Properties();
		objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		
		sheetRow = currentReportSheet.createRow(intRowCounter);
		if(objConfig.getProperty("SCV_Environment").equalsIgnoreCase("true")) 
		{
			String sStart="",sEnd="",sReq="",sDBLog="";
			int sTimeTaken=0;
			sStart = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
			sEnd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
			int sSCVSec = (int) this.getTimeDifferenceInSeconds(sStart,sEnd);
			sTimeTaken =  sSCVSec;	
			
			if(stepDesc.contains("open TCE Request")||stepDesc.contains("open RFQ Request")){
				String[] aLogData = stepDesc.split(":");
				if(aLogData.length>1)
				{
					sReq=aLogData[2];
					sDBLog = aLogData[0]+":"+aLogData[1];
				}
			}
			else
				sDBLog = stepDesc;
			
			this.createSheetFailCell(0, stepDesc);
			this.createSheetCell(1, Integer.toString(sTimeTaken)+" Sec");
			this.createSheetFailStepCell(2);
			this.createSheetFailCell(3, sStart);
			this.createSheetFailCell(4, sEnd);
			String sMachineName=InetAddress.getLocalHost().getHostName();
			String sMachineUser = System.getProperty("user.name");
			String ScriptName=runningScriptName;
			String sModuleName="";
			if(ScriptName.contains("SCV_Scenario1"))
				sModuleName = "CLM";
			else if(ScriptName.contains("SCV_Scenario2"))
				sModuleName = "CLM";
			else if(ScriptName.contains("SCV_Scenario3"))
				sModuleName = "SCV";
			if(sReq=="")
				sReq="N/A";
			String sFilePath="N/A"; 
			String sComment=stepDesc;
			String sInsertValues=sDBLog+"~"+sReq+"~"+sTimeTaken+"~"+"FAIL"+"~"+sStart+"~"+sEnd+"~"+sMachineUser+"~"+sMachineName+"~"+sModuleName+"~"+sFilePath+"~"+sComment;
			HashMap<Integer, BatchExcetionDeatils> objSqlData = new HashMap<Integer, BatchExcetionDeatils>();
			objSqlData.put(1, new BatchExcetionDeatils(sInsertValues, "", "", "", "", "", "", "", "", "", "", "",InitializeTearDownEnvironment.browserVersion));
			if(ScriptName.contains("SCV_Scenario2") && sDBLog.contains("https:"))
			{
				//do nothing
		}
		else
				this.insertRunResultInSQLForSCVTC(objSqlData, "");
			
			objSqlData = null;
		}
		else
		{
			this.createSheetFailCell(0, String.valueOf(excelReportStepNumber));
			this.createSheetFailCell(1, stepDesc);
			this.createSheetFailCell(2, inputValue);
			this.createSheetFailCell(3, exceptedValue);
			this.createSheetFailCell(4, actualValue);
			this.createSheetFailStepCell(5);
			this.createSheetFailCell(6, Integer.toString(RetryAnalyzer.globalRetryCount));
			String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
			this.createSheetFailCell(7, strDate);
			this.createSheetFailCell(8, sFailureScreenshotName);
			this.createSheetFailCell(9, WrapperFunctions.failureType);
		}
		intRowCounter++;
		excelReportStepNumber++;
		intExcelReportNoOfFails++;
	}

	/**
	 *  Add fail step in PDF report
	 */
	public void addPDFFailStep(String stepDesc, String inputValue, String exceptedValue, String actualValue)
	{
		/*
		 * tableSteps.addCell(NewCellFirstColumn(phrase, cell, fs12Red,
		 * String.valueOf(pdfReportStepNumber), 1, Element.ALIGN_LEFT));
		 * tableSteps.addCell(NewCellColumn(phrase, cell, fs12Red, stepDesc, 1,
		 * Element.ALIGN_LEFT)); tableSteps.addCell(NewCellColumn(phrase, cell, fs12Red,
		 * inputValue, 1, Element.ALIGN_LEFT)); tableSteps.addCell(NewCellColumn(phrase,
		 * cell, fs12Red, exceptedValue, 1, Element.ALIGN_LEFT));
		 * tableSteps.addCell(NewCellColumn(phrase, cell, fs12Red, actualValue, 1,
		 * Element.ALIGN_LEFT)); tableSteps.addCell(NewCellLastColumn(phrase, cell,
		 * fs12Red, "Fail", 1, Element.ALIGN_LEFT)); pdfReportStepNumber++;
		 */}

	/**
	 *  Add fail step in HTML report
	 */
	public void addHTMLFailStep(String stepDesc, String inputValue, String exceptedValue, String actualValue)
	{
		String htmlOutput = " <tr> "+ 
				" <td align ='Center'>" + String.valueOf(htmlReportStepNumber) + "</td> \n"+
				" <td align ='left' >" + stepDesc + "</td> \n"+ 
				" <td align ='left'>" + inputValue + "</td>\n "+
				" <td align ='left'>" + exceptedValue + "</td> \n"+
				" <td align ='left'>" + actualValue + "</td> \n"+
				" <td align ='Center' ><img src='./IMG/logfail.png' alt='FAIL' style='height:20px;width:20px;border:none'></img></td> \n"+ 
				" </tr>  \n";
		htmlPageOutput = htmlPageOutput + htmlOutput;
		htmlReportStepNumber++;
		intHTMLReportNoOfFails++;
	}

	/**
	 *  Add info step in excel report
	 */
	public void addExcelInfoStep(String stepDesc, String infoMessage)
	{
		sheetRow = currentReportSheet.createRow(intRowCounter);
		this.createSheetCell(0, String.valueOf(excelReportStepNumber));
		this.createSheetCell(1, stepDesc);
		this.createSheetCell(2, infoMessage);
		this.createSheetInfoStepCell(5);
		intRowCounter++;
		excelReportStepNumber++;
	}

	/**
	 *  Add info step in PDF report
	 */
	public void addPDFInfoStep(String stepDesc, String infoMessage)
	{
		/*
		 * tableSteps.addCell(NewCellFirstColumn(phrase, cell, fs12,
		 * String.valueOf(pdfReportStepNumber), 1, Element.ALIGN_LEFT));
		 * tableSteps.addCell(NewCellColumn(phrase, cell, fs12, stepDesc, 1,
		 * Element.ALIGN_LEFT)); tableSteps.addCell(NewCellColumn(phrase, cell, fs12,
		 * infoMessage, 1, Element.ALIGN_LEFT));
		 * tableSteps.addCell(NewCellColumn(phrase, cell, fs12, "", 1,
		 * Element.ALIGN_LEFT)); tableSteps.addCell(NewCellColumn(phrase, cell, fs12,
		 * "", 1, Element.ALIGN_LEFT)); tableSteps.addCell(NewCellLastColumn(phrase,
		 * cell, fs12, "Info", 1, Element.ALIGN_LEFT)); pdfReportStepNumber++;
		 */}

	/**
	 *  Add info step in HTML report
	 */
	public void addHTMLInfoStep(String stepDesc, String infoMessage)
	{
		String htmlOutput = " <tr> "+ 
				" <td align ='Center'>" + String.valueOf(htmlReportStepNumber) + "</td> \n"+
				" <td align ='left' >" + stepDesc + "</td> \n"+ 
				" <td align ='left' colspan='3'>" + infoMessage + "</td> \n"+
				" <td align ='Center' ><img src='./IMG/loginfo.png' alt='INFO' style='height:20px;width:20px;border:none'></img></td> \n"+ 
				" </tr>  \n";
		htmlPageOutput = htmlPageOutput + htmlOutput;
		htmlReportStepNumber++;
	}

	/**
	 * End excel report
	 * @throws IOException
	 * @throws InvalidFormatException 
	 * @throws ParseException 
	 */

//	public void endExcelReport() throws IOException  
//	{
//		System.out.println("End excel report");
//		this.createEndDateTimeCell(4);
//		if(intExcelReportNoOfFails > 0)
//		{
//			
//			System.out.println("Inside failed excel report");
//			this.createSummaryFailStepCell(2);
//		}
//		else
//		{
//			System.out.println("Inside passed excel report");
//			this.createSummaryPassStepCell(2);
//		}
//			
//
//		this.addSummarySheetLink();
//
//		this.autoSetColumnWidth();
//		// writing the workbook object into the file created in startReport()
//		FileOutputStream fileOutputStream = new FileOutputStream(strExcelFilePath);
//		workbook.write(fileOutputStream);
//		fileOutputStream.flush();
//		fileOutputStream.close();
//	}
	
	public void endExcelReport() throws IOException, InvalidFormatException, ParseException  
	{
		String sTCResult = "";
		String sFailureLog = "";
		String sFailScreenShotPath = "";
		String sLogFilePath = "";
		
		//RPHADKE_27072018 - Added parameter in cofig which decides whether to insert results in sql or not
		Properties objConfig = new Properties();
		objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		
		//RPHADKE_06082018 - get today's date in yyyy-MM-dd format in oder to get shared drive path
		LocalDate dtTodayWithoutTime = LocalDate.now();
		System.out.println(dtTodayWithoutTime);
		
		
		HashMap<Integer, BatchExcetionDeatils> objSqlData = new HashMap<Integer, BatchExcetionDeatils>();
		System.out.println("End excel report");
		FileOutputStream fileOutputStream = new FileOutputStream(strExcelFilePath);
//		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		String sReportSheet = "";
		
		//runningScriptName = "TestCase";
		// Excel allow sheet name long upto 31 characters. 
		// In excel report sheets are created by using running script name. 
		if(runningScriptName.length() > 30)
			sReportSheet = runningScriptName.substring(0, 30);
		else
			sReportSheet = runningScriptName;
		//Read TC sheet
		HashMap<String, String> hmTCLog = this.readTCExecutionReport(sReportSheet);
		fileOutputStream = new FileOutputStream(strExcelFilePath);
		
		if(hmTCLog.get("Iteration1") != null)
		{
			String[] sIteration1Data =  hmTCLog.get("Iteration1").split("~");
			if(sIteration1Data.length < 4)
			{
				if(sIteration1Data[2].equals("true"))
				{
					//RPHADKE_08102018 - Changed status from Pass With Warning to Pass and sent Warning word in failure log
					sTCResult = "PASS";
					createSummaryWarningStepCell(2);
					sFailureLog = "WARNING";
				}
				else
				{
					sTCResult = "PASS";
					createSummaryPassStepCell(2);
					sFailureLog = "N/A";
				}
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "1");
				
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";;
				sFailScreenShotPath = "N/A";
			}
			else if(sIteration1Data.length == 4)
			{
				sTCResult = "FAIL";
				createSummaryFailStepCell(2);
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "1");
				createSummaryStepCell(8, sIteration1Data[2]);
				createSummaryStepCell(9, sIteration1Data[3]);
				sFailureLog = sIteration1Data[2];
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";
				sFailScreenShotPath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\surefire_reports\\ScreenShot\\" +sIteration1Data[3];
			}
			
			objSqlData.put(1, new BatchExcetionDeatils(runningScriptName, System.getProperty("user.home"), sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "1", sFailureLog, objConfig.getProperty("Deliverables"), sLogFilePath, sFailScreenShotPath,InitializeTearDownEnvironment.browserVersion));
		}
		if(hmTCLog.get("Iteration2") != null)
		{
			intSummaryRowCounter++;
			summarySheet = workbook.getSheet("TestSummary");
			summaryRow = summarySheet.createRow(intSummaryRowCounter);
			this.createSummaryLinkCell(0, runningScriptName, sReportSheet);
			this.createSummaryStepCell(1, System.getProperty("user.name"));
			this.createStartDateTimeCell(3);
			this.createSummaryStepCell(5, runningPackageName);
			this.createSummaryStepCell(6,InetAddress.getLocalHost().getHostName());
			this.createSummaryStepCell(7,"1");
			String[] sIteration1Data =  hmTCLog.get("Iteration2").split("~");
			if(sIteration1Data.length < 3)
			{
				sTCResult = "PASS";
				createSummaryPassStepCell(2);
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "2");
				sFailureLog = "N/A";
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";;
				sFailScreenShotPath = "N/A";
			}
			else if(sIteration1Data.length == 4)
			{
				sTCResult = "FAIL";
				createSummaryFailStepCell(2);
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "2");
				createSummaryStepCell(8, sIteration1Data[2]);
				createSummaryStepCell(9, sIteration1Data[3]);
				sFailureLog = sIteration1Data[2];
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";
				sFailScreenShotPath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + runningPackageName + "\\surefire_reports\\ScreenShot\\" +sIteration1Data[3];
				sFailureLog = sIteration1Data[2];
			}
			objSqlData.put(2, new BatchExcetionDeatils(runningScriptName, System.getProperty("user.home"), sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "2", sFailureLog, objConfig.getProperty("Deliverables"), sLogFilePath, sFailScreenShotPath,InitializeTearDownEnvironment.browserVersion));
		}
		//this.createSummaryStepCell(10, objPojoObject.getObjectId());
		this.addSummarySheetLink();

		//this.autoSetColumnWidth();
		// writing the workbook object into the file created in startReport()
		
		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		
		//RPHADKE_02082018 - Added logic for wednesday execution
		Calendar objCalender = Calendar.getInstance();
		Date objToday = objCalender.getTime();
		String sDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(objToday.getTime());
		System.out.println(sDay);
		if(objConfig.getProperty("insertResultInDB").equalsIgnoreCase("true") || sDay.equalsIgnoreCase("Wednesday"))
		{
			System.out.println("Insert into SQL");
			this.insertRunResultInSQL(objSqlData);
		}
		objSqlData = null;
	}

	public void endExcelReport(String sObjectID) throws IOException, InvalidFormatException, ParseException  
	{
		String sTCResult = "";
		String sFailureLog = "";
		String sFailScreenShotPath = "";
		String sLogFilePath = "";
		String sTester_Name = "";
		String sSuiteName="";
		
		//RPHADKE_27072018 - Added parameter in cofig which decides whether to insert results in sql or not
		Properties objConfig = new Properties();
		objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		
		//RPHADKE_06082018 - get today's date in yyyy-MM-dd format in oder to get shared drive path
		LocalDate dtTodayWithoutTime = LocalDate.now();
		System.out.println(dtTodayWithoutTime);
		
		
		HashMap<Integer, BatchExcetionDeatils> objSqlData = new HashMap<Integer, BatchExcetionDeatils>();
		System.out.println("End excel report");
		FileOutputStream fileOutputStream = new FileOutputStream(strExcelFilePath);
//		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		String sReportSheet = "";
		
		//runningScriptName = "TestCase";
		// Excel allow sheet name long upto 31 characters. 
		// In excel report sheets are created by using running script name. 
		if(runningScriptName.length() > 30)
			sReportSheet = runningScriptName.substring(0, 30);
		else
			sReportSheet = runningScriptName;
		
		//Set Test Name
		String sMachineUser = System.getProperty("user.name");
		if(sMachineUser.equalsIgnoreCase("sbansodex0108235"))
			sTester_Name = "Supriya Bansode";
		else if(sMachineUser.equalsIgnoreCase("PChopadeX0113581"))
			sTester_Name = "Poonam Chopade";	
		else if(sMachineUser.equalsIgnoreCase("X0147435"))
			sTester_Name = "Shubhankar Puri";	
		else if(sMachineUser.equalsIgnoreCase("X0147450"))
			sTester_Name = "Pranit Borkar";	
		else if(sMachineUser.equalsIgnoreCase("X0147434"))
			sTester_Name = "Suraj Rathod";	
		else if(sMachineUser.equalsIgnoreCase("x0142281"))
			sTester_Name = "Sanjay Joshi";	
		else if(sMachineUser.equalsIgnoreCase("x0147569"))
			sTester_Name = "Amit Mali";
		else if(sMachineUser.equalsIgnoreCase("x0147449"))
			sTester_Name = "Avinash Kadam";
		
		//==================== Copy Result to share location ==============================================
		String strRptFolderDate = new SimpleDateFormat("dd_MMM_yyyy").format(new java.util.Date());
		String sLogFilePathNew1 = objConfig.getProperty("SharedDriveResultsPath")+"\\ExecutionResults\\"+strRptFolderDate;
		File ResultFile = new File(sLogFilePathNew1);
		if(!ResultFile.exists())
			new File(sLogFilePathNew1).mkdir();
		
		String strMachineName = InetAddress.getLocalHost().getHostName();
		sLogFilePathNew1 = objConfig.getProperty("SharedDriveResultsPath")+"\\ExecutionResults\\"+strRptFolderDate+"\\"+strMachineName;
		File ResultFile1 = new File(sLogFilePathNew1);
		if(!ResultFile1.exists())
			new File(sLogFilePathNew1).mkdir();
		
		String RptTxtPath = System.getProperty("user.dir") + "\\target\\custom-reports\\"+runningScriptName+".txt";
		this.CopyRptTXTFileToAnotherLocation(RptTxtPath, sLogFilePathNew1);
		String RptSnapshotPath = System.getProperty("user.dir") + "\\target\\surefire-reports\\ScreenShot";
		this.CopyRptTXTFileToAnotherLocation(RptSnapshotPath, sLogFilePathNew1);
	//==================================================================================================
			
		//Read TC sheet
		HashMap <String,String> hmTCLog = new HashMap<String,String>();
		if(objConfig.getProperty("SCV_Environment").equalsIgnoreCase("true")) 
			hmTCLog = this.readSCVTCExecutionReport(sReportSheet);
		else
			hmTCLog = this.readTCExecutionReport(sReportSheet);
		 
		fileOutputStream = new FileOutputStream(strExcelFilePath);
				
		//Set SuiteName
		sSuiteName = System.getProperty("suiteName");
		
		if(hmTCLog.get("Iteration1") != null)
		{
			String[] sIteration1Data =  hmTCLog.get("Iteration1").split("~");
			if(sIteration1Data.length < 4)
			{
				if(sIteration1Data[2].equals("true"))
				{
					sTCResult = "PASS";
					createSummaryWarningStepCell(2);
					sFailureLog = "WARNING";
					WrapperFunctions.failureType="NA";

				}
				else
				{
					sTCResult = "PASS";
					createSummaryPassStepCell(2);
					sFailureLog = "N/A";
					WrapperFunctions.failureType="NA";
				}
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "1");
				createSummaryStepCell(8, sFailureLog);
				createSummaryStepCell(11, WrapperFunctions.failureType);
				createSummaryStepCell(12, runningJiraID); //namrata10082021
				
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";;
				sFailScreenShotPath = sLogFilePathNew1+"\\"+runningScriptName+"_Iteration1_LastPassStep.png";
				sLogFilePath = sLogFilePathNew1+"\\"+runningScriptName+".txt";
			}
			else if(sIteration1Data.length == 4)
			{
				sTCResult = "FAIL";
				createSummaryFailStepCell(2);
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "1");
				createSummaryStepCell(8, sIteration1Data[2]);
				createSummaryStepCell(9, sIteration1Data[3]);
				createSummaryStepCell(11, WrapperFunctions.failureType);
				createSummaryStepCell(12, runningJiraID); //namrata10082021
				
				sFailureLog = sIteration1Data[2];
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";
				sFailScreenShotPath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\surefire_reports\\ScreenShot\\" +sIteration1Data[3];
				sLogFilePath = sLogFilePathNew1+"\\"+runningScriptName+".txt";
				sFailScreenShotPath = sLogFilePathNew1+"\\"+runningScriptName+"_Iteration1.png";
			}
			
			//objSqlData.put(1, new BatchExcetionDeatils(runningScriptName, System.getProperty("user.home"), sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "1", sFailureLog, objConfig.getProperty("Deliverables"), sLogFilePath, sFailScreenShotPath));
			//RPHADKE_29102018 - deliverables parameter accepted from Jenkins
			String sDelv = "N/A";
			if(System.getProperty("deliverables") != null)
			{
				sDelv = System.getProperty("deliverables");
				//RPHADKE_25022019 - Code to add space between deliverables value if it is like Sprint1
				if(sDelv.contains("Sprint"))
				{
					String arrDelv[] = sDelv.split("Sprint");
					if(arrDelv[1].matches("[0-9]+"))
						sDelv = "Sprint "+ arrDelv[1];
				}
				
			}
			objSqlData.put(1, new BatchExcetionDeatils(runningScriptName, sTester_Name, sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "1", sFailureLog, sDelv, sLogFilePath, sFailScreenShotPath,InitializeTearDownEnvironment.browserVersion));
		}
		if(hmTCLog.get("Iteration2") != null)
		{
			intSummaryRowCounter++;
			summarySheet = workbook.getSheet("TestSummary");
			summaryRow = summarySheet.createRow(intSummaryRowCounter);
			this.createSummaryLinkCell(0, runningScriptName, sReportSheet);
			this.createSummaryStepCell(1, System.getProperty("user.name"));
			this.createStartDateTimeCell(3);
			this.createSummaryStepCell(5, runningPackageName);
			this.createSummaryStepCell(6,InetAddress.getLocalHost().getHostName());
			this.createSummaryStepCell(7,"1");
			createSummaryStepCell(11, WrapperFunctions.failureType);
			createSummaryStepCell(12, runningJiraID); //namrata10082021
			
			String[] sIteration1Data =  hmTCLog.get("Iteration2").split("~");
			if(sIteration1Data.length < 4)
			{
				if(sIteration1Data[2].equals("true"))
				{
					sTCResult = "PASS";
					createSummaryWarningStepCell(2);
					sFailureLog = "WARNING";
					WrapperFunctions.failureType="NA";
				}
				else
				{
					sTCResult = "PASS";
					createSummaryPassStepCell(2);
					sFailureLog = "N/A";
					WrapperFunctions.failureType="NA";

				}
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "2");
				createSummaryStepCell(8, sFailureLog);
				createSummaryStepCell(11, WrapperFunctions.failureType);
				createSummaryStepCell(12, runningJiraID); //namrata10082021
				
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";;
				sFailScreenShotPath = "N/A";
				sFailScreenShotPath = sLogFilePathNew1+"\\"+runningScriptName+"_Iteration1_LastPassStep.png";
				sLogFilePath = sLogFilePathNew1+"\\"+runningScriptName+".txt";
			}
			else if(sIteration1Data.length == 4)
			{
				sTCResult = "FAIL";
				createSummaryFailStepCell(2);
				
				createSummaryStepCell(3, sIteration1Data[0]);
				createSummaryStepCell(4, sIteration1Data[1]);
				createSummaryStepCell(7, "2");
				createSummaryStepCell(8, sIteration1Data[2]);
				createSummaryStepCell(9, sIteration1Data[3]);
				createSummaryStepCell(11, WrapperFunctions.failureType);
				createSummaryStepCell(12, runningJiraID); //namrata10082021
				
				sFailureLog = sIteration1Data[2];
				sLogFilePath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\custom_reports\\Execution_Report.xlsx#" + runningScriptName + "!A1";
				sFailScreenShotPath = objConfig.getProperty("SharedDriveResultsPath") + "\\" + dtTodayWithoutTime.toString() + "\\" + sSuiteName + "\\surefire_reports\\ScreenShot\\" +sIteration1Data[3];
				sFailureLog = sIteration1Data[2];
				sLogFilePath = sLogFilePathNew1+"\\"+runningScriptName+".txt";
				sFailScreenShotPath = sLogFilePathNew1+"\\"+runningScriptName+"_Iteration1.png";
			}
			//objSqlData.put(2, new BatchExcetionDeatils(runningScriptName, System.getProperty("user.home"), sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "2", sFailureLog, objConfig.getProperty("Deliverables"), sLogFilePath, sFailScreenShotPath));
			//RPHADKE_29102018 - deliverables parameter accepted from Jenkins
			String sDelv = "N/A";
			if(System.getProperty("deliverables") != null)
			{
				sDelv = System.getProperty("deliverables");
				//RPHADKE_25022019 - Code to add space between deliverables value if it is like Sprint1
				if(sDelv.contains("Sprint"))
				{
					String arrDelv[] = sDelv.split("Sprint");
					if(arrDelv[1].matches("[0-9]+"))
						sDelv = "Sprint "+ arrDelv[1];
				}
				
			}
			objSqlData.put(2, new BatchExcetionDeatils(runningScriptName, sTester_Name, sTCResult, sIteration1Data[0], sIteration1Data[1], runningPackageName, InetAddress.getLocalHost().getHostName(), "2", sFailureLog, sDelv, sLogFilePath, sFailScreenShotPath,InitializeTearDownEnvironment.browserVersion));
		}
		//RPHADKE_24092018 - Added code which will add object ID in excel column Object ID
		if(sObjectID.equals(null))
			sObjectID = "N/A";
		
		//Added code for SCV Testcases
		if(objConfig.getProperty("SCV_Environment").equalsIgnoreCase("true"))
		{
			if(sObjectID.length()<=0)
				sObjectID = "N/A";			 
		}
		this.createSummaryStepCell(10, sObjectID);
		this.addSummarySheetLink();

		//this.autoSetColumnWidth();
		// writing the workbook object into the file created in startReport()
		
		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		
		//========== Update Result to JIRA ======================================
		if(objConfig.getProperty("UploadJiraResult").equalsIgnoreCase("true"))
		{
			System.out.println("Updating results into JIRA");
			this.updateExecutionResultsinJIRA(objSqlData, sObjectID,RptSnapshotPath);
		}
		//=======================================================================
		
		//RPHADKE_02082018 - Added logic for wednesday execution
		System.out.println("Insert in SQL : "+System.getProperty("insertInSQL"));
		System.out.println("Deliverables : "+System.getProperty("deliverables"));
		System.out.println("Suite Name : "+System.getProperty("suiteName"));
		
		//RPHADKE_29102018 - insert in SQL based on parameter value 'insertInSQL'
		//if(System.getProperty("insertInSQL") != null && System.getProperty("insertInSQL").equals("Yes"))
		String insert = "true";
	/*	if(insert=="true")
			this.insertRunResultInSQL(objSqlData, sObjectID);*/
		//if(System.getProperty("deliverables")!=null&&System.getProperty("deliverables")!="")
		
		if(objConfig.getProperty("DBInsertInSQL").contains("true"))
//		if(System.getProperty("insertInSQL").contains("true"))
			this.insertRunResultInSQL(objSqlData, sObjectID);
			//System.out.println("Currently commented insertIntoSQL function for testing purpose");
		
		/*Calendar objCalender = Calendar.getInstance();
		Date objToday = objCalender.getTime();
		String sDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(objToday.getTime());
		System.out.println(sDay);
		System.out.println("Insert in SQL parameter value : "+System.getProperty("insertInSQL"));
		if(objConfig.getProperty("insertResultInDB").equalsIgnoreCase("true") || sDay.equalsIgnoreCase("Wednesday"))
		{
			System.out.println("Insert into SQL");
			//this.insertRunResultInSQL(objSqlData, sObjectID);
		}*/
		
		if(runningScriptName.equalsIgnoreCase("SCV_Scenario3") && objConfig.getProperty("SCV_Environment").equalsIgnoreCase("true"))
		{
			try {
			String sOldLogFilePath = "\""+System.getProperty("user.dir") + "\\target\\custom-reports\\Execution_Report.xlsx"+"\"";
			String sNewLogFilePath = "\""+objConfig.getProperty("sSCVResultLocation")+"\"";
			this.CopyXlsxFileToAnotherLocation(sOldLogFilePath, sNewLogFilePath);
			
			String sNewLogFilePath_1 = "\""+objConfig.getProperty("sSCVResultLocation")+ "\\Execution_Report.xlsx"+"\"";
			String strDate = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new java.util.Date());
			String sNewnResultName = "SCV_" + strDate +".xlsx";
			this.RenameXlsxFileName(sNewLogFilePath_1, sNewnResultName);
			
			sNewLogFilePath_1 = objConfig.getProperty("sSCVResultLocation")+ "\\"+sNewnResultName;
			String sInsertValues=sNewLogFilePath_1+","+InetAddress.getLocalHost().getHostName();
			this.UpdateFailLogPathRunResultInSQLForSCVTC(sNewLogFilePath_1,InetAddress.getLocalHost().getHostName());	
			
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
		
		
		
		objSqlData = null;
	}
	
	/**
	 * End HTML report
	 * @throws IOException
	 */
	public void endHTMLReport() throws IOException  
	{
		if(exixtingHTML)
		{
			FileReader fileReader = new FileReader(strHTMLFilePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			strTempHTMLFilePath = System.getProperty("user.dir") + "/target/custom-reports/Html_Report/Execution_Report_New.html";
			// Assume default encoding.
			fileWriter = new FileWriter(strTempHTMLFilePath);

			// Wrap FileWriter in BufferedWriter.
			bufferedWriter = new BufferedWriter(fileWriter);

			String currentLine = "";
			String putData = "";
			String pageContent = "";
			String tempContent = "";
			boolean blntestCaseTable = false;
			boolean blnFadeDiv = false;
			boolean blnCustomReportPassCount = false;
			boolean blnCustomReportFailCount = false;
			boolean blnHighChartDiv = false;
			boolean blnHighChartDivPass = false;
			boolean blnHighChartDivFail = false;
			currentLine = bufferedReader.readLine();

			while(currentLine != null)
			{ 
				putData = currentLine;
				if(currentLine.contains("_customReportTestCaseTable"))
					blntestCaseTable = true;

				if(blntestCaseTable && currentLine.contains("</table>"))
				{ 
					putData =  	"<tr> \n"+
							"		<td width='70%' align='left' class='expandCollpase'> "+ " <a href='javascript:void(0)' onclick=\"document.getElementById('" + runningScriptDivName + "').style.display='block';document.getElementById('fade').style.display='block'\"> " + runningScriptName + "</a></td> \n"+ 
							"		<td width='30%' align ='center' class='expandCollpase' id = '"+ runningScriptDivName +" _Status'>  </td> \n"+
							"	</tr> \n" +
							"</table> \n";
					blntestCaseTable = false;
				}

				if(currentLine.contains("_customReport_black_overlay"))
					blnFadeDiv = true;

				if(blnFadeDiv)
				{
					htmlPageOutput = htmlPageOutput + "<script> document.getElementById('"+ runningScriptDivName +" _Status').innerHTML =";
					if(intHTMLReportNoOfFails > 0)
					{
						intTotalFail = 1;
						htmlPageOutput = htmlPageOutput + "'<span> <font color=#E03C31><b>Fail</b> </font></span>'";
					}
					else
					{
						intTotalPass = 1;
						htmlPageOutput = htmlPageOutput + "'<span> <font color=#7BB661><b>Pass</b> </font></span>'";
					}

					htmlPageOutput = htmlPageOutput + "</script></table>\n</div>\n</div>\n";

					putData = putData + "\n" + htmlPageOutput;
					blnFadeDiv = false;
				}

				if(currentLine.contains("_customReportPassCount"))
					blnCustomReportPassCount = true;

				if(blnCustomReportPassCount && currentLine.contains("value='"))
				{
					tempContent = currentLine.substring(currentLine.indexOf("value='"), currentLine.indexOf("'>"));
					tempContent = tempContent.substring(tempContent.indexOf("'") + 1);
					intTotalPassGraph = (Integer.parseInt(tempContent) + intTotalPass);
					if(intTotalPass > 0)
						putData = "<input type='hidden' name ='_customReportPassCount' id='_customReportPassCount' value='" + intTotalPassGraph + "'>\n";
					blnCustomReportPassCount = false;
				}

				if(currentLine.contains("_customReportFailCount"))
					blnCustomReportFailCount = true;

				if(blnCustomReportFailCount)
				{
					tempContent = currentLine.substring(currentLine.indexOf("value='"), currentLine.indexOf("'>"));
					tempContent = tempContent.substring(tempContent.indexOf("'") + 1);
					intTotalFailGraph = (Integer.parseInt(tempContent) + intTotalFail);
					if(intTotalFail > 0)
						putData = "<input type='hidden' name ='_customReportFailCount' id='_customReportFailCount' value='" + intTotalFailGraph + "'>\n";
					blnCustomReportFailCount = false;
				}

				if(currentLine.contains("jQuery(document).ready"))
					blnHighChartDiv = true;

				if(blnHighChartDiv && currentLine.contains("var passCount"))
					blnHighChartDivPass =  true;

				if(blnHighChartDiv && blnHighChartDivPass)
				{
					putData = " 	  var passCount = " + intTotalPassGraph + "; "+ "\n";
					blnHighChartDivPass = false;
				}

				if(blnHighChartDiv && currentLine.contains("var failCount"))
					blnHighChartDivFail =  true;

				if(blnHighChartDiv && blnHighChartDivFail)
				{
					putData =  " 	  var failCount = " + intTotalFailGraph +"; "+ "\n";
					blnHighChartDivFail = false;
					blnHighChartDiv = false;
				}

				pageContent = pageContent + (putData + "\n");
				currentLine = bufferedReader.readLine();
			}
			bufferedWriter.write(pageContent);
			bufferedReader.close();
			fileReader.close();
			bufferedWriter.close();
			fileWriter.close();

			File existingReportFile = new File(strHTMLFilePath);
			existingReportFile.delete();
			new File(strTempHTMLFilePath).renameTo(new File(strHTMLFilePath));
		}
		else
		{
			htmlPageOutput = htmlPageOutput + "<script> document.getElementById('"+ runningScriptDivName +" _Status').innerHTML =";
			if(intHTMLReportNoOfFails > 0)
			{
				intTotalFail = 1;
				intTotalFailGraph = 1;
				htmlPageOutput = htmlPageOutput + "'<span> <font color=#E03C31><b>Fail</b> </font></span>'";
			}
			else
			{
				intTotalPass = 1;
				intTotalPassGraph = 1;
				htmlPageOutput = htmlPageOutput + "'<span> <font color=#7BB661><b>Pass</b> </font></span>'";
			}

			htmlPageOutput = htmlPageOutput + "</script>\n</table>\n</div>\n</div>\n"+
					"<input type='hidden' name ='_customReportPassCount' id='_customReportPassCount' value='" + intTotalPass + "'>\n"+
					"<input type='hidden' name ='_customReportFailCount' id='_customReportFailCount' value='" + intTotalFail + "'>\n"+
					" <script type='text/javascript'> "+ "\n"+
					" $(function () { "+ "\n"+
					" jQuery(document).ready(function(){ "+ "\n"+
					" 	  var passCount = " + intTotalPassGraph + "; "+ "\n"+
					" 	  var failCount = " + intTotalFailGraph +"; "+ "\n"+
					" 	  var chart = new Highcharts.Chart({ "+ "\n"+
					" 	 chart: {renderTo: '_customReportGraph', margin: 75, options3d: {enabled: true,alpha: 20,beta: 33,depth: 50,viewDistance:25}}, "+ "\n"+  	
					" 		title: {text: 'Execution Status'}, "+ "\n"+
					" 		subtitle: {text: ''}, "+ "\n"+	
					" 		credits: {enabled: false}, "+ "\n"+
					" 		xAxis: {categories: ['']}, "+ "\n"+
					" 		yAxis: {title: {text: ''}}, "+ "\n"+
					" 		plotOptions: {column: {depth: 25}}, "+ "\n"+
					" 		series: [{type: 'column', name: 'Pass', color: '#33CC33', data: [['Pass', passCount ]]}, "+ "\n"+
					" 					{type: 'column', name: 'Fail', color: '#CC0000', data: [['Fail', failCount ]] }] "+ "\n"+
					" 		}); "+ "\n"+
					"   $('#GraphType').change(function() { "+ "\n"+
					" 	 var graphType = $('#GraphType option:selected').text(); "+ "\n"+
					"     if(graphType == '3D Column') "+ "\n"+
					" 	{ "+ "\n"+
					" 	  var chart = new Highcharts.Chart({ "+ "\n"+
					" 	 chart: {renderTo: '_customReportGraph', margin: 75, options3d: {enabled: true,alpha: 20,beta: 33,depth: 50,viewDistance:25}}, "+ "\n"+
					" 		title: {text: 'Execution Status'}, "+ "\n"+
					" 		subtitle: {text: ''}, "+ "\n"+
					" 		credits: {enabled: false}, "+ "\n"+
					" 		xAxis: {categories: ['']}, "+ "\n"+
					" 		yAxis: {title: {text: ''}}, "+ "\n"+
					" 		plotOptions: {column: {depth: 25}}, "+ "\n"+
					" 		series: [{type: 'column', name: 'Pass', color: '#33CC33', data: [['Pass', passCount ]]}, "+ "\n"+ 	
					" 					{type: 'column', name: 'Fail', color: '#CC0000', data: [['Fail', failCount ]] }] "+ "\n"+  	
					" 		}); "+ "\n"+ 
					"  	} "+ "\n"+
					" 	if(graphType == 'Pie') "+ "\n"+
					" 	{ "+ "\n"+
					" 	     $('#_customReportGraph').highcharts({ "+ "\n"+
					"             chart: {plotBackgroundColor: null,plotBorderWidth: null,plotShadow: false}, "+ "\n"+
					"             title: {    text: 'Execution Status'}, "+ "\n"+
					" 			credits: {enabled: false}, "+ "\n"+
					"             tooltip: {pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'}, "+ "\n"+
					"             plotOptions: {pie: {allowPointSelect: true,cursor: 'pointer',dataLabels: {enabled: true},showInLegend: true}}, "+ "\n"+
					"             series: [{type: 'pie',data: [['Pass', passCount ],['Fail', failCount ],]}] "+ "\n"+
					"         }); "+ "\n"+
					" 	}  "+ "\n"+
					" 	if(graphType == 'Basic Column') "+ "\n"+
					" 	{ "+ "\n"+
					" 	$('#_customReportGraph').highcharts({ "+ "\n"+
					"         chart: {type: 'column'}, "+ "\n"+
					"         title: {text: 'Execution Status'}, "+ "\n"+
					" 		credits: {enabled: false}, "+ "\n"+
					"         xAxis: {categories: ['']}, "+ "\n"+
					"         yAxis: {title: {text: ''}}, "+ "\n"+
					" 		series: [{type: 'column', name: 'Pass', color: '#33CC33', data: [['Pass', passCount ]]}, "+ "\n"+ 	
					" 					{type: 'column', name: 'Fail', color: '#CC0000', data: [['Fail', failCount ]] }]  "+ "\n"+ 
					"     }); "+ "\n"+
					" 	} "+ "\n"+
					" 	if(graphType == 'Basic Bar') "+ "\n"+
					" 	{ "+ "\n"+
					" 	$('#_customReportGraph').highcharts({ "+ "\n"+
					"         chart: { type: 'bar'}, "+ "\n"+
					"         title: {text: 'Execution Status'}, "+ "\n"+
					" 		credits: {enabled: false}, "+ "\n"+
					"         xAxis: {categories: ['']}, "+ "\n"+
					"         yAxis: {title: {text: ''}}, "+ "\n"+
					"        series: [{type: 'column', name: 'Pass', color: '#33CC33', data: [['Pass', passCount ]]}, "+ "\n"+ 	
					" 					{type: 'column', name: 'Fail', color: '#CC0000', data: [['Fail', failCount ]] }] "+ "\n"+  
					"     }); "+ "\n"+
					" 	} "+ "\n"+
					"   }); "+ "\n"+
					" }); "+ "\n"+
					" }); "+ "\n"+		 
					" </script> "+ "\n"+
					" </body>\n</html>\n"; 

			bufferedWriter.write(htmlPageOutput);
			bufferedWriter.close();
			fileWriter.close();
		}
	}

	/**
	 * 
	 * @param cellNumber
	 * @param value
	 * @param nevigationSheet
	 */
	public void createSummaryLinkCell(int cellNumber, String value, String navigationSheet) {
        XSSFCell cell = summaryRow.createCell(cellNumber);
        cell.setCellValue(value);

        CreationHelper createHelper = workbook.getCreationHelper();
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link.setAddress("'" + navigationSheet + "'!A1");
        cell.setHyperlink(link);
        cell.setCellStyle(getHyperlinkCellStyle());
    }

    private XSSFCellStyle getHyperlinkCellStyle() {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setUnderline(FontUnderline.SINGLE);
        font.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(font);
        return style;
    }

    public XSSFCellStyle getHeaderCellStyle() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Arial");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    public XSSFCellStyle getPassCellStyle() {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    public XSSFCellStyle getInfoCellStyle() {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public XSSFCellStyle getFailCellStyle() {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

	public void createSummaryPassStepCell(int cellNumber)
	{
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("PASS"));
		cell.setCellStyle(getPassCellStyle());
	}
	
	public void createStartDateTimeCell(int cellNumber)
	{
		String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
		//startDate = CustomReporterHelper.GetItemDate(strDate);
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(strDate));
		cell.setCellStyle(getInfoCellStyle());
	}
	
	public void createEndDateTimeCell(int cellNumber)
	{
		String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
		endDate = CustomReporterHelper.GetItemDate(strDate);
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(strDate));
		cell.setCellStyle(getInfoCellStyle());
		int duration = CustomReporterHelper.minutesDiff(startDate,endDate);
		System.out.println("Duration: "+duration);
	//	XSSFCell cell1 = summaryRow.createCell(7);
	//	cell1.setCellValue(new XSSFRichTextString(String.valueOf(duration)));
	//	cell1.setCellStyle(getInfoCellStyle());
	}
	
	
	//********************** Rohit Ghanekar Duration between 2 dates methods
	public static Date GetItemDate(final String date)
	{
	    final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	    final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a",Locale.US);
	    format.setCalendar(cal);

	    try {
	        return format.parse(date);
	    } catch (ParseException e) {
	        return null;
	    }
	}
	
	public static int minutesDiff(Date earlierDate, Date laterDate)
	{ System.out.println(earlierDate);
	    if( earlierDate == null || laterDate == null ) return 0;

	    return (int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000));
	}
	
	public void createSummaryStepCell(int cellNumber, String value)
	{
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(getInfoCellStyle());
	}

	public void createSummaryFailStepCell(int cellNumber)
	{
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("FAIL"));
		cell.setCellStyle(getFailCellStyle());
	}

	public XSSFCellStyle getFailCellFontStyle() {
        XSSFFont failFont = workbook.createFont();
        failFont.setColor(IndexedColors.RED.getIndex()); // Modern replacement for HSSFColor.RED
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(failFont);
        return cellStyle;
    }

    public XSSFCellStyle getCategoryCellStyle() {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // Modern replacement
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

	public void createSheetHeaderCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getHeaderCellStyle());
	}

	public void createSheetCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
	}

	public void createSheetFailCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(getFailCellFontStyle());
	}
	
	public void createSheetPassStepCell(int cellNumber)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("PASS"));
		cell.setCellStyle(getPassCellStyle());
	}

	public void createSheetFailStepCell(int cellNumber)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("FAIL"));
		cell.setCellStyle(getFailCellStyle());
	}

	public void createSheetInfoStepCell(int cellNumber)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("INFO"));
		cell.setCellStyle(getInfoCellStyle());
	}

	public void createSheetCategoryCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(getCategoryCellStyle());
	}


	public void createSummaryHeaderCell(int cellNumber, String value)
	{
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getHeaderCellStyle());
	}

	public void addSummarySheetLink() {
        XSSFRow navigationBar = currentReportSheet.createRow(intRowCounter++);
        CreationHelper createHelper = workbook.getCreationHelper();

        XSSFCell cell = navigationBar.createCell(0);
        cell.setCellValue("");
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link.setAddress("'TestSummary'!A1");
        cell.setHyperlink(link);
        cell.setCellStyle(getNavigationCellStyle());

        XSSFCell cell1 = navigationBar.createCell(1);
        cell1.setCellValue("Go To Test Summary Sheet");
        Hyperlink link1 = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link1.setAddress("'TestSummary'!A1");
        cell1.setHyperlink(link1);
        cell1.setCellStyle(getNavigationCellStyle());
    }

    private XSSFCellStyle getNavigationCellStyle() {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont hlinkFont = workbook.createFont();
        hlinkFont.setUnderline(FontUnderline.SINGLE);
        hlinkFont.setColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFont(hlinkFont);
        cellStyle.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

	public void autoSetColumnWidth()
	{
		summarySheet.autoSizeColumn(0);
		summarySheet.autoSizeColumn(1);
		currentReportSheet.autoSizeColumn(0);
		currentReportSheet.autoSizeColumn(1);
		currentReportSheet.autoSizeColumn(2);
		currentReportSheet.autoSizeColumn(3);
		currentReportSheet.autoSizeColumn(4);
		currentReportSheet.autoSizeColumn(5);
		currentReportSheet.autoSizeColumn(6);
		currentReportSheet.autoSizeColumn(7);
	}  

	public String addScriptDetailsTable(String runningScriptName, String runningScriptDivName)
	{
		String strScriptDetails = ""+
				" 	<div style='display: none;' id='" + runningScriptDivName + "' class='white_content'> \n"+ 
				" 		<table id='testNameTable'> \n"+   
				" 			<tr> \n"+ 
				" 				<td align ='center' width ='80%'> " + runningScriptName + " </td> \n"+ 
				" 				<td align ='right' width ='20%'> <button onclick=\"document.getElementById('fade').style.display='none';document.getElementById('" + runningScriptDivName + "').style.display='none'\">Close</button></td> \n"+
				" 			</tr> \n"+
				" 		</table> \n"+
				" 		<div style='width:100%; overflow:auto;height:90%;'> \n"+
				" 		<table id='tableStyle' width='100%' class='chartStyle' > \n"+ 
				" 			<thead> \n"+  
				" 				<tr> \n"+
				" 					<th width='5%' align ='center'  class='expandCollpase'>S.No</th> \n"+
				" 					<th width='60%' align='Center' class='expandCollpase'>Step Description</th> \n"+
				" 					<th width='10%' align ='center'  class='expandCollpase'>Input Value</th> \n"+
				" 					<th width='10%' align ='center'  class='expandCollpase'>Expected Value</th> \n"+
				" 					<th width='10%' align ='center'  class='expandCollpase'>Actual Value</th> \n"+
				" 					<th width='5%' align ='center'  class='expandCollpase'>Status</th> \n"+
				" 				</tr> \n"+
				" 			</thead> \n";
		return strScriptDetails;
	}

	/**
	 * @Method		: getRequiredDate
	 * @Description	: This method will give require date
	 * @param		: incrfementDateByDays Number by which user want increase date 
	 * @param		: sExpectedDateFormat - User expected date format
	 * 					eg. 9 april 2014 --- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 */
	public String getRequiredDate(int incrementDays, String expectedDateFormat, String timeZoneId)
	{
		try 
		{
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if(timeZoneId != null && ! timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.DAY_OF_MONTH, incrementDays);
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}
	
	public void CreateBatchExcelReport() throws IOException, InvalidFormatException
	{
		String reportSheet = "";
		
		// Excel report file path
		strExcelFilePath = System.getProperty("user.dir") + "/target/custom-reports/BatchDetails.xlsx";
		File reportFile = new File(strExcelFilePath);

		// If file not exist create new with summary sheet and sheet for running script details
		if(!reportFile.exists())
		{
			new File(System.getProperty("user.dir") + "/target/custom-reports").mkdir();
			reportFile.createNewFile();
			workbook = new XSSFWorkbook();
			
			summarySheet = workbook.createSheet("TestDetails");
			summaryRow = summarySheet.createRow(0);
			this.createSummaryHeaderCell(0, "Sr No.");
			this.createSummaryHeaderCell(1, "EIT Release");
			this.createSummaryHeaderCell(2, "Functionality");
			this.createSummaryHeaderCell(3, "Priority");
			this.createSummaryHeaderCell(4, "Environment");
			this.createSummaryHeaderCell(5, "Site");
			this.createSummaryHeaderCell(6, "Business Group");
			this.createSummaryHeaderCell(7, "Test Case");
			this.createSummaryHeaderCell(8, "Tester Name");
			this.createSummaryHeaderCell(9, "Machine Name");
			this.createSummaryHeaderCell(10, "Batch Iteration");
			this.createSummaryHeaderCell(11, "Start Time");
			this.createSummaryHeaderCell(12, "End Time");
			this.createSummaryHeaderCell(13, "Duration in Minutes");
			this.createSummaryHeaderCell(14, "Result");
			this.createSummaryHeaderCell(15, "Comments");
			//Removed logs column
			//this.createSummaryHeaderCell(16, "logs");
			//Removed deliverables column
			//this.createSummaryHeaderCell(16, "Deliverable");
			this.createSummaryHeaderCell(16, "FailureType");
			this.createSummaryHeaderCell(17, "ObjectID");
			this.createSummaryHeaderCell(18, "FunctionName");
			this.createSummaryHeaderCell(19, "Failure");
			
			
			
			FileOutputStream fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
	}
	public String getCellData(int RowNum, int ColNum)
	{
	   try{
		   XSSFCell Cell = summarySheet.getRow(RowNum).getCell(ColNum);
		  String CellData = Cell.getStringCellValue();
		  return CellData;
		  }catch (Exception e){
			return"";
			}
	}
	
	public int getRowUsed() 
	{
			try{
			int RowCount = summarySheet.getLastRowNum();
			return RowCount;

			}catch (Exception e){

				System.out.println(e.getMessage());
			}
			return 0;

	}
	
	public int getColumnUsed() 
	{
		try{
			int ColumnCount = summarySheet.getRow(0).getLastCellNum();
			return ColumnCount;

			}catch (Exception e){

				System.out.println(e.getMessage());
			}
			return 0;

	}
	public void ReadExecutionExcelReport() throws IOException, InvalidFormatException
	{
		String userhome,reportSheet = "";		
		int iCount;
		Hm = new HashMap<Integer, BatchExcetionDeatils>();
		try {
			ExcelFile = new FileInputStream("./target/custom-reports/Execution_Report.xlsx");
			workbook = new XSSFWorkbook(ExcelFile);
			summarySheet = workbook.getSheet("TestSummary");
			
			 for(iCount=1;iCount<=getRowUsed();iCount++)
			   {
				   //System.out.println(getCellData(iCount,0));
				   //Hm.put(iCount,new BatchExcetionDeatils(getCellData(iCount,0),getCellData(iCount,1),getCellData(iCount,2),getCellData(iCount,3),getCellData(iCount,4),getCellData(iCount,5),getCellData(iCount,6),getCellData(iCount,7),getCellData(iCount,8)));
				   //userhome = System.getProperty("user.home");				   
//				   File destfile = new File(userhome+"/Downloads/workspace/Selenium_POC");
//				   File srcdir = new File("C:/AMAT_Automation/Mainline_SeleniumCorePlus/target/custom-reports/Execution_Report.xlsx");
//				   FileUtils.copyFileToDirectory(srcdir, destfile);
			   }
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			ExcelFile = null;
			workbook = null;
			summarySheet = null;
		}
	}
	public void WriteExecutionExcelReport() throws IOException, InvalidFormatException
	{
		String reportSheet = "";
		int iCount;
		String sModuleName = "";
		String sPriorityWiseModules = "";
		String sPriority = "Priority_P";
		boolean bPriorityFound = false;
		Properties objPropConfig = this.loadConfigProperties();
		
		//Hm = new HashMap<Integer, BatchExcetionDeatils>();
		try {
			ExcelFile = new FileInputStream("./target/custom-reports/BatchDetails.xlsx");
			workbook = new XSSFWorkbook(ExcelFile);
			summarySheet = workbook.getSheet("TestDetails");
			 for(iCount=1;iCount<=Hm.size();iCount++)
			   {
				 BatchExcetionDeatils Bed =Hm.get(iCount);
					//System.out.println(Bed.toString());
					//summarySheet = workbook.createSheet("TestDetails");
					summaryRow = summarySheet.createRow(iCount);
					//System.out.println("TestCase name-"+Bed.getTestCaseName());
					createSummaryStepCell(0,Integer.toString(iCount));
					createSummaryStepCell(1,objPropConfig.getProperty("EIT_Release"));//"ITC2"
					sModuleName = Bed.getModuleName();
					createSummaryStepCell(2,sModuleName);
					for(int iPriority = 1; iPriority < 4; iPriority++)
					{
						sPriority = "Priority_P";
						sPriority = sPriority + iPriority;
						sPriorityWiseModules = objPropConfig.getProperty(sPriority);
						if(sPriorityWiseModules.toLowerCase().contains(sModuleName.toLowerCase()))
						{
							bPriorityFound = true;
							break;
						}
							
					}
					if(bPriorityFound)
					{
						String sArrPriority[]= sPriority.split("_");
						createSummaryStepCell(3,sArrPriority[1]);
					}
					else
						createSummaryStepCell(3,"N/A");
					createSummaryStepCell(4,objPropConfig.getProperty("Environment"));//"ITC2"
					createSummaryStepCell(5, objPropConfig.getProperty("site"));//site
					createSummaryStepCell(6, objPropConfig.getProperty("Business_Group"));
					createSummaryStepCell(7,Bed.getTestCaseName() );
					createSummaryStepCell(8,Bed.getExecutedBy() );
					createSummaryStepCell(9,Bed.getMachineName() );
					createSummaryStepCell(10,Bed.getIteration());
					createSummaryStepCell(11,Bed.getStartTime() );
					createSummaryStepCell(12,Bed.getEndTime() );
					createSummaryStepCell(13, Float.toString(Bed.getTCDurationInMin()));
					//removed deliverables column
					//createSummaryStepCell(16,"deliverables");
					if(Bed.getStatus().equalsIgnoreCase("Fail"))
					{
						createSummaryFailStepCell(14);//StepCell(14,Bed.getStatus());
						createSummaryStepCell(15,Bed.getFailureLog());
						createSummaryStepCell(16,"Failure Type");
						//createSummaryStepCell(16,Bed.getFailureLog());//removed log column
						////////
						String sFailureReason = Bed.getFailureLog();
						String sDataUnderTest = sFailureReason.substring(sFailureReason.indexOf("'") + 1, sFailureReason.lastIndexOf("'"));
						if(sDataUnderTest == null || sDataUnderTest == "")
							sDataUnderTest = "N/A";
						createSummaryStepCell(17,sDataUnderTest);
						String sArrFailRsn[] = sFailureReason.split(":");
						if(sArrFailRsn.length > 2)
						{
							createSummaryStepCell(18, sArrFailRsn[2]);
						}
						else
						{
							createSummaryStepCell(18, "N/A");
						}
						 
							/////
						
						
						
						createSummaryStepCell(19,"Failure"/*Bed.getStatus()*/);
					}
					else
					{
						createSummaryPassStepCell(14);
						createSummaryStepCell(15,"N/A");
						createSummaryStepCell(16,"N/A");
						createSummaryStepCell(17,"N/A");
						createSummaryStepCell(18,"N/A");
						createSummaryStepCell(19,"N/A");
					}
					Bed = null; 
			   }
			
			FileOutputStream fileOutputStream = new FileOutputStream("./target/custom-reports/BatchDetails.xlsx");
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			ExcelFile = null;
			workbook = null;
			summarySheet = null;
		}
	}
	public HashMap<String,String> ReadFailExecutionExcelReport() throws IOException, InvalidFormatException
	{
		//String reportSheet = "";
		int iCount,jCount;

		HashMap <String,String> hm = new HashMap<String,String>();
		try {
			ExcelFile = new FileInputStream("./target/custom-reports/Execution_Report.xlsx");
			workbook = new XSSFWorkbook(ExcelFile);
			
				 for(jCount=1;jCount<workbook.getNumberOfSheets();jCount++)
				 {
					 summarySheet = workbook.getSheetAt(jCount);
					 //for(iCount=1;iCount<getRowUsed();iCount++)
					 //RPHADKE_20Mar2018 - to get exact failure after retry
					 for(iCount=getRowUsed()-1;iCount>0;iCount--)
					 {
						 if (summarySheet.getRow(iCount).getCell(5).getStringCellValue().contains("FAIL"))
						 {
							 hm.put(workbook.getSheetName(jCount),summarySheet.getRow(iCount).getCell(1).getStringCellValue());
							 System.out.println("Cell Value -"+summarySheet.getRow(iCount).getCell(1).getStringCellValue());
							 break;
						 }
					}
				 }
			 
			 FileOutputStream fileOutputStream = new FileOutputStream("./target/custom-reports/Execution_Report.xlsx");
				workbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			ExcelFile = null;
			workbook = null;
			summarySheet = null;
		}
		return hm; 
	}
	
	public void WriteFailExecutionExcelReport(HashMap<String, String> hm)
	{
	
		int iCount,jCount;
		String FailureReason;
		XSSFRow summaryRow; 
		XSSFCell cell; 
		 
		try {
			ExcelFile = new FileInputStream("./target/custom-reports/BatchDetails.xlsx");
			workbook = new XSSFWorkbook(ExcelFile);
			summarySheet = workbook.getSheet("TestDetails");
			
			 for(iCount=1;iCount<=hm.size();iCount++)
			   {
				 //System.out.println(getRowUsed());
				 for(jCount=1;jCount<=getRowUsed();jCount++)
				 {
					 if (summarySheet.getRow(jCount).getCell(14).getStringCellValue().contains("FAIL"))
					 {
//						 for(int iRow = 0; iRow < 21; iRow++)
//						 {
//							 System.out.println(summarySheet.getRow(jCount).getCell(iRow).toString());
//						 }
						 FailureReason =hm.get(summarySheet.getRow(jCount).getCell(7).toString());
						 System.out.println(FailureReason);
						 String[] arr = FailureReason.split(":");
//						 System.out.println(arr[2]);
//						 System.out.println(arr[1]);
						 String sDataUnderTest = FailureReason.substring(FailureReason.indexOf("'") + 1, FailureReason.lastIndexOf("'"));
						 if(sDataUnderTest == null || sDataUnderTest == "")
							 sDataUnderTest = "N/A";
						 	summaryRow= summarySheet.getRow(jCount);
						 	cell = summaryRow.createCell(19);
							cell.setCellValue(new XSSFRichTextString(sDataUnderTest));
							 if(arr.length > 0)
							 {
								 cell = summaryRow.createCell(16); 
								cell.setCellValue(new XSSFRichTextString(arr[1]));
								if(arr.length > 1)
								{
								//FAIL:Set Project Name'TestProject_1173': setProjectName()
									cell = summaryRow.createCell(20);
									cell.setCellValue(new XSSFRichTextString(arr[2]));
								}
								else
								{
									cell = summaryRow.createCell(20);
									cell.setCellValue(new XSSFRichTextString("N/A"));
								}
							 }
							 else
							 {
								 cell = summaryRow.createCell(20);
								cell.setCellValue(new XSSFRichTextString("N/A"));
								 cell = summaryRow.createCell(20);
								cell.setCellValue(new XSSFRichTextString("N/A"));
							 }
							arr = null;
					 }					 
				 }
			   } 
			
			FileOutputStream fileOutputStream = new FileOutputStream("./target/custom-reports/BatchDetails.xlsx");
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	private String arr(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//RPHADKE_26032018 - Read TC execution report
	public HashMap<String, String> readTCExecutionReport(String sTCName) throws IOException, InvalidFormatException
	{
		int iCount;
		boolean bWarningFlag = false;
		HashMap <String,String> hmTCData = new HashMap<String,String>();
		try {
			//ExcelFile = new FileInputStream("./target/custom-reports/Execution_Report.xlsx");
			//workbook = new XSSFWorkbook(ExcelFile);
//			
//				 
					 summarySheet = workbook.getSheet(sTCName);
			//summarySheet = currentReportSheet;
					 String sIteration1 = "";
					 String sIteration2 = "";
					 for(iCount=1; iCount <= getRowUsed() ;iCount++)
					 {
						 if(summarySheet.getRow(iCount).getCell(6).getStringCellValue().equals("1"))
						 {
							 bWarningFlag = false;
							 sIteration1 = summarySheet.getRow(iCount).getCell(7).getStringCellValue();
							 do
							 {
								 if(summarySheet.getRow(iCount).getCell(5).getStringCellValue().contains("WARNING"))
									 bWarningFlag = true;
								 iCount++;
								 if(iCount > getRowUsed())
								 {
									 sIteration1 = sIteration1 + "~" + summarySheet.getRow(iCount - 1).getCell(7).getStringCellValue() + "~" + bWarningFlag;
									 hmTCData.put("Iteration1", sIteration1);
									 break;
								 }
							 }while(!summarySheet.getRow(iCount).getCell(5).getStringCellValue().contains("FAIL"));
							 if(hmTCData.get("Iteration1") == null)
							 {
								 sIteration1 = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(7).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(1).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(8).getStringCellValue();
								 hmTCData.put("Iteration1", sIteration1);
							 }
							
						 }
						 else  if(summarySheet.getRow(iCount).getCell(6).getStringCellValue().equals("2"))
						 {
							 bWarningFlag = false;
							 sIteration2 = summarySheet.getRow(iCount).getCell(7).getStringCellValue();
							 do
							 {
								 if(summarySheet.getRow(iCount).getCell(5).getStringCellValue().contains("WARNING"))
									 bWarningFlag = true;
								 iCount++;
								 if(iCount > getRowUsed())
								 {
									 sIteration2 = sIteration2 + "~" + summarySheet.getRow(iCount - 1).getCell(7).getStringCellValue() + "~" + bWarningFlag;
									 hmTCData.put("Iteration2", sIteration2);
									 break;
								 }
							 }while(!summarySheet.getRow(iCount).getCell(5).getStringCellValue().contains("FAIL"));
							 if(hmTCData.get("Iteration2") == null)
							 {
								 sIteration2 = sIteration2 + "~" + summarySheet.getRow(iCount).getCell(7).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(1).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(8).getStringCellValue();
								 hmTCData.put("Iteration2", sIteration2);
							 }
						 }
					}
			
		}
		finally{
			
			summarySheet = null;
		}
		return hmTCData; 
	}
	
	//RPHADKE_30032018 - Load config properties
	public Properties loadConfigProperties() throws FileNotFoundException, IOException
	{
		Properties objPropConfig = new Properties();
		objPropConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		return objPropConfig;
	}
	
	//RPHADKE_30032018 - Insert data into sql table for Tableu report
	public void insertRunResultInSQL(HashMap<Integer, BatchExcetionDeatils> objHashmap)
	{
		System.out.println("inside insert function");
		String sPriority = "";
		String sPriorityWiseModule = "";
		
		String sArrPriority[] = null;
		
		try
		{
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), 			
			objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			Statement objStMt = objCon.createStatement();
			for(int iCount = 1; iCount <= objHashmap.size(); iCount++)
			{
				boolean bPriorityFound = false;
				BatchExcetionDeatils objBED = objHashmap.get(iCount);
				String sModuleName = objBED.getModuleName();
				for(int iPriority = 1; iPriority < 4; iPriority++)
				{
					sPriority = "Priority_P";
					sPriority = sPriority + iPriority;
					sPriorityWiseModule = objPropConfig.getProperty(sPriority);
					if(sPriorityWiseModule.toLowerCase().contains(sModuleName.toLowerCase()))
					{
						bPriorityFound = true;
						break;
					}
				}
				if(bPriorityFound)
				{
					sArrPriority = sPriority.split("_");
				}
				String sFailureReason = objBED.getFailureLog();
				String sQuery = "";
				if(objBED.getStatus().equalsIgnoreCase("Fail"))
				{
					
//					String sDataUnderTest = "";
//					if(sFailureReason.contains("'"))
//						sDataUnderTest = sFailureReason.substring(sFailureReason.indexOf("'") + 1, sFailureReason.lastIndexOf("'"));
//					if(sDataUnderTest == null || sDataUnderTest == "")
//						sDataUnderTest = "N/A";
					String sArrFunctionName[] = null;
					if(sFailureReason.contains(":"))
						sArrFunctionName = sFailureReason.split(":");
					String sFunctionName = "";
					if(sArrFunctionName.length > 2)
						sFunctionName = sArrFunctionName[2];
					else
						sFunctionName = "N/A";
					String sModulePriority = "N/A";
					if(sArrPriority != null && sArrPriority.length > 1)
						sModulePriority = sArrPriority[1];
					sFailureReason = sFailureReason.replace("'", " ");
					if(sFailureReason.length() > 255)
						sFailureReason = sFailureReason.substring(0, 254);
//					System.out.println(sDataUnderTest);
					
					//RPHADKE_Changed table to original
					sQuery = "INSERT INTO SQS_CorePlus_Automation (EIT_Release, Functionality, Priority, Environment, Site, Business_Group, Test_Case, Tester_Name, Machine_Name, Batch_Iteration, StartTime, EndTime, Duration_in_Minutes, Result, Comments, FailureType, ObjectID, FunctionName, Failure, Deliverables, Log_Path, ScreenShot_Path)" +
		                   "VALUES ('"+objPropConfig.getProperty("EIT_Release")+"',"+
		    			  "'"+objBED.getModuleName()+"', "+
		    			  "'"+sModulePriority+"', "+
		    			  "'"+objPropConfig.getProperty("Environment")+"', "+
		    			  "'"+objPropConfig.getProperty("site")+"', "+
		    			  "'"+objPropConfig.getProperty("Business_Group")+"', "+
		    			  "'"+objBED.getTestCaseName()+"', "+
		    			  "'"+objBED.getExecutedBy()+"', "+
		    			  "'"+objBED.getMachineName()+"', "+
		    			  "'"+objBED.getIteration()+"', "+
		    			  "convert(datetime,'"+objBED.getStartTime()+"',103), "+
		    			  "convert(datetime,'"+objBED.getEndTime()+"',103), "+
		    			  "'"+objBED.getTCDurationInMin()+"', "+
		    			  "'"+objBED.getStatus()+"', "+
		    			  "'"+sFailureReason+"', "+
		    			  //"'"+objExcelIterator.getCellValue(iColLogs)+"', "+
		    			  //"'"+objExcelIterator.getCellValue(iColDeliverables)+"', "+
		    			  "'Failure Type', "+
		    			  "'N/A', "+
		    			  "'"+sFunctionName+"', "+
		                  "'Failure', "+
		    			  "'"+objBED.getDeliverable()+"', "+
		    			  "'"+objBED.getLogPath()+"', "+
		    			  "'"+objBED.getScreenshotPath()+"')";
					System.out.println(sQuery);
				}
				else if(objBED.getStatus().equalsIgnoreCase("Pass") || objBED.getStatus().equalsIgnoreCase("Pass With Warning"))
				{
					String sModulePriority = "N/A";
					if(sArrPriority != null && sArrPriority.length > 1)
						sModulePriority = sArrPriority[1];
					//RPHADKE_Changed table to original
					sQuery = "INSERT INTO SQS_CorePlus_Automation (EIT_Release, Functionality, Priority, Environment, Site, Business_Group, Test_Case, Tester_Name, Machine_Name, Batch_Iteration, StartTime, EndTime, Duration_in_Minutes, Result, Comments, FailureType, ObjectID, FunctionName, Failure, Deliverables, Log_Path, ScreenShot_Path)" +
			                   "VALUES ('"+objPropConfig.getProperty("EIT_Release")+"',"+
			    			  "'"+objBED.getModuleName()+"', "+
			    			  "'"+sModulePriority+"', "+
			    			  "'"+objPropConfig.getProperty("Environment")+"', "+
			    			  "'"+objPropConfig.getProperty("site")+"', "+
			    			  "'"+objPropConfig.getProperty("Business_Group")+"', "+
			    			  "'"+objBED.getTestCaseName()+"', "+
			    			  "'"+objBED.getExecutedBy()+"', "+
			    			  "'"+objBED.getMachineName()+"', "+
			    			  "'"+objBED.getIteration()+"', "+
			    			  "convert(datetime,'"+objBED.getStartTime()+"',103), "+
			    			  //""+dtStartTime+","+
			    			  "convert(datetime,'"+objBED.getEndTime()+"',103), "+
			    			 // ""+dtEndTime+","+
			    			  "'"+objBED.getTCDurationInMin()+"', "+
			    			  "'"+objBED.getStatus()+"', "+
			    			  "'"+sFailureReason+"', "+
			    			  //"'"+objExcelIterator.getCellValue(iColLogs)+"', "+
			    			  //"'"+objExcelIterator.getCellValue(iColDeliverables)+"', "+
			    			  "'N/A', "+
			    			  "'N/A', "+
			    			  "'N/A', "+
			                  "'N/A', "+
			                  "'"+objBED.getDeliverable()+"', "+
			    			  "'"+objBED.getLogPath()+"', "+
			    			  "'N/A')";
					System.out.println(sQuery);
				}
		    	  
				objStMt.executeUpdate(sQuery);
				
				objCon = null;
				
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	//RPHADKE_30032018 - Insert data into sql table for Tableu report
	public void insertRunResultInSQL(HashMap<Integer, BatchExcetionDeatils> objHashmap, String sObjectID)
	{
		System.out.println("inside insert function");
		String sPriority = "";
		String sPriorityWiseModule = "";
		
		String sArrPriority[] = null;
		
		try
		{
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			Statement objStMt = objCon.createStatement();
			for(int iCount = 1; iCount <= objHashmap.size(); iCount++)
			{
				boolean bPriorityFound = false;
				BatchExcetionDeatils objBED = objHashmap.get(iCount);
				String sModuleName = objBED.getModuleName();
				for(int iPriority = 1; iPriority < 4; iPriority++)
				{
					sPriority = "Priority_P";
					sPriority = sPriority + iPriority;
					sPriorityWiseModule = objPropConfig.getProperty(sPriority);
					if(sPriorityWiseModule.toLowerCase().contains(sModuleName.toLowerCase()))
					{
						bPriorityFound = true;
						break;
					}
				}
				if(bPriorityFound)
				{
					sArrPriority = sPriority.split("_");
				}
				String sFailureReason = objBED.getFailureLog();
				String sQuery = "";
				if(objBED.getStatus().equalsIgnoreCase("Fail"))
				{
					
//						String sDataUnderTest = "";
//						if(sFailureReason.contains("'"))
//							sDataUnderTest = sFailureReason.substring(sFailureReason.indexOf("'") + 1, sFailureReason.lastIndexOf("'"));
//						if(sDataUnderTest == null || sDataUnderTest == "")
//							sDataUnderTest = "N/A";
					String sArrFunctionName[] = null;
					if(sFailureReason.contains(":"))
						sArrFunctionName = sFailureReason.split(":");
					String sFunctionName = "";
					if(sArrFunctionName.length > 2)
						sFunctionName = sArrFunctionName[2];
					else
						sFunctionName = "N/A";
					String sModulePriority = "N/A";
					if(sArrPriority != null && sArrPriority.length > 1)
						sModulePriority = sArrPriority[1];
					sFailureReason = sFailureReason.replace("'", " ");
					if(sFailureReason.length() > 255)
						sFailureReason = sFailureReason.substring(0, 254);
//						System.out.println(sDataUnderTest);
					//RPHADKE_Changed table to original
					sQuery = "INSERT INTO SQS_Automation ([PMT Release],[Functionality],[Priority],[Environment],[Site],[Group],[Test Case],[Tester Name],[Machine Name],[Batch Iteration],[StartTime],[EndTime],[Duration in Minutes],[Result],[Comments],[FailureType],[ItemID],[FunctionName],[Failure],[Deliverable],[Logs],[Screenshot],[Execution Type],[Category],[Client Type],[Modules],[TestCycle],[Test Run Title],[ArchitechtureType],[Syslog],[Console],[Teamcenter Build],[PMT Build],[Browser Version])" +
		                   "VALUES ('"+objPropConfig.getProperty("EIT_Release")+"',"+
		    			  "'"+objBED.getModuleName()+"', "+
		    			  "'"+sModulePriority+"', "+
		    			  "'"+objPropConfig.getProperty("Environment")+"', "+
		    			  "'"+objPropConfig.getProperty("site")+"', "+
		    			  "'"+objPropConfig.getProperty("Business_Group")+"', "+
		    			  "'"+objBED.getTestCaseName()+"', "+
		    			  "'"+objBED.getExecutedBy()+"', "+
		    			  "'"+objBED.getMachineName()+"', "+
		    			  "'"+objBED.getIteration()+"', "+
		    			  "convert(datetime,'"+objBED.getStartTime()+"',103), "+
		    			  "convert(datetime,'"+objBED.getEndTime()+"',103), "+
		    			  "'"+objBED.getTCDurationInMin()+"', "+
		    			  "'"+objBED.getStatus()+"', "+
		    			  "'"+sFailureReason+"', "+
		    			  //"'"+objExcelIterator.getCellValue(iColLogs)+"', "+
		    			  //"'"+objExcelIterator.getCellValue(iColDeliverables)+"', "+
		    			  "'Failure Type', "+
		    			  "'"+sObjectID+"', "+
		    			  "'"+sFunctionName+"', "+
		                  "'Failure', "+
		    			  "'', "+ 
		    			  "'"+objBED.getLogPath()+"', "+
		    			  "'"+objBED.getScreenshotPath()+"', "+
		    			  "'"+objPropConfig.getProperty("ExecutionType")+"', "+
		    			  "'"+objPropConfig.getProperty("Category")+"', "+
		    			  "'"+objPropConfig.getProperty("ClientType")+"','',"+
		    			  "'"+objBED.getDeliverable()+"', '','','','','','',"+
		    			  "'"+InitializeTearDownEnvironment.browserVersion+"'"+
		    			  		")";
					System.out.println(sQuery);
				}
				else if(objBED.getStatus().equalsIgnoreCase("Pass") || objBED.getStatus().equalsIgnoreCase("Pass With Warning"))
				{
					String sModulePriority = "N/A";
					if(sArrPriority != null && sArrPriority.length > 1)
						sModulePriority = sArrPriority[1];
					//RPHADKE_Changed table to original
					//TableName= INSERT INTO SQS_CorePlus_Automation
					sQuery = "INSERT INTO SQS_Automation ([PMT Release],[Functionality],[Priority],[Environment],[Site],[Group],[Test Case],[Tester Name],[Machine Name],[Batch Iteration],[StartTime],[EndTime],[Duration in Minutes],[Result],[Comments],[FailureType],[ItemID],[FunctionName],[Failure],[Deliverable],[Logs],[Screenshot],[Execution Type],[Category],[Client Type],[Modules],[TestCycle],[Test Run Title],[ArchitechtureType],[Syslog],[Console],[Teamcenter Build],[PMT Build],[Browser Version])" +
			                   "VALUES ('"+objPropConfig.getProperty("EIT_Release")+"',"+
			    			  "'"+objBED.getModuleName()+"', "+
			    			  "'"+sModulePriority+"', "+
			    			  "'"+objPropConfig.getProperty("Environment")+"', "+
			    			  "'"+objPropConfig.getProperty("site")+"', "+
			    			  "'"+objPropConfig.getProperty("Business_Group")+"', "+
			    			  "'"+objBED.getTestCaseName()+"', "+
			    			  "'"+objBED.getExecutedBy()+"', "+
			    			  "'"+objBED.getMachineName()+"', "+
			    			  "'"+objBED.getIteration()+"', "+
			    			  "convert(datetime,'"+objBED.getStartTime()+"',103), "+
			    			  //""+dtStartTime+","+
			    			  "convert(datetime,'"+objBED.getEndTime()+"',103), "+
			    			 // ""+dtEndTime+","+
			    			  "'"+objBED.getTCDurationInMin()+"', "+
			    			  "'"+objBED.getStatus()+"', "+
			    			  "'"+sFailureReason+"', "+
			    			  //"'"+objExcelIterator.getCellValue(iColLogs)+"', "+
			    			  //"'"+objExcelIterator.getCellValue(iColDeliverables)+"', "+
			    			  "'N/A', "+
			    			  "'"+sObjectID+"', "+
			    			  "'N/A', "+
			                  "'N/A', "+
			                  "'', "+
			    			  "'"+objBED.getLogPath()+"', "+
			    			  "'"+objBED.getScreenshotPath()+"', "+
			    			  "'"+objPropConfig.getProperty("ExecutionType")+"', "+
			    			  "'"+objPropConfig.getProperty("Category")+"', "+
			    			  "'"+objPropConfig.getProperty("ClientType")+"','',"+
			    			  "'"+objBED.getDeliverable()+"', '','','','','','',"+
			    			  "'"+InitializeTearDownEnvironment.browserVersion+"'"+
			    			  		")";
					System.out.println(sQuery);
				}
				Properties objConfig = new Properties();
				objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
				
				String strRptFolderDate = new SimpleDateFormat("dd_MMM_yyyy").format(new java.util.Date());
				String sLogFilePathNew1 = objConfig.getProperty("SharedDriveResultsPath")+"\\ExecutionResults\\"+strRptFolderDate;
				File ResultFile = new File(sLogFilePathNew1);
				if(!ResultFile.exists())
					new File(sLogFilePathNew1).mkdir();
				
				String strMachineName = InetAddress.getLocalHost().getHostName();
				if(strMachineName.contains("9CWNGP2") || strMachineName.contains("6CJ4FN2") ||strMachineName.contains("9YBNGP2") ||strMachineName.contains("J8KJQM2") ||strMachineName.contains("J9B1YC2") ||strMachineName.contains("J9BP6C2") ||strMachineName.contains("BC1MGP2"))
				{
					
					String sFilePath = System.getProperty("user.dir") + "\\target\\custom-reports\\" + strMachineName + ".txt";
					FileWriter objFW = new FileWriter(sFilePath, true);
					String sStepLine = "";
					sStepLine = sQuery + System.lineSeparator();
					objFW.write(sStepLine);
					objFW.close(); 
				}
				sLogFilePathNew1 = objConfig.getProperty("SharedDriveResultsPath")+"\\ExecutionResults\\"+strRptFolderDate+"\\"+strMachineName;
				File ResultFile1 = new File(sLogFilePathNew1);
				if(!ResultFile1.exists())
					new File(sLogFilePathNew1).mkdir();
				
				String sFilePath = objConfig.getProperty("SharedDriveResultsPath")+"\\ExecutionResults\\"+strRptFolderDate+"\\" + strMachineName + ".txt";
				FileWriter objFW = new FileWriter(sFilePath, true);
				String sStepLine = "";
				sStepLine = sQuery + System.lineSeparator();
				objFW.write(sStepLine);
				objFW.close(); 
				//for execution only to avoid DB slowness will execute this quey after execution
				objStMt.executeUpdate(sQuery);
				
				objCon = null;
				
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	//RPHADKE_30032018 - set connection with SQL server
	public Connection setSQLConnection(String sJDBCDriver, String sDBUrl, String sDBUser, String sDBPassword)
	{
		try 
		{
			Class.forName(sJDBCDriver);
			return DriverManager.getConnection(sDBUrl, sDBUser, sDBPassword);
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//RPHADKE_18092018 - add excel warning step
	public void addExcelWarningStep(String stepDesc, String inputValue, String exceptedValue, String actualValue)
	{
		
		System.out.println("intRowCounter = " + intRowCounter);
		
		sheetRow = currentReportSheet.createRow(intRowCounter);
		this.createSheetCell(0, String.valueOf(excelReportStepNumber));
		this.createSheetCell(1, stepDesc);
		this.createSheetCell(2, inputValue);
		this.createSheetCell(3, exceptedValue);
		this.createSheetCell(4, actualValue);
		this.createSheetWarningStepCell(5);
		this.createSheetCell(6, Integer.toString(RetryAnalyzer.globalRetryCount));
		String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
		this.createSheetCell(7, strDate);
			
		intRowCounter++;
		excelReportStepNumber++;
		System.out.println("After intRowCounter = " + intRowCounter);
	}
	
	//RPHADKE_18092018 - create sheet warning step cell
	public void createSheetWarningStepCell(int cellNumber)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("WARNING"));
		cell.setCellStyle(getWarningCellStyle());
	}

	//RPHADKE_18092018 - get warning cell style
	 public XSSFCellStyle getWarningCellStyle() {
	        XSSFCellStyle cellStyle = workbook.createCellStyle();
	        cellStyle.setAlignment(HorizontalAlignment.CENTER);
	        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
	        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        return cellStyle;
	    }
	
	//RPHADKE_18092018 - create summary warning step cell
	public void createSummaryWarningStepCell(int cellNumber)
	{
		XSSFCell cell = summaryRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("PASS"));
		cell.setCellStyle(getWarningCellStyle());
	}
	public HashMap<String, String> readSCVTCExecutionReport(String sTCName) throws IOException, InvalidFormatException
	{
		int iCount;
		boolean bWarningFlag = false;
		HashMap <String,String> hmTCData = new HashMap<String,String>();
		
		try {
			//ExcelFile = new FileInputStream("./target/custom-reports/Execution_Report.xlsx");
			//workbook = new XSSFWorkbook(ExcelFile);
			
			summarySheet = workbook.getSheet(sTCName);
			String sIteration1 = "",sIterationData = "";			
				 for(iCount=1; iCount < getRowUsed() ;iCount++)
				 {
					 System.out.println("Total Count="+getRowUsed());
					 bWarningFlag = false;
					 String sfaillog="",sFail=" ";
					 if(iCount==1)
					 sIteration1 = summarySheet.getRow(iCount).getCell(3).getStringCellValue();
					 
					 iCount = getRowUsed();
					 System.out.println("last row  Count="+iCount);
					 if(summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("PASS"))
					 {
						 sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + bWarningFlag;
						 hmTCData.put("Iteration1", sIterationData);
						 break;
					 }
					 else if(summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("FAIL"))
					 {
						 sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(0).getStringCellValue() + "~" + sFail;
						 hmTCData.put("Iteration1", sIterationData);
						 break;
					 }
					 
					 /*
					 do
					 {
						 if(summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("WARNING"))
							 bWarningFlag = true;
						 if(summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("FAIL"))
							 sfaillog = "Fail";
						 iCount++;
						 if(iCount >= getRowUsed())
						 {
							 if(sfaillog.equalsIgnoreCase("Fail")) {
								 if(iCount == getRowUsed())
								 {
									 sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(0).getStringCellValue() + "~" + sFail;
									 hmTCData.put("Iteration1", sIterationData);
								 break;
							 }
							 else {
									 sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(0).getStringCellValue() + "~" + sFail;
									 hmTCData.put("Iteration1", sIterationData);
									 break;
								 }
							 }
							 else {
								 
								 sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + bWarningFlag;
								 hmTCData.put("Iteration1", sIterationData);
								 break;
							 } 
						 }
					 }while(!summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("FAIL"));
					 
					 if(iCount <= getRowUsed())
					 {
						if(summarySheet.getRow(iCount).getCell(2).getStringCellValue().contains("FAIL")) {
							if(sIteration1.contains("~")){
								sIterationData = sIteration1 + "~" + summarySheet.getRow(iCount).getCell(4).getStringCellValue() + "~" + summarySheet.getRow(iCount).getCell(0).getStringCellValue() + "~" + sFail;
								hmTCData.put("Iteration1", sIterationData);
								break;
				}
						}
					 }*/
				}
			
		}
		finally{
			
			summarySheet = null;
		}
		return hmTCData; 
	}
	public float getTimeDifferenceInSeconds(String sDateTime1, String sDateTime2) {
		try {
				float fDurationInSec = 0;
				Date dtStart;
				Date dtEnd;
				DateFormat objDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				dtStart = objDateFormat.parse(sDateTime1);
				dtEnd = objDateFormat.parse(sDateTime2);
				if (sDateTime1 == null || sDateTime1 == null)
					return 0;
				fDurationInSec = (float) ((dtEnd.getTime() / 1000) - (dtStart.getTime() / 1000));
				if (fDurationInSec < 0)
					return 0;
				else
					return fDurationInSec;
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
			return 0;
		}
	}
	//PoonamC_07June2021 - code to remove sheet Name
	public void RemoveSheetFromXlsxFile(String filenamewithExtension,String sheetName) {
		String sPath = filenamewithExtension;
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + sPath);
		String[] str = null;

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(sPath);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int Index = workbook.getSheetIndex(sheet);
				workbook.removeSheetAt(Index);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	}
	//PoonamC_07June2021 - code to remove sheet Name
	public void RenameXlsxFileName(String sOldName,String sNewName) {
		String Cmd = "ren "+sOldName+" "+sNewName;
		Process p;
		String line = new String();
		try {
			p = Runtime.getRuntime().exec("cmd /c " + Cmd);
			p.waitFor();
			BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) 
			    System.out.println(line);
			input.close();
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	}
	//PoonamC_07June2021 - code to remove sheet Name
	public void CopyXlsxFileToAnotherLocation(String sOldloc,String sNewloc) {
		String Cmd = "xcopy "+sOldloc+" "+sNewloc;
		Process p;
		String line = new String();
		try {
			p = Runtime.getRuntime().exec("cmd /c " + Cmd);
			p.waitFor();
			BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) 
			    System.out.println(line);
			input.close();
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	}
	public void insertRunResultInSQLForSCVTC(HashMap<Integer, BatchExcetionDeatils> objHashmap, String sObjectID)
	{
		System.out.println("inside insert function");
		
		try
		{
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			Statement objStMt = objCon.createStatement();
			String sQuery = "";
			String sTestStep="",RequestID="",sStatus="",StartTime="",EndTime="",sExecutedBy="",MachineName="",ModuleName="",FailureLog="",sComments="";
			int DurationinSeconds=0;
			for(int iCount = 1; iCount <= objHashmap.size(); iCount++)
			{
				BatchExcetionDeatils objBED = objHashmap.get(iCount);
				String sModuleName = objBED.getTestCaseName();
				String[]aTCDetails = sModuleName.split("~");
				sTestStep = aTCDetails[0].replace("https://", "");
				if(aTCDetails[1].contains("N/A"))
					RequestID = "";
				else
					RequestID = aTCDetails[1];
				
				DurationinSeconds=Integer.parseInt(aTCDetails[2]);
				sStatus = aTCDetails[3];
				StartTime = aTCDetails[4];
				EndTime = aTCDetails[5];
				sExecutedBy = aTCDetails[6];
				MachineName = aTCDetails[7];
				ModuleName = aTCDetails[8];
				if(aTCDetails[9].contains("N/A"))
					FailureLog = "";
				else
					FailureLog = aTCDetails[9];
				
				if(aTCDetails[10].contains("N/A"))
					sComments = "";
				else
					sComments = aTCDetails[10];
				
				System.out.println(FailureLog + "--"+ sComments + "--" +  RequestID);
				sQuery = "INSERT INTO [SCV_Performance_Result] ([Test Step],[Request ID],[Duration in Seconds],[Status],[StartTime IST],[EndTime IST],[Executed By],[MachineName],[ModuleName],[Failure Log],[Fail Comments])" +
		                   " VALUES ('"+sTestStep+"','"+RequestID+"',"+DurationinSeconds+",'"+sStatus+"',convert(datetime,'"+StartTime+"',103),convert(datetime,'"+EndTime+"',103),'"+sExecutedBy+"','"+MachineName+"','"+ModuleName+"','"+FailureLog+"','"+sComments+"')";
				System.out.println(sQuery);
}
				objStMt.executeUpdate(sQuery);

				objCon = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	  }
	public void UpdateFailLogPathRunResultInSQLForSCVTC(String sFileLogPath,String sMachineName)
	{
		System.out.println("inside Update function");

		try
		{
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			Statement objStMt = objCon.createStatement();
			String sQuery = "";
			sQuery = "UPDATE [SCV_Performance_Result] SET [Failure Log] = '"+sFileLogPath+"' WHERE [EndTime IST]>format(dateadd(hour,-1,getdate()),'yyyy-dd-MM hh:mm:ss')"+
					 "and [MachineName]='"+sMachineName+"' and [Status]='FAIL'";
			
			System.out.println(sQuery);
			
			objStMt.executeUpdate(sQuery);
				
			objCon = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	  }
	
	/**
	 * Start text report 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void startTxtReport() throws IOException, InvalidFormatException
	{
		strTxtFilePath = System.getProperty("user.dir") + "/target/custom-reports/" + runningScriptName +".txt";
		File reportFile = new File(strTxtFilePath);

		// If file not exist create new with summary sheet and sheet for running script details
		if(reportFile.exists())
		{
			reportFile.delete();
		}
		new File(System.getProperty("user.dir") + "/target/custom-reports").mkdir();
		reportFile.createNewFile();
	}
	public void CopyRptTXTFileToAnotherLocation(String sOldloc,String sNewloc) {
		String Cmd = "copy /Y "+sOldloc+" "+sNewloc;
		
		Process p;
		String line = new String();
		try {
			p = Runtime.getRuntime().exec("cmd /c " + Cmd);
			if(sOldloc.contains("ScreenShot")==false)
				p.waitFor();
			BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) 
			    System.out.println(line);
			input.close();
			input=null;
			p=null;
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	}
	public void insertRunResultInSQLForExploratoryTC(HashMap<Integer, BatchExcetionDeatils> objHashmap, String sObjectID)
	{
		System.out.println("inside insert function");
		try
		{
			Properties objPropConfig = this.loadConfigProperties();
			Connection objCon = this.setSQLConnection(objPropConfig.getProperty("JDBC_DRIVER"), objPropConfig.getProperty("DB_URL"), objPropConfig.getProperty("DB_USER"), objPropConfig.getProperty("DB_PWD"));
			Statement objStMt = objCon.createStatement();
			String sQuery = "";
			String sScenario="",sEITRelease="",sEnv="",sModuleName="",sMachineName="",strDate="";
			int DurationinSeconds=0;
			for(int iCount = 1; iCount <= objHashmap.size(); iCount++)
			{
				BatchExcetionDeatils objBED = objHashmap.get(iCount);
				String sModuleName1 = objBED.getTestCaseName();//get deatils
				String[]aTCDetails = sModuleName1.split("~");
				DurationinSeconds=Integer.parseInt(aTCDetails[6]);
				sScenario = aTCDetails[0];
				sEITRelease = aTCDetails[1];
				sEnv = aTCDetails[2];
				sModuleName = aTCDetails[3];
				sMachineName = aTCDetails[4];
				strDate = aTCDetails[5];
				
				sQuery = "INSERT INTO [CorePlus_Performance_Readings] ([Scenario],[EIT Release],[Environment],[Module],[Machine Name],[Date],[Duration in Sec])" +
		                   " VALUES ('"+sScenario+"','"+sEITRelease+"','"+sEnv+"','"+sModuleName+"','"+sMachineName+"',convert(datetime,'"+strDate+"',103),"+DurationinSeconds+")";
				System.out.println(sQuery);
			}
				objStMt.executeUpdate(sQuery);
				objCon = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	  }
	
		//PoonamC_28092023 Update results into JIRA
		public void updateExecutionResultsinJIRA(HashMap<Integer, BatchExcetionDeatils> objHashmap, String sObjectID,String SnapPath)
		{
			System.out.println("inside results JIRA");
			String sTestData="";
			if(sObjectID.equals(null))
				sTestData = "No Test Data";
			else if(sObjectID.length()<=0)
				sTestData = "No Test Data";
			else	
				sTestData = sObjectID;
			
			for(int iCount = 1; iCount <= objHashmap.size(); iCount++)
			{
				BatchExcetionDeatils objBED = objHashmap.get(iCount);		
				try
				{
					  Properties objPropConfig = this.loadConfigProperties();
					  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					  Date objDate = new Date();
					  String date1 = dateFormat.format(objDate);
					  String date2 = dateFormat.format(objDate);  
					  String sFailureReason = objBED.getFailureLog();
					  if(sFailureReason.equals("N/A") && sTestData.equals("No Test Data"))
						  sFailureReason = "Item ID used in test cases is : None";
					  else if(sFailureReason.equals("N/A") && !sTestData.equals("No Test Data"))
						  sFailureReason = "Item ID used in test cases is : "+sTestData; 
					 
					 String sVal = objPropConfig.getProperty("EIT_Release").trim().replace("_", " ")+","+objPropConfig.getProperty("TestExecutionName").trim()+","+
	  							objPropConfig.getProperty("Environment").trim()+","+
	  							objBED.getModuleName().trim()+","+
	  							objBED.getTestCaseName().trim()+","+
	  							"NA"+","+
	  							date1+","+
	  							date2+","+
	  							objBED.getStatus().trim()+","+
	  							sFailureReason.replace("'", " ")+","+
	  							objBED.getLogPath().trim()+","+
	  							SnapPath+","+"NA"+","+"NA"+","+"NA"+","+"Selenium_TestNG_Batch_Run"+","+
	  							"Desktop"+","+sTestData+","+"NA"+","+InitializeTearDownEnvironment.browserVersion;
					  String[] Jiravalues= new String[]{sVal};
					 /* String[] Jiravalues= new String[]{objPropConfig.getProperty("EIT_Release").trim().replace("_", " "),"Automation",
							  							objPropConfig.getProperty("Environment").trim(),
							  							objBED.getModuleName().trim(),
							  							objBED.getTestCaseName().trim(),
							  							"",
							  							date1,
							  							date2,
							  							objBED.getStatus().trim(),
							  							sFailureReason.replace("'", " "),
							  							objBED.getLogPath().trim(),
							  							SnapPath,"","","","Selenium_TestNG_Batch_Run",
							  							"Desktop",sTestData,""};*/
					  
					  System.out.println(objPropConfig.getProperty("EIT_Release")+","+objPropConfig.getProperty("Environment")
					  +","+objBED.getModuleName()+","+objBED.getTestCaseName()
					  +","+objPropConfig.getProperty("Business_Group")+","+date1+","+date2
					  +","+objBED.getStatus()+","+sFailureReason
					  +","+objBED.getLogPath()+","+SnapPath+","+sTestData+","+InitializeTearDownEnvironment.browserVersion);
					  
//					  TestRunner.main(Jiravalues);
					 // GetExecutionResultsFromDBandUpdateJira.main(Jiravalues);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		}
}


