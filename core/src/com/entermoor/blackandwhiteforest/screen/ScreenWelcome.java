package com.entermoor.blackandwhiteforest.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest.ResourceType;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;

public class ScreenWelcome implements Screen {

	public static Image icon;
	public static float totalDelta = 0F;
	public static boolean hasBlockAdded = false;
	public static boolean hasShown = false;

	@Override
	public void show() {
		//init();
		totalDelta = 0;
		icon = new Image(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "icon.png")));
		icon.setBounds(0, 0, BlackAndWhiteForest.width, BlackAndWhiteForest.height);
		//AlphaAction alpha = Actions.fadeIn((float) (Math.PI - Math.E));
		//BlackAndWhiteForest.stage.addAction(alpha);
		BlackAndWhiteForest.stage.addActor(icon);
	}

	@Override
	public void render(float delta) {
		BlackAndWhiteForest.INSTANSE.setScreen(new ScreenLoading((float)BlackAndWhiteForest.FI, BlackAndWhiteForest.main));
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		//init();
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
