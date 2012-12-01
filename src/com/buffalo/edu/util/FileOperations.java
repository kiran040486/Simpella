package com.buffalo.edu.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.buffalo.edu.ServerConnections;

public class FileOperations {
	
	
	public static void main(String[] args){
		
		FileOperations fileOP = new FileOperations();
		
		fileOP.indexFiles();
		
		
		
		
	}

	public String indexFiles() {

		String filePath = ServerConnections.sharedFolder;

		File file = new File(filePath);

		File[] files = file.listFiles();

		FileWriter outFile = null;
		try {
			outFile = new FileWriter("file.index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(outFile);

		
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			System.out.println(i + "\t" + fileName);
			out.println(i + "\t" + fileName);

		}
        out.flush();
        out.close();
		return "success";

	}

}
