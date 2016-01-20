package com.entermoor.blackandwhiteforest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest.SoundType;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;
import com.entermoor.blackandwhiteforest.map.BAWFMap;
import com.entermoor.blackandwhiteforest.util.HumanPlayerMovementListener;

public class ScreenMain implements Screen {

	public static ImageButton buttonStart;
	public static ImageButton buttonSettings;
	public static TextureRegionDrawable startUp;
	public static TextureRegionDrawable startDown;
	public static TextureRegionDrawable settingsUp;
	public static TextureRegionDrawable settingsDown;
	public static boolean hasStartClicked, hasSettingsClicked = false;
	public static EventListener startListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				BlackAndWhiteForest.INSTANSE.setScreen(BlackAndWhiteForest.gaming);
				BlackAndWhiteForest.stage.clear();
				BlackAndWhiteForest.stage.getRoot().getColor().a=1.0F;
				BlackAndWhiteForest.stage.getBatch().flush();
				BAWFMap.INSTANCE.load();
				BlackAndWhiteForest.addProcessor(new InputProcessor() {

					@Override
					public boolean touchUp(int screenX, int screenY, int pointer, int button) {
						return false;
					}

					@Override
					public boolean touchDragged(int screenX, int screenY, int pointer) {
						return false;
					}

					@Override
					public boolean touchDown(int screenX, int screenY, int pointer, int button) {
						return false;
					}

					@Override
					public boolean scrolled(int amount) {
						return false;
					}

					@Override
					public boolean mouseMoved(int screenX, int screenY) {
						return false;
					}

					@Override
					public boolean keyUp(int keycode) {
						System.out.println(keycode);
						if (BAWFMap.INSTANCE.getCurrentPlayer().listener instanceof HumanPlayerMovementListener) {
							HumanPlayerMovementListener listener = (HumanPlayerMovementListener) BAWFMap.INSTANCE
									.getCurrentPlayer().listener;
							if (keycode == Input.Keys.LEFT)
								listener.keyTypedX--;
							if (keycode == Input.Keys.RIGHT)
								listener.keyTypedX++;
							if (keycode == Input.Keys.UP)
								listener.keyTypedY--;
							if (keycode == Input.Keys.DOWN)
								listener.keyTypedY++;
						}
						return false;
					}

					@Override
					public boolean keyTyped(char character) {
						return false;
					}

					@Override
					public boolean keyDown(int keycode) {
						return false;
					}
				});
			}
			return true;
		}
	};
	public static EventListener settingsListener = new EventListener() {
		@Override
		public boolean handle(Event event) {
			if (event instanceof InputEvent && ((InputEvent) event).getType().equals(InputEvent.Type.touchUp)) {
				BlackAndWhiteForest.INSTANSE.setScreen(new ScreenLoading(BlackAndWhiteForest.settings));
				BlackAndWhiteForest.playSound(SoundType.click);
			}
			return true;
		}
	};
	
	static{
		BlackAndWhiteForest.toInitList.add(new IBAWFPlugin() {
			
			@Override
			public void init() {
				startUp = new TextureRegionDrawable(
						new TextureRegion((Texture)BlackAndWhiteForest.assetManager.get("textures/start.png",Texture.class)));
				startDown = new TextureRegionDrawable(
						new TextureRegion((Texture)BlackAndWhiteForest.assetManager.get("textures/startClicked.png",Texture.class)));
				buttonStart = new ImageButton(startUp, startDown);
				buttonStart.setBounds(BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F,
						BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F);
				buttonStart.addListener(startListener);

				settingsUp = new TextureRegionDrawable(
						new TextureRegion((Texture)BlackAndWhiteForest.assetManager.get("textures/settings.png",Texture.class)));
				settingsDown = new TextureRegionDrawable(new TextureRegion(
						(Texture)BlackAndWhiteForest.assetManager.get("textures/settingsClicked.png",Texture.class)));
				buttonSettings = new ImageButton(settingsUp, settingsDown);
				buttonSettings.setBounds(0, 0, BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
				buttonSettings.addListener(settingsListener);
			}
		});
	}
	
	public ScreenMain(){
		BlackAndWhiteForest.assetManager.load("textures/start.png", Texture.class);
		BlackAndWhiteForest.assetManager.load("textures/startClicked.png", Texture.class);
		BlackAndWhiteForest.assetManager.load("textures/settings.png", Texture.class);
		BlackAndWhiteForest.assetManager.load("textures/settingsClicked.png", Texture.class);
	}

	@Override
	public void show() {
		BlackAndWhiteForest.stage.addActor(buttonStart);
		BlackAndWhiteForest.stage.addActor(buttonSettings);
		Gdx.graphics.setContinuousRendering(false);  
	}

	@Override
	public void render(float delta) {
		buttonStart.setBounds(BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F,
				BlackAndWhiteForest.width / 3F, BlackAndWhiteForest.height / 3F);
		buttonSettings.setBounds(0, 0, BlackAndWhiteForest.width / 5F, BlackAndWhiteForest.height / 5F);
	}

	@Override
	public void resize(int width, int height) {
		buttonStart.setBounds(width / 3F, height / 3F, width / 3F, height / 3F);
		buttonSettings.setBounds(0, 0, width / 5F, height / 5F);
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
		startUp.getRegion().getTexture().dispose();
		startDown.getRegion().getTexture().dispose();
		settingsUp.getRegion().getTexture().dispose();
		settingsDown.getRegion().getTexture().dispose();
	}

}
