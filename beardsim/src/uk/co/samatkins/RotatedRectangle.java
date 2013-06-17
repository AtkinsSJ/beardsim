package uk.co.samatkins;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RotatedRectangle {
	float width, height;
	Vector2 centre;
	float rotation;
	
	/**
	 * Create a rectangle, unrotated, centred on 0,0
	 * @param width
	 * @param height
	 */
	public RotatedRectangle(float width, float height) {
		this(width, height, 0, 0, 0);
	}
	
	/**
	 * Create a rectangle centred on x,y and rotated by rotation degrees
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public RotatedRectangle(float width, float height, float x, float y, float rotation) {
		this.width = width;
		this.height = height;
		this.centre = new Vector2(x, y);
		this.rotation = rotation;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public Vector2 getCentre() {
		return centre;
	}
	
	public float getX() {
		return centre.x;
	}
	
	public float getY() {
		return centre.y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	/**
	 * Get the 4 corner coordinates, as Vector2s, in the order:
	 * Top-left, top-right, bottom-right, bottom-left
	 * @return
	 */
	public Vector2[] getCorners() {
		Vector2[] corners = new Vector2[4];
		// Top-left
		corners[0] = new Vector2(centre).add(
				new Vector2(-width/2, -height/2).rotate(rotation)
		);
		// Top-right
		corners[1] = new Vector2(centre).add(
				new Vector2(width/2, -height/2).rotate(rotation)
		);
		// Bottom-right
		corners[2] = new Vector2(centre).add(
				new Vector2(width/2, height/2).rotate(rotation)
		);
		// Bottom-left
		corners[3] = new Vector2(centre).add(
				new Vector2(-width/2, height/2).rotate(rotation)
		);
		
		return corners;
	}
	
	/**
	 * Get the axis-aligned bounding rectangle.
	 * @return
	 */
	public Rectangle getBoundingRectangle() {
		Vector2[] corners = getCorners();
		float l = getX(),
			r = getX(),
			t = getY(),
			b = getY();
		
		for (Vector2 corner: corners) {
			l = Math.min(l, corner.x);
			r = Math.max(r, corner.x);
			t = Math.max(t, corner.y);
			b = Math.min(b, corner.y);
		}
		
		return new Rectangle(l, b, r-l, t-b);
	}
	
	/**
	 * Get an equivalent Polygon object.
	 * @return
	 */
	public Polygon getPolygon() {
		Vector2[] corners = getCorners();
		float[] vertices = new float[8];
		for (int i=0; i<corners.length; i++) {
			vertices[2*i] = corners[i].x;
			vertices[2*i + 1] = corners[i].y;
		}
		
		return new Polygon(vertices);
	}
	
}