package com.entermoor.blackandwhiteforest.map;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.entermoor.blackandwhiteforest.api.IBAWFPlayerMovementListener;

public class BAWFPlayer extends Image {

	public static float size = (float) (BAWFMap.pixalsPerBlock / Math.E);
	public static BAWFMap map = BAWFMap.INSTANCE;

	public static enum BAWFPlayerShape {
		circle, rectangle
	}

	public static class MovementPackage {

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
	}

	public static float realX(int blockX) {
		return (float) (BAWFMap.INSTANCE.edgeWidth + BAWFMap.pixalsPerBlock * (blockX + 0.5 - 1 / (2 * Math.E)));
	}

	public static float realY(int blockY) {
		return (float) (BAWFMap.INSTANCE.edgeWidth + BAWFMap.pixalsPerBlock * (blockY + 0.5 - 1 / (2 * Math.E)));
	}

	public Color color;
	public BAWFPlayerShape shape;
	public int blockX, blockY;
	public List<MovementPackage> todoList = new LinkedList<MovementPackage>();
	public IBAWFPlayerMovementListener listener;

	public BAWFPlayer(Color color, BAWFPlayerShape shape, int x, int y) {
		this.color = color;
		this.shape = shape;
		blockX = x;
		blockY = y;
		Pixmap pixmap = new Pixmap(new Gdx2DPixmap((int) (BAWFMap.pixalsPerBlock / Math.E),
				(int) (BAWFMap.pixalsPerBlock / Math.E), Gdx2DPixmap.GDX2D_FORMAT_RGBA8888));
		pixmap.setColor(color);
		switch (shape) {
		case circle:
			pixmap.fillCircle((int) (BAWFMap.pixalsPerBlock / Math.E / 2), (int) (BAWFMap.pixalsPerBlock / Math.E / 2),
					(int) (BAWFMap.pixalsPerBlock / Math.E / 2));
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
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		size = (float) (BAWFMap.pixalsPerBlock / Math.E);
		if (map.player[map.currentPlayerId] == this) {
			if(listener.refresh())
				map.nextPlayer();
			for (MovementPackage movementPackage : todoList) {
				blockX += movementPackage.transverse;
				blockY += movementPackage.longitudinal;
			}
		}
		setBounds();
	}

	public void setBounds() {
		setBounds(realX(blockX), realY(blockY), size, size);
	}

}
