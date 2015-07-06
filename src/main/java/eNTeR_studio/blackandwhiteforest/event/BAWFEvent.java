package eNTeR_studio.blackandwhiteforest.event;

import org.newdawn.slick.AppGameContainer;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest.BAWFToSaveObj;

public class BAWFEvent {
	
	public static class BAWFWillStartEvent {
		
		public AppGameContainer app;
		
		@SuppressWarnings("unused")
		private BAWFWillStartEvent(){
			
		}
		
		public BAWFWillStartEvent(AppGameContainer app){
			this.app = app;
		}
	}
	
	public static class BAWFFinishedEvent {
		
		public AppGameContainer app;
		
		private BAWFFinishedEvent(){
			
		}
		
		public BAWFFinishedEvent(AppGameContainer app){
			this();
			this.app = app;
		}
	}
	
	public static class BAWFToSaveEvent {
		
		public BAWFToSaveObj toSaveObj;
		
		private BAWFToSaveEvent(){
			
		}
		
		public BAWFToSaveEvent(BAWFToSaveObj toSaveObj){
			this();
			this.toSaveObj = toSaveObj;
		}
	}
	
}
