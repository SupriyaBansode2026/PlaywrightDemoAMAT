package com.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.ViewportSize;

import org.junit.jupiter.api.*;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

//import com.jira.requests.JiraRequest;
//import com.teamcenter.clientx.AppXSession;
import com.microsoft.playwright.*;
import java.util.Properties;
import io.qameta.allure.testng.AllureTestNg;
public class BaseTest extends Pojo{

	// Local Variables 
	private Properties objConfig;
	private Properties objModuleConfig;
	private Utilities objUtilities;
	private WrapperFunctions objWrapperFunctions;
	private CustomReporter objCustomReporter;
	private String testDataFilePath = "";
	private String callingClassName = "";
	private String callipngPackageName = "";
	public List<Hashtable<String, String>> hashtableList = new ArrayList<Hashtable<String,String>>() ;
	private InitializeTearDownEnvironment objInitializeTearDownEnvironment;
	public String sCurrentURL = "";
	//RPHADKE_08102018 - Removed static parameter for object id variable
	private String sObjectID = "";
	private String websiteURL = "";	
//	private static AppXSession session;
	private String sJiraID = "";
//	private JiraRequest objJiraRequest;
	public static String sJiraTestSummary="";
	protected Playwright playwright;
    protected Browser browser;
    public BrowserContext context;
    private Page page;
	
	/**
	 * @Method		: 	initializeWebEnvironment
	 * @Description	:	This method initialize web driver for web application 
	 * 					by opening the browser and URL specified in config.properties file
	 * @author		: 	Harshvardhan Yadav (SQS) 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void initializeWebEnvironment(){
        this.loadConfigProperties();

        String callingClassName = this.getClass().getName();
        String callingPackageName = this.getClass().getPackage().getName();
        this.setRunningScriptName(callingClassName);
        this.setRunningPackageName(callingPackageName);

        this.setBrowser(objConfig.getProperty("web.browser").trim().toLowerCase());

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setChannel("chrome") // use actual Chrome, not bundled Chromium
                .setArgs(Arrays.asList("--start-maximized")) // ⬅️ open maximized
        );

        // Prevent Playwright from setting viewport — use full screen size
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        
        this.setPage(page);
		/*
		 * page.waitForLoadState(LoadState.LOAD); page.waitForTimeout(500);
		 */
        if (objConfig.getProperty("UploadJiraResult").equalsIgnoreCase("true")) {
            String scriptName = this.getRunningScriptName().replace(".", "~");
            String[] scriptParts = scriptName.split("~");
            objUtilities = new Utilities(this);
            sJiraID = objUtilities.getAutomationJiraIDFromTextFile(scriptParts[3]);
            if (sJiraID != null) {
                String[] aValues = sJiraID.split("~");
                if (aValues.length > 3) {
                    sJiraID = aValues[1];
                    sJiraTestSummary = aValues[3];
                }
            }
            System.out.println("Jira ID: " + sJiraID);
            this.setJiraTestId(sJiraID);
        } else {
            try {
				sJiraID = getAnnotationValue();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            this.setJiraTestId(sJiraID);
        }

        objCustomReporter = new CustomReporter();
		if(objConfig.getProperty("test.custom.reporter").equalsIgnoreCase("true"))
			this.startCustomReports();


        objUtilities = new Utilities(this);
        this.setObjUtilities(objUtilities);

        objWrapperFunctions = new WrapperFunctions(this);
        this.setObjWrapperFunctions(objWrapperFunctions);

        // Determine URL based on environment
        String URL;
        if (objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true")) {
            URL = objConfig.getProperty("itc1.web.Url");
        } else {
            URL = objConfig.getProperty("web.Url");
        }

        websiteURL = URL;
        System.out.println("Launching URL: " + URL);
        page.navigate(URL);

        objUtilities.waitFor(5);
        int counter = 0;
        boolean bFlag;
        do {
            bFlag = verifyURLLoadedCompletely();
            if (!bFlag) {
                page.navigate(URL);
                objUtilities.waitFor(20);
            }
            counter++;
        } while (!bFlag && counter < 5);
        
    }

	

	public boolean verifyURLLoadedCompletely() {
	    try {
	        // Check if document.readyState is 'complete'
	        String readyState = (String) page.evaluate("() => document.readyState");
	        if (!"complete".equalsIgnoreCase(readyState)) {
	            return false;
	        }

	        // Check for error in title
	        String title = page.title();
	        if (title.contains("ERROR") || title.contains("Error")) {
	            return false;
	        }

	        // Check for "site can't be reached" or malformed title
	        String pageContent = page.content();
	        String domain = websiteURL.substring(websiteURL.indexOf("//") + 2, websiteURL.indexOf("/", 10));

	        if (pageContent.contains("This site can't be reached") ||
	            title.contains(".") ||
	            title.contains(domain)) {
	            return false;
	        }

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * @Method		: tearDownEnvironment
	 * @Description	: quit webdriver  
	 * @author		: Harshvardhan Yadav (SQS) 
	 */
	public void tearDownWebEnvironment() {
	    String sTCJiraID = this.getJiraTestId();
	    if(objConfig.getProperty("test.custom.reporter").equalsIgnoreCase("true"))
			objCustomReporter.endReport(this.getObjectId());//RPHADKE_08102018 - Sent object ID for reporting
		
	    objUtilities.deleteExecutionReportXlsx();
	    objUtilities.iStepCounter = 1;

	    // Close Playwright resources
	    if (page != null) page.close();
	    if (context != null) context.close();
	    if (browser != null) browser.close();
	    if (playwright != null) playwright.close();
	}
	
	/**
	 * @Method      : loadTestData 
	 * @param		: runID - test case run id
	 * @param		: dataSet - test data hash table 
	 * @Description : Load data from excel for the running testCase and return as Object array    
	 * @author      : Indrajeet Chavan (SQS)
	 */
	public void loadTestData(String runID, Hashtable<String, String> dataSet)
	{
		this.setRunID(runID);
		this.setDataPoolHashTable(dataSet);
		objUtilities = new Utilities(this);
		this.setObjUtilities(objUtilities);

		objWrapperFunctions = new WrapperFunctions(this);
		this.setObjWrapperFunctions(objWrapperFunctions);
	}

	/**
	 * @Method      : loadDataProvider 
	 * @param		: testCaseID - test case id
	 * @param		: testDataFile - test data file 
	 * @Description : Load Data from Excel for the running testCase and return as Object array    
	 * @author      : Indrajeet Chavan (SQS)
	 */
	public Object[][] loadDataProvider(String testCaseID, String testDataFile)
	{
		Object[][] dataPool = null;

		if(!testDataFile.equals("") && !testCaseID.equals(""))
		{
			testDataFilePath = System.getProperty("user.dir") + objConfig.getProperty("testdata.path") +"/"+ testDataFile + ".xlsx";
			System.out.println("Test Data File Path -"+testDataFilePath+" Test ID - "+testCaseID);
			this.setTestCaseID(testCaseID);
			this.setStrTestDataFilePath(testDataFilePath);

			DataPool objDataPool = new DataPool();
			dataPool = objDataPool.loadTestData(testCaseID, testDataFilePath);
		}
		return dataPool;
	}
	public Object[][] loadDataProvider(String testCaseID,String Module, String testDataFile)
	{
		Object[][] dataPool = null;

		if(!testDataFile.equals("") && !testCaseID.equals(""))
		{
			testDataFilePath = System.getProperty("user.dir") + objConfig.getProperty("testdata.path") +"/"+ Module+"/"+ testDataFile + ".xlsx";
			System.out.println("Test Data File Path -"+testDataFilePath+" Test ID - "+testCaseID);
			this.setTestCaseID(testCaseID);
			this.setStrTestDataFilePath(testDataFilePath);

			DataPool objDataPool = new DataPool();
			dataPool = objDataPool.loadTestData(testCaseID, testDataFilePath);
		}
		System.out.println(dataPool.length==0);
		if(dataPool.length==0)
			objUtilities.logReporter("No Test Data Found in Regression sheet with test case ID - '"+testCaseID+"'", false, false);
		return dataPool;
	}

	/**
	 * @Method		: 	loadConfigProperties
	 * @Description	: 	load config properties 
	 * @author		:	Harshvardhan Yadav (SQS)
	 */
	public void loadConfigProperties()
	{
		try
		{
			objConfig = new Properties();
			objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
			this.setObjConfig(objConfig);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	/**
	 * @Method		: 	loadConfigProperties
	 * @Description	: 	load config properties 
	 * @author		:Samiksha Pandey (SQS)
	 */

	
	//Rupali _051520 adding two file code as need two config for NSR
	public Properties loadConfigPropertiesForModules(String Module)
	{
		try
		{
			if(Module.equalsIgnoreCase("NSR")) {
			objModuleConfig = new Properties();
			FileInputStream file1 = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/testData/"+ Module+"/"+"config.properties");
			FileInputStream file2 = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/testData/"+ Module+"/"+"ConfigForSo.properties");
			SequenceInputStream file3 = new SequenceInputStream(file1, file2);
			objModuleConfig.load(file3);
			file1.close();
			file2.close();
			
			this.setObjConfig(objModuleConfig);
			return objModuleConfig;
		}
			else {
				objModuleConfig = new Properties();
				objModuleConfig.load(new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/testData/"+ Module+"/"+"config.properties"));
				this.setObjConfig(objModuleConfig);
				return objModuleConfig;
				}
			}
		catch(Exception exception)
		{
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * startCustomeReports - this method starts the custome report  
	 * @author		:	Harshvardhan Yadav (SQS) 
	 */ 
	public void startCustomReports()
	{
		System.out.println("custom report initiated");
		setCustomeReporter(objCustomReporter);
		//objCustomReporter.startReport(callingClassName,callipngPackageName);
		objCustomReporter.startReport(callingClassName,callipngPackageName,sJiraID); //Namrata_10082021
	}

	//RPHADKE_29052018 - Open webmail url
	public void openWebMailURL()
	{
		this.saveCurrentURL();
		this.loadConfigProperties();
		page.navigate(objConfig.getProperty("web.Mail"));
	}
	
	//RPHADKE_29052018 - save current url
	public void saveCurrentURL()
	{		
		sCurrentURL = page.url();
	}
	
	//RPHADKE_29052018 - Open application url
	public void openApplicationURL()
	{
		if(sCurrentURL != "")
			page.navigate(sCurrentURL);
		else
		{
			this.loadConfigProperties();
			page.navigate(objConfig.getProperty("web.Url"));
		}
	}
	
	//sz30052018
	public String GetCommonPathForDifferentUser()
	{
		this.loadConfigProperties();
	//	System.out.println("Common file path :"+objConfig.getProperty("DiffUserTestData"));
	//	return objConfig.getProperty("DiffUserTestData"); 
		
		if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))
		{
			System.out.println("Common file path :"+objConfig.getProperty("DiffUserTestData_ITC1"));
			return objConfig.getProperty("DiffUserTestData_ITC1");
		}
		else
		{
			System.out.println("Common file path :"+objConfig.getProperty("DiffUserTestData"));
			return objConfig.getProperty("DiffUserTestData");
			
		}
	}
	
	
	//Namrata_5122018 - launch browser
	public void launchBrowser(String sWebUrl) {
        this.loadConfigProperties();

        callingClassName = this.getClass().getName();
        callipngPackageName = this.getClass().getPackage().getName();
        this.setRunningScriptName(callingClassName);
        this.setRunningPackageName(callipngPackageName);

        this.setBrowser(objConfig.getProperty("web.browser").trim().toLowerCase());

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        this.setPage(page);

        this.setObjectId(sObjectID);

        int wait = Integer.parseInt(objConfig.getProperty("driver.WebDriverWait"));
        this.setDefaultTimeout(wait);
        page.setDefaultTimeout(wait);

        objUtilities = new Utilities(this);
        this.setObjUtilities(objUtilities);

        objWrapperFunctions = new WrapperFunctions(this);
        this.setObjWrapperFunctions(objWrapperFunctions);

        // Determine URL to launch
        String targetUrl;
        if (sWebUrl == null || sWebUrl.isEmpty()) {
            if (objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true")) {
                targetUrl = objConfig.getProperty("itc1.web.Url");
            } else {
                targetUrl = objConfig.getProperty("web.Url");
            }
        } else {
            targetUrl = sWebUrl;
        }

        System.out.println("Launching URL: " + targetUrl);
        websiteURL = targetUrl;
        page.navigate(targetUrl);
    }
		
	//Namrata_5122018 - Close browser
	public void closeBrowser()
	{
		page.close();
	}
	//PoonamC_02Dec2020 : Added to open NSR Summary page link
	public void openNSRSummaryPageURL(String sURL)
	{
		this.saveCurrentURL();
		this.loadConfigProperties();
		page.navigate(sURL);
	}
	//PoonamC_06June2021 : Added to open SCV app page link
	public void openSCVCPCTeamViewURL(String sURL)
	{
		this.saveCurrentURL();
		this.loadConfigProperties();
		page.navigate(sURL);
	}
	
	//Roshan_16/7/2021
	public void openWebTeamURL()
	{
		this.saveCurrentURL();
		this.loadConfigProperties();
		page.navigate(objConfig.getProperty("web.teams"));
		
	}
	
	//Namrata_10082021 - Get the TestID annotation value
	public String getAnnotationValue() throws InstantiationException, IllegalAccessException {
	    try {
	        Class<?> cls = Class.forName(callingClassName);
	        Method[] methods = cls.getDeclaredMethods();

	        for (Method method : methods) {
	            TestID testIdAnnotation = method.getAnnotation(TestID.class);
	            if (testIdAnnotation != null) {
	                return testIdAnnotation.value();
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	@AfterAll
	public  void afterSuiteMethod()
	{
		try {
			String sOldLogFilePath = "\""+System.getProperty("user.dir") + "\\target\\custom-reports\\Execution_Report.xlsx"+"\"";
			String sNewLogFilePath = "\""+objConfig.getProperty("sPerfResultLocation")+"\"";
			this.CopyXlsxFileToAnotherLocation(sOldLogFilePath, sNewLogFilePath);
			
			String sNewLogFilePath_1 = "\""+objConfig.getProperty("sPerfResultLocation")+ "\\Execution_Report.xlsx"+"\"";
			String strDate = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(new java.util.Date());
			String sMachineName=InetAddress.getLocalHost().getHostName();
			String sNewnResultName = "Core_" +sMachineName+"_"+ strDate +".xlsx";
			this.RenameXlsxFileName(sNewLogFilePath_1, sNewnResultName);
			}
			catch (Exception exception) {
				exception.printStackTrace();
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
		
		@BeforeMethod
		public void setUp(ITestResult result) {
	 
			String testName = result.getMethod().getMethodName();
			String className = result.getTestClass().getRealClass().getSimpleName();
	 
			TestContext.setTestName(className + "_" + testName);
		}
	
}
