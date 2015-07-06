package eNTeR_studio.blackandwhiteforest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Calendar;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.EventBus;

import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.*;
import eNTeR_studio.blackandwhiteforest.other.BAWFAppGameContainer;
import eNTeR_studio.blackandwhiteforest.state.*;

public class BlackAndWhiteForest extends StateBasedGame {

	public static int width = 640;
	public static int height = 480;
	public static int fps = 30;
	/** The Event Bus. You can register to it. */
	public static final EventBus bawfEventBus = new EventBus();
	public static final String gameName = "Black And White Forest";

	public BlackAndWhiteForest() {
		this(gameName);
	}

	public BlackAndWhiteForest(String name) {
		super(name);
	}

	/**
	 * Don't use it.<br/>
	 * Because I can't add library here.
	 * 
	 * @param args
	 *            is arguments.
	 * @param b
	 *            just is used to let eclipse can't run Java Applications.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args, boolean b) {
		// File(BlackAndWhiteForest.class.getResource("").getFile()+"/../../../../resources/assets/fxzjshm/textures/icon/icon.png").getPath());
		bawfEventBus.register(new BlackAndWhiteForest());
		try {
			BAWFAppGameContainer app = new BAWFAppGameContainer(
					new BlackAndWhiteForest());
			app.setDisplayMode(width, height, false);
			app.setTargetFrameRate(fps);
			System.out.println("AppGameContainer is starting.");
			bawfEventBus.post(new BAWFWillStartEvent(app));
			app.start();
			System.out.println("AppGameContainer has finished.");
			bawfEventBus.post(new BAWFFinishedEvent(app));
			System.out.println("Ready to save data.");
			BAWFToSaveObj toSaveObj = new BAWFToSaveObj(width, height, fps);
			bawfEventBus.post(toSaveObj);
			new ObjectOutputStream(new FileOutputStream(
					"./BlackAndWhiteForest.dat")).writeObject(toSaveObj);
		} catch (Exception allExceptions) {
			allExceptions.printStackTrace();
			writeCrash(allExceptions);
		}
	}

	/**
	 * To get system time.
	 * 
	 * @return Year-Month-Date-Hour-Minute-Second-Millisecond
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int millisecond = c.get(Calendar.MILLISECOND);
		return year + "-" + month + "-" + date + "_" + hour + "." + minute
				+ "." + second + "." + millisecond;
	}

	/**
	 * To get system time.
	 * 
	 * @param connective0
	 *            is the <u>string</u> between <i>year</i> and <i>month</i>,
	 *            <i>month</i> and <i>date</i>.
	 * @param connective1
	 *            is the <u>string</u> between <i>date</i> and <i>hour</i>.
	 * @param connective2
	 *            is the <u>string</u> between <i>hour</i> and <i>minute</i>,
	 *            <i>minute</i> and <i>second</i>, <i>second</i> and
	 *            <i>millisecond</i>.
	 * @return year + connective0 + month + connective0 + date + connective1 +
	 *         hour + connective2 + minute + connective2 + second + connective2
	 *         + millisecond
	 */
	public static String getDate(String connective0, String connective1,
			String connective2) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int millisecond = c.get(Calendar.MILLISECOND);
		return year + connective0 + month + connective0 + date + connective1
				+ hour + connective2 + minute + connective2 + second
				+ connective2 + millisecond;
	}

	public static boolean writeCrash(Exception allExceptions) {
		File file = new File("./crash-report/BAWFcrash." + getDate() + ".log");
		File filefolder = new File("./crash-report");

		try {
			if (!filefolder.exists()) {
				filefolder.mkdir();
			}
			if (!file.exists())
				file.createNewFile();
			PrintStream printStream = new PrintStream(file);
			allExceptions.printStackTrace(printStream);
			printStream.flush();
			printStream.close();
		} catch (Exception exception) {
			// ARE YOU FOOLING ME ?!
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(StatePool.stateShowMaker);
		this.addState(StatePool.stateMain);
	}

	/** This is just a "pool" to save states and id of them. */
	public static class StatePool {
		public static StateShowMaker stateShowMaker = new StateShowMaker();
		public static StateMain stateMain = new StateMain();

		public static int idStateShowMaker = stateShowMaker.hashCode();
		public static int idStateMain = stateMain.hashCode();
	}

	/** This is an object to save data. */
	public static class BAWFToSaveObj implements Serializable {

		private static final long serialVersionUID = 6346939607815139066L;
		public static int width;
		public static int height;
		public static int fps;

		private BAWFToSaveObj() {

		}

		public BAWFToSaveObj(int widthTo, int heightTo, int fpsTo) {
			this();
			width = widthTo;
			height = heightTo;
			fps = fpsTo;
		}

	}
}
