package eNTeR_studio.blackandwhiteforest;

import java.io.File;

public class BlackAndWhiteForestLauncher {
	public static void main(String[] args){
		//System.setProperty("java.library.path", "libs");
		String osName = System.getProperty("os.name");
		System.out.println("Os name is: "+osName);
		if(osName.contains("Windows")){
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-windows").getAbsolutePath());
		}else if(osName.contains("Mac")){
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-mac").getAbsolutePath());
		}else{
			System.setProperty("org.lwjgl.librarypath", new File("libs/natives-linux").getAbsolutePath());
		}
		BlackAndWhiteForest.main(args, true);
	}
}
