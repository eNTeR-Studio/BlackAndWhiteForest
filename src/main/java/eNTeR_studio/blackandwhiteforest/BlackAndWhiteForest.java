package eNTeR_studio.blackandwhiteforest;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.EventBus;

import eNTeR_studio.blackandwhiteforest.event.BAWFWillStartEvent;

public class BlackAndWhiteForest extends StateBasedGame {
	public static int width = 640;
	public static int height = 480;
	public static int fps = 30;
	public static final EventBus bawfEventBus = new EventBus();
	public static final String gameName = "Black And White Forest";
	
	private BlackAndWhiteForest() {
		this(gameName);
	}

	public BlackAndWhiteForest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		//System.out.println(new File(BlackAndWhiteForest.class.getResource("").getFile()+"/../../../../resources/assets/fxzjshm/textures/icon/icon.png").getPath());
		bawfEventBus.register(new BlackAndWhiteForest());
		try {
			AppGameContainer app = new AppGameContainer(
					new BlackAndWhiteForest());
			app.setDisplayMode(width, height, false);
			app.setTargetFrameRate(fps);
			System.out.println("AppGameContainer is starting.");
			bawfEventBus.post(new BAWFWillStartEvent(app));
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		this.addState(StatePool.stateShowMaker);
		this.addState(StatePool.stateMain);
	}
	/**It doesn't works.
	 * @return PI(3.14159265358979323846)*/
	public static double calculatePI() {
		double a[] = new double[11];
		double b[] = new double[11];
		double c[] = new double[11];
		double s[] = new double[11];
		double pi[] = new double[11];
		a[0] = 1.0D;
		b[0] = 1.0D / Math.sqrt(2);
		s[0] = 0.5D;

		for (int k = 1; k < 10; k++) {
			a[k] = (a[k - 1] + b[k - 1]) / 2.0D;
			b[k] = Math.sqrt(a[k - 1] * b[k - 1]);
			c[k] = ((a[k]) * (a[k])) - ((b[k]) * (b[k]));
			s[k] = s[k - 1] - ((2 ^ k) * c[k]);
			pi[k] = 2.0D * a[k] * a[k] / s[k];
		}

		return pi[pi.length - 1];
	}
}
