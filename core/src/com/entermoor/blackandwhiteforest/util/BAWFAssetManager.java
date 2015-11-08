package com.entermoor.blackandwhiteforest.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;

public class BAWFAssetManager extends AssetManager {

	public Map<String, Class<?>> resourcesMap = new HashMap<String, Class<?>>();

	@Override
	public synchronized <T> void load(String fileName, Class<T> type) {
		super.load(fileName, type);
		if (!resourcesMap.containsKey(fileName))
			resourcesMap.put(fileName, type);
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

	public void resume() {
		clear();
		for (Map.Entry<String, Class<?>> entry : resourcesMap.entrySet()) {
			load(entry.getKey(), entry.getValue());
		}
	}

}
