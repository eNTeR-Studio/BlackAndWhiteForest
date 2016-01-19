package com.entermoor.blackandwhiteforest.map;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.map.BAWFPlayer.BAWFPlayerShape;
import com.entermoor.blackandwhiteforest.util.BAWFCrashHandler;
import com.entermoor.blackandwhiteforest.util.HumanPlayerMovementListener;

public class BAWFMap {

	public static final BAWFMap INSTANCE = new BAWFMap();
	public static float pixalsPerBlock = 60;
	public static FileHandle file;

	public BAWFMapBlock[][] blocks;
	public int countX, countY;
	public float edgeWidth, edgeHeight;
	public BAWFPlayer player[]=new BAWFPlayer[1];
	public int currentPlayerId=0;

	private BAWFMap() {
	}

	public void load() {
		edgeWidth = (BlackAndWhiteForest.width % pixalsPerBlock) / 2 + BlackAndWhiteForest.width / 30;
		edgeHeight = (BlackAndWhiteForest.height % pixalsPerBlock) / 2 + BlackAndWhiteForest.height / 15;
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
		player[0]=new BAWFPlayer(Color.BLACK, BAWFPlayerShape.circle, 0, 0,new HumanPlayerMovementListener());
		////////// HumanPlayerMovementListener
		BlackAndWhiteForest.addProcessor(new GestureDetector(new GestureListener() {

			@Override
			public boolean zoom(float initialDistance, float distance) {
				return false;
			}

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				System.out.println("touchDown.");
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
					Vector2 pointer2) {
				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				// TODO Auto-generated method stub
				System.out.println("velocityX: " + velocityX + ", velocityY: " + velocityY);
				if (BAWFMap.INSTANCE.getCurrentPlayer().listener instanceof HumanPlayerMovementListener) {
					HumanPlayerMovementListener listener = (HumanPlayerMovementListener) BAWFMap.INSTANCE
							.getCurrentPlayer().listener;
					listener.velocityX = velocityX;
					listener.velocityY = velocityY;
				}
				return false;
			}
		}));
		//////////
		BlackAndWhiteForest.stage.addActor(player[0]);
	}

	/*public void load(FileHandle file) {
		if (!file.exists())
			load();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.file()));

		} catch (Exception e) {
			BAWFCrashHandler.handleCrash(e);
		}
	}*/
	
	public BAWFPlayer getCurrentPlayer(){
		return player[currentPlayerId];
	}
	
	public void nextPlayer(){
		currentPlayerId++;
		if(currentPlayerId>=player.length){
			currentPlayerId=0;
		}
	}
	
	//public Entry<>
}
