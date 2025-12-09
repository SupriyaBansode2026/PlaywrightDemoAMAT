package com.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CopyContentOneWorkbookToOther {

	public static void main(String[] args) throws IOException {

		File inputFile = new File("C:\\IIE_Automation_Framework\\CorePlus_TestCase_Repository.xlsx");
		FileInputStream fis = new FileInputStream(inputFile);
		XSSFWorkbook inputWorkbook = new XSSFWorkbook(fis);
		int inputSheetCount = inputWorkbook.getNumberOfSheets();
		System.out.println("Input sheetCount: " + inputSheetCount);

		for (int i = 0; i < inputSheetCount; i++) {

			XSSFSheet inputSheet = inputWorkbook.getSheetAt(i);
			String inputSheetName = inputWorkbook.getSheetName(i);
			if (!("cover".equalsIgnoreCase(inputSheetName) || "Scenarios".equalsIgnoreCase(inputSheetName))) {
				File outputFile = new File("C:\\IIE_Automation_Framework\\New folder\\resultsheet\\" + inputSheetName + ".xlsx");
				FileOutputStream fos = new FileOutputStream(outputFile);

				XSSFWorkbook outputWorkbook = new XSSFWorkbook();
				XSSFSheet outputSheet = outputWorkbook.createSheet(inputSheetName);

				copySheet(inputSheet, outputSheet);
				outputWorkbook.write(fos);

				fos.close();
				
				

			}

		}

	}
	
	private static void createHeaderRow(XSSFSheet outputSheet) {
		//Create header row as required
		int newRowCount = 0;
		Row headerRow = outputSheet.createRow(newRowCount);
		Cell cell0 = headerRow.createCell(0);
		cell0.setCellValue("Test Case Identifier");
		
		Cell cell1 = headerRow.createCell(1);
		cell1.setCellValue("Test Case ID");
		
		Cell cell2 = headerRow.createCell(2);
		cell2.setCellValue("Description");
		
		Cell cell3 = headerRow.createCell(3);
		cell3.setCellValue("Step #");
		
		Cell cell4 = headerRow.createCell(4);
		cell4.setCellValue("Test Condition");
		
		Cell cell5 = headerRow.createCell(5);
		cell5.setCellValue("Input Data");
		
		Cell cell6 = headerRow.createCell(6);
		cell6.setCellValue("Expected Results");
		Cell cell7 = headerRow.createCell(7);
		cell7.setCellValue("Actual Results");
		Cell cell8 = headerRow.createCell(8);
		cell8.setCellValue("Status");
	}
	
	private static void copySheet(XSSFSheet inputSheet, XSSFSheet outputSheet) {
	    int rowCount = inputSheet.getPhysicalNumberOfRows();
	    System.out.println(rowCount + " rows in input sheet: " + inputSheet.getSheetName());

	    if (rowCount == 0) {
	        System.out.println("No rows found. Skipping sheet copy.");
	        return;
	    }

	    // Create header row in output
	    createHeaderRow(outputSheet);
	    int currentRowIndex = 1;
	    int testCaseCount = 0;
	    String testCaseId = null;
	    String description = null;

	    for (Row inputRow : inputSheet) {
	        boolean isEmptyRow = true;
	        boolean isHeaderRow = false;

	        // Used when writing data
	        Row outputRow = null;
	        int outputCellIndex = 0;
	        int cellIndex = 0;

	        for (Cell cell : inputRow) {
	            String cellData = cell.toString().trim();
	            if (!cellData.isEmpty()) {
	                isEmptyRow = false;
	            }

	            // Identify and skip header rows
	            if (cellData.matches("(?i)Scenarios|Step #|Test Condition|Input Data|Expected Results|Status")) {
	                isHeaderRow = true;
	            }

	            // Detect and store test case info
	            if ("Test Case ID".equalsIgnoreCase(cellData)) {
	                testCaseId = getCellValue(inputRow, cellIndex + 1);
	                testCaseCount++;
	                break; // Move to next row
	            }

	            if ("Description".equalsIgnoreCase(cellData)) {
	                description = getCellValue(inputRow, cellIndex + 1);
	                break;
	            }

	            // Copy data to output only if we already have case metadata
	            if (!isHeaderRow && StringUtils.isNotBlank(testCaseId) && StringUtils.isNotBlank(description)) {
	                if (outputRow == null) {
	                    outputRow = outputSheet.createRow(currentRowIndex++);
	                    int col = 0;
	                    outputRow.createCell(col++).setCellValue(testCaseCount);
	                    outputRow.createCell(col++).setCellValue(testCaseId);
	                    outputRow.createCell(col++).setCellValue(description);
	                    outputCellIndex = col;
	                }

	                Cell outputCell = outputRow.createCell(outputCellIndex++);
	                int colIndex = outputCell.getColumnIndex();

	                if (colIndex == 8) {
	                    outputCell.setCellValue("TODO");
	                } else {
	                    outputCell.setCellValue(cellData);
	                }
	            }

	            cellIndex++;
	        }

	        // Skip truly empty or header rows
	        if (isEmptyRow || isHeaderRow) {
	            continue;
	        }
	    }

	    System.out.println((currentRowIndex - 1) + " rows added to output sheet: " + outputSheet.getSheetName());
	}

	/*public static void copySheet(XSSFSheet inputSheet, XSSFSheet outputSheet) {
		int rowCount = inputSheet.getPhysicalNumberOfRows();
		System.out.println(rowCount + " rows in inputsheet " + inputSheet.getSheetName());
		boolean descriptionFlag = false;
		boolean removeRowflag = false;

		int currentRowIndex = 0;
		if (rowCount > 0) {

			Iterator<Row> rowIterator = inputSheet.iterator();
			while (rowIterator.hasNext()) {
				int currentCellIndex = 0;
				Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
				while (cellIterator.hasNext()) {
					String cellData = cellIterator.next().toString();
					if (descriptionFlag == true && !"".equals(cellData)) {
						int row = currentRowIndex - 1;
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue("");
						outputSheet.getRow(row).createCell(3).setCellValue(cellData);
						descriptionFlag = false;
						removeRowflag=true;
						
					}
					
					if ((cellData.equals("Test Case ID"))) {

						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue("");
						removeRowflag=false;

					}
					if (cellData.equals("Description")) {
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue("");
						descriptionFlag = true;
						removeRowflag=false;
					}
					if (currentCellIndex == 0) {
						outputSheet.createRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
						removeRowflag=false;
					}
					
					
					else if (cellData.equalsIgnoreCase("Step #")) {
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
						currentCellIndex++;
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue("Test Case ID");
						currentCellIndex++;
						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue("Description");
						removeRowflag=false;

					} else if (!(cellData.equals("Test Case ID") || (cellData.equals("Description")))&& (removeRowflag==false)) {

						outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
						

					}

					currentCellIndex++;
				}
				currentRowIndex++;
			}
			System.out.println((currentRowIndex - 1) + " rows added to outputsheet " + outputSheet.getSheetName());
			System.out.println();
		}
	}*/
	private static String getCellValue(Row row, int index) {
	    Cell cell = row.getCell(index);
	    return (cell != null) ? cell.toString().trim() : "";
	}

}
