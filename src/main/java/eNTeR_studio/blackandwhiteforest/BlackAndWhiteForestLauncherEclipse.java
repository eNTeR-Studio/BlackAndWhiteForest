package eNTeR_studio.blackandwhiteforest;

import java.io.File;

public class BlackAndWhiteForestLauncherEclipse {
	public static void main(String[] args) {
		System.setProperty("java.library.path", "libs/natives-windows/");
		String osName = System.getProperty("os.name");
		System.out.println("Os name is: " + osName);
		System.out.println("Here is " + new File("").getAbsolutePath());
		if (osName.contains("Windows")) {
			System.setProperty("org.lwjgl.librarypath", new File(
					"libs/natives-windows").getAbsolutePath());
			BlackAndWhiteForest.isWindowsOs = true;
		} else if (osName.contains("Mac")) {
			System.setProperty("org.lwjgl.librarypath", new File(
					"libs/natives-mac").getAbsolutePath());
		} else {
			System.setProperty("org.lwjgl.librarypath", new File(
					"libs/natives-linux").getAbsolutePath());
		}
		BlackAndWhiteForest.start(args, true);
	}
}
