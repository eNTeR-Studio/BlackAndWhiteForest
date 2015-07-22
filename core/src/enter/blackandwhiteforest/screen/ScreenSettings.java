package enter.blackandwhiteforest.screen;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.api.IBAWFPlugin;

import com.badlogic.gdx.Screen;

public class ScreenSettings implements Screen, IBAWFPlugin {

	@Override
	public void show() {

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

	public void init() {
		BlackAndWhiteForest.initTime++;
	}

}
