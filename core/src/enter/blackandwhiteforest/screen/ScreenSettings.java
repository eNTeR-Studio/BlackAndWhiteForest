package enter.blackandwhiteforest.screen;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.BlackAndWhiteForest.ResourceType;
import enter.blackandwhiteforest.BlackAndWhiteForest.SoundType;
import enter.blackandwhiteforest.api.IBAWFPlugin;

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

public class ScreenSettings implements Screen, IBAWFPlugin {

	public static ImageButton buttonBack, buttonFeedback;
	public static TextureRegionDrawable backUp, backDown, feedbackUp, feedbackDown;
	public static boolean hasBackClicked = false;
	public static boolean hasActionAdded = false;
	public static float totalDelta = 0;

	public void init() {
		backUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "back.png"))));
		backUp.getRegion().setRegion(0, 0, backUp.getRegion().getRegionWidth() / 3 * 4,
				backUp.getRegion().getRegionHeight());
		backDown = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "backClicked.png"))));
		backDown.getRegion().setRegion(0, 0, backDown.getRegion().getRegionWidth() / 3 * 4,
				backDown.getRegion().getRegionHeight());
		buttonBack = new ImageButton(backUp, backDown);
		buttonBack.setBounds(0, (float) (BlackAndWhiteForest.height / 4F * 3F), BlackAndWhiteForest.width / 3F,
				BlackAndWhiteForest.height / 5F);
		buttonBack.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
					hasBackClicked = true;
					BlackAndWhiteForest.playSound(SoundType.click);
				}
				return true;
			}
		});

		feedbackUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "feedback.png"))));
		feedbackDown = new TextureRegionDrawable(new TextureRegion(
				new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "feedbackClicked.png"))));
		buttonFeedback = new ImageButton(feedbackUp, feedbackDown);
		buttonFeedback.setBounds(BlackAndWhiteForest.width / 5F * 4F, BlackAndWhiteForest.height / 5F * 4F,
				BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
		buttonFeedback.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
					BlackAndWhiteForest.playSound(SoundType.click);
					Gdx.input.getTextInput(new TextInputListener() {
						@Override
						public void input(String text) {

						}
						@Override
						public void canceled() {

						}
					}, "title", "", "");
				}
				return true;
			}
		});

		// BlackAndWhiteForest.initTime++;
	}

	@Override
	public void show() {
		totalDelta = 0;
		AlphaAction alpha = Actions.fadeIn((float) (Math.PI - Math.E));
		BlackAndWhiteForest.stage.addAction(alpha);
		BlackAndWhiteForest.stage.addActor(buttonBack);
		BlackAndWhiteForest.stage.addActor(buttonFeedback);
	}

	@Override
	public void render(float delta) {
		if (hasBackClicked) {
			if (!hasActionAdded) {
				AlphaAction alpha = Actions.fadeOut((float) (Math.PI - Math.E));
				BlackAndWhiteForest.stage.addAction(alpha);
				hasActionAdded = true;
			}
			if (totalDelta >= (Math.PI - Math.E)) {
				hasActionAdded = false;
				hasBackClicked = false;
				BlackAndWhiteForest.stage.clear();
				BlackAndWhiteForest.stage.getBatch().flush();
				BlackAndWhiteForest.INSTANSE.setScreen(BlackAndWhiteForest.main);
			}
			totalDelta += delta;
		}
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

	}
}
