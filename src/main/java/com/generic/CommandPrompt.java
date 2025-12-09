package com.generic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandPrompt {

	// Local Variables
	Process process;
	ProcessBuilder builder;

	/**
	 * This method run command on windows and mac
	 * @param 		  : command to run  
	 * @Author        : harshvardhan yadav (SQS)
	 */
	public String runCommand(String command)  
	{
		String allLine="";
		try
		{
			String os = System.getProperty("os.name");
			//System.out.println(os);

			// build cmd proccess according to os
			if(os.contains("Windows")) // if windows
			{
				builder = new ProcessBuilder("cmd.exe","/c", command);
				builder.redirectErrorStream(true);
				Thread.sleep(1000);
				process = builder.start();
			}
			else // If Mac
				process = Runtime.getRuntime().exec(command);

			// get std output
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while((line = buffReader.readLine()) != null)
			{
				allLine = allLine + "" + line + "\n";
				if(line.contains("Console LogLevel: debug"))
					break;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return allLine;
	}

	public static void main(String[] args) throws Exception {
		CommandPrompt cmd = new CommandPrompt();
		cmd.runCommand("dir");
	}
}
