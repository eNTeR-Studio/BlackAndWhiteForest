package eNTeR_studio.blackandwhiteforest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new BlackAndWhiteForest(), config);
	}
}