package com.entermoor.blackandwhiteforest.screen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest.ResourceType;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest.SoundType;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;

public class ScreenSettings implements Screen {

	public static ImageButton buttonBack;
	public static ImageButton buttonFeedback;
	public static TextureRegionDrawable backUp;
	public static TextureRegionDrawable backDown;
	public static TextureRegionDrawable feedbackUp;
	public static TextureRegionDrawable feedbackDown;
	public static EventListener backListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				BlackAndWhiteForest.INSTANSE.setScreen(new ScreenLoading(BlackAndWhiteForest.main));
				BlackAndWhiteForest.playSound(SoundType.click);
			}
			return true;
		}
	};
	public static EventListener feedbackListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				BlackAndWhiteForest.playSound(SoundType.click);
				Gdx.input.getTextInput(new TextInputListener() {
					@Override
					public void input(String text) {
						Map<String, String> contentMap = new HashMap<String, String>();
						contentMap.put("\"status\"", "\"open\"");
						contentMap.put("\"content\"", "\"" + text + "\"");
						String contactInfo = BlackAndWhiteForest.config.get("ContactInfo");
						if (contactInfo == null || contactInfo.equals("")) {

							Gdx.input.getTextInput(new TextInputListener() {

								@Override
								public void input(String text) {
									BlackAndWhiteForest.config.setProperty("ContactInfo", text);
								}

								@Override
								public void canceled() {

								}

							}, "We need more information to contact you !", "", "");

						}
						contactInfo = BlackAndWhiteForest.config.get("ContactInfo");
						contentMap.put("\"contact\"", "\"" + contactInfo + "\"");
						BlackAndWhiteForest.feedback(contentMap);
					}

					@Override
					public void canceled() {

					}
				}, "User Feedback", "", "");
			}
			return true;
		}
	};

	static {
		BlackAndWhiteForest.toInitList.add(new IBAWFPlugin() {

			@Override
			public void init() {
				backUp = new TextureRegionDrawable(
						new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "back.png"))));
				backUp.getRegion().setRegion(0, 0, backUp.getRegion().getRegionWidth() / 3 * 4,
						backUp.getRegion().getRegionHeight());
				backDown = new TextureRegionDrawable(new TextureRegion(
						new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "backClicked.png"))));
				backDown.getRegion().setRegion(0, 0, backDown.getRegion().getRegionWidth() / 3 * 4,
						backDown.getRegion().getRegionHeight());
				buttonBack = new ImageButton(backUp, backDown);
				buttonBack.setBounds(0, (float) (BlackAndWhiteForest.height / 4F * 3F), BlackAndWhiteForest.width / 3F,
						BlackAndWhiteForest.height / 5F);
				buttonBack.addListener(backListener);

				feedbackUp = new TextureRegionDrawable(new TextureRegion(
						new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "feedback.png"))));
				feedbackDown = new TextureRegionDrawable(new TextureRegion(
						new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "feedbackClicked.png"))));
				buttonFeedback = new ImageButton(feedbackUp, feedbackDown);
				buttonFeedback.setBounds(BlackAndWhiteForest.width / 5F * 4F, BlackAndWhiteForest.height / 5F * 4F,
						BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
				buttonFeedback.addListener(feedbackListener);
			}
		});
	}

	@Override
	public void show() {
		AlphaAction alpha = Actions.fadeIn((float) (Math.PI - Math.E));
		BlackAndWhiteForest.stage.addAction(alpha);
		BlackAndWhiteForest.stage.addActor(buttonBack);
		BlackAndWhiteForest.stage.addActor(buttonFeedback);
	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		backUp.getRegion().getTexture().dispose();
		backDown.getRegion().getTexture().dispose();
		feedbackUp.getRegion().getTexture().dispose();
		feedbackDown.getRegion().getTexture().dispose();
	}
}
