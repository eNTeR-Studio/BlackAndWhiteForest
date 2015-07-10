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

	public int mouseX;
	public int mouseY;
	public boolean isMouseLeftButtonDown = false;
	public boolean wasMouseLeftButtonDown = false;

	public Image start;
	public Image startClicked;

	public float realX1_start;
	public float realY1_start;
	public float realX2_start;
	public float realY2_start;

	public Image settings;
	public Image settingsClicked;

	public float realX1_settings;
	public float realY1_settings;
	public float realX2_settings;
	public float realY2_settings;

	public boolean lastImageState_start = false;
	public boolean presentImageState_start = false;
	public boolean isMouseLeftButtonDown_start = false;

	public boolean lastImageState_settings = false;
	public boolean presentImageState_settings = false;
	public boolean isMouseLeftButtonDown_settings = false;

	public static float scaling_start = 8.0F;
	public static float scaling_settings = (float) (Math.PI + Math.E);

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateInitingEvent(this, gc, sbg));
		try {
			start = new Image((InputStream) new FileInputStream(new File(
					BAWFUsefulFunctions.getResource("assets", "fxzjshm", "textures", "statemain", "start.png"))),
					"start", false);
			startClicked = new Image((InputStream) new FileInputStream(new File(
					BAWFUsefulFunctions.getResource("assets", "fxzjshm", "textures", "statemain", "startClicked.png"))),
					"startClicked", false);
			settings = new Image((InputStream) new FileInputStream(new File(
					BAWFUsefulFunctions.getResource("assets", "fxzjshm", "textures", "statemain", "settings.png"))),
					"settings", false);
			settingsClicked = new Image((InputStream) new FileInputStream(new File(BAWFUsefulFunctions
					.getResource("assets", "fxzjshm", "textures", "statemain", "settingsClicked.png"))),
					"settingsClicked", false);
		} catch (Exception e) {
			BAWFUsefulFunctions.handleException(e, true);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateRenderingEvent(this, gc, sbg, g));
		g.setBackground(new Color(0.1F, 0.1F, 0.1F, 1.0F));
		g.setColor(Color.white);
		wasMouseLeftButtonDown = isMouseLeftButtonDown;
		average = (gc.getWidth() + gc.getHeight()) / 2;
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		isMouseLeftButtonDown = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		refreshStart(gc, g);
		refreshSettings(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		BlackAndWhiteForest.BAWF_EVENT_BUS.post(new StateUpdatingEvent(this, gc, sbg, delta));
	}

	@Override
	public int getID() {
		return StateIdPool.idStateMain.hashCode();
	}

	public void refreshStart(GameContainer gc, Graphics g) {
		realX1_start = (gc.getWidth() / 2) - (average / scaling_start);
		realY1_start = (gc.getHeight() / 2) - (average / scaling_start);
		realX2_start = (gc.getWidth() / 2) + (average / scaling_start);
		realY2_start = (gc.getHeight() / 2) + (average / scaling_start);

		lastImageState_start = presentImageState_start;
		if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown) && BAWFUsefulFunctions.isMouseInRectangle(realX1_start,
				realY1_start, realX2_start, realY2_start, mouseX, mouseY)) {
			isMouseLeftButtonDown_start = true;
		} else if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)) {
			isMouseLeftButtonDown_start = false;
		}
		if ((isMouseLeftButtonDown) && BAWFUsefulFunctions.isMouseInRectangle(realX1_start, realY1_start, realX2_start,
				realY2_start, mouseX, mouseY) && (isMouseLeftButtonDown_start)) {
			presentImageState_start = true;
			g.drawImage(startClicked, realX1_start, realY1_start, realX2_start, realY2_start, 0, 0,
					startClicked.getWidth(), startClicked.getHeight());
		} else {
			presentImageState_start = false;
			g.drawImage(start, realX1_start, realY1_start, realX2_start, realY2_start, 0, 0, start.getWidth(),
					start.getHeight());
		}
		if (BAWFUsefulFunctions.isMouseInRectangle(realX1_start, realY1_start, realX2_start, realY2_start, mouseX,
				mouseY) && lastImageState_start && !presentImageState_start && (isMouseLeftButtonDown_start)) {
			String notice = "Add code when it is ready to make up gaming state.";
			System.out.println("Add code when it is ready to make up gaming state.");
		}
	}

	@SuppressWarnings("deprecation")
	public void refreshSettings(GameContainer gc, Graphics g) {
		realX1_settings = 0;
		realY1_settings = gc.getHeight() - (average / scaling_settings);
		realX2_settings = average / scaling_settings;
		realY2_settings = gc.getHeight();

		lastImageState_settings = presentImageState_settings;
		if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown) && BAWFUsefulFunctions.isMouseInRectangle(
				realX1_settings, realY1_settings, realX2_settings, realY2_settings, mouseX, mouseY)) {
			isMouseLeftButtonDown_settings = true;
		} else if ((!wasMouseLeftButtonDown) && (isMouseLeftButtonDown)) {
			isMouseLeftButtonDown_settings = false;
		}
		if ((isMouseLeftButtonDown) && BAWFUsefulFunctions.isMouseInRectangle(realX1_settings, realY1_settings,
				realX2_settings, realY2_settings, mouseX, mouseY) && (isMouseLeftButtonDown_settings)) {
			presentImageState_settings = true;
			g.drawImage(settingsClicked, realX1_settings, realY1_settings, realX2_settings, realY2_settings, 0, 0,
					settingsClicked.getWidth(), settingsClicked.getHeight());
		} else {
			presentImageState_settings = false;
			g.drawImage(settings, realX1_settings, realY1_settings, realX2_settings, realY2_settings, 0, 0,
					settings.getWidth(), settings.getHeight());
		}
		if (BAWFUsefulFunctions.isMouseInRectangle(realX1_settings, realY1_settings, realX2_settings, realY2_settings,
				mouseX, mouseY) && lastImageState_settings && !presentImageState_settings
				&& (isMouseLeftButtonDown_settings)) {
			gc.setAlwaysRender(false);
			try {
				new FrameSettings(this).show();
			} catch (Exception e) {
				BAWFUsefulFunctions.handleException(e, false);
			}
			gc.setAlwaysRender(true);
		}
	}

}
