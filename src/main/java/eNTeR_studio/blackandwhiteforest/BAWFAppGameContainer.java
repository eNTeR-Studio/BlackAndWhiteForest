package eNTeR_studio.blackandwhiteforest;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class BAWFAppGameContainer extends AppGameContainer {

	public BAWFAppGameContainer(Game game) throws SlickException {
		super(game);
	}

	@Override
	public void start() throws SlickException {
		try {
			setup();

			getDelta();
			while (running()) {
				gameLoop();
			}
		} finally {
			destroy();
		}
	}

}
