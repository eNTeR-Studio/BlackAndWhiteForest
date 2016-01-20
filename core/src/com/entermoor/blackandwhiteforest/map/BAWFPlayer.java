package com.entermoor.blackandwhiteforest.map;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.entermoor.blackandwhiteforest.api.IBAWFPlayerMovementListener;

public class BAWFPlayer extends Image {

	public static float size = (float) (BAWFMap.pixalsPerBlock / Math.E);
	public static BAWFMap map = BAWFMap.INSTANCE;

	public static enum BAWFPlayerShape {
		circle, rectangle
	}

	public static class MovementPackage implements Disposable {

		public int transverse;
		public int longitudinal;

		public MovementPackage() {
		}

		/** @deprecated */
		public MovementPackage(int transverse, int longitudinal) {
			this.transverse = transverse;
			this.longitudinal = longitudinal;
		}

		public MovementPackage left() {
			return left(1);
		}

		public MovementPackage left(int count) {
			transverse -= count;
			return this;
		}

		public MovementPackage right() {
			return right(1);
		}

		public MovementPackage right(int count) {
			transverse += count;
			return this;
		}

		public MovementPackage up() {
			return up(1);
		}

		public MovementPackage up(int count) {
			longitudinal += count;
			return this;
		}

		public MovementPackage down() {
			return down(1);
		}

		public MovementPackage down(int count) {
			longitudinal -= count;
			return this;
		}

		@Override
		public void dispose() {
			transverse = 0;
			longitudinal = 0;
		}
	}

	public static float realX(int blockX) {
		return (float) (BAWFMap.INSTANCE.edgeWidth + BAWFMap.pixalsPerBlock * (blockX + 0.5));
	}

	public static float realY(int blockY) {
		return (float) (BAWFMap.INSTANCE.edgeWidth + BAWFMap.pixalsPerBlock * (blockY + 0.5));
	}

	public Color color;
	public BAWFPlayerShape shape;
	public int blockX, blockY;
	public List<MovementPackage> todoList = new LinkedList<MovementPackage>();
	public IBAWFPlayerMovementListener listener;

	public BAWFPlayer(Color color, BAWFPlayerShape shape, int x, int y, IBAWFPlayerMovementListener movementListener) {
		this.color = color;
		this.shape = shape;
		blockX = x;
		blockY = y;
		Pixmap pixmap = new Pixmap((int) (BAWFMap.pixalsPerBlock), (int) (BAWFMap.pixalsPerBlock),
				Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		switch (shape) {
		case circle:
			pixmap.fillCircle((int) (BAWFMap.pixalsPerBlock / 2), (int) (BAWFMap.pixalsPerBlock / 2),
					(int) (BAWFMap.pixalsPerBlock / 2));
			break;
		case rectangle:
			pixmap.fill();
			break;
		}
		setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
		setScaling(Scaling.stretch);
		setAlign(Align.center);
		// float realX = (float) (BAWFMap.INSTANCE.edgeWidth +
		// BAWFMap.pixalsPerBlock * (x + 0.5 - 1 / (2 * Math.E)));
		// float realY = (float) (BAWFMap.INSTANCE.edgeHeight +
		// BAWFMap.pixalsPerBlock * (y + 0.5 - 1 / (2 * Math.E)));
		setBounds();
		listener = movementListener;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		size = (float) (BAWFMap.pixalsPerBlock / Math.E);
		if (map.player[map.currentPlayerId] == this) {
			if (listener != null && listener.refresh(this))
				map.nextPlayer();
			for (MovementPackage movementPackage : todoList) {
				blockX += movementPackage.transverse;
				blockY += movementPackage.longitudinal;
				todoList.remove(movementPackage);
				movementPackage.dispose();
				Pools.get(MovementPackage.class).free(movementPackage);
			}
		}
		setBounds();
	}

	public void setBounds() {
		if (blockX < 0)
			blockX = 0;
		if (blockY < 0)
			blockY = 0;
		if (blockX > map.countX - 1)
			blockX = map.countX - 1;
		if (blockY > map.countY - 1)
			blockY = map.countY - 1;
		setBounds(realX(blockX), realY(blockY), size, size);
	}

}
