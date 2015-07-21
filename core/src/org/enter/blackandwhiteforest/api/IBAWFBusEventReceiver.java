package org.enter.blackandwhiteforest.api;

import org.enter.blackandwhiteforest.event.BAWFEvent;

public interface IBAWFBusEventReceiver {
	void receiveEvent(BAWFEvent event);
}
