package uk.co.samatkins;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * Similar to Flashpunk's Entity.
 * 
 * Base class for all game objects. 
 * 
 * @author Sam
 *
 */
public class Entity extends Widget {
	
	public Scene scene;
	protected Sprite sprite;
	
	protected boolean spriteCentred = false;
	
	/**
	 * Whether to transform the sprite automatically based on the Entity's transformations.
	 */
	protected boolean transformSprite = true;

	public Entity() {

	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		this.added();
	}
	
	/**
	 * Function that's called after setScene. Override when you want to execute code
	 * as soon as the entity is added.
	 */
	protected void added() {
		
	}
	
	protected void setSpriteCentred(boolean centred) {
		this.spriteCentred = centred;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (this.isVisible() && (sprite != null)) {
			if (this.spriteCentred) {
				sprite.setPosition(this.getX() + (this.getWidth()/2) - (sprite.getWidth()/2),
						this.getY() + (this.getHeight()/2) - (sprite.getHeight()/2));
			} else {
				sprite.setPosition(this.getX(), this.getY());
			}
			
			if (transformSprite) {
				sprite.setRotation(this.getRotation());
				sprite.setScale(this.getScaleX(), this.getScaleY());
			}
			
			sprite.draw(batch, parentAlpha);
		}
	}
	
	public void setPosition(Vector2 position) {
		this.setPosition(position.x, position.y);
	}
	
	public void setPosition(Vector3 position) {
		this.setPosition(position.x, position.y);
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}
	
	public float getCentreX() {
		return getX() + (getWidth()/2);
	}
	
	public float getCentreY() {
		return getY() + (getHeight()/2);
	}

}
