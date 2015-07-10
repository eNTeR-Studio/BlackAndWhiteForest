package eNTeR_studio.blackandwhiteforest.event;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class BAWFEvent {

	public static class BAWFWillStartEvent {

		public AppGameContainer app;

		private BAWFWillStartEvent() {

		}

		public BAWFWillStartEvent(AppGameContainer app) {
			this();
			this.app = app;
		}
	}

	public static class BAWFFinishedEvent {

		public AppGameContainer app;

		private BAWFFinishedEvent() {

		}

		public BAWFFinishedEvent(AppGameContainer app) {
			this();
			this.app = app;
		}
	}
	
	public static class StateInitingEvent {
		
		GameState gameState;
		GameContainer gameContainer;
		StateBasedGame stateBasedGame;
		
		private StateInitingEvent() {
		}

		public StateInitingEvent(GameState gs, GameContainer gc,
				StateBasedGame sbg) {
			this();
			gameState = gs;
			gameContainer = gc;
			stateBasedGame = sbg;
		}
	}

	public static class StateRenderingEvent {

		GameState gameState;
		GameContainer gameContainer;
		StateBasedGame stateBasedGame;
		Graphics graphics;

		private StateRenderingEvent() {
		}

		public StateRenderingEvent(GameState gs, GameContainer gc,
				StateBasedGame sbg, Graphics g) {
			this();
			gameState = gs;
			gameContainer = gc;
			stateBasedGame = sbg;
			graphics = g;
		}
	}

	public static class StateUpdatingEvent {

		GameState gameState;
		GameContainer gameContainer;
		StateBasedGame stateBasedGame;
		int delta;

		private StateUpdatingEvent() {
		}

		public StateUpdatingEvent(GameState gs, GameContainer gc,
				StateBasedGame sbg, int delta) {
			this();
			gameState = gs;
			gameContainer = gc;
			stateBasedGame = sbg;
			this.delta = delta;
		}
	}

}
