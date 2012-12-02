/**
 * 
 */
package com.buffalo.edu;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * @author Kiran
 *
 */
public class Simpella 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(">>>>>>>"+ args);
		if(args.length == 0){
			ServerConnections.TCP_PORT = 6346;
			ServerConnections.DOWNLOADING_PORT = 5635;
		}else{
			ServerConnections.TCP_PORT = Integer.parseInt(args[0]);
			ServerConnections.DOWNLOADING_PORT = Integer.parseInt(args[1]);
		}
		
		System.out.println("Starting the server ....");
		startTcpServer(ServerConnections.TCP_PORT);
		
		System.out.println("-----------------------------------------------");
		System.out.println("----------Starting Interactive Shell-----------");
		System.out.println("-----------------------------------------------");
		startShell();
		
	}



	private static void startShell() 
	{
		Service service = new Service();
		Scanner input = new Scanner(System.in);
		String line = null;
		String response = null;
		System.out.print(">>");
		System.out.println("Please Enter the input");
		System.out.print(">>");
		while((line = input.nextLine()) != null)
		{
			
			if(line.equalsIgnoreCase("Quit")){
				System.out.println("Exiting Application . Bye!");
				close();
				break;
			}
			if(line.contains("info")){
				service.getInfo(line);
			}
			else if(line.contains("share")){
				response = service.shareFolder(line);
			}else if(line.contains("scan")){
				response = service.scanFolder(line);
			}else if (line.contains("open")){
				String[] splits = line.split("\\s");
				response  = service.connect(splits[1]);
			}
			else if(line.contains("update")){
				System.out.println("inside if : update");
				response = service.doUpdate();
			}
			else{
				System.out.println("Invalid Command");
			}
			System.out.println(response);
			System.out.print(">>");
			System.out.println("Please Enter the input");
			System.out.print(">>");
		}
		System.exit(1);
	}



	private static void close() {
		
		try {
			if(ServerConnections.socket.isClosed())
			ServerConnections.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private static void startTcpServer(int tcpPort2) 
	{
		
		try {
			StringBuffer response = new StringBuffer();
			Server server =  new Server(tcpPort2);
			Thread serverThread = new Thread(server);
			serverThread.start();
			response.append("Local IP :"+ ServerConnections.IP_ADDRESS +"\n");
			response.append("Simpella Net port:"+ServerConnections.TCP_PORT+"\n");
			response.append("Downloading port :"+ ServerConnections.DOWNLOADING_PORT+"\n");
			response.append("simpella version 0.6 (c) 2002-2003 XYZ");
			
			
			System.out.println(response.toString());
		} catch (IOException e) 
		{
			System.out.println("Error Starting the server at port "+ tcpPort2);
			e.printStackTrace();
			
		}
		
		
	}

}
