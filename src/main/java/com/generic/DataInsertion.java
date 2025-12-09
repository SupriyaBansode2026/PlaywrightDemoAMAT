package com.generic;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInsertion {

	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
	   //static final String DB_URL = "jdbc:sqlserver://in-dev-21/tcsqldev1:1433;databaseName=test";
	   //static final String DB_URL = "jdbc:sqlserver://152.135.122.232:1433;databaseName=test";
	   static final String DB_URL = "jdbc:sqlserver://10.208.119.168:59311;databaseName=test";
	   

	   //  Database credentials
	   static final String USER = "uftpoc";
	   static final String PASS = "Welcome123";
	   
	   
	   public static void main(String[] args) throws ConnectException, SQLException {
	   Connection conn = null;
	   Statement stmt = null;
	   //STEP 2: Register JDBC driver
	  try {
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		  Class.forName(JDBC_DRIVER);
		  System.out.println("Connecting to a selected database...");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	  //STEP 3: Open a connection
	  
	  try {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");
	      
	      //STEP 4: Execute a query
	      
	} catch (SQLException e)
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("Inserting records into the table...");
	      stmt = conn.createStatement();
	      
	      //Delete table entries
	      /*String sSql = "Delete from SQS_CorePlus_Automation where Sr_No = '1960'";
	      stmt.execute(sSql);*/
	      
	      //Alter table
	      /*String sSql = "ALTER TABLE SQS_CorePlus_Automation DROP column [Group]";
	      stmt.execute(sSql);*/
	      
	      //Rename table column name
	      /*String sSql = "sp_rename 'SQS_CorePlus_Automation.Sr No', 'Sr_No', 'COLUMN'";
	      stmt.execute(sSql);*/
	      
	      //set Sr_No column auto incremented
	      //String sSql = "Alter table dbo.SQS_CorePlus_Automation Add Sr_No_New INT IDENTITY";
//	      String sSql = "Alter table dbo.SQS_CorePlus_Automation Drop Column Sr_No";
//	      String sSql = "sp_rename 'SQS_CorePlus_Automation.Sr_No_New', 'Sr_No', 'COLUMN'";
	      //stmt.execute(sSql);
	      
	    //Alter table to reset auto increment
	      /*String sSql = "DBCC CHECKIDENT (SQS_CorePlus_Automation, RESEED, 0)";
	      stmt.execute(Sql);*/
	      
	    //Alter table to change data type of column
	      /*String sSql = "Alter table SQS_CorePlus_Automation Alter column ObjectID VARCHAR(100)";
	      stmt.execute(sSql); */
	      
	      //drop table
	     /* String sSql = "Drop table SQS_CorePlus_Automation";
	      stmt.execute(sSql); */
	      
	      //Alter table to add column with default value
	      /*String sSql = "Alter table SQS_CorePlus_Automation Add ScreenShot_Path varchar(1000) NOT NULL constraint D_SQS_CorePlus_Automation_ScreenShot_Path Default ('N/A')";
	      stmt.execute(sSql);*/
	      
	      
	      
	      /*ExcelIterator objExcelIterator = new ExcelIterator("C:\\Mainline_SeleniumCorePlus\\target\\custom-reports\\BatchDetails.xlsx", 0, true);
	      int iRows = objExcelIterator.getNumberOfRows();
	      int iColEITRelease = objExcelIterator.getColumnNo("EIT Release");
	      int iColFunctionality = objExcelIterator.getColumnNo("Functionality");
	      int iColPriority = objExcelIterator.getColumnNo("Priority");
	      int iColEnvironment = objExcelIterator.getColumnNo("Environment");
	      int iColSite = objExcelIterator.getColumnNo("Site");
	      int iColBusinessGroup = objExcelIterator.getColumnNo("Business_Group");
	      int iColTestCase = objExcelIterator.getColumnNo("Test_Case");
	      int iColTesterName = objExcelIterator.getColumnNo("Tester_Name");
	      int iColMachineName = objExcelIterator.getColumnNo("Machine_Name");
	      int iColBatchIteration = objExcelIterator.getColumnNo("Batch_Iteration");
	      int iColStartTime = objExcelIterator.getColumnNo("Start_Time");
	      int iColEndTime = objExcelIterator.getColumnNo("End_Time");
	      int iColDuration = objExcelIterator.getColumnNo("Duration_In_Minutes");
	      int iColResult = objExcelIterator.getColumnNo("Result");
	      int iColComments = objExcelIterator.getColumnNo("Comments");
	      //int iColLogs = objExcelIterator.getColumnNo("Logs");
	      //int iColDeliverables = objExcelIterator.getColumnNo("Deliverables");
	      int iColFailureType = objExcelIterator.getColumnNo("FailureType");
	      int iColItemID = objExcelIterator.getColumnNo("ObjectID");
	      int iColFunctionName = objExcelIterator.getColumnNo("FunctionName");
	      int iColFailure = objExcelIterator.getColumnNo("Failure");
	      
	      
	      for(int iRow = 1; iRow <= iRows; iRow++)
	      {
	    	  String sQuery = "INSERT INTO SQS_CorePlus_Automation (Test_Case)" +
	                   "VALUES ('"+objExcelIterator.getCellValue(iColEITRelease)+"',"+
	    			  "'"+objExcelIterator.getCellValue(iColFunctionality)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColPriority)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColEnvironment)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColSite)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColBusinessGroup)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColTestCase)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColTesterName)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColMachineName)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColBatchIteration)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColStartTime)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColEndTime)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColDuration)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColResult)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColComments)+"', "+
	    			  //"'"+objExcelIterator.getCellValue(iColLogs)+"', "+
	    			  //"'"+objExcelIterator.getCellValue(iColDeliverables)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColFailureType)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColItemID)+"', "+
	    			  "'"+objExcelIterator.getCellValue(iColFunctionName)+"', "+
	                   "'"+objExcelIterator.getCellValue(iColFailure)+"')";
	    	  
	    	  stmt.executeUpdate(sQuery);
	    	  objExcelIterator.next();
	      }*/
	      //Print table names in database
	      /*DatabaseMetaData meta = conn.getMetaData();
	      ResultSet rs = meta.getTables(null, null, null, new String[] {"TABLE"});
	      while(rs.next())
	      {
	    	  System.out.println("   "+rs.getString("TABLE_NAME"));
	      }*/
	      
	      
	      
//		      String sql = "INSERT INTO CoreDemo " +
//		                   "VALUES (100, 'Richa', 'Ali')";
//		      stmt.executeUpdate(sql);
//		      sql = "INSERT INTO CoreDemo " +
//		                   "VALUES (101, 'Vijay', 'Fatma')";
//		      stmt.executeUpdate(sql);
//		      sql = "INSERT INTO CoreDemo " +
//		                   "VALUES (102, 'Zaid', 'Khan')";
//		      stmt.executeUpdate(sql);
//		      sql = "INSERT INTO CoreDemo " +
//		                   "VALUES(103, 'Sumit', 'Mittal')";
//		      stmt.executeUpdate(sql);
	      //System.out.println("Inserted records into the table...");

	    //get column names
	      /*String sSql = "Select column_name from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME like 'SQS_CorePlus_Automation'";//column_name
	      ResultSet rs1 = stmt.executeQuery(sSql);
	      ResultSetMetaData rsmd = rs1.getMetaData();
	      while(rs1.next()) 
	      {
	    	  System.out.print("|");
	    	  for(int iCol = 1; iCol <= rsmd.getColumnCount(); iCol++)
	    	  {
	    		  System.out.print(rs1.getString(iCol));
	    	  }
	      }*/
	      
	      //Check data got inserted in table CoreDemo
	      String sql1 = "Select * from SQS_CorePlus_Automation where Test_Case='NSR_Core_30_1' and Deliverables='N/A'";
	      ResultSet rs2 = stmt.executeQuery(sql1);
	      ResultSetMetaData rsmd2 = rs2.getMetaData();
	      while(rs2.next()) 
	      {
	    	  System.out.println("|");
	    	  for(int iCol = 1; iCol <= rsmd2.getColumnCount(); iCol++)
	    	  {
	    		  System.out.print(rs2.getString(iCol) + "|");
	    	  }
	      }
	      
	      //get column names along with its data types
	      /*String sSql = "select DATA_TYPE from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'SQS_CorePlus_Automation' and COLUMN_NAME = 'StartTime'";
	      ResultSet rs1 = stmt.executeQuery(sSql);
	      System.out.println(rs1.getString(0));*/
	      
	   System.out.println("Goodbye!");
	   
	   
	}//end main
	
}
