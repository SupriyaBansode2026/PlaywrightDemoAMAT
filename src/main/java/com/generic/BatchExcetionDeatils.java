package com.generic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BatchExcetionDeatils {
	
	private String TestCaseName;
	private String ExecutedBy;
	private String Status;
	private String StartTime;
	private String EndTime;
	private String MachineName;
	private String ModuleName;
	private String sDurationMin;
	private String sRunIteration;
	private String sFailureLog;
	private String sDeliverables;
	private String sLogPath;
	private String sScreenshotPath;
	private String browserVersion;
	
	public BatchExcetionDeatils(String TestName,String Excuted, String Status,String Start,String End,String Module, String Machine, String sIteration, String sFailureLog, String sDeliverables, String sLogPath, String sScreenshotPath,String browserVersion)
	{
		this.EndTime=End;
		this.ExecutedBy = Excuted;
		this.MachineName = Machine;
		this.StartTime = Start;
		this.Status = Status;
		this.TestCaseName = TestName;
		this.ModuleName = Module;
		this.sRunIteration = sIteration;
		this.sFailureLog = sFailureLog;
		this.sDeliverables = sDeliverables;
		this.sLogPath = sLogPath;
		this.sScreenshotPath = sScreenshotPath;
		this.browserVersion = browserVersion;
		System.out.println(TestName+" "+Module);
	}
	
	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getTestCaseName() {
		return TestCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		TestCaseName = testCaseName;
	}
	public String getExecutedBy() {
		return ExecutedBy;
	}
	public void setExecutedBy(String executedBy) {
		ExecutedBy = executedBy;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getModuleName() {
		return ModuleName;
	}
	public void setModuleName(String moduleName) {
		ModuleName = moduleName;
	}
	public String getMachineName() {
		return MachineName;
	}
	public void setMachineName(String machineName) {
		MachineName = machineName;
	}

	public float getTCDurationInMin()
	{
		
		try 
		{
			//RPHADKE_12112018 - Changed date format to get exact difference
			DateFormat objDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dtStart;
			float fDurationInMin = 0;
			dtStart = objDateFormat.parse(StartTime);
			Date dtEnd = objDateFormat.parse(EndTime);
			if( dtStart == null || dtEnd == null ) return 0;
			//RPHADKE_27082018 - Added condition to avoid garbage values in db in case of  not handled exception
			fDurationInMin = (float)((dtEnd.getTime()/60000) - (dtStart.getTime()/60000));
			if(fDurationInMin < 0)
				return 0;
			else
				return fDurationInMin;
			//return (float)((dtEnd.getTime()/60000) - (dtStart.getTime()/60000));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
		
	}
	
	public String getIteration()
	{
		return sRunIteration;
	}
	
	public String getFailureLog()
	{
		return sFailureLog;
	}
	
	public String getDeliverable()
	{
		return sDeliverables;
	}
	
	public String getLogPath()
	{
		return sLogPath;
	}
	
	public String getScreenshotPath()
	{
		return sScreenshotPath;
	}
	
//	public String getObjectID()
//	{
//		return sObjectID;
//	}
}
