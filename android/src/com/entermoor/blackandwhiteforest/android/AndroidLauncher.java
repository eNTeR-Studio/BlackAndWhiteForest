package com.entermoor.blackandwhiteforest.android;

import android.os.Bundle;
import dalvik.system.DexClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;
import com.entermoor.blackandwhiteforest.event.BAWFEventBus;
import com.entermoor.blackandwhiteforest.util.BAWFConfig;
import com.entermoor.blackandwhiteforest.util.BAWFCrashHandler;

public class AndroidLauncher extends AndroidApplication {

	private AndroidLauncher BAWFAndroidLauncher = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(BlackAndWhiteForest.INSTANSE, config);
		BlackAndWhiteForest.eventBus = new BAWFEventBus();
		BlackAndWhiteForest.config = new BAWFConfig();
		BlackAndWhiteForest.toInitList.add(new IBAWFPlugin() {

			@Override
			public void init() {
				loadPlugin(".jar");
				loadPlugin(".dex");
			}
		});
	}

	private void loadPlugin(String suffix) {
		FileHandle pluginFolder = Gdx.app.getType().equals(ApplicationType.Android)
				? Gdx.files.external("BlackAndWhiteForest/plugins/") : Gdx.files.local("plugins/");
		if (!pluginFolder.exists())
			pluginFolder.mkdirs();
		FileHandle[] files = pluginFolder.list(suffix);
		for (int i = 0; i < files.length; i++) {
			try {
				File jarFile = files[i].file();
				ClassLoader loader = new DexClassLoader(jarFile.getAbsolutePath(),
						BAWFAndroidLauncher.getContext().getDir("dex", 0).getAbsolutePath(), null,
						ClassLoader.getSystemClassLoader());
				File propsFile = new File(files[i].file().getAbsolutePath() + ".properties");
				Properties props = new Properties();
				if (propsFile.exists()) {
					props.load(new FileInputStream(propsFile));
					String className = props.getProperty("mainClass", "");
					if (className != "" || className != null) {
						Class<?> aClass = loader.loadClass(className);
						Class<?>[] interfaces = aClass.getInterfaces();
						for (int j = 0; j < interfaces.length; j++) {
							if (interfaces[j].getName().equals(IBAWFPlugin.class.getName())) {
								IBAWFPlugin instance = (IBAWFPlugin) aClass.newInstance();
								instance.init();
								break;
							}
						}
					}
				}
				// loader.close();
			} catch (Exception e) {
				BAWFCrashHandler.handleCrash(e);
				continue;
			}
		}
	}
}
