package com.entermoor.blackandwhiteforest.util;

import com.entermoor.blackandwhiteforest.api.IBAWFPlayerMovementListener;
import com.entermoor.blackandwhiteforest.map.BAWFPlayer;
import com.entermoor.blackandwhiteforest.map.BAWFPlayer.MovementPackage;

public class HumanPlayerMovementListener implements IBAWFPlayerMovementListener {

	public static float velocityX = 0;
	public static float velocityY = 0;

	@Override
	public boolean refresh(BAWFPlayer player) {
		String str = "add code here.";
		MovementPackage movementPackage = new MovementPackage();
		if (velocityX < 0 && Math.toDegrees(Math.asin(Math.abs(velocityY)
				/ /* The distance of the two points */Math.sqrt(velocityX * velocityX + velocityY * velocityY))) < 60)
			movementPackage.left();
		if (velocityY < 0 && Math.toDegrees(Math.asin(Math.abs(velocityY)
				/ /* The distance of the two points */Math.sqrt(velocityX * velocityX + velocityY * velocityY))) > 30)
			movementPackage.up();
		if (velocityX > 0 && Math.toDegrees(Math.asin(Math.abs(velocityY)
				/ /* The distance of the two points */Math.sqrt(velocityX * velocityX + velocityY * velocityY))) < 60)
			movementPackage.right();
		if (velocityY > 0 && Math.toDegrees(Math.asin(Math.abs(velocityY)
				/ /* The distance of the two points */Math.sqrt(velocityX * velocityX + velocityY * velocityY))) > 30)
			movementPackage.down();
		velocityX = 0;
		velocityY = 0;
		if (movementPackage.longitudinal == 0 && movementPackage.transverse == 0)
			return false;
		player.todoList.add(movementPackage);
		return true;
	}
}
