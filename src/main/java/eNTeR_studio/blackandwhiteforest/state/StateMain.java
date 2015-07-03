package eNTeR_studio.blackandwhiteforest.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eNTeR_studio.blackandwhiteforest.StatePool;

public class StateMain extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		String strE = "Start";
		// String strC = "开始";
		// float strDelta = (gc.getDefaultFont().getWidth(strE) +
		// gc.getDefaultFont().getWidth(strC)) / 2;
		g.drawString(strE,
				(gc.getWidth() - gc.getDefaultFont().getWidth(strE)) / 2,
				(gc.getHeight() - gc.getDefaultFont().getHeight(strE)) / 2);
		// g.drawString(strC, gc.getWidth() / 2, (gc.getHeight() / 2);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return StatePool.idStateMain;
	}

}
