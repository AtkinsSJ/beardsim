package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.RotatedRectangle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Razor extends Tool {
	
	private ShapeRenderer shapeRenderer;

	public Razor() {
		super();
		
		shapeRenderer = new ShapeRenderer();
		setSize(50, 20);
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
	public void use(Beard beard) {
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
						beard.getHair(x,y).shave(1);
					}
				}
			}
		}
	}
}
