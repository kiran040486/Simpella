package com.buffalo.edu.util;

import java.io.Serializable;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] header ;
	private String payLoad;
	
	public Message(){
		
	}
	
	public Message(int size){
		
		this.header = new byte[size];
		this.payLoad = null;
	}
	
	
	
	
	public byte[] getHeader() {
		return header;
	}
	public void setHeader(byte[] header) {
		this.header = header;
	}
	public String getPayLoad() {
		return payLoad;
	}
	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

}
