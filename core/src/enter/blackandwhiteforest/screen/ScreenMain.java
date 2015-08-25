package enter.blackandwhiteforest.screen;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.BlackAndWhiteForest.ResourceType;
import enter.blackandwhiteforest.BlackAndWhiteForest.SoundType;
import enter.blackandwhiteforest.api.IBAWFPlugin;
import enter.blackandwhiteforest.map.BAWFMap;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScreenMain implements Screen, IBAWFPlugin {

	public ImageButton buttonStart, buttonSettings;
	public TextureRegionDrawable startUp, startDown, settingsUp, settingsDown;
	public static boolean hasStartClicked, hasSettingsClicked = false;
	public static EventListener startListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				BAWFMap.INSTANCE.load();
			}
			return true;
		}
	};
	public static EventListener settingsListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				hasSettingsClicked = true;
				BlackAndWhiteForest.playSound(SoundType.click);
			}
			return true;
		}
	};

	public static boolean hasActionAdded = false;
	public static float totalDelta = 0;

	public void init() {
		startUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "start.png"))));
		startDown = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "startClicked.png"))));
		buttonStart = new ImageButton(startUp, startDown);
		buttonStart.setBounds(BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F,
				BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F);
		buttonStart.addListener(startListener);

		settingsUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "settings.png"))));
		settingsDown = new TextureRegionDrawable(new TextureRegion(
				new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "settingsClicked.png"))));
		buttonSettings = new ImageButton(settingsUp, settingsDown);
		buttonSettings.setBounds(0, 0, BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
		buttonSettings.addListener(settingsListener);
		// BlackAndWhiteForest.initTime++;
	}

	@Override
	public void show() {
		init();
		totalDelta = 0;
		AlphaAction alpha = Actions.fadeIn((float) (Math.PI - Math.E));
		BlackAndWhiteForest.stage.addAction(alpha);
		BlackAndWhiteForest.stage.addActor(buttonStart);
		BlackAndWhiteForest.stage.addActor(buttonSettings);
	}

	@Override
	public void render(float delta) {
		if (hasSettingsClicked) {
			if (!hasActionAdded) {
				AlphaAction alpha = Actions.fadeOut((float) (Math.PI - Math.E));
				BlackAndWhiteForest.stage.addAction(alpha);
				hasActionAdded = true;
			}
			if (totalDelta >= (Math.PI - Math.E)) {
				hasActionAdded = false;
				hasSettingsClicked = false;
				BlackAndWhiteForest.stage.clear();
				BlackAndWhiteForest.stage.getBatch().flush();
				BlackAndWhiteForest.INSTANSE.setScreen(BlackAndWhiteForest.settings);
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
		init();
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		startUp.getRegion().getTexture().dispose();
		startDown.getRegion().getTexture().dispose();
		settingsUp.getRegion().getTexture().dispose();
		settingsDown.getRegion().getTexture().dispose();
	}

}
