package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Comb extends Tool {
	
	private ShapeRenderer shapeRenderer;

	public Comb() {
		super();
		
		shapeRenderer = new ShapeRenderer();
		setSize(80, 30);
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
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.filledRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	@Override
	public void use(Beard beard) {
		
	}
}
