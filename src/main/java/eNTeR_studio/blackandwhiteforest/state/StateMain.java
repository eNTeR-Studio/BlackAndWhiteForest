package eNTeR_studio.blackandwhiteforest.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.StateIdPool;

public class StateMain extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		String strE = "Start";
		g.drawString(strE,
				(gc.getWidth() - gc.getDefaultFont().getWidth(strE)) / 2,
				(gc.getHeight() - gc.getDefaultFont().getHeight(strE)) / 2);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return StateIdPool.idStateMain.hashCode();
	}

}
