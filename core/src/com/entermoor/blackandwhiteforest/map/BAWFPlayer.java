package com.entermoor.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;

public class BAWFPlayer {
	
	public static enum BAWFPlayerShape{
		circle,rectangle
	}
	
	public Color color;
	public BAWFPlayerShape shape;
	public BAWFMapBlock block;
	
	public BAWFPlayer(Color color, BAWFPlayerShape shape, BAWFMapBlock block){
		this.color=color;
		this.shape=shape;
		this.block=block;
	}
	
	public void draw() {
		if(shape==BAWFPlayerShape.circle){
			block.pixmap.drawCircle(block.pixmap.getHeight()/2, block.pixmap.getHeight()/2, (int) (block.getHeight()/Math.PI));
		}
	}
	
}
