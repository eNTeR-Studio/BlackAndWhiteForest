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

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;
import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.StateIdPool;
import eNTeR_studio.blackandwhiteforest.api.BAWFUsefulFunctions;
import eNTeR_studio.blackandwhiteforest.event.BAWFEvent.*;

public class StateMain extends BasicGameState {

	public float average;
	public float realX1_start;
	public float realY1_start;
	public float realX2_start;
	public float realY2_start;
	public int mouseX;
	public int mouseY;
	public boolean isMouseLeftButtonDown = false;
	public boolean wasMouseLeftButtonDown = false;
	public Image start;
	public Image startClicked;
	public Image settings;
	public Image settingsClicked;

	public boolean lastMouseState_start = false;
	public boolean presentMouseState_start = false;
	public boolean isMouseLeftButtonDown_start = false;

	public static float scaling = 8.0F;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateInitingEvent(this, gc, sbg));
		try {
			start = new Image((InputStream) new FileInputStream(
					new File(BAWFUsefulFunctions.getResource("stateMain", "start.png"))), "start", false);
			startClicked = new Image(
					(InputStream) new FileInputStream(
							new File(BAWFUsefulFunctions.getResource("stateMain", "startClicked.png"))),
					"startClicked", false);
		} catch (Exception e) {
			BAWFUsefulFunctions.handleException(e, true);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateRenderingEvent(this, gc, sbg, g));
		g.setColor(Color.white);
		lastMouseState_start = presentMouseState_start;
		wasMouseLeftButtonDown = isMouseLeftButtonDown;
		average = (gc.getWidth() + gc.getHeight()) / 2;
		realX1_start = (gc.getWidth() / 2) - (average / scaling);
		realY1_start = (gc.getHeight() / 2) - (average / scaling);
		realX2_start = (gc.getWidth() / 2) + (average / scaling);
		realY2_start = (gc.getHeight() / 2) + (average / scaling);
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		isMouseLeftButtonDown = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)
				&& BAWFUsefulFunctions.isMouseInRectangle(realX1_start, realY1_start, realX2_start, realY2_start, mouseX, mouseY)) {
			isMouseLeftButtonDown_start = true;
		} else if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)) {
			isMouseLeftButtonDown_start = false;
		}
		if ((isMouseLeftButtonDown)
				&& BAWFUsefulFunctions.isMouseInRectangle(realX1_start, realY1_start, realX2_start, realY2_start, mouseX, mouseY)
				&& (isMouseLeftButtonDown_start)) {
			presentMouseState_start = true;
			g.drawImage(startClicked, realX1_start, realY1_start, realX2_start, realY2_start, 0, 0,
					startClicked.getWidth(), startClicked.getHeight());
		} else {
			presentMouseState_start = false;
			g.drawImage(start, realX1_start, realY1_start, realX2_start, realY2_start, 0, 0, start.getWidth(),
					start.getHeight());
		}
		if (BAWFUsefulFunctions.isMouseInRectangle(realX1_start, realY1_start, realX2_start, realY2_start, mouseX, mouseY)
				&& lastMouseState_start && !presentMouseState_start && (isMouseLeftButtonDown_start)) {
			String notice = "Add code when it is ready to make up next state.";
			System.out.println("Add code when it is ready to make up next state.");
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateUpdatingEvent(this, gc, sbg, delta));
	}

	@Override
	public int getID() {
		return StateIdPool.idStateMain.hashCode();
	}

}
