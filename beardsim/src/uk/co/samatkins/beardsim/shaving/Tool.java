package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import uk.co.samatkins.Entity;
import uk.co.samatkins.RotatedRectangle;
import uk.co.samatkins.beardsim.shaving.Beard.Hair;

public class Tool extends Entity {
	
	protected static final float scrollRotation = 5;

	public Tool() {
		
		addListener(new InputListener() {
			@Override
			public boolean scrolled(InputEvent event, float x, float y,
					int amount) {
				addAction(Actions.rotateBy(amount * scrollRotation, 0.1f));
				return true;
			}
		});
		
		setVisible(false);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition( Gdx.input.getX(), scene.getHeight()-Gdx.input.getY() );
	}

	public RotatedRectangle getRotatedRectangle() {
		return new RotatedRectangle(getWidth(), getHeight(), getX(), getY(), getRotation());
	}
	
	public void use(Beard beard, Vector2 mouseMovement) {
		RotatedRectangle area = getRotatedRectangle();
		Rectangle bounds = area.getBoundingRectangle();
		Polygon poly = area.getPolygon();
		float hairSpacing = beard.getHairSpacing();
		
		int startX = (int) Math.floor((bounds.x - beard.getX()) / hairSpacing) - 1;
		int startY = (int) Math.floor((bounds.y - beard.getY()) / hairSpacing) - 1;
		int endX = (int) Math.ceil(bounds.width / hairSpacing) + startX + 2;
		int endY = (int) Math.ceil(bounds.height / hairSpacing) + startY + 2;
		
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				if (poly.contains(beard.getX() + (x*hairSpacing), beard.getY() + (y*hairSpacing))) {
					if (beard.hairExists(x,y)) {
						useOnHair(beard.getHair(x, y), mouseMovement);
					}
				}
			}
		}
	}
	
	protected void useOnHair(Hair hair, Vector2 movement) {}

}
