package eNTeR_studio.blackandwhiteforest;

import java.io.File;
import java.net.URLDecoder;
import java.util.Properties;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.EventBus;

import eNTeR_studio.blackandwhiteforest.api.BAWFUsefulFunctions;
import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.*;
import eNTeR_studio.blackandwhiteforest.state.*;

/**
 * The main class of the game:Black And White Forest.
 * 
 * @author fxzjshm
 */
public class BlackAndWhiteForest extends StateBasedGame {

	public static int width = 640;
	public static int height = 480;
	public static int fps = 30;
	/** The Event Bus. You can register to it. */
	public static final EventBus BAWF_EVENT_BUS = new EventBus("The Event Bus of Black And White Forest.");
	public static final String GAME_NAME = "Black And White Forest";
	public static int stateWelcomeDeltaTime = 2000;
	public static boolean isClosingExceptionDialog = false;
	public static boolean isWindowsOs = false;
	public static File textureFolder;
	public static String separator = (BlackAndWhiteForest.isWindowsOs ? "\\" : "/");

	public BlackAndWhiteForest() {
		this(GAME_NAME);
	}

	public BlackAndWhiteForest(String name) {
		super(name);
	}

	/**
	 * Don't use it. Because I can't add library here.
	 * 
	 * @param args
	 *            are arguments.
	 * @param isInEclipse
	 *            Is this game running in eclipse?
	 * @see eNTeR_studio.blackandwhiteforest.BlackAndWhiteForestLauncher
	 * @see eNTeR_studio.blackandwhiteforest.BlackAndWhiteForestLauncherEclipse
	 */
	@SuppressWarnings("deprecation")
	public static void start(String[] args, boolean isInEclipse) {
		try {
			readConfig();
			separator = (isWindowsOs ? "\\" : "/");
			if (isInEclipse) {
				textureFolder = new File(URLDecoder.decode(BlackAndWhiteForest.class.getResource("/assets").getFile()))
						.getParentFile();
			} else {
				textureFolder = new File(new File("").getAbsolutePath());
			}
			BAWF_EVENT_BUS.register(new BlackAndWhiteForest());
			AppGameContainer app = new AppGameContainer(new BlackAndWhiteForest());
			app.setDisplayMode(width, height, false);
			app.setTargetFrameRate(fps);
			app.setShowFPS(false);
			app.setForceExit(false);
			System.out.println("AppGameContainer is starting.");
			BAWF_EVENT_BUS.post(new BAWFWillStartEvent(app));
			app.start();
			System.out.println("AppGameContainer has finished.");
			BAWF_EVENT_BUS.post(new BAWFFinishedEvent(app));
		} catch (Exception allExceptions) {
			allExceptions.printStackTrace();
			BAWFUsefulFunctions.writeCrash(allExceptions);
			BAWFUsefulFunctions.showExceptionDialog(allExceptions);
		}
	}

	private static void readConfig() throws Exception {
		Properties properties = BAWFUsefulFunctions.readConfig(new File("BAWFConfig.cfg"),
				new String[] { "width", "height", "fps", "", "showTime" },
				new String[] { "640", "480", "30", "# The time to show welcome (ms)", "2000" },
				new boolean[] { true, true, true, false, true });
		String width = properties.getProperty("width", "640");
		String height = properties.getProperty("height", "480");
		String fps = properties.getProperty("fps", "30");
		String stateWelcomeDeltaTime = properties.getProperty("showTime", "2000");
		BlackAndWhiteForest.width = Integer.valueOf(width);
		BlackAndWhiteForest.height = Integer.valueOf(height);
		BlackAndWhiteForest.fps = Integer.valueOf(fps);
		BlackAndWhiteForest.stateWelcomeDeltaTime = Integer.valueOf(stateWelcomeDeltaTime);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new StateWelcome());
		this.addState(new StateMain());
	}

	/**
	 * This is just a "pool" to save id of states. Please use it like : <br/>
	 * <code>idStateMain.hashcode()</code>
	 */
	public static enum StateIdPool {
		idStateWelcome, idStateMain
	}
}
