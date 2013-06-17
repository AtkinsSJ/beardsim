package uk.co.samatkins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Overlay extends Entity {
	
	protected ShapeRenderer shapeRenderer;
	
	public Overlay() {
		this.shapeRenderer = new ShapeRenderer();
		this.setColor(0, 0, 0, 0);
		
		this.setWidth(Gdx.graphics.getWidth());
		this.setHeight(Gdx.graphics.getHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		Camera cam = this.scene.getCamera();
		this.setPosition(cam.position.x - (cam.viewportWidth/2),
				cam.position.y - (cam.viewportHeight/2));
		
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.shapeRenderer.setProjectionMatrix(cam.combined);
		this.shapeRenderer.begin(ShapeType.FilledRectangle);
		this.shapeRenderer.setColor(this.getColor());
		this.shapeRenderer.filledRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		this.shapeRenderer.end();
		
		batch.begin();
	}
	
	public void resize(float width, float height) {
		this.setSize(width, height);
	}

}
