package enter.blackandwhiteforest.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class BAWFMapBlock extends Actor{
	public Color color;
	public Image image;
	
	public BAWFMapBlock(Color color, float x,float y,float size){
		this.color=color;
		setX(x);
		setY(y);
		setWidth(size);
		setHeight(size);
		image=new Image(new SpriteDrawable().tint(color));
		image.setBounds(x, y, size, size);
	}
	
	@Override
	public void act(float delta){
		image.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		image.draw(batch, parentAlpha);
	}
}
