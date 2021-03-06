package com.entermoor.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class BAWFMapBlock extends Image {

	/*public static TextureRegionDrawable black = new TextureRegionDrawable(
			new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "black.jpg"))));
	public static TextureRegionDrawable white = new TextureRegionDrawable(
			new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "white.jpg"))));*/

	//public BAWFPlayer player;
	public int blockX, blockY;
	public Pixmap pixmap;

	public BAWFMapBlock(Color color, float pixelX, float pixalY, float size, int x, int y) {
		/*
		 * if (color.equals(Color.BLACK)) setDrawable(black); else if
		 * (color.equals(Color.WHITE)) setDrawable(white); else throw new
		 * IllegalArgumentException("Color must be black or white.");
		 */

		pixmap = new Pixmap((int) size, (int) size, Format.RGBA8888);
		pixmap.setColor(color);
		setColor(color);
		pixmap.fill();
		setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
		setScaling(Scaling.stretch);
		setAlign(Align.center);
		setBounds(pixelX, pixalY, size, size);
		blockX = x;
		blockY = y;
	}
}
