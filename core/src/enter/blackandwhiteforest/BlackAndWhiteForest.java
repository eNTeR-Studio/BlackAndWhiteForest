package enter.blackandwhiteforest;

import enter.blackandwhiteforest.api.IBAWFPlugin;
import enter.blackandwhiteforest.event.BAWFEventBus;
import enter.blackandwhiteforest.screen.ScreenGaming;
import enter.blackandwhiteforest.screen.ScreenMain;
import enter.blackandwhiteforest.screen.ScreenSettings;
import enter.blackandwhiteforest.screen.ScreenWelcome;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
 * 感谢 <a href="libgdx.badlogicgames.com">libGDX</a> 和
 * <a href="http://blog.sina.com.cn/weyingkj">奋斗小土豆丶</a>
 * </p>
 * <p>
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/3.0/">
 * <img alt= "知识共享许可协议" style="border-width:0" src=
 * "https://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png" /></a><br />
 * 本作品采用
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/3.0/">
 * 知识共享署名-非商业性使用- 相同方式共享 3.0 国际许可协议</a>进行许可。
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
				File file = new File(jars[i].file().getAbsolutePath() + ".xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(file);
				NodeList nodeList = document.getElementsByTagName("*");
				for (int i1 = 0; i1 < nodeList.getLength(); i1++) {
					if (nodeList.item(i1).getNodeName().equals("mainClassName")) {
						String className = nodeList.item(i1).getChildNodes().item(0).getNodeValue();
						Class<?> aClass = loader.loadClass(className);
						Class<?>[] interfaces = aClass.getInterfaces();
						for (int j = 0; j < interfaces.length; j++) {
							if (interfaces[j].getName().equals(IBAWFPlugin.class.getName())) {
								IBAWFPlugin instance = (IBAWFPlugin) aClass.newInstance();
								instance.init();
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
		Gdx.gl.glClearColor((float) (FI - 1), (float) (FI - 1), (float) (FI - 1), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		delta = Gdx.graphics.getDeltaTime();
		totalDelta += delta;
	}
}
