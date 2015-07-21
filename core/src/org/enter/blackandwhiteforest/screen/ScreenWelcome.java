package org.enter.blackandwhiteforest.screen;

import org.enter.blackandwhiteforest.BlackAndWhiteForest;
import org.enter.blackandwhiteforest.api.IBAWFPlugin;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ScreenWelcome implements Screen, IBAWFPlugin {

	public static Image icon;
	public static float totalDelta = 0F;
	public static boolean hasBlockAdded = false;

	public void init() {
		icon = new Image(new Texture("fxzjshm/textures/icon.png"));
		icon.setBounds(0, 0, BlackAndWhiteForest.width, BlackAndWhiteForest.height);
		BlackAndWhiteForest.initTime++;
	}

	@Override
	public void show() {
		BlackAndWhiteForest.stage.addActor(icon);
	}

	@Override
	public void render(float delta) {
		if (BlackAndWhiteForest.totalDelta > (Math.PI + Math.E) / 2) {
			if (totalDelta > 1) {
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
