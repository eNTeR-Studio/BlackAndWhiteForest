package com.entermoor.blackandwhiteforest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.entermoor.blackandwhiteforest.api.IBAWFPlugin;
import com.entermoor.blackandwhiteforest.event.IBAWFEventBus;
import com.entermoor.blackandwhiteforest.map.BAWFMap;
import com.entermoor.blackandwhiteforest.screen.ScreenGaming;
import com.entermoor.blackandwhiteforest.screen.ScreenMain;
import com.entermoor.blackandwhiteforest.screen.ScreenSettings;
import com.entermoor.blackandwhiteforest.screen.ScreenWelcome;
import com.entermoor.blackandwhiteforest.util.BAWFAssetManager;
import com.entermoor.blackandwhiteforest.util.BAWFCrashHandler;
import com.entermoor.blackandwhiteforest.util.HumanPlayerMovementListener;

/**
 * <p>
 * I wish to express my most sincere thanks for
 * <a href="libgdx.badlogicgames.com">libGDX</a> and
 * <a href="http://blog.sina.com.cn/weyingkj">Potato</a>
 * </p>
 * <p>
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/4.0/">
 * <img alt= "Creative Commons License" style="border-width:0" src=
 * "https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />
 * This work is licensed under a
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/4.0/">
 * Creative Commons Attribution 4.0 International License</a>.
 * <hr/>
 * </p>
 * 
 * @author fxzjshm
 */
public class BlackAndWhiteForest extends Game {

	public static final double FI = (Math.sqrt(5) + 1) / 2;

	public static final BlackAndWhiteForest INSTANSE = new BlackAndWhiteForest();
	
	public static IBAWFEventBus BAWF_EVENT_BUS;

	public static List<IBAWFPlugin> toInitList = new ArrayList<IBAWFPlugin>();

	public static boolean isDebug = true;

	public static BAWFAssetManager assetManager = new BAWFAssetManager();

	public static SpriteBatch batch;
	public static Stage stage;
	public static ScalingViewport viewport;
	public static OrthographicCamera camera;
	// public static Sprite sprite;
	public static Skin skin = new Skin();
	public static HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();

	public static float totalDelta, delta;
	public static int width, height;
	public static boolean doesRender = true;
	public static Color backgroundColor = new Color((float) (2 - FI), (float) (2 - FI), (float) (2 - FI), 1);

	public static ScreenWelcome welcome;
	public static ScreenMain main;
	public static ScreenSettings settings;
	public static ScreenGaming gaming;

	public static Sound[] click = new Sound[4];
	//public static FreeTypeFontGenerator fontGenerator;

	public static InputMultiplexer multiplexer = new InputMultiplexer();

	public static enum ResourceType {
		texture, sound, music, data
	}

	public static FileHandle getPath(ResourceType type, String fileName) throws IllegalArgumentException {
		switch (type) {
		case texture:
			return Gdx.files.internal("textures/" + fileName);
		case sound:
			return Gdx.files.internal("sounds/" + fileName);
		case music:
			return Gdx.files.internal("musics/" + fileName);
		case data:
			return Gdx.files.internal("data/" + fileName);
		default:
			throw new IllegalArgumentException("Type can't be null.");
		}
	}

	public static enum SoundType {
		click
	}

	public static long playSound(SoundType type) throws IllegalArgumentException {
		return playSound(type, 1.0F);
	}

	public static long playSound(SoundType type, float volume) throws IllegalArgumentException {
		switch (type) {
		case click:
			return click[new Random().nextInt(click.length)].play(volume);
		default:
			throw new IllegalArgumentException("Type can't be null.");
		}
	}

	public static enum MusicType {

	}

	public static long playMusic(MusicType type) throws IllegalArgumentException {
		switch (type) {

		default:
			throw new IllegalArgumentException("Type can't be null.");
		}
	}

	public static TextureRegionDrawable getDrawable(String fileName) {
		return new TextureRegionDrawable(new TextureRegion(new Texture(getPath(ResourceType.texture, fileName))));
	}

	public static void feedback(Map<String, String> map) {
		writeJson(httpRequestBuilder.newRequest().method(HttpMethods.POST).url("https://api.leancloud.cn/1.1/feedback")
				.header("X-LC-Id", "6v9rp1ndzdl5zbv9uiqjlzeex4v7gv2kh7hawtw02kft5ccd")
				.header("X-LC-Key", "jlgcq1xbr6op5f5yuyj304x7iu6ee4b70tfei0dtzoghjxgv"), map);
		final HttpRequest httpRequest = httpRequestBuilder.build();
		System.out.println(httpRequest.getContent());

		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				System.out.println(httpResponse.getResultAsString());
				Pools.free(httpRequest);
			}

			@Override
			public void failed(Throwable t) {
				BAWFCrashHandler.handleCrash(t);
				Pools.free(httpRequest);
			}

			@Override
			public void cancelled() {
				Pools.free(httpRequest);
			}
		});
	}

	public static HttpRequestBuilder writeJson(HttpRequestBuilder requestBuilder, Map<String, String> map) {
		requestBuilder.header(HttpRequestHeader.ContentType, "application/json");
		StringBuilder content = new StringBuilder(200);
		content.append("{");
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> current = iterator.next();
			content.append(current.getKey());
			content.append(":");
			content.append(current.getValue());
			if (iterator.hasNext())
				content.append(",");
			else
				content.append("}");
		}
		requestBuilder.content(content.toString());
		return requestBuilder;
	}

	public static FileHandle getSavePath(String fileName) {
		return Gdx.app.getType().equals(ApplicationType.Android) ? Gdx.files.external("BlackAndWhiteForest/" + fileName)
				: Gdx.files.local(fileName);
	}

	public static void addProcessor(InputProcessor inputProcessor) {
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(multiplexer);
	}

	static {
		toInitList.add(new IBAWFPlugin() {

			@Override
			public void init() {
				totalDelta = 0;
				delta = 0;
				width = Gdx.graphics.getWidth();
				height = Gdx.graphics.getHeight();

				assetManager.load("textures/dialogBackground.png", Texture.class);
				assetManager.load("sounds/202312__7778__dbl-click-edited.mp3", Sound.class);
				assetManager.load("sounds/213004__agaxly__clicking-2-edited.mp3", Sound.class);
				assetManager.load("sounds/219068__annabloom__click2-edited.mp3", Sound.class);
				assetManager.load("sounds/256116__kwahmah-02__click-edited.mp3", Sound.class);

				while (!assetManager.update())
					;

				// fontGenerator = new
				// FreeTypeFontGenerator(Gdx.files.internal("data/SourceHanSansCN-Normal.ttf"));
				click[0] = assetManager.get("sounds/202312__7778__dbl-click-edited.mp3", Sound.class);
				click[1] = assetManager.get("sounds/213004__agaxly__clicking-2-edited.mp3", Sound.class);
				click[2] = assetManager.get("sounds/219068__annabloom__click2-edited.mp3", Sound.class);
				click[3] = assetManager.get("sounds/256116__kwahmah-02__click-edited.mp3", Sound.class);

				batch = new SpriteBatch();
				camera = new OrthographicCamera(width, height);
				camera.position.set(camera.viewportWidth / 2F, camera.viewportHeight / 2F, 0);
				viewport = new ScalingViewport(Scaling.stretch, width, height, camera);
				stage = new Stage(viewport, batch);
				stage.setDebugAll(isDebug);
				camera.setToOrtho(false, width, height);
			}
		});
	}

	private BlackAndWhiteForest() {
	}

	@Override
	public void create() {
		try {
			
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

			welcome = new ScreenWelcome();
			main = new ScreenMain();
			settings = new ScreenSettings();
			gaming = new ScreenGaming();

			skin = new Skin();
			while (!assetManager.update())
				;
			WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, new TextureRegionDrawable(
					new TextureRegion((Texture) assetManager.get("textures/dialogBackground.png", Texture.class))));
			skin.add("default", windowStyle);
			LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
			skin.add("default", labelStyle);

			for (IBAWFPlugin plugin : toInitList)
				plugin.init();

			addProcessor(stage);

			addProcessor(new InputProcessor() {

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
		} catch (Throwable t) {
			BAWFCrashHandler.handleCrash(t);
		}
		setScreen(welcome);
	}

	@Override
	public void render() {
		try {
			super.render();
			width = Gdx.graphics.getWidth();
			height = Gdx.graphics.getHeight();
			Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act();
			stage.draw();
			delta = Gdx.graphics.getDeltaTime();
			totalDelta += delta;
		} catch (Throwable t) {
			BAWFCrashHandler.handleCrash(t);
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		// camera.setToOrtho(false, width, height);
	}

	@Override
	public void resume() {
		super.resume();
		assetManager.finishLoading();
	}
}
