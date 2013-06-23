package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.beardsim.shaving.Beard.Hair;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Comb extends Tool {

	public Comb(Skin skin) {
		super();
		setSize(80, 30);

		sprite = new Sprite(skin.getRegion("comb"));
		setSpriteOffset(getWidth()/2, getHeight()/2);
	}
	
	@Override
	protected void useOnHair(Hair hair, Vector2 movement) {
		hair.setAngle(movement.angle());
	}
}
