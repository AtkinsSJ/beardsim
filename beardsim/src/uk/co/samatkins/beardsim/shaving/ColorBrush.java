package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.beardsim.shaving.Beard.Hair;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class ColorBrush extends Tool {
	
	private Color color;
	
	private ShapeRenderer shapeRenderer;

	public ColorBrush(Color color) {
		super();
		this.color = color;
		
		shapeRenderer = new ShapeRenderer();
		setSize(20, 10);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.identity();
		shapeRenderer.translate(getX(), getY(), 0);
		shapeRenderer.rotate(0, 0, 1, getRotation());
		
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(color);
		shapeRenderer.filledRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	@Override
	protected void useOnHair(Hair hair, Vector2 movement) {
		hair.setColor(color);
	}

}
