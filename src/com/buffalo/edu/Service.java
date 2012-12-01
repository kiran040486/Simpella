package com.buffalo.edu;

import java.io.File;

import com.buffalo.edu.util.FileOperations;

public class Service {
	
	public String getInfo(String input){
		
		return null;
	}
	
	public String shareFolder(String input){
		
		String response = null;
		if(input.contains("-i")){
			response = "Sharing /s" + ServerConnections.sharedFolder;
			return response;
		}
		String[] tokens = input.split("\\s");
		if(tokens.length >1)
		{
			ServerConnections.sharedFolder = tokens[1];
			return "";
		}
		
		
		return null;
	}
	
	public String scanFolder(String input)
	{
	 String response = null;
	 File newFile = new File(ServerConnections.sharedFolder);
	 if(newFile != null)
	 {
		 ServerConnections.noOfFiles = newFile.listFiles().length;
		 File [] filesList = newFile.listFiles();
		 for(int i =0; i < ServerConnections.noOfFiles ; i++){
			 ServerConnections.sizeOfSharedFolder +=filesList[i].length();
		 }
		 FileOperations fileOP = new FileOperations();
			
			fileOP.indexFiles();
	 }
	 response = "Scanned "+ServerConnections.noOfFiles +" files and " + ServerConnections.sizeOfSharedFolder+" Bytes";
	 return response;
	}
	
	public String connect(String input)
	{
		String response = null;
		if(ServerConnections.outgoingConnectionCounter < 4)
		{
		
		String[] tokens = input.split(":");
		String ipAddress = tokens[0];
		int port = Integer.parseInt(tokens[1]);
		
		Client newClient = new Client();
		
		response = newClient.connect(ipAddress, port);
		
		return response;
		}
		else{
			response = "reached limit for no of outgoing connections";
			return response;
			
		}
	}

	public String doUpdate() {
		// TODO Auto-generated method stub
		Byte[] message = new Byte[24];
		String mess = message.toString();
		 
		for(int i=0 ; i< ServerConnections.activeConnection.size() ; i++){
			
			Connection newconn = ServerConnections.activeConnection.get(i);
			ConnectionHandler handle = newconn.getHandler();
			handle.send(mess);
		}
		
		return null;
	}
	
	

}
