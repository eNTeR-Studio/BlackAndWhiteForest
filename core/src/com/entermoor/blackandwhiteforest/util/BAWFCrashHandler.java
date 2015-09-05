package com.entermoor.blackandwhiteforest.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
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

				/*
				 * Gdx.input.getTextInput(new TextInputListener() {
				 * 
				 * @Override public void input(String text) {
				 * 
				 * }
				 * 
				 * @Override public void canceled() {
				 * 
				 * } }, "Error!", msgBuilder.toString(), "");
				 */
			}
			if (Gdx.app.getType().equals(ApplicationType.Android)
					|| Gdx.app.getType().equals(ApplicationType.Desktop)) {
				/*
				 * Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP,
				 * "https://api.leancloud.cn/1.1/feedback", 80, null);
				 */

				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("\"status\"", "\"open\"");
				contentMap.put("\"content\"", "\"" + msgBuilder.toString() + "\"");
				contentMap.put("\"contact\"", "\"" + Gdx.app.getType().toString() + "\"");
				HttpRequestBuilder requestBuilder = new HttpRequestBuilder().newRequest().method(HttpMethods.POST)
						.url("https://api.leancloud.cn/1.1/feedback")
						.header("X-AVOSCloud-Application-Id", "6v9rp1ndzdl5zbv9uiqjlzeex4v7gv2kh7hawtw02kft5ccd")
						.header("X-AVOSCloud-Application-Key", "jlgcq1xbr6op5f5yuyj304x7iu6ee4b70tfei0dtzoghjxgv");
				writeJson(requestBuilder, contentMap);
				HttpRequest httpRequest = requestBuilder.build();
				System.out.println(httpRequest.getContent());

				Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						System.out.println(httpResponse.getResultAsString());
					}

					@Override
					public void failed(Throwable t) {
						t.printStackTrace();
					}

					@Override
					public void cancelled() {

					}
				});

			}
		}
	}

	public static void writeJson(HttpRequestBuilder requestBuilder, Map<String, String> map) {
		requestBuilder.header(HttpRequestHeader.ContentType, "application/json");
		StringBuilder content = new StringBuilder(200);
		content.append("{");
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> current = iterator.next();
			content.append(current.getKey());
			content.append(":");
			content.append(current.getValue());
			if (iterator.hasNext())
				content.append(",");
			else
				content.append("}");
		}
		requestBuilder.content(content.toString());
	}

}