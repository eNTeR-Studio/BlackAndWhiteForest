package eNTeR_studio.blackandwhiteforest;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.EventBus;

public class BlackAndWhiteForest extends StateBasedGame{
	public static int width = 640;
	public static int height = 480;
	public static final EventBus bawfEventBus = new EventBus();
	public static final String gameName = "Black And White Forest";
	
	public BlackAndWhiteForest() {
        super(gameName);
    }
	
    public static void main(String[] args) {
    	//System.out.println( System.getProperty("java.library.path"));
    	bawfEventBus.register(new BlackAndWhiteForest());
    	try{
        AppGameContainer app = new AppGameContainer(new BlackAndWhiteForest());
        app.setDisplayMode(width, height, false);
        app.start();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}
