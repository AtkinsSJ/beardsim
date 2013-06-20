package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import uk.co.samatkins.Entity;
import uk.co.samatkins.RotatedRectangle;

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
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition( Gdx.input.getX(), scene.getHeight()-Gdx.input.getY() );
	}

	public RotatedRectangle getRotatedRectangle() {
		return new RotatedRectangle(getWidth(), getHeight(), getX(), getY(), getRotation());
	}

}
