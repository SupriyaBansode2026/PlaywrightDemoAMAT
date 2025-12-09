package com.generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataPool {

	private XSSFSheet sheet;
	private XSSFWorkbook workbook;
	private FileInputStream excelFileIS;
	private Row headerRow;
	private Row testDataRow;
	
	public Object[][] loadTestData(String testCaseID, String testDataFilePath)
	{
		ArrayList<Hashtable<String, String>> hashTableList = new ArrayList<Hashtable<String, String>>();
		Object[][] objDataProvider = null;

		int headerRowCount = 0, lastRowNumber = 1;
		String bufferCell = "";
		// Logic to read the Excel workBook
		try 
		{
			excelFileIS = new FileInputStream(testDataFilePath);
			workbook = new XSSFWorkbook(excelFileIS);
			sheet = workbook.getSheetAt(0);
			headerRow = sheet.getRow(0);
			testDataRow = sheet.getRow(1);
			lastRowNumber = sheet.getLastRowNum();
			int rowIndex = 0;

			while( rowIndex <= lastRowNumber)
			{
				String cellData = this.getCellValue(sheet.getRow(rowIndex), 0);
				if(cellData.equalsIgnoreCase(testCaseID))
				{
					headerRowCount = rowIndex - 1;
					bufferCell = this.getCellValue(sheet.getRow(rowIndex), 0);
					while(rowIndex <= lastRowNumber && !bufferCell.equalsIgnoreCase("TestCaseId"))
					{
						if(bufferCell.equalsIgnoreCase(testCaseID))
						{
							headerRow = sheet.getRow(headerRowCount);
							testDataRow = sheet.getRow(rowIndex);
							Hashtable<String , String> dataValueSet = new Hashtable<String, String>();
							int clmNo = 0;
							//iterating over cells
							do
							{
								String header = "", testData = "";
								// Key Data
								header = this.getCellValue(headerRow, clmNo);
								// Value
								testData = this.getCellValue(testDataRow, clmNo);

								if(!header.equals(""))
									dataValueSet.put(header, testData);
								clmNo++;

							}while(clmNo < headerRow.getLastCellNum());
							//put the hash-table in list
							hashTableList.add(dataValueSet);
							dataValueSet = null;
							clmNo = 0;
						}
						rowIndex++;
						if(rowIndex > lastRowNumber)
							bufferCell = "";
						else
							bufferCell = this.getCellValue(sheet.getRow(rowIndex), 0);
					};
					break;
				}
				rowIndex++;     
			}

			objDataProvider = new Object[hashTableList.size()][2];
			int rowCount = 0;
			for( Hashtable<String, String>hashTable : hashTableList) 
			{
				objDataProvider[rowCount][0] = "Run" + (rowCount + 1);
				objDataProvider[rowCount][1] = hashTable;
				rowCount++;
			}
			excelFileIS.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			hashTableList = null;
		}
		return objDataProvider;
	}
	
	public boolean updateTestDataSheet(String testDataFilePath, String testCaseID, String runID,Hashtable<String, String> testDataToUpdate)
	{
		int headerRowCount = 0,lastRowNumber = 1;
		String bufferCell = "";
		// Logic to Write in the Excel workBook
		try 
		{
			excelFileIS = new FileInputStream(testDataFilePath);
			workbook = new XSSFWorkbook(excelFileIS);
			sheet = workbook.getSheetAt(0);
			headerRow = sheet.getRow(0);
			testDataRow = sheet.getRow(1);
			lastRowNumber = sheet.getLastRowNum();
			int rowIndex = 0;
			int runCounter = 1;

			while( rowIndex <= lastRowNumber)
			{
				String cellData = this.getCellValue(sheet.getRow(rowIndex), 0);
				if(cellData.equalsIgnoreCase(testCaseID))
				{
					headerRowCount = rowIndex - 1;
					bufferCell = this.getCellValue(sheet.getRow(rowIndex), 0);
					while(rowIndex <= lastRowNumber && !bufferCell.equalsIgnoreCase("TestCaseId"))
					{
						if(bufferCell.equalsIgnoreCase(testCaseID) && runID.equalsIgnoreCase("Run" + runCounter))
						{
							headerRow = sheet.getRow(headerRowCount);
							testDataRow = sheet.getRow(rowIndex);
							int clmNo = 0;
							//iterating over cells
							do
							{
								String header = "";
								// Key Data
								header = this.getCellValue(headerRow, clmNo);
								if(testDataToUpdate.containsKey(header))
									this.putCellValue(testDataRow, clmNo, testDataToUpdate.get(header));
								clmNo++;
							}while(clmNo < headerRow.getLastCellNum());
							// Save and close file
							clmNo = 0;
							excelFileIS.close();
							FileOutputStream fileOut = new FileOutputStream(testDataFilePath);
							workbook.write(fileOut);
							fileOut.close();
							return true; 
						}
						rowIndex++;
						runCounter++;
						if(rowIndex > lastRowNumber)
							bufferCell = "";
						else
							bufferCell = this.getCellValue(sheet.getRow(rowIndex), 0);
					};
					break;
				}
				rowIndex++;     
			}
			excelFileIS.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
		return false;
	}
	
	private boolean putCellValue(Row testDataRow, int columnNumber, String testData) {
	    if (testDataRow == null) {
	        return false;
	    }

	    // Create or get existing cell safely
	    Cell testDataCell = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    if (testDataCell == null) {
	        return false;
	    }

	    testDataCell.setCellValue(testData != null ? testData : "");
	    return true;
	}
	
	private String getCellValue(Row testDataRow, int columnNumber) {
	    if (testDataRow == null) {
	        return "";
	    }

	    // Use modern MissingCellPolicy
	    Cell testDataCell = testDataRow.getCell(columnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	    if (testDataCell == null) {
	        return "";
	    }

	    // Convert based on cell type
	    switch (testDataCell.getCellType()) {
	        case STRING:
	            return testDataCell.getStringCellValue().trim();

	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(testDataCell)) {
	                // Return date as formatted string (optional)
	                return testDataCell.getDateCellValue().toString();
	            } else {
	                // Remove trailing .0 for integers
	                double numericValue = testDataCell.getNumericCellValue();
	                return (numericValue == Math.floor(numericValue))
	                        ? String.valueOf((long) numericValue)
	                        : String.valueOf(numericValue);
	            }

	        case BOOLEAN:
	            return String.valueOf(testDataCell.getBooleanCellValue());

	        case FORMULA:
	            try {
	                return testDataCell.getStringCellValue();
	            } catch (IllegalStateException e) {
	                return String.valueOf(testDataCell.getNumericCellValue());
	            }

	        default:
	            return "";
	    }
	}

}
