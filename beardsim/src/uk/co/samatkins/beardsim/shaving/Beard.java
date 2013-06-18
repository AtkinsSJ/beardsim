package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.Entity;
import uk.co.samatkins.RotatedRectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Beard extends Entity {
	
	private float[][] hairs;
	private boolean[][] canGrow;
	private int hairsX = 20;
	private int hairsY = 20;
	
	private final float hairSpacing = 5;
	
	private ShapeRenderer shapeRenderer;

	public Beard(Polygon mask, Polygon mouth) { //int x, int y, int width, int height) {
		shapeRenderer = new ShapeRenderer();
		
		Rectangle area = mask.getBoundingRectangle();		
		setBounds(area.x, area.y, area.width, area.height);
		
		hairsX = (int) Math.ceil(getWidth() / hairSpacing);
		hairsY = (int) Math.ceil(getHeight() / hairSpacing);
		
		hairs = new float[hairsX][hairsY];
		canGrow = new boolean[hairsX][hairsY];
		for (int i=0; i<hairsX; i++) {
			for (int j=0; j<hairsY; j++) {
				hairs[i][j] = 10;
				canGrow[i][j] = true;
			}
		}
		
		applyMask(mask, mouth);
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
			x2 = x1;// + hairSpacing/2;
			
			for (int j=0; j<hairsY; j++) {
				if (canGrow[i][j]) {
					y1 = getY() + (hairSpacing * j);
					y2 = y1 - hairs[i][j];
					
					xOff = (float) (hairSpacing/2 * Math.sin(i*j));
					yOff = (float) (hairSpacing/2 * Math.cos(i*j));
							
					shapeRenderer.line(
							x1 + xOff, y1 + yOff,
							x2 + xOff, y2 + yOff
					);
				}
			}
		}
		
		shapeRenderer.end();
		
		batch.begin();
	}
	
	public void grow(float amount) {
		for (int i=0; i<hairsX; i++) {
			for (int j=0; j<hairsY; j++) {
				if (canGrow[i][j]) {
					hairs[i][j] += amount;
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
		if (canGrow[x][y] == false) {
			return;
		}
		
		if (amount >= hairs[x][y]) {
			hairs[x][y] = 0;
			return;
		}
		
		hairs[x][y] -= amount;
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
				} else {
					canGrow[x][y] = false;
					hairs[x][y] = 0;
				}
			}
		}
	}
	
	/**
	 * Calculates how symmetrical the beard is, and returns a value between 0 and 1
	 * @return 0 = not at all symmetrical, 1 = perfect symmetry
	 */
	public float evaluateSymmetry() {
		float[][] difference = new float[hairsX/2][hairsY];
		int totalArea = 0;
		float runningTotal = 0;
		
		for (int i=0; i<difference.length; i++) {
			for (int j=0; j<hairsY; j++) {
				if (canGrow[i][j]) {
					difference[i][j] = Math.abs(hairs[i][j] - hairs[hairsX-i-1][j]);
					totalArea++;
				}
			}
		}
		
		for (int i=0; i<difference.length; i++) {
			for (int j=0; j<hairsY; j++) {
				if (canGrow[i][j] && difference[i][j] < 1) {
					runningTotal++;
				}
			}
		}
		
		return runningTotal / totalArea;
		
	}
}
