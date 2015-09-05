package com.entermoor.blackandwhiteforest.android;

import android.os.Bundle;
import dalvik.system.DexClassLoader;

import java.io.File;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest.IPluginClassLoader;

public class AndroidLauncher extends AndroidApplication {
	
	private AndroidLauncher BAWFAndroidLauncher = this;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		BlackAndWhiteForest.iLoader = new IPluginClassLoader() {
			@Override
			public ClassLoader getClassLoader(File... files) throws Exception {
				return new DexClassLoader(files[0].getAbsolutePath(),
						BAWFAndroidLauncher.getContext().getDir("dex", 0).getAbsolutePath(), null,
						ClassLoader.getSystemClassLoader());
			}
		};
		initialize(BlackAndWhiteForest.INSTANSE, config);
	}
}
