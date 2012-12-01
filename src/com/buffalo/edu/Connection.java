package com.buffalo.edu;

/**
 * Connection info
 * @author Kiran
 *
 */
public class Connection {
	public int connectionId;
	public String ip;
	public String hostname;
	public int localPort;
	public int remotePort;
	public ConnectionHandler handler;
	
	public Connection(){
		
	}

	public Connection(int connectionId, String ip, String hostname,
			int localPort, int remotePort) {
		super();
		this.connectionId = connectionId;
		this.ip = ip;
		this.hostname = hostname;
		this.localPort = localPort;
		this.remotePort = remotePort;
	}

	public ConnectionHandler getHandler() {
		return handler;
	}

	public void setHandler(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	
	
	

}
