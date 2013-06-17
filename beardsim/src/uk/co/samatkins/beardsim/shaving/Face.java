package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Face extends Entity {
	
	private Beard beard;

	public Face(TextureRegion faceRegion) {
		this.sprite = new Sprite(faceRegion);
		setSize(sprite.getWidth(), sprite.getHeight());
		
		beard = new Beard(72, 93, 310, 175);
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
