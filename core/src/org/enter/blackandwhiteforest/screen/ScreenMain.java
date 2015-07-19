package org.enter.blackandwhiteforest.screen;

import org.enter.blackandwhiteforest.BlackAndWhiteForest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class ScreenMain implements Screen{
	
	public BlackAndWhiteForest bawf;

	public ScreenMain(BlackAndWhiteForest game) {
		this.bawf = game;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bawf.batch.begin();
		
		bawf.batch.end();
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
