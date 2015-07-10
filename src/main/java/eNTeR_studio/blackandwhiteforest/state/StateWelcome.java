package eNTeR_studio.blackandwhiteforest.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;
import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.StateIdPool;
import eNTeR_studio.blackandwhiteforest.api.BAWFUsefulFunctions;
import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.*;

public class StateWelcome extends BasicGameState {

	Image makerIcon;
	public int totalDelta = 0;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			makerIcon = new Image((InputStream) new FileInputStream(new File(
					BAWFUsefulFunctions.getResource("assets", "fxzjshm", "textures", "icon", "icon_640_480.png"))),
					"Icon", false);

		} catch (Exception e) {
			BAWFUsefulFunctions.handleException(e, true);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateRenderingEvent(this, gc, sbg, g));
		if (gc.hasFocus()) {
			g.drawImage(makerIcon, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0, makerIcon.getWidth(),
					makerIcon.getHeight());
			g.setColor(Color.blue);
			/*
			 * String info = "Press eNTeR to skip."; String showDelta =
			 * "Total delta:" + String.valueOf(totalDelta); g.drawString(info,
			 * (gc.getWidth() - gc.getDefaultFont().getWidth(info)) / 2, (float)
			 * (gc.getHeight() - (gc.getHeight() / (Math.PI + Math.E))));
			 * g.drawString(showDelta, gc.getWidth() / 10, gc.getHeight() / 10);
			 */
			if ((totalDelta >= BlackAndWhiteForest.stateWelcomeDeltaTime)
					|| (gc.getInput().isKeyPressed(Input.KEY_ENTER))) {
				totalDelta = 0;
				sbg.enterState(StateIdPool.idStateMain.hashCode(), new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateUpdatingEvent(this, gc, sbg, delta));
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER) || (totalDelta >= BlackAndWhiteForest.stateWelcomeDeltaTime)) {
			sbg.enterState(StateIdPool.idStateMain.hashCode(), new FadeOutTransition(), new FadeInTransition());
		}
		this.totalDelta += delta;
	}

	@Override
	public int getID() {
		return StateIdPool.idStateWelcome.hashCode();
	}

}
