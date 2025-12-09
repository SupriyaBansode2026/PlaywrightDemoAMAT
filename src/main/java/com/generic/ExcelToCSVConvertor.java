package com.generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToCSVConvertor {

	public void convertExcelToCSV(Sheet sheet, String sheetName) {
		int count = 0;

		StringBuilder data = new StringBuilder();
		try {
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				count = 0;
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext() && count <= 8) {
					Cell cell = cellIterator.next();
					String cellData = cell.toString();
					if (cellData.contains(".0")) {
						cellData = cellData.replace(".0", "");
					}
					/*if (cellData.contains("\n")) {
						cellData = cellData.trim();
						
						cellData = cellData.replace("\"", "\"\"");
						cellData = "\"" + cellData + "\"";

					}
					if(cellData.contains(",")) {
						cellData=cellData.replace(",", "||");
					}

					else {
						System.out.println("Not detect");
					}*/
					
					cellData = StringEscapeUtils.escapeCsv(cellData.trim());
					data.append(cellData + "" + ",");

					count++;
				}
				System.out.print(data.toString());
				data.append("\n");
			}

			Files.write(Paths.get("C:\\AMAT_Automation\\New folder\\resultsheet\\" + sheetName + ".csv"),
					data.toString().getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExcelToCSVConvertor app = new ExcelToCSVConvertor();
		String path = "C:\\AMAT_Automation\\New folder\\resultsheet\\Create ESW Non- Oracle.xlsx";
		InputStream inp = null;

		try (InputStream inp1 = new FileInputStream(path)) {
			Workbook wb = WorkbookFactory.create(inp1);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				System.out.println(wb.getSheetAt(i).getSheetName());
				app.convertExcelToCSV(wb.getSheetAt(i), wb.getSheetAt(i).getSheetName());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
