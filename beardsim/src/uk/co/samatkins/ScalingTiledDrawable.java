package uk.co.samatkins;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Scaling;

public class ScalingTiledDrawable extends TiledDrawable {
	
	protected Scaling scaling;

	public ScalingTiledDrawable(TextureRegionDrawable drawable, Scaling scaling) {
		super(drawable);
		this.scaling = scaling;
	}

	@Override
	public void draw(SpriteBatch batch, float x, float y, float width, float height) {
		TextureRegion region = getRegion();
		float regionWidth = region.getRegionWidth(),
				regionHeight = region.getRegionHeight();
		
		// Scale the texture first
		Vector2 size = scaling.apply(regionWidth, regionHeight, width, height);
		float imageWidth = size.x;
		float imageHeight = size.y;
		float xRatio = imageWidth / regionWidth,
				yRatio = imageHeight / regionHeight;
		
		float remainingX = width % imageWidth,
				remainingY = height % imageHeight;
		float startX = x, startY = y;
		float endX = x + width - remainingX,
				endY = y + height - remainingY;
		while (x < endX) {
			y = startY;
			while (y < endY) {
				batch.draw(region, x, y, imageWidth, imageHeight);
				y += imageHeight;
			}
			x += imageWidth;
		}
		
		Texture texture = region.getTexture();
		float u = region.getU();
		float v2 = region.getV2();
		if (remainingX > 0) {
			// Right edge.
			float u2 = u + remainingX / (texture.getWidth() * xRatio);
			float v = region.getV();
			y = startY;
			while (y < endY) {
				batch.draw(texture, x, y, remainingX, imageHeight, u, v2, u2, v);
				y += imageHeight;
			}
			// Upper right corner.
			if (remainingY > 0) {
				v = v2 - remainingY / (texture.getHeight() * yRatio);
				batch.draw(texture, x, y, remainingX, remainingY, u, v2, u2, v);
			}
		}
		if (remainingY > 0) {
			// Top edge.
			float u2 = region.getU2();
			float v = v2 - remainingY / (texture.getHeight() * yRatio);
			x = startX;
			while (x < endX) {
				batch.draw(texture, x, y, imageWidth, remainingY, u, v2, u2, v);
				x += imageWidth;
			}
		}
	}
}
