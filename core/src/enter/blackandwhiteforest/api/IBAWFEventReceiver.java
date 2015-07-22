package enter.blackandwhiteforest.api;

import enter.blackandwhiteforest.event.BAWFEvent;

public interface IBAWFEventReceiver {
	void receiveEvent(BAWFEvent event);
}
