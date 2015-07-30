package enter.blackandwhiteforest.screen;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.api.IBAWFPlugin;

import com.badlogic.gdx.Screen;

public class ScreenGaming implements Screen, IBAWFPlugin {

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		BlackAndWhiteForest.camera.update();
		BlackAndWhiteForest.batch.setProjectionMatrix(BlackAndWhiteForest.camera.combined);
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
		//BlackAndWhiteForest.initTime++;
	}

}
