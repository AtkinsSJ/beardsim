package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import uk.co.samatkins.Entity;

public class Beard extends Entity {
	
	private float[][] hairs;
	private int hairsX = 20;
	private int hairsY = 20;
	
	private final float hairSpacing = 10;
	
	private ShapeRenderer shapeRenderer;

	public Beard() {
		shapeRenderer = new ShapeRenderer();
		
		hairs = new float[hairsX][hairsY];
		for (int i=0; i<hairsX; i++) {
			for (int j=0; j<hairsY; j++) {
				hairs[i][j] = 10;
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
		batch.end();
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.identity();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		float x1,x2,y1,y2;
		for (int i=0; i<hairsX; i++) {
			x1 = getX() + (hairSpacing * i);
			x2 = x1 + hairSpacing/2;
			
			for (int j=0; j<hairsY; j++) {
				y1 = getY() + (hairSpacing * j);
				y2 = y1 + hairs[i][j];
				
				shapeRenderer.line(x1, y1, x2, y2);
			}
		}
		
		shapeRenderer.end();
		
		batch.begin();
	}

}
