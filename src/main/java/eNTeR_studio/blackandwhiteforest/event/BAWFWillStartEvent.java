package eNTeR_studio.blackandwhiteforest.event;

import org.newdawn.slick.AppGameContainer;

public class BAWFWillStartEvent extends BAWFEvent {
	public AppGameContainer app;
	
	public BAWFWillStartEvent(AppGameContainer app){
		this.app = app;
	}
}
