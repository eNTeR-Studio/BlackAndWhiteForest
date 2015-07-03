package eNTeR_studio.blackandwhiteforest;

import eNTeR_studio.blackandwhiteforest.state.*;

/**This is just a "pool" to save states and id of them.*/
public class StatePool {
	public static StateShowMaker stateShowMaker = new StateShowMaker();
	public static StateMain stateMain = new StateMain();
	
	public static int idStateShowMaker = stateShowMaker.hashCode();
	public static int idStateMain = stateMain.hashCode();
}
