package com.entermoor.blackandwhiteforest.util;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.screen.ScreenError;

public class BAWFCrashHandler {

	public static void handleCrash(Throwable e) {
		handleCrash(e, true);
	}

	public static void handleCrash(Throwable e, boolean hasDialog) {
		e.printStackTrace();

		StackTraceElement[] stackTraceElement = e.getStackTrace();
		StringBuilder msgBuilder = new StringBuilder(162);
		msgBuilder.append("Platform : ");
		msgBuilder.append(Gdx.app.getType().name());
		msgBuilder.append("\r\n");
		msgBuilder.append(e.getMessage());
		msgBuilder.append("\r\n");
		for (int i = 0; i < stackTraceElement.length; i++) {
			msgBuilder.append("        at ");
			msgBuilder.append(stackTraceElement[i]);
			msgBuilder.append("\r\n");
		}
		if (!BlackAndWhiteForest.isDebug) {
			if (hasDialog) {
				Dialog errDialog = new Dialog("Error!", BlackAndWhiteForest.skin);
				errDialog.text(msgBuilder.toString());

				ButtonStyle buttonStyle = new ButtonStyle(BlackAndWhiteForest.getDrawable("ok.png"),
						BlackAndWhiteForest.getDrawable("okClicked.png"), null);
				Button button = new Button(buttonStyle);
				button.addListener(new EventListener() {
					@Override
					public boolean handle(Event event) {
						if (event instanceof InputEvent
								&& ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
							Screen screen = BlackAndWhiteForest.INSTANSE.getScreen();
							if (screen instanceof ScreenError)
								BlackAndWhiteForest.INSTANSE.setScreen(((ScreenError) screen).lastScreen);
							else
								handleCrash(new RuntimeException(""), false);
						}
						return true;
					}
				});
				errDialog.button(button);
				
				errDialog.show(BlackAndWhiteForest.stage);
				BlackAndWhiteForest.INSTANSE.setScreen(new ScreenError(BlackAndWhiteForest.INSTANSE.getScreen()));
			}

			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("\"status\"", "\"open\"");
			contentMap.put("\"content\"", "\"" + msgBuilder.toString() + "\"");
			contentMap.put("\"contact\"", "\"\"");
			BlackAndWhiteForest.feedback(contentMap);
			// }
		}
		
		/*FileHandle crashLog=BlackAndWhiteForest.getSavePath(String.valueOf(new Date().getTime())+".txt");
		try {
			crashLog.file().createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		crashLog.writeString(msgBuilder.toString(), true);*/
	}
}
