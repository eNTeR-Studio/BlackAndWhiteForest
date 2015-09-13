package com.entermoor.blackandwhiteforest.util;

import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class BAWFConfig {

	public static Properties config = new Properties();

	static {
		try {
			FileHandle configFile = Gdx.files.local("BAWFConfig.properties");
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
}