package com.buffalo.edu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.buffalo.edu.util.Message;

/**
 * Handles socket connection so frees main thread of doing all the work thus,
 * making it multithreaded
 * 
 * @author Kiran
 * 
 */
public class ConnectionHandler implements Runnable {
	Socket socket = null;

	public ConnectionHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		// do the work with socket here
		if (socket != null) {
			try {

				while (true) {
					try {
						ObjectInputStream inpstr = new ObjectInputStream(
								socket.getInputStream());
						Message message = (Message) inpstr.readObject();
						System.out.println(">>>>>>>>>>>>>>"+ message.getPayLoad().toString());
						if(message.getPayLoad().contains("200")){
						    System.out.println("inside if 200");
							Connection connection = new Connection();
							connection.localPort = this.socket.getLocalPort();
							connection.remotePort = this.socket.getPort();
							connection.ip = this.socket.getInetAddress().getHostAddress();
							connection.hostname = this.socket.getInetAddress().getHostName();
							connection.connectionId = ServerConnections.outgoingConnectionCounter++;
							connection.setHandler(this);
							ServerConnections.activeConnection.add(connection);
							
							ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
							Message mess = new Message();
							mess.setPayLoad("Thanks for Accepting \n");
							os.writeObject(mess);
							
							
						}else if(message.getPayLoad().contains("503")){
							
							socket.close();
						}
						

						
					} catch (Exception ex) {
						break; // exit if there is an exception
					}

					// send response if seeking one
					// inpstr.close();

					// remove from active connections list
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * sends a tcp message
	 * 
	 * @param message
	 */
	public String send(String message) {
		if (socket != null) {
			try {

				ObjectOutputStream os = new ObjectOutputStream(
						socket.getOutputStream());
				System.out.println("echoing " + message);
				System.out.println("to: IP = "
						+ socket.getInetAddress().getHostAddress());
				System.out.println("type = tcp");
				os.writeObject(message);
				// os.flush();
				// os.close();
				return "message sent";
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "error sending";

		}
		return "socket error";
	}

	/**
	 * 
	 * @return
	 */
	public void disconnect() {
		if (this.socket.isConnected()) {

			try {
				this.socket.close();
				// return

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// return null;
	}

}
