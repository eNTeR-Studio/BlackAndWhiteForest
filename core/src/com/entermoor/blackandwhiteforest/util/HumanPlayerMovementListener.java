package com.entermoor.blackandwhiteforest.util;

import com.entermoor.blackandwhiteforest.api.IBAWFPlayerMovementListener;
import com.entermoor.blackandwhiteforest.map.BAWFPlayer;
import com.entermoor.blackandwhiteforest.map.BAWFPlayer.MovementPackage;

public class HumanPlayerMovementListener implements IBAWFPlayerMovementListener {

	/** Left=(-1)*length, Right=(+1)*length */
	public float velocityX = 0;
	/** Up=(-1)*length, Down=(+1)*length */
	public float velocityY = 0;
	/** Left=-1, Right=+1 */
	public int keyTypedX = 0;
	/** Up=-1, Down=+1 */
	public int keyTypedY = 0;

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
		movementPackage.right(keyTypedX);
		movementPackage.down(keyTypedY);
		keyTypedX = 0;
		keyTypedY = 0;
		if (movementPackage.longitudinal == 0 && movementPackage.transverse == 0)
			return false;
		player.todoList.add(movementPackage);
		return true;
	}
}
