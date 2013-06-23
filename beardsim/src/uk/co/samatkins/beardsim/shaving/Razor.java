package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.beardsim.shaving.Beard.Hair;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Razor extends Tool {

	public Razor(Skin skin) {
		super();
		
		setSize(50, 20);
		
		sprite = new Sprite(skin.getRegion("razor"));
		setSpriteOffset(getWidth()/2, sprite.getHeight() - getHeight()/2);
	}
	
	@Override
	protected void useOnHair(Hair hair, Vector2 movement) {
		hair.shave(1);
	}
}
