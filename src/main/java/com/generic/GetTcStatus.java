package com.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class GetTcStatus implements ITestListener {

	private String strConsolidatedExcelReportPath ;
	private XSSFWorkbook workbook ;
	private XSSFSheet sheet ;
	private FileOutputStream fileOut;
	
	public void createfileAndWriteToFile()
	{
		// Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String strFormattedDate = dateFormat.format(date);
		strConsolidatedExcelReportPath =  System.getProperty("user.dir") + "/src/test/java/testresult/ConsolidatedExcelReport_"  + strFormattedDate + ".xlsx";
		File excelFile =  new File(strConsolidatedExcelReportPath);
		if(!excelFile.exists())
		{      
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("report");
			XSSFRow rowhead = sheet.createRow((short)0);

			rowhead.createCell(0).setCellValue("TestCaseName");
			rowhead.createCell(1).setCellValue("TestCaseStatus");
			try
			{
				fileOut = new FileOutputStream(strConsolidatedExcelReportPath);
				workbook.write(fileOut);
				fileOut.close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace(); 
			}
		}
	}

	public void writeToFile(String TCName , String TCStatus )
	{
		try
		{
			InputStream inputStream = new FileInputStream(strConsolidatedExcelReportPath);
			workbook = new XSSFWorkbook(inputStream);
			sheet  = workbook.getSheet("report");

			int lastRow  = sheet.getLastRowNum();
			XSSFRow rowdata = sheet.createRow(lastRow + 1);
			rowdata.createCell(0).setCellValue(TCName);
			rowdata.createCell(1).setCellValue(TCStatus);

			fileOut = new FileOutputStream(strConsolidatedExcelReportPath);
			workbook.write(fileOut);
			fileOut.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}

	}

	@Override
	public void onTestStart(ITestResult result)
	{
		this.createfileAndWriteToFile();
	}

	@Override
	public void onTestSuccess(ITestResult result)
	{
		String strTestCase =  result.getMethod().getTestClass().getName();
		strTestCase = strTestCase.substring(strTestCase.lastIndexOf(".") + 1);
		this.writeToFile(strTestCase, "PASS");
	}

	@Override
	public void onTestFailure(ITestResult result)
	{
		String strTestCase = result.getMethod().getTestClass().getName();
		strTestCase = strTestCase.substring(strTestCase.lastIndexOf(".") + 1);
		this.writeToFile(strTestCase, "FAIL");
	}

	@Override
	public void onTestSkipped(ITestResult result)
	{
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{

	}

	@Override
	public void onStart(ITestContext context)
	{

	}

	@Override
	public void onFinish(ITestContext context)
	{
	}
	
}
