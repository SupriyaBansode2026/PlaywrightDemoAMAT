package com.generic;

import java.net.ServerSocket;

public class AvailabelPorts {

	public String getPort()  
	{
		String port = "";
		try 
		{
			ServerSocket socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			port = Integer.toString(socket.getLocalPort()); 
			socket.close();
		} catch ( Exception exception) {
			exception.printStackTrace();
		}
		return port;
	}
}
