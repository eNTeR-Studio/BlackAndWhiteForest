package enter.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import enter.blackandwhiteforest.BlackAndWhiteForest;
import enter.blackandwhiteforest.BlackAndWhiteForest.ResourceType;

public class BAWFMapBlock extends Image {
	
	public static TextureRegionDrawable black=new TextureRegionDrawable(new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "black.jpg"))));
	public static TextureRegionDrawable white=new TextureRegionDrawable(new TextureRegion(new Texture(BlackAndWhiteForest.getPath(ResourceType.texture, "white.jpg"))));

	public BAWFMapBlock(Color color, float x, float y, float size) {
		if (color.equals(Color.BLACK))
			setDrawable(black);
		else if (color.equals(Color.WHITE))
			setDrawable(white);
		else
			throw new IllegalArgumentException("Color must be black or white.");

		setScaling(Scaling.stretch);
		setAlign(Align.center);
		setBounds(x, y, size, size);
	}
}
