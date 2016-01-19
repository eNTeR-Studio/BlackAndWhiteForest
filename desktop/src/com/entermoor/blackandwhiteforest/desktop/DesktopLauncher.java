package com.entermoor.blackandwhiteforest.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;
import com.entermoor.blackandwhiteforest.event.BAWFEventBus;
import com.entermoor.blackandwhiteforest.util.BAWFCrashHandler;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		BlackAndWhiteForest.BAWF_EVENT_BUS=new BAWFEventBus();
		BlackAndWhiteForest.toInitList.add(new IBAWFPlugin() {
			
			@Override
			public void init() {
				loadPlugin(".jar");
			}
		});
		new LwjglApplication(BlackAndWhiteForest.INSTANSE, config);
	}
	
	private static void loadPlugin(String suffix) {
		FileHandle pluginFolder = Gdx.app.getType().equals(ApplicationType.Android)
				? Gdx.files.external("BlackAndWhiteForest/plugins/") : Gdx.files.local("plugins/");
		if (!pluginFolder.exists())
			pluginFolder.mkdirs();
		FileHandle[] files = pluginFolder.list(suffix);
		for (int i = 0; i < files.length; i++) {
			try {
				File jarFile = files[i].file();
				ClassLoader loader = new URLClassLoader(new URL[] { jarFile.toURI().toURL() });
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
