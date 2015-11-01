package com.entermoor.blackandwhiteforest.util;

import com.badlogic.gdx.assets.AssetManager;

public class BAWFAssetManager extends AssetManager{
	
	@Override
	@Deprecated
	public synchronized <T> T get (String fileName) {
		return super.get(fileName);
	}
	
	@Override
	public synchronized <T> T get (String fileName, Class<T> type) {
		if(!isLoaded(fileName)){
			BAWFCrashHandler.handleCrash(new Exception());
			load(fileName,type);
		}
		while(!isLoaded(fileName)){
			update();
		}
		return super.get(fileName, type);
	}

}
