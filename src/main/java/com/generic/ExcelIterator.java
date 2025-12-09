package com.generic; 
import java.io.File;
import java.io.FileInputStream;

//import org.apache.poi.hssf.usermodel.examples.CellTypes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.CellType;

//import com.teamcenter.soa.client.model.strong.Sheet;
/**
 * @ScriptName    : ExcelIterator
 * @Description   : This class is used to iterate excel file data
 * @Author        : Harshvardhan Yadav (SQS)
 */
public class ExcelIterator 
{
	private String flFile;
	private int intSheetNumber;
	private int intIndex = 0;
	private int intNoOfRow;
	private int intNoOfColumn;
	private XSSFSheet sheet;
	private XSSFWorkbook workbook;
	private FileInputStream excelFileIS;
	

	public ExcelIterator(String strExcelFile, int intSheetno, boolean ignoreHeaderRow)
	{
		flFile = strExcelFile;
		intSheetNumber = intSheetno;
		
		try
		{
			if(!strExcelFile.contains("\\"))
				flFile = System.getProperty("user.home") + "\\downloads\\" + strExcelFile;
			excelFileIS = new FileInputStream(new File(flFile));
			//workbook = new XSSFWorkbook(new File(flFile));
			workbook = new XSSFWorkbook(excelFileIS);
			excelFileIS.close();
			if(intSheetno >= 0) //Ranjeeta
			{
				sheet = workbook.getSheetAt(intSheetNumber);
				intNoOfRow = sheet.getPhysicalNumberOfRows();
				if (ignoreHeaderRow)
					intIndex++;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		} 
	}
	
	//Ranjeeta
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

	public boolean isDone() 
	{
		if(intIndex < intNoOfRow) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	public String getColumn(int clmNo) {
        String cellValue = "";
        try {
            Row row = sheet.getRow(intIndex);
            if (row == null) {
                return "";
            }

            intNoOfColumn = row.getPhysicalNumberOfCells();

            Cell cell = row.getCell(clmNo, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                cellValue = getCellValueAsString(cell);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellValue;
    }

    /** ✅ Get cell value by column header name */
    public String getColumn(String columnHeader) {
        if (sheet == null) return "";

        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(intIndex);
        if (headerRow == null || dataRow == null) return "";

        String cellValue = "";
        try {
            int lastCell = headerRow.getLastCellNum();
            for (int i = 0; i < lastCell; i++) {
                Cell headerCell = headerRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                String headerName = (headerCell != null) ? headerCell.toString().trim() : "";

                if (headerName.equalsIgnoreCase(columnHeader)) {
                    Cell dataCell = dataRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (dataCell != null) {
                        cellValue = getCellValueAsString(dataCell);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cellValue;
    }

    /** ✅ Helper to convert any POI cell to String safely */
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double value = cell.getNumericCellValue();
                    return (value == Math.floor(value)) ? String.valueOf((long) value) : String.valueOf(value);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }

	public void next()
	{
		intIndex++;
	}

	public int getCurrentRowNumber()
	{
		return intIndex;
	}

	public int getNumberOfRows()
	{
		return intNoOfRow; 
	}

	public int getNumberOfColumnForCurrentRow()
	{
		return intNoOfColumn;
	}
	
	public String getCellValue(int iCol)
	{
		DataFormatter objDataFormatter = new DataFormatter();
		org.apache.poi.ss.usermodel.Row testDataRow = sheet.getRow(intIndex);
		
		String cellValue = "";
		try
		{
			org.apache.poi.ss.usermodel.Cell testDataCell = testDataRow.getCell(iCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			if (testDataCell == null)
				cellValue = "";
			else 
				cellValue = objDataFormatter.formatCellValue(testDataCell);
				//cellValue = testDataCell.toString().trim();
				
		}
		catch(Exception exception)
		{
		}
		return cellValue;
	}
	
	public int getColumnNo(String sColumnHeader)
	{
		org.apache.poi.ss.usermodel.Row headerRow = sheet.getRow(0);
		int iCol = 0;
		try
		{
			do
			{
				String currentHeader = "";
				org.apache.poi.ss.usermodel.Cell headerCell = headerRow.getCell(iCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (headerCell == null)
					currentHeader = "";
				else 
					currentHeader = headerCell.toString().trim();
				System.out.println(currentHeader);
				if(currentHeader.equalsIgnoreCase(sColumnHeader))
					return iCol;
				iCol++;
			}while(iCol < headerRow.getLastCellNum());
		}
		catch(Exception ex)
		{
			
		}
		return -1;
	}
	
	public boolean checkValueInExcel(String sColumnName, String sCellValue)
	{
		boolean bFound = false;
		int iCol = this.getColumnNo(sColumnName);
		do
		{
			String sCellVal = this.getCellValue(iCol);
			System.out.println(sCellVal);
			if(sCellVal.toLowerCase().contains(sCellValue.toLowerCase()))
			{
				bFound = true;
				break;
			}
			this.next();
		}while(intIndex < this.getNumberOfRows());
		return bFound;
	}
	
	public String getSheetNames()
	{
		String Sheets = "";
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) 
		{

			if(i == 0)
			{
				Sheets = workbook.getSheetName(i).trim();
			}
			else
			{
				Sheets = Sheets+"~"+workbook.getSheetName(i).trim();
			}
		}
		return Sheets;
	}
	
	//sz10072018
	public Boolean VerifyFooterOfExcel(String sFooter)
	{
		org.apache.poi.ss.usermodel.Row headerRow = sheet.getRow(intNoOfRow-1);
		int iCol = 0;
		try
		{
			String currentHeader = "";
			org.apache.poi.ss.usermodel.Cell headerCell = headerRow.getCell(iCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (headerCell == null)
				currentHeader = "";
			else 
				currentHeader = headerCell.toString().trim();
			System.out.println(currentHeader);
			if(currentHeader.equalsIgnoreCase(sFooter))
				return true;
			else
				return false;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return false;
	}
	
	//Rupali
	public String getCelllongValue(int icol )
	
	{
		String data;
		//if(sheet.getRow(irow).getCell(icol).getCellType()==XSSFCell.CELL_TYPE_NUMERIC()) {
			double d =sheet.getRow(intIndex).getCell(icol).getNumericCellValue();
			 System.out.println(d);
			long i = (long)d;
		 data =String.valueOf(i);
		
		 return data;
		
		}
		
		
		//Rupali
	public boolean checkValueInExcellong(String sColumnName, String sCellValue)
	{
		boolean bFound = false;
		int iCol = this.getColumnNo(sColumnName);
		//int iRow = sheet.getRow(Rownum);
		do
		{
			//int iRow;
			String sCellVal =  this.getCelllongValue(iCol);
			System.out.println(sCellVal);
			if(sCellVal.toLowerCase().contains(sCellValue.toLowerCase()))
			{
				bFound = true;
				break;
			}
			this.next();
		}while(intIndex < this.getNumberOfRows());
		return bFound;
	}
	public boolean checkValueInExcelInSortedOrder(String sColumnName)
	{
		boolean bFound = false;
		int iCol = this.getColumnNo(sColumnName);
		String sItems="";
		do
		{
			String sCellVal = this.getCellValue(iCol);
			System.out.println(sCellVal);
			if(intIndex!=0){
			if(sItems=="")
				sItems = sCellVal;
			else
				sItems = sItems + "~" + sCellVal;
			}
			this.next();
		}while(intIndex < this.getNumberOfRows());
		
		String []asItems=sItems.split("~");
		for(int i=0;i<asItems.length-1;++i)
		{
			if(asItems[i].compareTo(asItems[i+1])>0)
			{
				bFound = false;
				break;
			}
			else
				bFound = true;
		}
		return bFound;
	}
	
	//Richa_08022023 - verify duplicate rows in excel
	public boolean verifyDuplicateRowsInPCRNo()
	{
		DataFormatter objDataFormatter = new DataFormatter();
//		org.apache.poi.ss.usermodel.Row testDataRow = sheet.getRow(intIndex);
		int iPCRNoCol = this.getColumnNo("PCR #");
		boolean bDuplicate = false;
		try
		{
			for(int iRow = 0; iRow < sheet.getLastRowNum(); iRow++)
			{
				org.apache.poi.ss.usermodel.Row objRow = sheet.getRow(iRow);
				int iNextRow = iRow + 1;
				org.apache.poi.ss.usermodel.Row objNextRow = sheet.getRow(iNextRow);
				
				if(objRow.getCell(iPCRNoCol).getStringCellValue().equalsIgnoreCase(objNextRow.getCell(iPCRNoCol).getStringCellValue()))
				{
					bDuplicate = true;
					for(int iCol = 2; iCol < objRow.getLastCellNum(); iCol++)
					{
						if(objRow.getCell(iCol).getCellType() == CellType.NUMERIC)
						{
							objRow.getCell(iCol).setCellType(CellType.STRING);
							objNextRow.getCell(iCol).setCellType(CellType.STRING);
						}
						System.out.println(objRow.getCell(iCol).getStringCellValue());
						System.out.println(objNextRow.getCell(iCol).getStringCellValue());
						if(!objRow.getCell(iCol).getStringCellValue().equalsIgnoreCase(objNextRow.getCell(iCol).getStringCellValue()))
						{
							bDuplicate = false;
							break;
						}
						
					}
					if(bDuplicate)
						break;
				}
			}
		}
		catch(Exception exception)
		{
			System.out.println(exception);
		}
		return bDuplicate;
	}
	
	//Richa_19042023 - Verify cell type in excel
	public boolean verifyCellTypeInExcel(String sColumnName, String sCellType)
	{
		boolean bRes = false;
		int iCol = this.getColumnNo(sColumnName);
		do
		{
			if(this.getCellType(iCol).toLowerCase().equalsIgnoreCase(sCellType))
				bRes = true;
			else
				return false;
			this.next();
		}while(intIndex < this.getNumberOfRows());
		return bRes;
	}
	
	//Richa_19042023 - Get cell type
	

	public String getCellType(int iCol) {
	    intIndex++;
	    Row testDataRow = sheet.getRow(intIndex);
	    String sCellType = "";

	    try {
	        if (testDataRow == null) return "blank";

	        Cell testDataCell = testDataRow.getCell(iCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	        if (testDataCell == null) return "blank";

	        // ✅ Handle Date
	        if (DateUtil.isCellDateFormatted(testDataCell)) {
	            return "date";
	        }

	        // ✅ Modern switch using CellType enum
	        switch (testDataCell.getCellType()) {
	            case STRING:
	                sCellType = "string";
	                break;
	            case BOOLEAN:
	                sCellType = "boolean";
	                break;
	            case NUMERIC:
	                sCellType = "numeric";
	                break;
	            case FORMULA:
	                sCellType = "formula";
	                break;
	            case BLANK:
	                sCellType = "blank";
	                break;
	            default:
	                sCellType = "unknown";
	        }

	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }

	    return sCellType;
	}
}
