package com.generic;

public class ExceptionHandler {

	String failureCategory = null;
	
	public String analyzeFailureReason(Exception exception, String bPageLoadValueAndFlag, String... args)
	
	{
		String exceptioName = exception.getClass().getSimpleName().toString();
		
		if(bPageLoadValueAndFlag!=null)
		{
			failureCategory = bPageLoadValueAndFlag;
			return failureCategory;
		}
		
		
		if(args.length>=1)
			if(args[0]=="true")
				for(String str : args)
				{
					checkforNullParameter(str);
					if(failureCategory=="TEST DATA")
						return failureCategory;
				}
			
		System.out.println("Exception Name: "+exceptioName);
		switch(exceptioName)
		{
				case "TimeoutException": 
							failureCategory="SYNC";
							break;
		
			case "UnhandledAlertException": 
							failureCategory="DEFECT";
							break;
				
			case "NullPointerException": 
							failureCategory="TEST DATA";
							break;
							
			case "ElementNotVisibleException": 
							failureCategory="DEFECT";
							break;
							
			case "NoSuchElementException": 
							failureCategory="XPATH CHANGE";
							break;
							
			case "NoSuchWindowException": 
							failureCategory="DEFECT";
							break;
				
			case "NoSuchFrameException": 
							failureCategory="DEFECT";
							break;
				
			case "NoAlertPresentException": 
							failureCategory="DEFECT";
							break;
				
			case "InvalidSelectorException": 
							failureCategory="XPATH CHANGE";
							break;
				
			case "ElementNotSelectableException": 
							failureCategory="ROOT CAUSE Pending";
							break;
				
			case "StaleElementReferenceException": 
							failureCategory="ROOT CAUSE Pending";
							break;
				
			case "ElementNotInteractableException": 
							failureCategory="ROOT CAUSE Pending";
							break;
							
			case "NoSuchAttributeException": 
							failureCategory="XPATH CHANGE - ATTRIBUTE MISSING";
							break;
				
			case "WebDriverException": 
							failureCategory="ROOT CAUSE Pending";
							break;
			
			case "UnexpectedTagNameException": 
							failureCategory="XPATH CHANGE";
							break;
							
			default :
							failureCategory="UNKOWN FAILURE TYPE";
							break;
		}
		
		
		return failureCategory;
	}

	
	public String checkforNullParameter(String parameterValue)
	{
		if(parameterValue=="" || parameterValue==null)
		{
			failureCategory = "TEST DATA";
			return failureCategory;
			
		}
		
		return "";
	}
	
}
