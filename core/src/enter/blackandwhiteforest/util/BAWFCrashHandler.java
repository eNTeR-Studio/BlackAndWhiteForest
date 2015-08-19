package enter.blackandwhiteforest.util;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.screen.ScreenError;

public class BAWFCrashHandler {

	public static void handleCrash(Throwable e) {
		handleCrash(e, true);
	}

	public static void handleCrash(Throwable e, boolean hasDialog) {
		e.printStackTrace();

		StackTraceElement[] stackTraceElement = e.getStackTrace();
		StringBuilder msgBuilder = new StringBuilder(162);
		msgBuilder.append(e.getMessage());
		msgBuilder.append("\n");
		for (int i = 0; i < stackTraceElement.length; i++) {
			msgBuilder.append("        at ");
			msgBuilder.append(stackTraceElement[i]);
			msgBuilder.append("\n");
		}

		if (hasDialog) {
			Dialog errDialog = new Dialog("Error!", BlackAndWhiteForest.skin);
			errDialog.text(msgBuilder.toString());

			ButtonStyle buttonStyle = new ButtonStyle(BlackAndWhiteForest.getDrawable("ok.png"),
					BlackAndWhiteForest.getDrawable("okClicked.png"), null);
			Button button = new Button(buttonStyle);
			button.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
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
		if (Gdx.app.getType().equals(ApplicationType.Android) || Gdx.app.getType().equals(ApplicationType.Desktop)) {
			Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "https://api.leancloud.cn/1.1/feedback", 80,
					null);

			HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
			HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.POST)
					.url("https://api.leancloud.cn/1.1/feedback")
					.header("X-AVOSCloud-Application-Id", "6v9rp1ndzdl5zbv9uiqjlzeex4v7gv2kh7hawtw02kft5ccd")
					.header("X-AVOSCloud-Application-Key", "jlgcq1xbr6op5f5yuyj304x7iu6ee4b70tfei0dtzoghjxgv")
					.header("Content-Type", "application/json").content("{ \"status\"  : \"open\",\"content\" : \""
							+ msgBuilder.toString() + "\", \"contact\" : \"\"}")
					.build();
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
					// TODO Auto-generated method stub

				}
			});
		}
	}

}
