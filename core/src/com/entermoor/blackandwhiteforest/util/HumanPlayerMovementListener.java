package com.entermoor.blackandwhiteforest.util;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.entermoor.blackandwhiteforest.BlackAndWhiteForest;
import com.entermoor.blackandwhiteforest.api.IBAWFPlayerMovementListener;

public class HumanPlayerMovementListener implements IBAWFPlayerMovementListener {

	@Override
	public boolean refresh() {
		String str = "add code here.";
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
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
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
				return false;
			}
		}));
		return false;
	}

}
