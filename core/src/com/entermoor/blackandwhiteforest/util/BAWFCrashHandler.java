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

				// BlackAndWhiteForest.doesRender = false;
				errDialog.show(BlackAndWhiteForest.stage);
				BlackAndWhiteForest.INSTANSE.setScreen(new ScreenError(BlackAndWhiteForest.INSTANSE.getScreen()));
			}
			/*
			 * if (Gdx.app.getType().equals(ApplicationType.Android) ||
			 * Gdx.app.getType().equals(ApplicationType.Desktop)) {
			 */
			/*
			 * Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP,
			 * "https://api.leancloud.cn/1.1/feedback", 80, null);
			 */

			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("\"status\"", "\"open\"");
			contentMap.put("\"content\"", "\"" + msgBuilder.toString() + "\"");
			/*String contactInfo = BAWFConfig.get("ContactInfo");
			if (contactInfo == null || contactInfo.equals("")) {

				Gdx.input.getTextInput(new TextInputListener() {

					@Override
					public void input(String text) {
						BAWFConfig.config.setProperty("ContactInfo", text);
					}

					@Override
					public void canceled() {

					}

				}, BAWFTranslator.get("We need more information to contact you !"), "", "");

			}
			contactInfo = BAWFConfig.get("ContactInfo");*/
			contentMap.put("\"contact\"", "\"\"");
			BlackAndWhiteForest.feedback(contentMap);
			// }
		}
	}
}
