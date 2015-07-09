package eNTeR_studio.blackandwhiteforest.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;
import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.StateIdPool;
import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.StateRenderingEvent;
import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.StateUpdatingEvent;

public class StateMain extends BasicGameState {

	public float average;
	public float realX1;
	public float realY1;
	public float realX2;
	public float realY2;
	public int mouseX;
	public int mouseY;
	public boolean isMouseLeftButtonDown = false;
	public boolean wasMouseLeftButtonDown = false;
	Image start;
	Image startClicked;

	public boolean lastMouseState_start = false;
	public boolean presentMouseState_start = false;
	public boolean isMouseLeftButtonDown_start = false;

	public static float scaling = 8;

	@SuppressWarnings("deprecation")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		try {
			start = new Image(
					(InputStream) new FileInputStream(
							new File(
									BlackAndWhiteForest.textureFolder.getPath()
									+ (BlackAndWhiteForest.isWindowsOs ? "\\stateMain\\start.png"
											: "/stateMain/start.png"))), "start",
					false);
			startClicked = new Image(
					(InputStream) new FileInputStream(
							new File(
									BlackAndWhiteForest.textureFolder.getPath()
									+ (BlackAndWhiteForest.isWindowsOs ? "\\stateMain\\startClicked.png"
											: "/stateMain/startClicked.png"))),
					"startClicked", false);
		} catch (Exception e) {
			BlackAndWhiteForest.handleException(e, true);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateRenderingEvent(this,
				gc, sbg, g));
		g.setColor(Color.white);
		lastMouseState_start = presentMouseState_start;
		wasMouseLeftButtonDown = isMouseLeftButtonDown;
		average = (gc.getWidth() + gc.getHeight()) / 2;
		realX1 = (gc.getWidth() / 2) - (average / scaling);
		realY1 = (gc.getHeight() / 2) - (average / scaling);
		realX2 = (gc.getWidth() / 2) + (average / scaling);
		realY2 = (gc.getHeight() / 2) + (average / scaling);
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		isMouseLeftButtonDown = gc.getInput().isMouseButtonDown(
				Input.MOUSE_LEFT_BUTTON);
		if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)
				&& (realX1 < mouseX) && (mouseX < realX2) && (realY1 < mouseY)
				&& (mouseY < realY2)) {
			isMouseLeftButtonDown_start = true;
		} else if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)) {
			isMouseLeftButtonDown_start = false;
		}
		if ((isMouseLeftButtonDown) && (realX1 < mouseX) && (mouseX < realX2)
				&& (realY1 < mouseY) && (mouseY < realY2)
				&& (isMouseLeftButtonDown_start)) {
			presentMouseState_start = true;
			g.drawImage(startClicked, realX1, realY1, realX2, realY2, 0, 0,
					startClicked.getWidth(), startClicked.getHeight());
		} else {
			presentMouseState_start = false;
			g.drawImage(start, realX1, realY1, realX2, realY2, 0, 0,
					start.getWidth(), start.getHeight());
		}
		if ((realX1 < mouseX) && (mouseX < realX2) && (realY1 < mouseY)
				&& (mouseY < realY2) && lastMouseState_start
				&& !presentMouseState_start && (isMouseLeftButtonDown_start)) {
			String notice = "Add code when it is ready to make up next state.";
			System.out
					.println("Add code when it is ready to make up next state.");
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateUpdatingEvent(this,
				gc, sbg, delta));
	}

	@Override
	public int getID() {
		return StateIdPool.idStateMain.hashCode();
	}

}
