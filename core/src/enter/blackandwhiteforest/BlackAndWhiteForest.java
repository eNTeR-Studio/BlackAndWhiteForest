package enter.blackandwhiteforest;

import enter.blackandwhiteforest.api.IBAWFPlugin;
import enter.blackandwhiteforest.event.BAWFEventBus;
import enter.blackandwhiteforest.screen.ScreenGaming;
import enter.blackandwhiteforest.screen.ScreenMain;
import enter.blackandwhiteforest.screen.ScreenSettings;
import enter.blackandwhiteforest.screen.ScreenWelcome;
import enter.blackandwhiteforest.util.BAWFCrashHandler;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

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
public class BlackAndWhiteForest extends Game implements IBAWFPlugin {
	/*
	 * Command: keytool -genkey -v -keystore key.keystore -alias key.keystore
	 * -keyalg RSA -keysize 2048 -validity 1000000. Password: 123456 Name: eNTeR
	 * Organization: eNTeR_studio City: haimen Province: jiangsu Country: CN
	 * 
	 * Info:CN=eNTeR, OU=eNTeR_studio, O=eNTeR_studio, L=haimen, ST=jiangsu,
	 * C=CN
	 */
	public static final double FI = (Math.sqrt(5) + 1) / 2;

	public static final BlackAndWhiteForest INSTANSE = new BlackAndWhiteForest();
	public static final BAWFEventBus BAWF_EVENT_BUS = new BAWFEventBus();
	public static final Random ran = new Random();

	// public static FreeTypeFontGenerator generator;

	public static boolean isDebug = true;

	public static SpriteBatch batch;
	public static Stage stage;
	public static ScalingViewport viewport;
	public static OrthographicCamera camera;
	// public static Sprite sprite;
	public static Skin skin = new Skin();

	public static float totalDelta, delta;
	public static int width, height;
	public static boolean doesRender = true;

	/*
	 * public static int initTime = 0; public static final int MAX_INIT_TIME =
	 * 5; public static Image progressBar; public static boolean doesLoad =
	 * true;
	 */

	public static ScreenWelcome welcome;
	public static ScreenMain main;
	public static ScreenSettings settings;
	public static ScreenGaming gaming;

	public static Sound[] click = new Sound[4];
	public static FreeTypeFontGenerator fontGenerator;

	public static IPluginClassLoader iLoader;
	// public static File optimizedDirectory;

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
			return click[ran.nextInt(click.length)].play(volume);
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

	public static interface IPluginClassLoader {
		ClassLoader getClassLoader(File... files) throws Exception;
	}

	private BlackAndWhiteForest() {
	}

	private void loadPlugin(String suffix) {
		FileHandle pluginFolder = Gdx.app.getType().equals(ApplicationType.Android)
				? Gdx.files.external("BlackAndWhiteForest/plugins/") : Gdx.files.local("plugins/");
		if (!pluginFolder.exists())
			pluginFolder.mkdirs();
		FileHandle[] files = pluginFolder.list(suffix);
		for (int i = 0; i < files.length; i++) {
			try {
				File jarFile = files[i].file();
				if (iLoader == null)
					iLoader = new IPluginClassLoader() {
						@Override
						public ClassLoader getClassLoader(File... files) throws MalformedURLException {
							// Only for desktop.
							return new URLClassLoader(new URL[] { files[0].toURI().toURL() });
						}
					};
				ClassLoader loader = iLoader.getClassLoader(jarFile/* ,optimizedDirectory */);
				File propsFile = new File(files[i].file().getAbsolutePath() + ".properties");
				Properties props = new Properties();
				if (propsFile.exists()) {
					props.load(new FileInputStream(propsFile));
					String className = props.getProperty("mainClass", "");
					if (className != "" || className != null) {
						/*
						 * Method findClass=loader.getClass().getDeclaredMethod(
						 * "findClass", String.class);
						 * findClass.setAccessible(true); Class<?> aClass =
						 * (Class<?>) (findClass.invoke(loader, className));
						 */
						Class<?> aClass = loader.loadClass(className);
						Class<?>[] interfaces = aClass.getInterfaces();
						for (int j = 0; j < interfaces.length; j++) {
							if (interfaces[j].getName().equals(IBAWFPlugin.class.getName())) {
								IBAWFPlugin instance = (IBAWFPlugin) aClass.newInstance();
								instance.init();
								break;
							}
						}
					}
				}
				// loader.close();
			} catch (Exception e) {
				BAWFCrashHandler.handleCrash(e);
				continue;
			}
		}
	}

	@Override
	public void init() {
		totalDelta = 0;
		delta = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		fontGenerator = new FreeTypeFontGenerator(getPath(ResourceType.data, "SourceHanSansCN-Normal.ttf"));
		click[0] = Gdx.audio
				.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "202312__7778__dbl-click-edited.mp3"));
		click[1] = Gdx.audio
				.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "213004__agaxly__clicking-2-edited.mp3"));
		click[2] = Gdx.audio
				.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "219068__annabloom__click2-edited.mp3"));
		click[3] = Gdx.audio
				.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "256116__kwahmah-02__click-edited.mp3"));

		batch = new SpriteBatch();
		camera = new OrthographicCamera(width, height);
		camera.position.set(camera.viewportWidth / 2F, camera.viewportHeight / 2F, 0);
		viewport = new ScalingViewport(Scaling.stretch, width, height, camera);
		stage = new Stage(viewport, batch);
		stage.setDebugAll(isDebug);
		// initTime++;
	}

	@Override
	public void create() {
		welcome = new ScreenWelcome();
		main = new ScreenMain();
		settings = new ScreenSettings();
		gaming = new ScreenGaming();

		skin = new Skin();
		WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, getDrawable("dialogBackground.png"));
		skin.add("default", windowStyle);
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
		skin.add("default", labelStyle);
		// TextButtonStyle buttonStyle = new
		// TextButtonStyle(getDrawable("ok.png"),getDrawable("okClicked.png"),null,
		// new BitmapFont());
		// skin.add("default", buttonStyle);

		init();
		setScreen(welcome);

		Gdx.input.setInputProcessor(stage);

		// optimizedDirectory=Gdx.files.external("BlackAndWhiteForest/plugins/dex/").file();
		// if(!optimizedDirectory.exists())optimizedDirectory.mkdirs();
		loadPlugin(".jar");
		if (Gdx.app.getType().equals(ApplicationType.Android))
			loadPlugin(".dex");
	}

	@Override
	public void render() {
		camera.setToOrtho(false, width, height);
		if (doesRender) {
			super.render();
			width = Gdx.graphics.getWidth();
			height = Gdx.graphics.getHeight();
			Gdx.gl.glClearColor((float) (2 - FI), (float) (2 - FI), (float) (2 - FI), 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act();
			stage.draw();
			delta = Gdx.graphics.getDeltaTime();
			totalDelta += delta;
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
	}
}
