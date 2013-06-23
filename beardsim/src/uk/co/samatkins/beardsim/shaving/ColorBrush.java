package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.beardsim.shaving.Beard.Hair;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ColorBrush extends Tool {
	
	private Color color;
	
	private ShapeRenderer shapeRenderer;

	public ColorBrush(Skin skin, Color color) {
		super();
		this.color = color;
		
		shapeRenderer = new ShapeRenderer();
		setSize(20, 10);
		
		sprite = new Sprite(skin.getRegion("colorbrush"));
		setSpriteOffset(getWidth()/2, sprite.getHeight() - getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
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
	
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}

}
