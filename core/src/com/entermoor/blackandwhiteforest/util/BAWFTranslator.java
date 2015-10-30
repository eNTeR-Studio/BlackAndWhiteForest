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
			if (!propFileName.equals("en_US.lang")) {
				String val2 = translator_en_US.get(key);
				if (val2 == null || val2.equals("")) {
					BAWFCrashHandler.handleCrash(new RuntimeException(
							String.format("Can't find %s in en_US.lang. It seems that there is no way...", key)));
					return key;
				} else {
					return val2;
				}
			} else {
				return key;
			}
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
