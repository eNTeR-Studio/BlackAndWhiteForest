package enter.blackandwhiteforest.screen;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.BlackAndWhiteForest.ResourceType;
import enter.blackandwhiteforest.api.IBAWFPlugin;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ScreenWelcome implements Screen, IBAWFPlugin {

	public static Image icon;
	public static float totalDelta = 0F;
	public static boolean hasBlockAdded = false;
	public static boolean hasShown = false;

	public void init() {
		icon = new Image(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "icon.png")));
		icon.setBounds(0, 0, BlackAndWhiteForest.width, BlackAndWhiteForest.height);
		//BlackAndWhiteForest.initTime++;
	}

	@Override
	public void show() {
		totalDelta = 0;
		BlackAndWhiteForest.stage.addActor(icon);
	}

	@Override
	public void render(float delta) {
		if (BlackAndWhiteForest.totalDelta > BlackAndWhiteForest.FI) {
			if (!hasBlockAdded) {
				AlphaAction alpha = Actions.fadeOut((float) (Math.PI - Math.E));
				BlackAndWhiteForest.stage.addAction(alpha);
				hasBlockAdded = true;
			} else {
				totalDelta += delta;
			}
			if (totalDelta >= (Math.PI - Math.E)) {
				BlackAndWhiteForest.stage.clear();
				BlackAndWhiteForest.stage.getBatch().flush();
				dispose();
				BlackAndWhiteForest.INSTANSE.setScreen(BlackAndWhiteForest.main);
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
		icon=null;
		System.gc();
	}

}
