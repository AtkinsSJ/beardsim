package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import uk.co.samatkins.Entity;

public class Razor extends Entity {
	
	private ShapeRenderer shapeRenderer;
	private final float scrollRotation = 5;

	public Razor() {
		shapeRenderer = new ShapeRenderer();
		setSize(50, 20);
		
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
	public void draw(SpriteBatch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
		batch.end();
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.identity();
		shapeRenderer.translate(getX(), getY(), 0);
		shapeRenderer.rotate(0, 0, 1, getRotation());
		
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.filledRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition( Gdx.input.getX(), scene.getHeight()-Gdx.input.getY() );
	}

}
