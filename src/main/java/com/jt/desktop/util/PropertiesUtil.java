package com.jt.desktop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	private static Properties properties;
	
	static {
		File file = new File("conf/search.properties");
		InputStream is = null;
        try {
	        is = new FileInputStream(file);
			properties = new Properties();
			properties.load(is);
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
        	try {
        		if(is!=null)
	            is.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
        }

	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}

}
