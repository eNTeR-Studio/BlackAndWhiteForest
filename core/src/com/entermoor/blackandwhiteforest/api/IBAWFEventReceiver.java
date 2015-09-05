package com.entermoor.blackandwhiteforest.api;

import com.entermoor.blackandwhiteforest.event.BAWFEvent;

public interface IBAWFEventReceiver {
	void receiveEvent(BAWFEvent event);
}
