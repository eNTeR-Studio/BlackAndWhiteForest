package eNTeR_studio.blackandwhiteforest.launcher;

import java.io.File;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;

public class BAWFLauncher {
	public static void main(String[] args){
		//System.setProperty("java.library.path", "libs");
		String osName = System.getProperty("os.name");
		System.out.println("Os name is: "+osName);
		if(osName.contains("Windows")){
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-windows").getAbsolutePath());
		}
		if(osName.contains("Mac")){
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-mac").getAbsolutePath());
		}
		if(osName.contains("Linux")){
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-linux").getAbsolutePath());
		}
		BlackAndWhiteForest.main(args);
	}
}
