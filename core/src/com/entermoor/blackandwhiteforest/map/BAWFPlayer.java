package com.entermoor.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class BAWFPlayer extends Image {

	public static enum BAWFPlayerShape {
		circle, rectangle
	}

	public Color color;
	public BAWFPlayerShape shape;
	public int blockX, blockY;

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
			pixmap.drawCircle((int) (BAWFMap.pixalsPerBlock / Math.E / 2), (int) (BAWFMap.pixalsPerBlock / Math.E / 2),
					(int) (BAWFMap.pixalsPerBlock / Math.E / 2));
			break;
		case rectangle:
			pixmap.drawRectangle((int) (BAWFMap.pixalsPerBlock / Math.E / 4),
					(int) (BAWFMap.pixalsPerBlock / Math.E / 4), (int) (BAWFMap.pixalsPerBlock / Math.E / 2),
					(int) (BAWFMap.pixalsPerBlock / Math.E / 2));
			break;
		}
		setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
		setScaling(Scaling.stretch);
		setAlign(Align.center);
		float realX = (float) (BAWFMap.INSTANCE.edgeWidth + BAWFMap.pixalsPerBlock * (x + 0.5 - 1 / (2 * Math.E)));
		float realY = (float) (BAWFMap.INSTANCE.edgeHeight + BAWFMap.pixalsPerBlock * (y + 0.5 - 1 / (2 * Math.E)));
		setBounds(realX, realY, (float) (BAWFMap.pixalsPerBlock / Math.E), (float) (BAWFMap.pixalsPerBlock / Math.E));
	}

	/*
	 * public void draw() { if (shape == BAWFPlayerShape.circle) {
	 * block.pixmap.setColor(color);
	 * block.pixmap.drawCircle(block.pixmap.getWidth() / 2,
	 * block.pixmap.getHeight() / 2, (int) (block.getHeight() / Math.PI));
	 * block.pixmap.setColor(block.getColor()); } if (shape ==
	 * BAWFPlayerShape.rectangle) { block.pixmap.setColor(color);
	 * block.pixmap.drawRectangle(block.pixmap.getWidth() / 2,
	 * block.pixmap.getHeight() / 2, (int) (block.getWidth() / Math.PI), (int)
	 * (block.getHeight() / Math.PI)); block.pixmap.setColor(block.getColor());
	 * } // BlackAndWhiteForest.batch.begin(); //
	 * block.draw(BlackAndWhiteForest.batch, 0.0F); //
	 * BlackAndWhiteForest.batch.end(); BlackAndWhiteForest.batch.flush(); }
	 */

}
