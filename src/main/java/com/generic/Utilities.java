package com.generic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


//import com.teamcenter.soa.client.model.strong.User;

import com.generic.ExcelOperations;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

//import org.sikuli.script.Screen;
/**
 * @ScriptName : Utilities
 * @Description : This class contains utlities function
 * @Author : Harshvardhan Yadav (SQS), Vikram Bendre (SQS), Indrjeet Chanvan
 *         (SQS)
 */
public class Utilities {
	private Properties objConfig;
	private CustomReporter objReporter;
	private Hashtable<String, String> testData;
	private WrapperFunctions objWrappers;
	private ExcelIterator objExcelIterator;
	String sScriptNameUnderTest = "";
	String sObjID = "";
	Pojo objPojoObject;
	public static int iStepCounter = 1;
	private CustomReporterHelper objCustomReporterHelper;
	private ExcelOperations ObjExcel;
	 public Page page;
	public Utilities(Pojo objPojo) {
		objConfig = objPojo.getObjConfig();
		objReporter = objPojo.getCustomeReporter();
		testData = objPojo.getDataPoolHashTable();
		objWrappers = new WrapperFunctions(objPojo);
		sScriptNameUnderTest = objPojo.getRunningScriptName();
		objPojoObject = objPojo;
		//objCustomReporterHelper = new CustomReporterHelper();
		ObjExcel = new ExcelOperations();
		page= objPojo.getPage();
	}
	
	// PoonamC_22June2021
	public String getCurrentURL() {
	    String currentUrl = "";
	    try {
	        currentUrl = page.url();
	    } catch (Exception e) {
	        System.out.println("Failed to get current URL: " + e.getMessage());
	        // Optionally log with your custom reporter
	        // logReporter("Fail to get current URL : getCurrentURL()", false, false);
	    }
	    return currentUrl;
	}


	/**
	 * @Method : waitFor
	 * @Description : Waits for the specified amount of [timeInMilliseconds].
	 * @param : timeUnitSeconds - wait time seconds
	 * @author : Harshvardhan Yadav (SQS)
	 */
	public void waitFor(int timeUnitSeconds) {
		try {
			Thread.sleep(TimeUnit.MILLISECONDS.convert(timeUnitSeconds, TimeUnit.SECONDS));
		} catch (Exception exception) {
			// throw new RuntimeException(exception);
			this.logReporter("InterruptedException thrown while waiting by Thread.sleep()", false, false);
		}
	}

	/**
	 * @Method : waitFor
	 * @Description : Waits for the specified amount of [timeInMilliseconds].
	 * @param : timeUnitMiliSeconds - wait time milliseconds
	 * @author : Namrata (SQS)
	 */
	public void waitForMilliSeconds(int timeUnitMilliSeconds) {
		try {
			Thread.sleep(timeUnitMilliSeconds);
		} catch (Exception exception) {
			// throw new RuntimeException(exception);
			this.logReporter("InterruptedException thrown while waiting by Thread.sleep()", false, false);
		}
	}

	/**
	 * /**
	 * 
	 * @Method : getRequiredDate
	 * @Description : This method will give require date
	 * @param : incrfementDateByDays Number by which user want increase date
	 * @param : sExpectedDateFormat - User expected date format eg. 9 april 2014 ---
	 *        dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param : timeZoneId - Time Zone
	 * @author : Harshvardhan Yadav (SQS)
	 */
	public String getRequiredDate(int incrementDays, String expectedDateFormat, String timeZoneId) {
		try {
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if (timeZoneId != null && !timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.DAY_OF_MONTH, incrementDays);
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		} catch (Exception exception) {
			System.out.println(exception.getStackTrace());
			return null;
		}
	}

	/**
	 * @Method : getFormatedDate
	 * @Description : This method will converted date into excepted date format
	 * @author : Indrajeet Chavan(SQS)
	 */
	public String getFormatedDate(String date, String originalDateFormat, String expectedDateFormat) {
		try {
			DateFormat inputFormatter = new SimpleDateFormat(originalDateFormat);
			Date originalDate = inputFormatter.parse(date);
			DateFormat outputFormatter = new SimpleDateFormat(expectedDateFormat);
			String expectedDate = outputFormatter.format(originalDate);
			return expectedDate;
		} catch (Exception exception) {
			System.out.println(exception.getStackTrace());
			return null;
		}
	}

	/**
	 * @Method : copyFileUsingStream
	 * @Description : copy files
	 * @param : Soure file path
	 * @param : destination file path
	 * @author : Harshvardhan Yadav (SQS)
	 */
	public void copyFileUsingStream(String sourceFilePath, String destinationFilePath) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(new File(sourceFilePath));
			outputStream = new FileOutputStream(new File(destinationFilePath));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception exception) {
			System.out.println(exception.getStackTrace());

		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException iOException) {
				iOException.printStackTrace();
			}
		}
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, resultLog - result log pass/fail
	 *        (true/false), includeMobile - result for mobile(true/false)
	 * @author : Indrajeet Chavan (SQS)
	 */
	@Step("{0}")
	public void logReporter(String step, boolean resultLog, boolean includeMobile) {
		this.addStepInTxtReport(step, resultLog);
		String strLog = step;
		this.addAssertTakeScreenShot(step, strLog, "", "", "", resultLog, includeMobile);
		StepReporter.stepWithScreenshot(page, step);
	}

	public void addStepInTxtReport(String sStep, boolean bStepResult) {
		try {
			String sScriptName = sScriptNameUnderTest.substring(sScriptNameUnderTest.lastIndexOf(".") + 1,
					sScriptNameUnderTest.length());
			String sFilePath = System.getProperty("user.dir") + "/target/custom-reports/" + sScriptName + ".txt";
			FileWriter objFW = new FileWriter(sFilePath, true);
			String sStepLine = "";
			
			File Tempfile = new File(sFilePath);
			if(Tempfile.length()==0)
			{
				Properties objConfig1 = new Properties();
				objConfig1.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
				String sModuleName=objPojoObject.getRunningPackageName();
				sModuleName = sModuleName.substring(sModuleName.lastIndexOf(".") + 1,sModuleName.length());
				String sEITRelease=objConfig1.getProperty("EIT_Release");
				String sEnv=objConfig1.getProperty("Environment");
				String sMachineName=InetAddress.getLocalHost().getHostName();
				String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());	

				sStepLine = "--------------------------------------------------------"+ System.lineSeparator();
				sStepLine = sStepLine + "Automation Script Name 		: " + sScriptName + System.lineSeparator();
				sStepLine = sStepLine + "Module Name			: " + sModuleName + System.lineSeparator();
				sStepLine = sStepLine + "EIT Release 			: " + sEITRelease + System.lineSeparator();
				sStepLine = sStepLine + "Environment 			: " + sEnv + System.lineSeparator();
				sStepLine = sStepLine + "Machine Name 			: " + sMachineName + System.lineSeparator();
				sStepLine = sStepLine + "Date 				: " + strDate + System.lineSeparator();
				sStepLine = sStepLine + "Browser Version 	        : " + InitializeTearDownEnvironment.browserVersion + System.lineSeparator();
				sStepLine = sStepLine + "--------------------------------------------------------"+ System.lineSeparator();
				objFW.write(sStepLine);
				Tempfile=null;
			}
			sStepLine = "";
			if (bStepResult)
				sStepLine = "Pass : " + sStep + System.lineSeparator();
			else
				sStepLine = "Fail : " + sStep + System.lineSeparator();
			objFW.write(sStepLine);
			objFW.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, inputValue - Input value, resultLog -
	 *        result log pass/fail (true/false), includeMobile - result for
	 *        mobile(true/false)
	 * @author : Indrajeet Chavan(SQS)
	 */
	@Step("{0} - {1}")
	public void logReporter(String step, String inputValue, boolean resultLog, boolean includeMobile) {
		String strLog = step + "|| Input Value : " + inputValue;
		this.addAssertTakeScreenShot(step, strLog, inputValue, "", "", resultLog, includeMobile);
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, expectedValue - verification point expected
	 *        value, actualValue - verification point actual value, resultLog -
	 *        result log pass/fail (true/false), includeMobile - result for
	 *        mobile(true/false)
	 * @author : Indrajeet Chavan (SQS)
	 */
	@Step("{0} - {2} - {3}")
	public void logReporter(String step, String expectedValue, String actualValue, boolean resultLog,
			boolean includeMobile) {
		String strLog = step + " || Expected Result : " + expectedValue + " || Actual Result : " + actualValue;
		this.addAssertTakeScreenShot(step, strLog, "", expectedValue, actualValue, resultLog, includeMobile);
	}
	/**
	 * @Method : addAssertTakeScreenShot
	 * @Description :
	 * @param :
	 * @author : Indrajeet Chavan(SQS)
	 */
	public void addAssertTakeScreenShot(String step, String strLog, String inputValue, String expectedValue,
			String actualValue, boolean resultLog, boolean includeMobile, String... stepText) {
		
        String scriptName = sScriptNameUnderTest.substring(sScriptNameUnderTest.lastIndexOf(".") + 1);
        String fileName = scriptName + "_Iteration" + ".png";
        String fileWithPath = System.getProperty("user.dir") + "\\" + objConfig.getProperty("screenshot.dir")
		+ "\\ScreenShot\\" + fileName;
        File screenshotFolder = new File(fileWithPath).getParentFile();
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

        // Take screenshot
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileWithPath)));

        // Log result
        if (resultLog) {
            System.out.println("PASS: " + step);
            // Optionally log to PDF/Excel/Console
        } else {
            System.out.println("FAIL: " + step);
            // Optionally log to PDF/Excel/Console
            throw new AssertionError("Step failed: " + strLog);
        }

        // Optional: capture final screenshot if step indicates test completion
        if (step.toLowerCase().contains("tc completed")) {
            String lastStepFile = scriptName + "_Iteration" + "_LastPassStep.png";
            String lastPassStep = System.getProperty("user.dir") + "\\" + objConfig.getProperty("screenshot.dir")
			+ "\\ScreenShot\\" + lastStepFile;
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(lastPassStep)));
        }

        // Optional: log timestamp
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        System.out.println("Logged at: " + timestamp);
    }

	/**
	 * @Method : takeScreenShot
	 * @Description : Take Screen shot for given web driver.
	 * @author : Indrajeet Chavan (SQS)
	 */
	public void takeScreenshot(Page page, String fileWithPath) {
		  try {
		    // Capture screenshot and save to file
			  page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileWithPath)));
			    File screenshotFile = new File(fileWithPath);
			    this.fileToByte(screenshotFile); 
		  } catch (IOException e) {
		    e.printStackTrace();
		    this.logReporter("IOException thrown while trying to save screenshot", true, false);
		  }
		}

	
	/**
	 * @Method : fileToByte
	 * @Description : Converts image file to byte array for allure.
	 * @author : Indrajeet Chavan (SQS)
	 * @throws : IOException
	 */
	 @Attachment(value = "Screenshot", type = "image/png")
	  public byte[] fileToByte(File file) throws IOException {
	    if (file != null) {
	      return Files.readAllBytes(Paths.get(file.getPath()));
	    } else {
	      return new byte[0];
	    }
	  }

	/**
	 * @Method: dpString
	 * @Description: this method returns data from the the previously loaded
	 *               datapool
	 * @param columnHeader - excel file header column name
	 * @return - value for corresponding header
	 * @author Vikram Bendre (SQS) @CreationDate: 27 April 2015 @ModifiedDate:
	 */
	public String dpString(String columnHeader) {
		Hashtable<String, String> dataPoolHashTable = testData;
		try {
			if (dataPoolHashTable.get(columnHeader) == null)
				return "";
			else {
				return dataPoolHashTable.get(columnHeader);
			}
		} catch (NullPointerException exception) {
			// throw new RuntimeException(exception);
			this.logReporter("NullPointerException thrown while loading data from datapool", false, false);
			return null;
		}
	}

	/**
	 * @Method: createFolderAtShareLocation
	 * @Description: this method creates and returns folder name , created at share
	 *               location
	 * @return - value for created folder
	 * @author Vijay Tyagi (SQS) @CreationDate: 24 Jan 2018 @ModifiedDate:
	 */
	public String createFolderAtShareLocation() {
		LocalDate date = LocalDate.now();
		String sPath = "\\\\amat.com\\Folders\\India\\GIS\\TD-TCE-COMMON\\CorePlus_ECR\\ECR_" + date;
		try {
			File file = new File(sPath);
			if (!file.exists()) {
				file.mkdir();
				System.out.println("Done");
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return sPath;

	}

	/**
	 * @Method: dataInsertionECR
	 * @Description: this method insertECR data into text file
	 * @author Vijay Tyagi (SQS) @CreationDate: 24 Jan 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public void dataInsertionECR(String TestCaseID, String ECRNumber, String sPath) throws IOException {
		String fileName = sPath + "\\" + TestCaseID + ".txt";
		File file = new File(fileName);
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				System.out.println("inside");
				file.createNewFile();
			}
			String sECR = "ECRNumber:" + ECRNumber;
			String sStatus = "Status:";
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(sECR);
			bw.newLine();
			bw.write(sStatus);
			System.out.println("Done");

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		finally {
			bw.close();
			fw.close();
		}

	}

	/**
	 * @Method: savePCRNumberInTextFile
	 * @Description: this method insertPCR data into text file
	 * @author Swapnil Zunjur (SQS) @CreationDate: 05 Apr 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public void savePCRNumberInTextFile(String TestCaseID, String PCRNumber, String sPath, String sFlag) {
		String fileName = sPath + "\\" + TestCaseID + ".txt";
		File file = new File(fileName);
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// String sPCR= PCRNumber;
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(PCRNumber);
			bw.newLine();
			bw.write(sFlag);
			System.out.println("'" + fileName + "',File is Created Successfully..");
		} catch (IOException exception) {
			this.logReporter("IOException thrown while trying to search file", false, false);
		}

		finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getStackTrace());
				this.logReporter("IOException thrown while closing the file handle", false, false);
			}

		}

	}

	/**
	 * @Method: saveMultiplelinedata in File
	 * @Description: this method insertPCR data into text file
	 * @author Rupali Pratapwar(SQS) @CreationDate: 092419 @ModifiedDate:
	 * @throws IOException
	 */
	public void saveMultipledataInTextFile(String TestCaseID, String Number, String sPath, String sFlag) {
		String fileName = sPath + "\\" + TestCaseID + ".txt";
		File file = new File(fileName);
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// String sPCR= PCRNumber;
			fw = new FileWriter(fileName, true);
			bw = new BufferedWriter(fw);

			bw.write(Number);
			bw.newLine();
			bw.write(sFlag);
			bw.newLine();
			System.out.println("'" + fileName + "',File is Created Successfully..");
		} catch (IOException exception) {
			this.logReporter("IOException thrown while reading file", false, false);
		}

		finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				this.logReporter("IOException thrown while reading file", false, false);
			}

		}

	}

	/**
	 * @Method: deleteTextFile
	 * @Description: this method delete the existing text file.
	 * @author Swapnil Zunjur (SQS) @CreationDate: 09 Apr 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public void deleteTextFile(String sPath, String TextFileName) {
		String fileName = sPath + "\\" + TextFileName + ".txt";
		File file = new File(fileName);

		if (!file.exists()) {
			System.out.println("File is not present to delete..");
		} else {
			file.delete();
			System.out.println("File is Deleted Successfully..");
		}

	}
	
	public void VerifyCompletedStatusCheckBoxShouldDisplay(String sHeader) {
	    boolean bResult;
	    Locator objPageHeader = page.locator("//table[@role='treegrid']//tbody//tr[1]//td[3]");

	    bResult = objWrappers.checkElementDisplyed(objPageHeader);

	    if (bResult)
	        this.logReporter(
	            "verify Column Allow NSR Submission Post Project Closure Attribute is Yes Completed Status Text Box Should Display '" 
	            + sHeader + "' in method 'verifyPageHeader'", 
	            bResult, false);
	    else
	        this.logReporter(
	            "verify Column Allow NSR Submission Post Project Closure Attribute is Yes Completed Status Text Box Should Display '" 
	            + sHeader + "' in method 'verifyPageHeader'", 
	            bResult, false);
	}


	/**
	 * @Method: getPCRNumberAndFlagFromTextFile
	 * @Description: this method read and return the PCR bumber and Flag from text
	 *               file
	 * @author Swapnil Zunjur (SQS) @CreationDate: 09 Apr 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public String getPCRNumberAndFlagFromTextFile(String sPath, String TestCaseID, String sFlag) {
		String fileName = sPath + "\\" + TestCaseID + ".txt";
		File file = new File(fileName);
		String line = null;
		String wFlag = null;
		String Data = null;
		int Counter = 0;
		try {
			do {
				if (file.exists()) // check the file existence
				{
					System.out.println("'" + fileName + "', File is present.., Runnnig for part :'" + sFlag + "'");// sz25092018
				} else {
					// wait for 20 sec till file exist
					System.out.println("'" + fileName + "', File is not present, waiting for file to be Created..");
					this.waitFor(20);
					Counter++;
				}
			} while (!file.exists() && Counter <= 1);

			if (file.exists()) // check the file existence
			{
				FileReader fileReader = new FileReader(file);
				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				if (bufferedReader.read() == -1) {
					System.out.println("'" + fileName + "', File is EMPTY");
				} else {
					System.out.println("'" + fileName + "', File is Exist and NOT EMPTY");
					do {
						// Initialize the file reader and buffer reader to null in each loop
						fileReader = null;
						bufferedReader = null;

						FileReader fileReader1 = new FileReader(file);
						BufferedReader bufferedReader1 = new BufferedReader(fileReader1);

						// Read the first line to get the PCR Number
						String PCRNumber = bufferedReader1.readLine();
						// Read the Second line to get the Flag status
						String Flagg = bufferedReader1.readLine();
						wFlag = Flagg;

						Data = PCRNumber + "~" + Flagg;
						System.out.println("***");
						if (!wFlag.trim().contentEquals(sFlag))// Rupali_092319
						{

							PCRNumber = bufferedReader1.readLine();
							Flagg = bufferedReader1.readLine();
							wFlag = Flagg;
							Data = PCRNumber + "~" + Flagg;

						}

						// Set the file reader and buffer reader to null at the end of each loop
						fileReader1.close();
						bufferedReader1.close();
						Counter++;

						// Wait for 20 sec till the while condition gets true
						this.waitFor(20);
					} while (!wFlag.trim().contentEquals(sFlag) && Counter <= 30); // sFlag is to wait until we
					// getExpected Flag value, till then
					// it will loop
					System.out.println("Counter = " + Counter);
					System.out.println("The PCR and Flag is :: " + Data);

					// sz01022019
					boolean bResult = true;
					if (!wFlag.trim().contentEquals(sFlag)) {
						bResult = false;
						this.logReporter("Script is not ready to Exceute part '" + sFlag
								+ "'  :getPCRNumberAndFlagFromTextFile()", bResult, false);
					} else {
						this.logReporter(
								"Script is ready to Exceute part '" + sFlag + "'  :getPCRNumberAndFlagFromTextFile()",
								bResult, false);
					}

				}
			} else {
				System.out.println("'" + fileName + "', EXPECTED FILE DOES NOT EXISTS");
				this.logReporter(
						"'" + fileName + "', EXPECTED FILE DOES NOT EXISTS : getPCRNumberAndFlagFromTextFile()", false,
						false);
			}

		} catch (Exception exception) {
			this.logReporter("Exception thrown while trying to read from text file", false, false);
		}

		// this.logReporter("The Expected Data 'PCR and Flag' is Returned '"+Data+"' :
		// getPCRNumberAndFlagFromTextFile()", true, false);
		return Data;
	}

	/**
	 * @Method: flagForUFT
	 * @Description: this method inserts a flag into text so as to inform UFT to run
	 *               script
	 * @author Vijay Tyagi (SQS) @CreationDate: 24 Jan 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public void flagForUFT() throws IOException {
		String fileName = "\\\\amat.com\\Folders\\India\\GIS\\TD-TCE-COMMON\\CorePlus_ECR\\flag.txt";
		File file = new File(fileName);
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String sFlag = "Yes";
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(sFlag);
			System.out.println("Done");

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		finally {
			bw.close();
			fw.close();
		}
	}

	/**
	 * @Method: getECRStatus
	 * @Description: this method gives the status of ECR number, it has got Locked
	 *               from UFT or not
	 * @author Vijay Tyagi (SQS) @CreationDate: 24 Jan 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public String getECRStatus(String TestCaseID, String sPath) {
		String fileName = sPath + "\\" + TestCaseID + ".txt";
		BufferedReader br = null;
		FileReader fr = null;
		boolean bFlag = false;
		String ECRNumber = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			while (br.readLine() != null) {
				String sdata = br.readLine();
				System.out.println(sdata);
				String[] sResult = sdata.split(":", 2);
				if (sResult[1] != "") {
					ECRNumber = new String(Files.readAllBytes(Paths.get(fileName)));
					String[] arrFileDetails = ECRNumber.split("\n");
					String[] arrStatusDetails = arrFileDetails[0].split(":");
					ECRNumber = arrStatusDetails[1];
				}
			}
		} catch (IOException exception) {
			this.logReporter("IO Exception throwm while reading file", false, false);
		}

		finally {

			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				this.logReporter("IO Exception throwm while reading file", false, false);
			}
		}
		return ECRNumber;
	}

	/**
	 * @Method: getDetailsFromListOfTestCaseTextFile
	 * @Description: this method gives the details of ListOfTestCase Text File,
	 * @author Vijay Tyagi (SQS) @CreationDate: 24 Jan 2018 @ModifiedDate:
	 * @throws IOException
	 */
	public String[] getDetailsFromListOfTestCaseTextFile() {
		String fileName = "\\\\amat.com\\Folders\\India\\GIS\\TD-TCE-COMMON\\CorePlus_ECR\\ListOfTestCaseID.txt";
		File file = new File(fileName);
		String[] arrOfTestID = null;

		try {

			if (!file.exists()) {
				System.out.println("No");
				JOptionPane.showMessageDialog(null, "File Not Found");
			}

			String Data = "";
			Data = new String(Files.readAllBytes(Paths.get(fileName)));
			System.out.println(Data);
			arrOfTestID = Data.split("\n");

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return arrOfTestID;

	}

	// RPHADKE_25012018 - Verify page header
	public void verifyPageHeader(String sHeader) {
	    if (sHeader.equals("Project Portfolio Manager"))
	        // Equivalent of waitTillPageLoad(By.cssSelector("div.k-loading-image"))
	        page.waitForSelector("div.k-loading-image", 
	            new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED));

	    boolean bResult;
	    String objPageHeader = "//div[contains(text(),'" + sHeader + "') and @id = 'PageTitle']";
	    Locator locator = page.locator("xpath=" + objPageHeader).first();

	    try {
	        bResult = locator.isVisible();
	    } catch (PlaywrightException e) {
	        bResult = false;
	    }

	    if (bResult) {
	        this.logReporter("Verify page title as '" + sHeader + "' in method 'verifyPageHeader'", bResult, false);
	    } else {
	        objPageHeader = "//div[@id = 'PageTitle']/label[contains(text(),'" + sHeader + "')]";
	        locator = page.locator("xpath=" + objPageHeader).first();

	        try {
	            bResult = locator.isVisible();
	        } catch (PlaywrightException e) {
	            bResult = false;
	        }

	        if (bResult)
	            this.logReporter("Verify page title as '" + sHeader + "' in method 'verifyPageHeader'", bResult, false);
	        else
	            this.logReporter("Verify page title as '" + sHeader + "' in method 'verifyPageHeader'", bResult, false);
	    }
	}

	public String getAutomationJiraIDFromTextFile(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteExecutionReportXlsx() {
		String sFilePath = System.getProperty("user.dir") + "/target/custom-reports/Execution_Report.xlsx";
		File file = new File(sFilePath);

		if (!file.exists()) {
			System.out.println("File is not present to delete..");
		} else {
			file.delete();
			System.out.println("File is Deleted Successfully..");
		}
	}

	public String getPageTitle() {
    return page.title();
}

	public void switchToTabBasedOnIndex(int index) {
		// TODO Auto-generated method stub
		
	}
	
	
}