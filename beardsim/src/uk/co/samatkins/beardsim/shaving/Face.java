package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Face extends Entity {
	
	private Beard beard;

	public Face(TextureRegion faceRegion) {
		this.sprite = new Sprite(faceRegion);
		setSize(sprite.getWidth(), sprite.getHeight());
		
		Polygon hairMask = new Polygon(new float[]{
				73, 338,
				81, 234,
				118, 173,
				179, 109,
				249, 103,
				311, 142,
				364, 222,
				375, 337,
				336, 254,
				279, 270,
				180, 270,
				125, 249
		});
		Polygon mouth = new Polygon(new float[]{
				155, 230,
				180, 184,
				260, 181,
				297, 227,
				228, 238
		});

		beard = new Beard(hairMask, mouth);
		beard.setColor(Color.BLACK);
	}
	
	@Override
	protected void added() {
		super.added();
		this.scene.add(beard);
	}
	
	public Beard getBeard() {
		return this.beard;
	}

}
