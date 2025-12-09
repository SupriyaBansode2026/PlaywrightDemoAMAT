package com.generic;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DeviceConfiguration {

	public static void main(String[] args) 
	{
		DeviceConfiguration gd = new DeviceConfiguration();
		gd.startADB();	
		gd.getDivces();
		gd.stopADB();
	}

	CommandPrompt cmd = new CommandPrompt();
	Map<String, String> devices = new HashMap<String, String>();

	/**
	 * This method start adb server
	 * @Author        : Harshvardhan Yadav (SQS)
	 */
	public void startADB()
	{
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}

	/**
	 * This method stop adb server
	 * @Author        : Harshvardhan Yadav (SQS)
	 */
	public void stopADB()  
	{
		cmd.runCommand("adb kill-server");
	}

	/**
	 * This method return connected devices
	 * @return hashmap of connected devices information
	 * @Author        : Harshvardhan Yadav (SQS)
	 */
	public Map<String, String> getDivces() 
	{
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if(lines.length<=1)
		{
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0);	// exit if no connected devices found
		}

		for(int i=1;i<lines.length;i++)
		{
			lines[i]=lines[i].replaceAll("\\s+", "");

			if(lines[i].contains("device"))
			{
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				
				String getprop = cmd.runCommand("adb shell getprop");
				System.out.println("osVersion----->" + getprop);
				
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, brand);
				devices.put("model"+i, model);
				devices.put("osVersion"+i, osVersion);
			 
				String[] properties = {"device.name:" + deviceID, "device.platformName:Android", "device.platformVersion:" + osVersion};
				
				System.out.println("Following device is connected");
				System.out.println(deviceID + " " + brand + " " + model + " " + osVersion + "\n");
				File deviceConfigFile = new File(System.getProperty("user.dir") + "/src/main/resources/mobileResources/devices/android/" + brand.toUpperCase() + "_" + model.toUpperCase() +"_" + osVersion + ".properties");
				
				// Write grid config file.
				try
				{
					if(deviceConfigFile.exists())
						deviceConfigFile.delete();
					FileOutputStream out = new FileOutputStream(deviceConfigFile);
					Properties props = new Properties();
					for(String propertyValue : properties)
					{
						String[]  devicePropertyValues = propertyValue.split(":");
						props.setProperty(devicePropertyValues[0], devicePropertyValues[1]);
				 	}
					props.setProperty("device.appium.ip", "");
					props.setProperty("device.appium.port", "");
					props.store(out, "Device configuration File");
					out.close();
				}
				catch(Exception exception)
				{
					exception.printStackTrace();
				}
			}
			else if(lines[i].contains("unauthorized"))
			{
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];

				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}
			else if(lines[i].contains("offline"))
			{
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];

				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return devices;
	}
	
}
