package enter.blackandwhiteforest.map;

import enter.blackandwhiteforest.BlackAndWhiteForest;

public class BAWFMap {
	
	public static final float PIXALS_PER_BLOCK = 60;
	
	public static BAWFMapBlock[][] blocks;
	public int countX, countY;
	
	public BAWFMap(){
		countX=(int) ((BlackAndWhiteForest.width-BlackAndWhiteForest.width%PIXALS_PER_BLOCK)/PIXALS_PER_BLOCK);
		countY=(int) ((BlackAndWhiteForest.height-BlackAndWhiteForest.height%PIXALS_PER_BLOCK)/PIXALS_PER_BLOCK);
		
	}
}
