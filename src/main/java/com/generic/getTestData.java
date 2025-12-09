package com.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class getTestData {

	public void readExcel(String filePath,String fileName,String sheetName,String sColumnHeader) { 
		String cellValue = null;
    File file = new File(filePath + "\\" + fileName);
    Workbook workbook = null;

    try (FileInputStream inputStream = new FileInputStream(file)) {
        workbook = fileName.endsWith(".xlsx") ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        int columnIndex = -1;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    if (cell.getStringCellValue().equalsIgnoreCase(sColumnHeader)) {
                        columnIndex = cell.getColumnIndex();
                        break;
                    }
                }
            } else if (columnIndex != -1) {
                Cell cell = row.getCell(columnIndex);
                if (cell != null && !cell.getStringCellValue().equalsIgnoreCase("used data")) {
                    cellValue = cell.getStringCellValue();
                    cell.setCellValue("Used Data");
                    break;
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

}


	//Main function is calling readExcel function to read data from excel file
	public static void main(String...strings)
	{
		//Create an object of ReadGuru99ExcelFile class
		getTestData objExcelFile = new getTestData();

		//Prepare the path of excel file
		String filePath = "\\\\amat.com\\Folders\\India\\GIS\\TD-TCE-COMMON\\Core+ Project Documents";

		//Call read file method of the class to read data
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");
		objExcelFile.readExcel(filePath,"SampleExcel.xlsx","Sheet1", "itemwith workflow1");

	}
	
}
