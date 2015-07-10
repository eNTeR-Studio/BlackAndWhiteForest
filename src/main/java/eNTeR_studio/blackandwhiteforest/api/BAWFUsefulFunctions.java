package eNTeR_studio.blackandwhiteforest.api;

import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;

public class BAWFUsefulFunctions {
	
	/**The date.*/
	public static String date;
	
	/**Read a config file. <code>key.length</code> must equals <code>defaultvalue.length</code> and <code>defaultvalue.length</code><br/>
	 * If there isn't a config file, we will create one for you.
	 * @param configFile is the file you need to read.
	 * @param key are the hashtable keys.
	 * @param defaultvalue are the default values. When we create a config file, we need them to return.
	 * @param defaultDoesNeedEqual To tell us if you need to add a '=' between key and value.
	 * @return A Properties Object. you can use it like : <code>properties.getProperty(key, defaultvalue);</code>
	 * @throws Exception if <code>key.length</code> doesn't equal <code>defaultvalue.length</code> or <code>defaultvalue.length</code>, we will throw it.
	 * @author fxzjshm*/
	public static Properties readConfig(File configFile, String[] key, String[] defaultvalue,
			boolean[] defaultDoesNeedEqual) throws Exception {
		if (key.length != defaultvalue.length)
			throw new Exception("Two length don't equals.");
		if (!configFile.exists()) {
			writeConfig(configFile, key, defaultvalue, defaultDoesNeedEqual);
		}
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFile));
		return properties;

	}

	/**Write a config file. <code>key.length</code> must equals <code>defaultvalue.length</code> and <code>defaultvalue.length</code><br/>
	 * @param configFile is the file you need to write.
	 * @param key are the hashtable keys.
	 * @param value are the default values. When we create a config file, we need them to return.
	 * @param doesNeedEqual To tell us if you need to add a '=' between key and value.
	 * @throws Exception if <code>key.length</code> doesn't equal <code>defaultvalue.length</code> or <code>defaultvalue.length</code>, we will throw it.
	 * @author fxzjshm*/
	public static void writeConfig(File configFile, String[] key, String[] value, boolean[] doesNeedEqual)
			throws Exception {
		if (key.length != value.length || doesNeedEqual.length != key.length)
			throw new Exception("Two length don't equals.");
		configFile.createNewFile();
		PrintStream configPrintStream = new PrintStream(configFile);
		for (int i = 0; i < key.length; i++) {
			if (doesNeedEqual[i] == false) {
				configPrintStream.write((key[i] + value[i]).getBytes());
			} else {
				configPrintStream.write((key[i] + "=" + value[i]).getBytes());
			}
			configPrintStream.write("\r\n".getBytes());
		}
		configPrintStream.flush();
		configPrintStream.close();
	}
	
	/**
	 * To get system time.
	 * 
	 * @return Year-Month-Date_Hour.Minute.Second.Millisecond
	 */
	public static String getDate() {
		return getDate("-", "_", ".");
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
	public static String getDate(String connective0, String connective1, String connective2) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int millisecond = c.get(Calendar.MILLISECOND);
		return year + connective0 + month + connective0 + date + connective1 + hour + connective2 + minute + connective2
				+ second + connective2 + millisecond;
	}

	@Deprecated
	public static boolean writeCrash(Exception allExceptions) {
		date = getDate();
		File file = new File("./crash-report/BAWFCrash." + date + ".log");
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

	@Deprecated
	public static boolean showExceptionDialog(Exception exception) {
		int x = 480;
		int y = 360;
		try {
			JFrame jframe = new JFrame("Error!");
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container container = jframe.getContentPane();

			JTextArea jtextarea = new JTextArea();
			jtextarea.setBounds(0, 0, x, y);
			jtextarea.setText(exception.toString() + "\nTo know more info, please see:\n./crash-report/BAWFCrash."
					+ date + ".log\nPlease send these to eNTeR-Studio.");
			container.add(jtextarea);

			jframe.setSize(x, y);
			jframe.show();
		} catch (Exception newException) {
			newException.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Let me handle the exception for you.
	 * 
	 * @param exception
	 *            is the exception to handle.
	 * @return If it is successful to handle the exception.
	 */
	public static boolean handleException(Exception exception, boolean isAutoClose) {
		boolean isSuccessful1 = writeCrash(exception);
		boolean isSuccessful2 = showExceptionDialog(exception);
		if (isAutoClose) {
			System.exit(0);
		}
		return isSuccessful1 && isSuccessful2;
	}

	public static String getResource(String... toAddPathName) {
		StringBuilder realPath = new StringBuilder(32).append(BlackAndWhiteForest.textureFolder.getPath());
		for (int i = 0; i < toAddPathName.length; i++) {
			realPath.append(BlackAndWhiteForest.separator).append(toAddPathName[i]);
		}
		return realPath.toString();
	}

	public static boolean isMouseInRectangle(float realX1, float realY1, float realX2, float realY2, float mouseX,
			float mouseY) {
		return ((realX1 < mouseX) && (mouseX < realX2) && (realY1 < mouseY) && (mouseY < realY2));
	}
}
