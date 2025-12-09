package com.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {
	private String strExcelFilePath = "";
	private XSSFWorkbook workbook;
	private XSSFSheet workbookSheet;
	private XSSFRow sheetRow;
	private int intSheetRowCounter = 1;
	private FileInputStream excelFileIS;
	private FileOutputStream fileOutputStream;
	private int intIndex = 0;
	private Properties objConfig;
	public static String dataType="";
	
	public void createSummaryStepCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
	}

	public void createSummaryHeaderCell(int cellNumber, String value)
	{
		XSSFCell cell = sheetRow.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getHeaderCellStyle());
	}
	
	//Namrata_09102019 - retrieves the cell value from specified row and column number.
	private String getCellValue(Row testDataRow, int columnNumber)
	{
		if (testDataRow == null)
			return "";
		else 
		{
			Cell testDataCell = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if(testDataCell == null)
				return "";
			else
				return testDataCell.toString().trim();
		}
	}	
	
	//Namrata_09102019 - sets the cell value at specified row and column number
	private boolean putCellValue(Row testDataRow, int columnNumber, String testData)
	{
		if (testDataRow != null)
		{
			Cell testDataCell = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if(testDataCell != null)
			{
				testDataCell.setCellValue(testData);
				return true;
			}
		}
		return false;
	}
	
	//Namrata_09102019 - Creates an excel if not exists and Writes data into the sheet 
	public void WriteDatatoExcel(String excelFilePath, String sSheetName, String sHeaders, String sValue)
	{
		
		// Excel file path
		strExcelFilePath =excelFilePath;
		
		String[] headerList = sHeaders.split("~");
		
		objConfig = new Properties();
		try {
			objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
			System.out.println("itc "+objConfig.getProperty("ITC1_Environment"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))
		{
			strExcelFilePath = strExcelFilePath.replace("env", "Pre_Requisite_TestData_ITC1");
			System.out.println(strExcelFilePath);			
		}
		else
		{
			strExcelFilePath = strExcelFilePath.replace("env", "Pre_Requisite_TestData_QA");
			System.out.println(strExcelFilePath);	
			
		}
		
		File excelFile = new File(strExcelFilePath);
		
		try
		{	
			// If file not exists, create new excel file with sheet and cell, and update value
			if(!excelFile.exists())
			{
			
				excelFile.createNewFile();
				workbook = new XSSFWorkbook();
				
				workbookSheet = workbook.createSheet(sSheetName);
				//created row and header cells with headers
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				
				//create next row and cells for 1st value
				sheetRow = workbookSheet.createRow(1);
				createSummaryStepCell(0, sValue);
				createSummaryStepCell(1, "Y");
				
			
			}
			 // If file exist update sheet and create Cells and update value
			else
			{
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(strExcelFilePath));
				workbookSheet = workbook.getSheet(sSheetName);
				if(workbookSheet==null)
				{	System.out.println("error");
				workbookSheet = workbook.createSheet(sSheetName);
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				}
					
				//get the last row number from excel having value, to avoid overwriting
				intSheetRowCounter = workbookSheet.getLastRowNum() + 1;
				sheetRow = workbookSheet.createRow(intSheetRowCounter);
				createSummaryStepCell(0, sValue);
				createSummaryStepCell(1, "Y");
		
			}
			
			//writes the workbook in file
			fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: WriteDatatoExcel()");
			exception.printStackTrace();
		} 
	}
	
	
	//Namrata_09102019 - retrieves the cell value with Y Flag and updates the Flag to N
	public String getCellDataWithYvalueAndUpdateCellValueN(String excelFilePath, String SheetName, boolean ignoreHeaderRow)
	{
		String excellCellData = null;
		int  lastRowNumber = 1;
		int rowIndex = 0;

		// Excel file path
		strExcelFilePath = excelFilePath;
		
		objConfig = new Properties();
		try {
			objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))
		{
			strExcelFilePath=strExcelFilePath.replace("env", "Pre_Requisite_TestData_ITC1");
			System.out.println(strExcelFilePath);			
		}
		else
		{
			strExcelFilePath=strExcelFilePath.replace("env", "Pre_Requisite_TestData_QA");
			System.out.println(strExcelFilePath);	
			
		}
		
		try
		{				
			excelFileIS = new FileInputStream(new File(strExcelFilePath));
		
			workbook = new XSSFWorkbook(excelFileIS);
			excelFileIS.close();
			
			workbookSheet = workbook.getSheet(SheetName);
			lastRowNumber = workbookSheet.getPhysicalNumberOfRows();
			if (ignoreHeaderRow)
				intIndex++;
			
			
			for(rowIndex = intIndex; rowIndex <= lastRowNumber; rowIndex++)
			{
				String cellData = this.getCellValue(workbookSheet.getRow(rowIndex), 1);
				//If Flag value is Y, gets cell value and updated Flag to N
				if(cellData.equalsIgnoreCase("Y"))
				{
					excellCellData= this.getCellValue(workbookSheet.getRow(rowIndex), 0);
					dataType=excellCellData;
					this.putCellValue(workbookSheet.getRow(rowIndex), 1, "N");
					break;
				}
				else
				{
					dataType="NoData";
				}
			}
			//writes the workbook in file
			fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
									
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: getCellDataWithYvalueAndUpdateCellValueN()");
			exception.printStackTrace();
		} 
		return excellCellData;
	}

	
	//Namrata_09102019  - commenting this method for future use
	/*public boolean UpdateCellvalue(String excelFilePath, String SheetName, String valueToUpdateAgainstCell, String valueToBeUpdated)
	{
		int  lastRowNumber = 1;
		boolean bFlag=false;
		int rowIndex = 0;

			try
			{				
				excelFileIS = new FileInputStream(new File(excelFilePath));
			
				workbook = new XSSFWorkbook(excelFileIS);
				excelFileIS.close();
				
				workbookSheet = workbook.getSheet(SheetName);
				lastRowNumber = workbookSheet.getPhysicalNumberOfRows();
							
				for(rowIndex = 0; rowIndex <= lastRowNumber; rowIndex++)
				{
					String cellData = this.getCellValue(workbookSheet.getRow(rowIndex), 0);
					if(cellData.equalsIgnoreCase(valueToUpdateAgainstCell))
					{
						this.putCellValue(workbookSheet.getRow(rowIndex), 1, valueToBeUpdated);
						bFlag=true;
						break;
					}
					
				}
				
				fileOutputStream = new FileOutputStream(excelFilePath);
				workbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
			}
			
			catch(Exception exception)
			{
				exception.printStackTrace();
			} 
			return bFlag;
	}*/
	
	public XSSFCellStyle getHeaderCellStyle() {
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);                                         // Use setBold true instead of deprecated setBoldweight
        headerFont.setFontName("Arial");

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);               // Use HorizontalAlignment enums
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Thin borders using BorderStyle enum
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        // Set fill foreground color as grey 25%
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }
	
	//Namrata_09102019  
	public boolean verifySheetName(String sSheetName) {
		boolean bResult = false;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) 
		{

			if (workbook.getSheetName(i).trim().equalsIgnoreCase(sSheetName))
				{
					bResult = true;
					break;
				}	
		}
		return bResult;
	}
	//Supriya use for prerequisite testing
	public void WriteDatatoExcelforPrerequisite(String excelFilePath, String sSheetName, String sHeaders, String sValue,boolean svalue1,String comment)
	{
		DateFormat objDateFormat = new SimpleDateFormat("dd_MM_yyyy");
		Date objDate = new Date();
		String File_Name=excelFilePath+objDateFormat.format(objDate)+".xlsx";

		String result;
		if(svalue1)
		{
			 result="True";
		}
		else
		{
			 result="False";
		}
		
		// Excel file path
		strExcelFilePath =File_Name;
		
		String[] headerList = sHeaders.split("~");
		
		objConfig = new Properties();
		try {
			objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
			System.out.println("itc "+objConfig.getProperty("ITC1_Environment"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(objConfig.getProperty("ITC1_Environment").equalsIgnoreCase("true"))
		{
			strExcelFilePath = strExcelFilePath.replace("env", "Pre_Requisite_TestData_ITC1");
			System.out.println(strExcelFilePath);			
		}
		else
		{
			strExcelFilePath = strExcelFilePath.replace("env", "Pre_Requisite_TestData_QA");
			System.out.println(strExcelFilePath);	
			
		}
		
		File excelFile = new File(strExcelFilePath);
		
		try
		{	
			
			// If file not exists, create new excel file with sheet and cell, and update value
			if(!excelFile.exists())
			{
			
				excelFile.createNewFile();
				workbook = new XSSFWorkbook();
				
				workbookSheet = workbook.createSheet(sSheetName);
				//created row and header cells with headers
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				
				//create next row and cells for 1st value
				sheetRow = workbookSheet.createRow(1);
				createSummaryStepCell(0, sValue);
				createSummaryStepCell(1, result);
				createSummaryStepCell(2, comment);
			}
			 // If file exist update sheet and create Cells and update value
			else
			{
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(strExcelFilePath));
				workbookSheet = workbook.getSheet(sSheetName);
				if(workbookSheet==null)
				{	System.out.println("error");
				workbookSheet = workbook.createSheet(sSheetName);
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				}
					
				//get the last row number from excel having value, to avoid overwriting
				intSheetRowCounter = workbookSheet.getLastRowNum() + 1;
				sheetRow = workbookSheet.createRow(intSheetRowCounter);
				createSummaryStepCell(0, sValue);
				createSummaryStepCell(1, result);
				createSummaryStepCell(2, comment);
			}
			
			//writes the workbook in file
			fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: WriteDatatoExcel()");
			exception.printStackTrace();
		} 
	}
	
	//Supriya Added for getting Jirra Id
	public String getCellDataOfTestCaseId( String SheetName, boolean ignoreHeaderRow,String sManualTCID)
	{
	
		String excellCellData = null;
		int  lastRowNumber = 1;
		int rowIndex = 0;

		objConfig = new Properties();
		try
		{				
			excelFileIS = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/Jira.xlsx"));
		
			workbook = new XSSFWorkbook(excelFileIS);
			excelFileIS.close();
			
			workbookSheet = workbook.getSheet(SheetName);
			lastRowNumber = workbookSheet.getPhysicalNumberOfRows();
			if (ignoreHeaderRow)
				intIndex++;
			
			for(rowIndex = intIndex; rowIndex <= lastRowNumber; rowIndex++)
			{
				String cellData = this.getCellValue(workbookSheet.getRow(rowIndex), 2);
				//If Flag value is Y, gets cell value and updated Flag to N
				if(cellData.equalsIgnoreCase(sManualTCID))
				{
					excellCellData= this.getCellValue(workbookSheet.getRow(rowIndex), 1);
					excellCellData= excellCellData+"~"+this.getCellValue(workbookSheet.getRow(rowIndex), 3);
					System.out.println("excel data in excel opr is ="+excellCellData);
					break;
				}
			}
			//writes the workbook in file
			//fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "/src/test/resources/Jira.xlsx");
			//workbook.write(fileOutputStream);
			//fileOutputStream.flush();
			//fileOutputStream.close();
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method : getCellDataOfTestCaseId()");
			exception.printStackTrace();
		} 
		return excellCellData;
	}
	public void WriteDatatoExcelForUpload(String excelFilePath, String sSheetName, String sHeaders, String sValue,int ColumnNo,int RowNo)
	{
		
		// Excel file path
		strExcelFilePath =excelFilePath;
		
		String[] headerList = sHeaders.split("~");
		
		objConfig = new Properties();
		File excelFile = new File(strExcelFilePath);
		try
		{	
			// If file not exists, create new excel file with sheet and cell, and update value
			if(!excelFile.exists())
			{
				excelFile.createNewFile();
				workbook = new XSSFWorkbook();
				workbookSheet = workbook.createSheet(sSheetName);
				//created row and header cells with headers
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				
				//create next row and cells for 1st value
				sheetRow = workbookSheet.createRow(RowNo);
				createSummaryStepCell(ColumnNo, sValue);
			}
			 // If file exist update sheet and create Cells and update value
			else
			{
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(strExcelFilePath));
				workbookSheet = workbook.getSheet(sSheetName);
				if(workbookSheet==null)
				{	System.out.println("error");
				workbookSheet = workbook.createSheet(sSheetName);
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				}
					
				//get the last row number from excel having value, to avoid overwriting
				intSheetRowCounter = workbookSheet.getLastRowNum() + 1;
				sheetRow = workbookSheet.createRow(intSheetRowCounter);
				createSummaryStepCell(ColumnNo, sValue);
			}
			
			//writes the workbook in file
			fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: WriteDatatoExcel()");
			exception.printStackTrace();
		} 
	}
	public void GenerateExcelWithExecutionTC(String excelFilePath, String sSheetName, String sHeaders, String sFunctionality,String sTCPath,String TCName,String XmlName,String sUser)
	{
		// Excel file path
		strExcelFilePath =excelFilePath;
		String[] headerList = sHeaders.split("~");
		strExcelFilePath = System.getProperty("user.dir") + "/target/custom-reports/"+excelFilePath;
		File excelFile = new File(strExcelFilePath);
		try
		{	
			if(!excelFile.exists())
			{
				excelFile.createNewFile();
				workbook = new XSSFWorkbook();
				
				workbookSheet = workbook.createSheet(sSheetName);
				//created row and header cells with headers
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				
				//create next row and cells for 1st value
				sheetRow = workbookSheet.createRow(1);
				createSummaryStepCell(0, sFunctionality);
				createSummaryStepCell(1, sTCPath);
				createSummaryStepCell(2, TCName);
				createSummaryStepCell(3, XmlName);
				createSummaryStepCell(4, sUser);
			}
			 // If file exist update sheet and create Cells and update value
			else
			{
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(strExcelFilePath));
				workbookSheet = workbook.getSheet(sSheetName);
				if(workbookSheet==null)
				{	System.out.println("error");
				workbookSheet = workbook.createSheet(sSheetName);
				sheetRow = workbookSheet.createRow(0);
				for(int i=0; i<headerList.length; i++)
					this.createSummaryHeaderCell(i, headerList[i]);
				}
					
				//get the last row number from excel having value, to avoid overwriting
				intSheetRowCounter = workbookSheet.getLastRowNum() + 1;
				sheetRow = workbookSheet.createRow(intSheetRowCounter);
				createSummaryStepCell(0, sFunctionality);
				createSummaryStepCell(1, sTCPath);
				createSummaryStepCell(2, TCName);
				createSummaryStepCell(3, XmlName);
				createSummaryStepCell(4, sUser);
		
			}
			
			//writes the workbook in file
			fileOutputStream = new FileOutputStream(strExcelFilePath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: GenerateExcelWithExecutionTC()");
			exception.printStackTrace();
		} 
	}
	
	
	//Namrata_09102019 - retrieves the cell value with Y Flag and updates the Flag to N
	public String []getUserDataWithXMLName(String SheetName,String sXMLName, boolean ignoreHeaderRow)
	{
		int  lastRowNumber = 1;
		int rowIndex = 0;
		String cellData=null;
		String[] aData=new String[100];
		// Excel file path
		strExcelFilePath = "AllSuiteName.xlsx";
		strExcelFilePath = System.getProperty("user.dir") + "/src/test/resources/"+strExcelFilePath;
		
		try
		{				
			excelFileIS = new FileInputStream(new File(strExcelFilePath));
		
			workbook = new XSSFWorkbook(excelFileIS);
			excelFileIS.close();
			
			workbookSheet = workbook.getSheet(SheetName);
			lastRowNumber = workbookSheet.getPhysicalNumberOfRows();
			if (ignoreHeaderRow)
				intIndex++;
			
			for(rowIndex = intIndex; rowIndex <= lastRowNumber; rowIndex++)
			{
				cellData = this.getCellValue(workbookSheet.getRow(rowIndex), 0);
				cellData = cellData + "~" + this.getCellValue(workbookSheet.getRow(rowIndex), 1);
				aData[rowIndex-1]=cellData;
				System.out.println(cellData);
				//If Flag value is Y, gets cell value and updated Flag to N	
			}						
		}
		catch(Exception exception)
		{
			System.out.println("Failed at method ExcelWriter: getUserDataWithXMLName()");
			exception.printStackTrace();
		} 
		return aData;
	}
	
}
