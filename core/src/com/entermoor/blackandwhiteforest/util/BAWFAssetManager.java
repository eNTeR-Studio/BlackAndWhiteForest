package com.entermoor.blackandwhiteforest.util;

import com.badlogic.gdx.assets.AssetManager;

public class BAWFAssetManager extends AssetManager {
	
	@Override
	public synchronized <T> void load (String fileName, Class<T> type) {
		load(fileName, type, null);
	}

	@Override
	@Deprecated
	public synchronized <T> T get(String fileName) {
		return super.get(fileName);
	}

	@Override
	public synchronized <T> T get(String fileName, Class<T> type) {
		if (!isLoaded(fileName)) {
			BAWFCrashHandler.handleCrash(new RuntimeException());
			load(fileName, type);
		}
		while (!isLoaded(fileName)) {
			update();
		}
		return super.get(fileName, type);
	}

}
