package eNTeR_studio.blackandwhiteforest.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.StatePool;

public class StateShowMaker extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		/*try {
			Image makerIcon = new Image((InputStream) new FileInputStream(new File(this.getClass().getResource("").getFile())), "", false);

			g.drawImage(makerIcon, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0,
					makerIcon.getWidth(), makerIcon.getHeight());
		} catch (Exception e) {

			e.printStackTrace();
		}*/
		String info = "Press eNTeR to skip.";
		g.drawString(info,
				(gc.getWidth() - gc.getDefaultFont().getWidth(info)) / 2,
				(float) (gc.getHeight() - (gc.getHeight() / Math.PI)));

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			sbg.enterState(StatePool.idStateMain, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return StatePool.idStateShowMaker;
	}

}
