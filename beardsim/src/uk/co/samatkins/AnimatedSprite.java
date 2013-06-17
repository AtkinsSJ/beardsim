package uk.co.samatkins;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimatedSprite extends Sprite {
	
	private int frameWidth;
	private int frameHeight;
	
	private int framesAcross;
	
	private Hashtable<String, int[]> animations;
	private int[] currentAnimation;
	private int currentAnimationFrame;
	
	private float timePerFrame;
	private float timeStep;
	
	public AnimatedSprite(Texture texture, int frameWidth, int frameHeight, int framesPerSecond) {
		super(texture, frameWidth, frameHeight);
		
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		this.framesAcross = (int) Math.floor(texture.getWidth() / frameWidth);
		
		this.animations = new Hashtable<String, int[]>();
		this.timePerFrame = 1f / framesPerSecond;
		this.timeStep = 0;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float alphaModulation) {
		
		// Do nothing if no animation not set
		if (this.currentAnimation != null) {
			// Update frame
			this.timeStep += Gdx.graphics.getDeltaTime();
			if (this.timeStep > this.timePerFrame) {
				this.timeStep -= this.timePerFrame;
				
				// Next frame
				this.showNextFrame();
			}
		}
		
		super.draw(spriteBatch, alphaModulation);
	}
	
	public void addAnimation(String name, int[] frames) {
		this.animations.put(name, frames);
	}
	
	public void play(String name) {
		this.currentAnimation = this.animations.get(name);
		this.currentAnimationFrame = 0;
		
		// Immediately set texture region
		this.showNextFrame();
	}
	
	protected void showNextFrame() {
		this.currentAnimationFrame++;
		if (this.currentAnimationFrame >= this.currentAnimation.length) {
			this.currentAnimationFrame = 0;
		}
		
		boolean flipX = this.isFlipX(),
				flipY = this.isFlipY();
		
		// Adjust texture region
		int frame = this.currentAnimation[this.currentAnimationFrame];
		int fx = (frame % this.framesAcross) * this.frameWidth;
		int fy = (int) Math.floor(frame / this.framesAcross) * this.frameHeight;
		this.setRegion(fx, fy, this.frameWidth, this.frameHeight);
		
		this.flip(flipX, flipY);
	}
	
	/**
	 * Set whether X and Y are flipped, because the default is confusing.
	 */
	@Override
	public void flip(boolean x, boolean y) {
		super.flip( (this.isFlipX() != x), (this.isFlipY() != y) );
	}

}
