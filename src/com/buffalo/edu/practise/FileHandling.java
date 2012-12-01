package com.buffalo.edu.practise;

import java.io.File;

public class FileHandling {

	public static void main(String[] args) 
	{
		File file = new File("Shared");
		File [] filesList = file.listFiles();
		for(int i=0;i<filesList.length;i++)
		{
			System.out.print("File Name \t" +i +":"+filesList[i].getName());
			System.out.print("\tSize is " +(filesList[i].length()/1024)+"kB \n" );
		}
	}

}
