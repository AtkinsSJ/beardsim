package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.Entity;
import uk.co.samatkins.RotatedRectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Beard extends Entity {
	
	private Hair[][] hairs;
	private boolean[][] canGrow;
	private int hairsX;
	private int hairsY;
	
	private final float hairSpacing = 5;
	
	private ShapeRenderer shapeRenderer;

	public Beard(Polygon mask, Polygon mouth) { //int x, int y, int width, int height) {
		shapeRenderer = new ShapeRenderer();
		
		Rectangle area = mask.getBoundingRectangle();		
		setBounds(area.x, area.y, area.width, area.height);
		
		hairsX = (int) Math.ceil(getWidth() / hairSpacing);
		hairsY = (int) Math.ceil(getHeight() / hairSpacing);
		
		hairs = new Hair[hairsX][hairsY];
		canGrow = new boolean[hairsX][hairsY];
//		for (int i=0; i<hairsX; i++) {
//			for (int j=0; j<hairsY; j++) {
//				hairs[i][j] = 10;
//				canGrow[i][j] = true;
//			}
//		}
//		
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
					canGrow[x][y] = true;
					hairs[x][y] = new Hair(10);
				} else {
					canGrow[x][y] = false;
				}
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
		shapeRenderer.setColor(getColor());
		float x1,x2,y1,y2;
		float xOff, yOff;
		for (int i=0; i<hairsX; i++) {
			x1 = getX() + (hairSpacing * i);
//			x2 = x1;// + hairSpacing/2;
			
			for (int j=0; j<hairsY; j++) {
				if (canGrow(i,j)) {
					y1 = getY() + (hairSpacing * j);
//					y2 = y1 - hairs[i][j].getLength();
					
					xOff = (float) (hairSpacing/2 * Math.sin(i*j));
					yOff = (float) (hairSpacing/2 * Math.cos(i*j));
					
					hairs[i][j].draw(shapeRenderer, x1 + xOff, y1 + yOff);
							
//					shapeRenderer.line(
//							x1 + xOff, y1 + yOff,
//							x2 + xOff, y2 + yOff
//					);
				}
			}
		}
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	public boolean canGrow(int x, int y) {
		return canGrow[x][y];
	}
	
	public void grow(float amount) {
		for (int i=0; i<hairsX; i++) {
			for (int j=0; j<hairsY; j++) {
				if (canGrow(i,j)) {
					hairs[i][j].grow(amount);
				}
			}
		}
	}
	
	/**
	 * Slice `amount` off the hair at the given coordinate
	 * @param x
	 * @param y
	 * @param amount
	 */
	public void shavePoint(int x, int y, float amount) {
		// Is coord invalid?
		if (x < 0 || x >= hairsX || y < 0 || y >= hairsY) {
			return;
		}
		
		// No hair here, ignore!
		if (!canGrow(x,y)) {
			return;
		}
		
		hairs[x][y].shave(amount);
	}

	public void shave(RotatedRectangle area) {
		Rectangle bounds = area.getBoundingRectangle();
		Polygon poly = area.getPolygon();
		
		int startX = (int) Math.floor((bounds.x - getX()) / hairSpacing) - 1;
		int startY = (int) Math.floor((bounds.y - getY()) / hairSpacing) - 1;
		int endX = (int) Math.ceil(bounds.width / hairSpacing) + startX + 2;
		int endY = (int) Math.ceil(bounds.height / hairSpacing) + startY + 2;
		
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				if (poly.contains(getX() + (x*hairSpacing), getY() + (y*hairSpacing))) {
					shavePoint(x,y, 1);
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
				if (canGrow(i,j)) {
					difference[i][j] = Math.abs(
							hairs[i][j].getLength()
							- (canGrow(hairsX-i-1, j)
									? hairs[hairsX-i-1][j].getLength()
									: 0)
					);
					totalArea++;
				}
			}
		}
		
		for (int i=0; i<difference.length; i++) {
			for (int j=0; j<hairsY; j++) {
				if (canGrow(i,j) && difference[i][j] < 1) {
					runningTotal++;
				}
			}
		}
		
		return runningTotal / totalArea;
		
	}
	
	public class Hair {
		private float length;
		private float angle;
		
		public Hair(float length) {
			this(length, 270);
		}
		
		public Hair(float length, float angle) {
			this.length = length;
			this.angle = angle;
		}
		
		public void draw(ShapeRenderer shapeRenderer, float x, float y) {

			float x2 = x,
					y2 = y - length;
					
			shapeRenderer.line( x, y, x2, y2 );
		}
		
		public float getLength() {
			return length;
		}
		
		public float getAngle() {
			return angle;
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
