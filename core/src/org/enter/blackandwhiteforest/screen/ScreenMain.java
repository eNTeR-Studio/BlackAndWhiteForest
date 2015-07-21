package org.enter.blackandwhiteforest.screen;

import org.enter.blackandwhiteforest.BlackAndWhiteForest;
import org.enter.blackandwhiteforest.api.IBAWFPlugin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScreenMain implements Screen, IBAWFPlugin {

	public static ImageButton buttonStart;
	public static TextureRegionDrawable startUp;
	public static TextureRegionDrawable startDown;

	public static ImageButton buttonSettings;
	public static TextureRegionDrawable settingsUp;
	public static TextureRegionDrawable settingsDown;

	public void init() {
		startUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("fxzjshm/textures/start.png"))));
		startDown = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("fxzjshm/textures/startClicked.png"))));
		buttonStart = new ImageButton(startUp, startDown);
		buttonStart.setBounds(BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F,
				BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F);

		settingsUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("fxzjshm/textures/settings.png"))));
		settingsDown = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("fxzjshm/textures/settingsClicked.png"))));
		buttonSettings = new ImageButton(settingsUp, settingsDown);
		buttonSettings.setBounds(0, 0, BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
		buttonSettings.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
					BlackAndWhiteForest.stage.clear();
					BlackAndWhiteForest.stage.getBatch().flush();
					BlackAndWhiteForest.INSTANSE.setScreen(BlackAndWhiteForest.settings);
				}
				return true;
			}
		});
		BlackAndWhiteForest.initTime++;
	}

	@Override
	public void show() {
		BlackAndWhiteForest.stage.addActor(buttonStart);
		BlackAndWhiteForest.stage.addActor(buttonSettings);
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

	}

}
