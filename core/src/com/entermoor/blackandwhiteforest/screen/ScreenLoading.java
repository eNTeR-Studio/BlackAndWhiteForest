package com.entermoor.blackandwhiteforest.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;

public class ScreenLoading implements Screen {

	public static float fadeTime = (float) (BlackAndWhiteForest.FI - 1);

	public float waitTime;
	public Screen nextScreen;
	public float totalDelta;
	public boolean hasActionAdded = false;

	public ScreenLoading(Screen next) {
		this(0, next);
	}

	public ScreenLoading(float time, Screen next) {
		waitTime = time;
		nextScreen = next;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		totalDelta += delta;
		if (totalDelta >= waitTime) {
			if (!hasActionAdded) {
				AlphaAction alpha = Actions.fadeOut(fadeTime);
				BlackAndWhiteForest.stage.addAction(alpha);
				hasActionAdded = true;
			} else {
				totalDelta += delta;
			}
			if (totalDelta >= (waitTime + fadeTime + 0.5)) {
				BlackAndWhiteForest.stage.clear();
				BlackAndWhiteForest.stage.getBatch().flush();
				BlackAndWhiteForest.INSTANSE.setScreen(nextScreen);
				AlphaAction alpha = Actions.fadeIn(fadeTime);
				BlackAndWhiteForest.stage.addAction(alpha);
			}
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
