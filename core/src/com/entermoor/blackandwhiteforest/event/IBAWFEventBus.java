package com.entermoor.blackandwhiteforest.event;

/**It's not necessary, just for GWT.*/
public interface IBAWFEventBus {

	public boolean register(Object receiver);
	
	public boolean post(BAWFEvent event);
	
	public boolean unregister(Object receiver);
	
}
