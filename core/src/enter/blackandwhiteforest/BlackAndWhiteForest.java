package enter.blackandwhiteforest;

import enter.blackandwhiteforest.api.IBAWFPlugin;
import enter.blackandwhiteforest.event.BAWFEventBus;
import enter.blackandwhiteforest.screen.ScreenGaming;
import enter.blackandwhiteforest.screen.ScreenMain;
import enter.blackandwhiteforest.screen.ScreenSettings;
import enter.blackandwhiteforest.screen.ScreenWelcome;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

	public static SpriteBatch batch;
	public static Stage stage;
	public static ScalingViewport viewport;
	public static OrthographicCamera camera;
	// public static Sprite sprite;

	public static float totalDelta, delta;
	public static int width, height;

	/*
	 * public static int initTime = 0; public static final int MAX_INIT_TIME =
	 * 5; public static Image progressBar; public static boolean doesLoad =
	 * true;
	 */

	public static ScreenWelcome welcome;
	public static ScreenMain main;
	public static ScreenSettings settings;
	public static ScreenGaming gaming;

	public static Sound click1, click2, click3;

	public static enum ResourceType {
		texture, sound, music
	}

	public static FileHandle getPath(ResourceType type, String fileName) throws IllegalArgumentException {
		switch (type) {
		case texture:
			return Gdx.files.internal("textures/" + fileName);
		case sound:
			return Gdx.files.internal("sounds/" + fileName);
		case music:
			return Gdx.files.internal("musics/" + fileName);
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
			switch (ran.nextInt(4)) {
			case 1:
				return click1.play(volume);
			case 2:
				return click2.play(volume);
			case 3:
				return click3.play(volume);
			default:
				return playSound(SoundType.click);
			}
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

	private BlackAndWhiteForest() {
	}

	@Override
	public void init() {
		totalDelta = 0;
		delta = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		click1 = Gdx.audio.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "click.mp3"));
		click2 = Gdx.audio.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "hat.mp3"));
		click3 = Gdx.audio.newSound(BlackAndWhiteForest.getPath(ResourceType.sound, "ignite.mp3"));

		batch = new SpriteBatch();
		camera = new OrthographicCamera(width, height);
		camera.position.set(camera.viewportWidth / 2F, camera.viewportHeight / 2F, 0);
		viewport = new ScalingViewport(Scaling.stretch, width, height, camera);
		stage = new Stage(viewport, batch);
		stage.setDebugAll(true);
		// initTime++;
	}

	@Override
	public void create() {
		welcome = new ScreenWelcome();
		main = new ScreenMain();
		settings = new ScreenSettings();
		gaming = new ScreenGaming();

		init();
		welcome.init();
		setScreen(welcome);
		main.init();
		settings.init();
		gaming.init();

		Gdx.input.setInputProcessor(stage);

		FileHandle pluginFolder = Gdx.app.getType().equals(ApplicationType.Desktop) ? Gdx.files.local("plugins/")
				: Gdx.files.external("BlackAndWhiteForest/plugins/");
		if (!pluginFolder.exists())
			pluginFolder.mkdirs();
		FileHandle[] jars = pluginFolder.list(".jar");
		for (int i = 0; i < jars.length; i++) {
			try {
				URL url = jars[i].file().toURI().toURL();
				URLClassLoader loader = new URLClassLoader(new URL[] { url });
				File file = new File(jars[i].file().getAbsolutePath() + ".properties");
				Properties props = new Properties();
				if (file.exists()) {
					props.load(new FileInputStream(file));
					String className = props.getProperty("mainClass", "");
					if (className != "" || className != null) {
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
				loader.close();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor((float) (2 - FI), (float) (2 - FI), (float) (2 - FI), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		delta = Gdx.graphics.getDeltaTime();
		totalDelta += delta;
	}
}
