package com.entermoor.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;

public class BAWFMap {

	public static final BAWFMap INSTANCE = new BAWFMap();
	public static float pixalsPerBlock = 60;

	public BAWFMapBlock[][] blocks;
	public int countX, countY;
	public float edgeWidth, edgeHeight;

	private BAWFMap() {
	}

	public void load() {
		System.out.println("Map is loading.");
		edgeWidth = (BlackAndWhiteForest.width % pixalsPerBlock) / 2;
		edgeHeight = (BlackAndWhiteForest.height % pixalsPerBlock) / 2;
		countX = (int) ((BlackAndWhiteForest.width - edgeWidth * 2) / pixalsPerBlock);
		countY = (int) ((BlackAndWhiteForest.height - edgeHeight * 2) / pixalsPerBlock);
		blocks = new BAWFMapBlock[countX][countY];
		boolean isWhite = false;
		for (int i = 0; i < countX; i++) {
			for (int j = 0; j < countY; j++) {
				if ((i + j) % 2 == 0)
					isWhite = true;
				else
					isWhite = false;
				blocks[i][j] = new BAWFMapBlock((isWhite ? Color.WHITE : Color.BLACK), i * pixalsPerBlock + edgeWidth,
						j * pixalsPerBlock + edgeHeight, pixalsPerBlock, i + 1, j + 1);
				BlackAndWhiteForest.stage.addActor(blocks[i][j]);
			}
		}
	}
}
