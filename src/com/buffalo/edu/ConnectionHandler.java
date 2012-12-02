package com.buffalo.edu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
						byte[] head = null;
						byte type = 90;
						if (message.getHeader() != null) {
							head = message.getHeader();
							System.out.println("hhead------" + head[16]);
							type = head[16];
							System.out.println("hex string ===="
									+ Integer.toHexString(head[16]));
						}
						System.out.println("type ===" + type);

						System.out.println(message.getPayLoad().toString()
								+ "from :"
								+ InetAddress.getLocalHost().getHostName());
						if (message.getPayLoad().contains("200")) {
							System.out.println("inside if 200");
							addConnection();

							ObjectOutputStream os = new ObjectOutputStream(
									socket.getOutputStream());
							Message mess = new Message();
							mess.setPayLoad("Thanks for Accepting \n");
							os.writeObject(mess);

						} else if (message.getPayLoad().contains("503")) {

							socket.close();
						} else if (type == ((byte) 0x00)) {
							System.out.println("inside if : equals 0x00");

							System.out.println("ping received from"
									+ InetAddress.getLocalHost()
											.getHostAddress());

							sendPongMessage(message);

						} else if (type == ((byte) 0x01)) {

							System.out.println("Received pong from "
									+ InetAddress.getLocalHost()
											.getHostAddress());
							System.out.println("Received info :"
									+ message.getPayLoad().toString());

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
	public String send(Message message) {
		if (socket != null) {
			try {
				System.out.println("sending to :"
						+ InetAddress.getLocalHost().getHostAddress());
				ObjectOutputStream os = new ObjectOutputStream(
						socket.getOutputStream());
				os.writeObject(message);
				os.flush();

				return "message sent";
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "error sending";

		}
		return "socket error";
	}

	public void addConnection() {
		Connection connection = new Connection();
		connection.localPort = this.socket.getLocalPort();
		connection.remotePort = this.socket.getPort();
		connection.ip = this.socket.getInetAddress().getHostAddress();
		connection.hostname = this.socket.getInetAddress().getHostName();
		connection.connectionId = ServerConnections.outgoingConnectionCounter++;
		connection.setHandler(this);
		ServerConnections.activeConnection.add(connection);

	}

	public void sendPongMessage(Message message) throws IOException {

		System.out.println("inside : sendPongMessage");
		byte[] head = message.getHeader();
		if ( !ServerConnections.pingsReceived.contains(InetAddress
						.getLocalHost().getHostAddress())) {
			System.out.println("inside if : sendPongMessage");
			StringBuffer payload = new StringBuffer();
			payload.append(ServerConnections.TCP_PORT + ",");
			payload.append(ServerConnections.IP_ADDRESS + ",");
			payload.append(ServerConnections.noOfFiles + ",");
			payload.append(ServerConnections.sizeOfSharedFolder);
			head[16] = (byte) 0x01;
			message.setHeader(head);
			message.setPayLoad(payload.toString());
			ObjectOutputStream os = new ObjectOutputStream(
					socket.getOutputStream());
			os.writeObject(message);
			os.flush();

		}

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
