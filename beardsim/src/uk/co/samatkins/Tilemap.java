package uk.co.samatkins;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tilemap extends Sprite {
	
	private int tileWidth, tileHeight;
	private int tilesAcross, tilesDown;
	
	private boolean[][] solidTiles;
	private int[][] tiles;
	
	private TextureRegion[] textureRegions;

	/**
	 * @param texture		Tile texture
	 * @param tileWidth		Width of each tile
	 * @param tileHeight	Height of each tile
	 * @param tilesAcross	How many tiles across the tilemap is
	 * @param tilesDown		How many tiles down the tilemap is
	 */
	public Tilemap(Texture texture, int tileWidth, int tileHeight, int tilesAcross, int tilesDown) {
		super();
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tilesAcross = tilesAcross;
		this.tilesDown = tilesDown;
		
		this.setTexture(texture);
		this.setSize(tileWidth * tilesAcross, tileHeight * tilesDown);
		
		this.solidTiles = new boolean[tilesAcross][tilesDown];
		this.tiles = new int[tilesAcross][tilesDown];
		
		this.textureRegions = new TextureRegion[( texture.getWidth() / tileWidth ) * ( texture.getHeight() / tileHeight )];
		this.generateTextureRegions();
	}
	
	public void clear() {
		this.solidTiles = new boolean[tilesAcross][tilesDown];
		this.tiles = new int[tilesAcross][tilesDown];
		this.setTileRect(0, tilesAcross-1, 0, tilesDown, -1);
	}
	
	private void generateTextureRegions() {
		int w = (int) Math.floor(this.getTexture().getWidth() / this.tileWidth);
		
		int tx, ty;
		
		for (int i=0; i < this.textureRegions.length; i++) {
			tx = (i % w) * this.tileWidth;
			ty = (int) Math.floor( i / w ) * this.tileHeight;
			this.textureRegions[i] = new TextureRegion(this.getTexture(), tx, ty, tileWidth, tileHeight);
		}
	}
	
	public void setTile(int x, int y, int tileIndex) {
		if (x >= 0 && x < this.tilesAcross && y >= 0 && y < this.tilesDown) {
			this.tiles[x][y] = tileIndex;
		}
	}
	
	public int getTile(int x, int y) {
		if (x >= 0 && x < this.tilesAcross && y >= 0 && y < this.tilesDown) {
			return this.tiles[x][y];
		}
		return -1;
	}
	
	public void setTileRect(int x1, int x2, int y1, int y2, int tileIndex) {
		for (int x=x1; x<=x2; x++) {
			for (int y=y1; y<=y2; y++) {
				this.setTile(x, y, tileIndex);
			}
		}
	}
	
	public void setSolid(int x, int y, boolean solid) {
		if (x >= 0 && x < this.tilesAcross && y >= 0 && y < this.tilesDown) {
			this.solidTiles[x][y] = solid;
		}
	}
	
	public boolean getSolid(int x, int y) {
		if (x >= 0 && x < this.tilesAcross && y >= 0 && y < this.tilesDown) {
			return this.solidTiles[x][y];
		}
		return false;
	}

	public void setSolidRect(int x1, int x2, int y1, int y2, boolean solid) {
		for (int x=x1; x<=x2; x++) {
			for (int y=y1; y<=y2; y++) {
				this.setSolid(x, y, solid);
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		this.draw(spriteBatch, 1.0f);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float alphaModulation) {
		// TODO: Only draw visible tiles
		
		for (int ty=0; ty < this.tilesDown; ty++) {
			for (int tx=0; tx < this.tilesAcross; tx++) {
				
				// Negative tile indices can be used to store data. They are not drawn.
				if (this.tiles[tx][ty] >= 0) {
					spriteBatch.draw(this.textureRegions[(this.tiles[tx][ty])],
							this.getX() + (tx * this.tileWidth),
							this.getY() + (ty * this.tileHeight));
				}
			}
		}
	}
	
	public int getTilesAcross() {
		return this.tilesAcross;
	}
	
	public int getTilesDown() {
		return this.tilesDown;
	}
	

}
