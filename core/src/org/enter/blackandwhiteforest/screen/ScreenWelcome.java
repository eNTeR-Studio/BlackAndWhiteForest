package org.enter.blackandwhiteforest.screen;

import org.enter.blackandwhiteforest.BlackAndWhiteForest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ScreenWelcome implements Screen {

	public BlackAndWhiteForest bawf;
	public Texture icon;

	public ScreenWelcome(BlackAndWhiteForest game) {
		this.bawf = game;
	}

	@Override
	public void show() {
		icon = new Texture("fxzjshm/textures/icon/icon.png");

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bawf.batch.begin();
		
		bawf.batch.draw(icon, 0, 0, bawf.width, bawf.height);
		
		bawf.batch.end();
		
		if (bawf.totalDelta > (Math.PI + Math.E) / 2)
			bawf.setScreen(bawf.main);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
