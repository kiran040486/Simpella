package com.buffalo.edu;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of active connections
 * @author Kiran
 *
 */

public class ServerConnections {
	
	//make this synchronized
	public static List<Connection> activeConnection = new ArrayList<Connection>();
	public static int incomingConnectionCounter ; 
	public static int outgoingConnectionCounter;//Do we need to recycle this
	
	//initialized on first run
	public static int TCP_PORT = 8989;
	public static int DOWNLOADING_PORT = 0;
	public static String IP_ADDRESS = "127.0.0.1";
	public static String HOST_NAME = "sol.cse.buffalo.edu";
	public static String sharedFolder = "Shared";
	public static int noOfFiles = 0;
	public static double sizeOfSharedFolder = 0.0;
	public static ServerSocket socket = null;
	public static ArrayList<String> pingsReceived = new ArrayList<String>();
	

}
