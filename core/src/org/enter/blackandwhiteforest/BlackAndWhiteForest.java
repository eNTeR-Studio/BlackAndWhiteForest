package org.enter.blackandwhiteforest;

import org.enter.blackandwhiteforest.screen.ScreenMain;
import org.enter.blackandwhiteforest.screen.ScreenWelcome;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class BlackAndWhiteForest extends ApplicationAdapter {
	/*
	 * Command: keytool -genkey -v -keystore key.keystore -alias key.keystore
	 * -keyalg RSA -keysize 2048 -validity 100000 Password: 123456 Name:
	 * eNTeR_studio.blackandwhiteforest Organization: eNTeR_studio City: haimen
	 * Province: jiangsu Country: CN
	 * 
	 * Info:CN=eNTeR_studio.blackandwhiteforest, OU=eNTeR_studio,
	 * O=eNTeR_studio, L=haimen, ST=jiangsu, C=CN
	 * 
	 * Command: jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore
	 * key.keystore bawf.apk key.keystore
	 */
	public Screen screen;
	public SpriteBatch batch;
	public Texture icon;
	public Stage stage;
	public ScalingViewport viewport;
	public Camera camera;
	public float totalDelta;
	public float delta;
	public int width;
	public int height;
	public ScreenWelcome welcome;
	public ScreenMain main;

	@Override
	public void create() {
		totalDelta = 0;
		delta = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ScalingViewport(Scaling.stretch, 0, 0, camera);
		stage = new Stage(viewport, batch);
		welcome = new ScreenWelcome(this);
		main = new ScreenMain(this);

		this.setScreen(welcome);

	}

	@Override
	public void render() {
		if (screen != null)
			screen.render(Gdx.graphics.getDeltaTime());
		delta = Gdx.graphics.getDeltaTime();
		totalDelta += delta;
		// System.out.println(screen.getClass().getName());
		// System.out.println(totalDelta);
	}

	@Override
	public void dispose() {
		if (screen != null)
			screen.hide();
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);
	}

	/**
	 * Sets the current screen. {@link Screen#hide()} is called on any old
	 * screen, and {@link Screen#show()} is called on the new screen, if any.
	 * 
	 * @param screen
	 *            may be {@code null}
	 */
	public void setScreen(Screen screen) {
		if (this.screen != null)
			this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	/**
	 * @return the currently active {@link Screen}.
	 */
	public Screen getScreen() {
		return screen;
	}
}
