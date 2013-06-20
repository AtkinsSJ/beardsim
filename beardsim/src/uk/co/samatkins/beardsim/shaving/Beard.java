package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Beard extends Entity {
	
	private Hair[][] hairs;
	private int hairsX;
	private int hairsY;
	
	private final float hairSpacing = 5;
	
	private ShapeRenderer shapeRenderer;

	public Beard(Polygon mask, Polygon mouth) {
		shapeRenderer = new ShapeRenderer();
		
		Rectangle area = mask.getBoundingRectangle();		
		setBounds(area.x, area.y, area.width, area.height);
		
		hairsX = (int) Math.ceil(getWidth() / hairSpacing);
		hairsY = (int) Math.ceil(getHeight() / hairSpacing);
		
		hairs = new Hair[hairsX][hairsY];

		applyMask(mask, mouth);
	}
	
	/**
	 * Mark all areas outside the mask area as places that hair cannot grow.
	 * @param mask
	 */
	public void applyMask(Polygon mask, Polygon mouth) {
		float xPos, yPos;
		for (int x = 0; x < hairsX; x++) {
			xPos = getX() + (x*hairSpacing);
			for (int y = 0; y < hairsY; y++) {
				yPos = getY() + (y*hairSpacing);
				if (mask.contains(xPos, yPos) && !mouth.contains(xPos, yPos)) {
					hairs[x][y] = new Hair(10);
				}
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.identity();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(getColor());
		float x,y;
		float xOff, yOff;
		for (int i=0; i<hairsX; i++) {
			x = getX() + (hairSpacing * i);
			
			for (int j=0; j<hairsY; j++) {
				if (hairExists(i,j)) {
					y = getY() + (hairSpacing * j);
					
					xOff = (float) (hairSpacing/2 * Math.sin(i*j));
					yOff = (float) (hairSpacing/2 * Math.cos(i*j));
					
					hairs[i][j].draw(shapeRenderer, x + xOff, y + yOff);
				}
			}
		}
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	public float getHairSpacing() {
		return hairSpacing;
	}
	
	public boolean hairExists(int x, int y) {
		if (x < 0 || x >= hairsX || y < 0 || y >= hairsY) {
			return false;
		}
		return (hairs[x][y] != null);
	}
	
	public Hair getHair(int x, int y) {
		if (x < 0 || x >= hairsX || y < 0 || y >= hairsY) {
			return null;
		}
		return hairs[x][y];
	}
	
	/**
	 * Grow the entire beard
	 * @param amount
	 */
	public void grow(float amount) {
		for (int i=0; i<hairsX; i++) {
			for (int j=0; j<hairsY; j++) {
				if (hairExists(i,j)) {
					hairs[i][j].grow(amount);
				}
			}
		}
	}
	
	/**
	 * Calculates how symmetrical the beard is, and returns a value between 0 and 1
	 * @return 0 = not at all symmetrical, 1 = perfect symmetry
	 */
	public float evaluateSymmetry() {
		// TODO: If I don't add any more complexity to this evaluation,
		// the array can be removed, and only one loop used.
		
		float[][] difference = new float[hairsX/2][hairsY];
		int totalArea = 0;
		float runningTotal = 0;
		
		for (int i=0; i<difference.length; i++) {
			for (int j=0; j<hairsY; j++) {
				if (hairExists(i,j)) {
					difference[i][j] = Math.abs(
							hairs[i][j].getLength()
							- (hairExists(hairsX-i-1, j)
									? hairs[hairsX-i-1][j].getLength()
									: 0)
					);
					totalArea++;
				}
			}
		}
		
		for (int i=0; i<difference.length; i++) {
			for (int j=0; j<hairsY; j++) {
				if (hairExists(i,j) && difference[i][j] < 1) {
					runningTotal++;
				}
			}
		}
		
		return runningTotal / totalArea;
		
	}
	
	public class Hair {
		private float length;
		private float angle;
		private Color color;
		
		public Hair(float length) {
			this(length, 270, Color.BLACK);
		}
		
		public Hair(float length, float angle, Color color) {
			this.length = length;
			this.angle = angle;
			this.color = color;
		}
		
		public void draw(ShapeRenderer shapeRenderer, float x, float y) {
			
			// For x2,y2, take a line of 'length', and rotate it around x,y
			Vector2 end = new Vector2(length, 0);
			end.setAngle(angle);
			end.add(x,y);
			
			shapeRenderer.setColor(color);
			shapeRenderer.line( x, y, end.x, end.y );
		}
		
		public float getLength() {
			return length;
		}
		
		public float getAngle() {
			return angle;
		}
		
		public void setAngle(float angle) {
			this.angle = angle;
		}
		
		public Color getColor() {
			return color;
		}
		
		public void setColor(Color color) {
			this.color = color;
		}
		
		public void grow(float amount) {
			length += amount;
		}
		
		public void shave(float amount) {
			if (amount >= length) {
				length = 0;
				return;
			}
			
			length -= amount;
		}
	}
}
