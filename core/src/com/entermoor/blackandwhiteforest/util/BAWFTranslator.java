package com.entermoor.blackandwhiteforest.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;

public class BAWFTranslator {

	public static Map<String, String> translator = Collections.synchronizedMap(new HashMap<String, String>());
	public static String propFileName;
	public static Map<String, String> translator_en_US = Collections.synchronizedMap(new HashMap<String, String>());

	static {

	}

	public static String get(String key) {
		String val = translator.get(key);
		if (val == null || val.equals("")) {
			BAWFCrashHandler.handleCrash(new RuntimeException(String.format("Can't find %s in %s", key, propFileName)));
			return key;
		} else {
			return val;
		}
	}

	public static FileHandle[] getLangs() {
		FileHandle langFolder = Gdx.app.getType().equals(ApplicationType.Android)
				? Gdx.files.external("BlackAndWhiteForest/lang/") : Gdx.files.local("lang/");
		if (!langFolder.exists())
			langFolder.mkdirs();
		return langFolder.list(".lang");
	}

	public static void load(Properties props) {

	}
}
