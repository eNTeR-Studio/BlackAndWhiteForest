package enter.blackandwhiteforest;

import enter.blackandwhiteforest.api.IBAWFPlugin;
import enter.blackandwhiteforest.event.BAWFEventBus;
import enter.blackandwhiteforest.screen.ScreenGaming;
import enter.blackandwhiteforest.screen.ScreenMain;
import enter.blackandwhiteforest.screen.ScreenSettings;
import enter.blackandwhiteforest.screen.ScreenWelcome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * 感谢 <a href="libgdx.badlogicgames.com">libGDX</a> 和
 * <a href="http://blog.sina.com.cn/weyingkj">奋斗小土豆丶</a> <br/>
 * <br/>
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/3.0/">
 * <img alt= "知识共享许可协议" style="border-width:0" src=
 * "https://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png" /></a><br />
 * 本作品采用
 * <a rel="license" href= "http://creativecommons.org/licenses/by-nc-sa/3.0/">
 * 知识共享署名-非商业性使用- 相同方式共享 3.0 国际许可协议</a>进行许可。
 * <hr/>
 * 
 * @author fxzjshm
 */
public class BlackAndWhiteForest extends Game implements IBAWFPlugin {
	/*
	 * Command: keytool -genkey -v -keystore key.keystore -alias key.keystore
	 * -keyalg RSA -keysize 2048 -validity 1000000 Password: 123456 Name:
	 * org.enter Organization: eNTeR_studio City: haimen Province: jiangsu
	 * Country: CN
	 * 
	 * Info:CN=eNTeR_studio.blackandwhiteforest, OU=eNTeR_studio,
	 * O=eNTeR_studio, L=haimen, ST=jiangsu, C=CN
	 */
	public static final double FI = (Math.sqrt(5) + 1) / 2;

	public static final BlackAndWhiteForest INSTANSE = new BlackAndWhiteForest();
	public static final BAWFEventBus BAWF_EVENT_BUS = new BAWFEventBus();

	public static SpriteBatch batch;
	public static Stage stage;
	public static ScalingViewport viewport;
	public static OrthographicCamera camera;
	public static Sprite sprite;

	public static float totalDelta;
	public static float delta;
	public static int width;
	public static int height;

	public static int initTime = 0;
	public static final int MAX_INIT_TIME = 6;
	public static Image progressBar;
	public static boolean doesLoad = true;
	public static int renderTime = 0;

	public static ScreenWelcome welcome;
	public static ScreenMain main;
	public static ScreenSettings settings;
	public static ScreenGaming gaming;

	private BlackAndWhiteForest() {
	}

	public static enum ResourceType {
		texture, sound, music
	}

	public static String getPath(ResourceType type, String fileName) throws IllegalArgumentException {
		switch (type) {
		case texture:
			return "fxzjshm/textures/" + fileName;
		case sound:
			return "fxzjshm/sounds/" + fileName;
		case music:
			return "fxzjshm/musics/" + fileName;
		default:
			throw new IllegalArgumentException("Type can't be null.");
		}
	}

	@Override
	public void init() {
		totalDelta = 0;
		delta = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		progressBar = new Image(new Texture("fxzjshm/textures/Progress_bar.png"));
		sprite = new Sprite();
		sprite.setPosition(0, 0);
		sprite.setSize(width, height);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(width, height);
		camera.position.set(camera.viewportWidth / 2F, camera.viewportHeight / 2F, 0);
		viewport = new ScalingViewport(Scaling.stretch, width, height, camera);
		stage = new Stage(viewport, batch);
		initTime++;
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
		stage.addActor(progressBar);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		if (doesLoad) {
			switch (initTime) {
			case 4: {
				main.init();
				break;
			}
			case 5: {
				settings.init();
				break;
			}
			case 6: {
				gaming.init();
				doesLoad = false;
				initTime = 0;
				break;
			}
			default: {
				initTime++;
				break;
			}
			}
			// Gdx.gl.glClearColor(0, 0, 0, 1);
			// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act();
			stage.draw();
			// if (initTime <= MAX_INIT_TIME)
			progressBar.setBounds(0, 0, width / MAX_INIT_TIME * initTime, height / 50);
			return;
		}
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		delta = Gdx.graphics.getDeltaTime();
		totalDelta += delta;
	}

}
