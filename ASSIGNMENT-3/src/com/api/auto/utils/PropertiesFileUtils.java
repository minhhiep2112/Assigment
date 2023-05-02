package com.api.auto.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesFileUtils {
	
	
	public static String getProperties(String key) 
	{
		try {
		File file = new File("./configuration/configs.properties");
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);
		return prop.getProperty(key).toString();
		
		}
		catch(Exception e) {
			return null;
		}
		
	}
	
	public static String getToken(String key) 
	{
		try {
		File file = new File("./configuration/token.properties");
		FileReader fr = new FileReader(file);
		Properties prop = new Properties();
		prop.load(fr);
		return prop.getProperty(key).toString();
		}
		catch(Exception e) {
			return null;
		}
		
	}
	
	public static void saveToken(String token, String value) {
		OutputStream output = null;
		try {
			output = new FileOutputStream("./configuration/token.properties");
			
			Properties prop = new Properties();
			prop.setProperty(token, value);
			
			prop.store(output, "");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	
	
	}
}
