package com.buffalo.edu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import com.buffalo.edu.util.Message;



public class Server implements Runnable
{
	private ServerSocket socket;
	public Server(int port)throws IOException{
		this.socket = ServerSocketFactory.getDefault().createServerSocket(port);
		ServerConnections.socket = this.socket;
		
	}
	

	@Override
	public void run() {
		while(true){
			Socket client = null;
			try{
				client = socket.accept();
				ObjectInputStream is = new ObjectInputStream(client.getInputStream());
				
				Message message = (Message) is.readObject();
								
				System.out.println(message.getPayLoad().toString());
				if(ServerConnections.incomingConnectionCounter < 4){
					
					System.out.println("inside if loop for incoming");
					if(message.getPayLoad().contains("connect")){
					System.out.println("inside if loop : connect");
					Runnable connectionHandler = new ConnectionHandler(client);
					new Thread(connectionHandler).start(); 
					Message mess = new Message();
					ObjectOutputStream outstr = new ObjectOutputStream(client.getOutputStream());
					message.setPayLoad("SIMPELLA/0.6 200 OK \r\n");
					outstr.writeObject(mess);
					outstr.flush();
					}
					
					
					
					
				}
				
				else{
					Message mess = new Message();
					message.setPayLoad("SIMPELLA/0.6 503 Max connections reached \r\n");
					ObjectOutputStream outstr = new ObjectOutputStream(client.getOutputStream());
					outstr.writeObject(mess);
					outstr.flush();
					
					// have to send the message to client saying max conns have been reached.
				}
				//will keep listening for anything sent to it
				
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
