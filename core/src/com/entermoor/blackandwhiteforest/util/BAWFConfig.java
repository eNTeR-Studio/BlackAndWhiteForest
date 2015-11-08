package com.entermoor.blackandwhiteforest.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.files.FileHandle;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;

/**
 * Key list:
 * ContactInfo
 * */
public class BAWFConfig {
	
	public static Properties config = new Properties();

	static {
		try {
			FileHandle configFile = BlackAndWhiteForest.getSavePath("BAWFConfig.properties");
			if (!configFile.exists())
				configFile.file().createNewFile();
			config.load(configFile.read());
		} catch (Exception e) {
			BAWFCrashHandler.handleCrash(e);
		}
	}

	public static String get(String key, String defaultValue) {
		String val = config.getProperty(key);
		if (val == null) {
			config.setProperty(key, defaultValue);
			return defaultValue;
		} else {
			return val;
		}
	}

	public static String get(String key) {
		return config.getProperty(key, null);
	}
	
	public static void set(String key, String value){
		config.setProperty(key, value);
		try {
			config.store(new FileOutputStream(BlackAndWhiteForest.getSavePath("BAWFConfig.properties").file()), "The properties of BlackAndWhiteForest");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}