package enter.blackandwhiteforest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import enter.blackandwhiteforest.screen.ScreenMain;
import enter.blackandwhiteforest.screen.ScreenWelcome;

public class BlackAndWhiteForest extends Game {
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
		super.render();
		delta = Gdx.graphics.getDeltaTime();
		totalDelta += delta;
		//System.out.println(screen.getClass().getName());
		//System.out.println(totalDelta);
	}
}