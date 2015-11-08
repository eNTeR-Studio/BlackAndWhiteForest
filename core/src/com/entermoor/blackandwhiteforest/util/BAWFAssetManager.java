package com.entermoor.blackandwhiteforest.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.transcoder.TranscoderException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import cn.outofmemory.util.SvgPngConverter;

public class BAWFAssetManager extends AssetManager {

	public Map<String, Class<?>> resourcesMap = new HashMap<String, Class<?>>();

	@Override
	public synchronized <T> void load(String fileName, Class<T> type) {
		if (fileName.endsWith("svg") && !Gdx.files.local(fileName.replace("svg", "png")).exists()) {
			try {
				SvgPngConverter.convertToPng(Gdx.files.local(String.copyValueOf(fileName.toCharArray()).replace("png", "svg")).readString(), fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TranscoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
