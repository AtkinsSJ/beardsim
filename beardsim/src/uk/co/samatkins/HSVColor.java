package uk.co.samatkins;

import com.badlogic.gdx.graphics.Color;

public class HSVColor {

	private float h, // 0 to 360
				  s, // 0 to 1
				  v; // 0 to 1
	
	/**
	 * @param hue 0 to 360
	 * @param saturation 0 to 1
	 * @param value 0 to 1
	 */
	public HSVColor(float hue, float saturation, float value) {
		this.h = hue;
		this.s = saturation;
		this.v = value;
	}
	
	public static HSVColor fromRGB(Color color) {
		int r = (int)(color.r * 255);
		int g = (int)(color.g * 255);
		int b = (int)(color.b * 255);
		
		float hue, sat, val;
		int chroma;
		
		int biggest = Math.max(Math.max(r, g), b);
		if (biggest == r) {
			chroma = r - Math.min(g, b);
			hue = (g-b)/chroma;
		} else if (biggest == g) {
			chroma = g - Math.min(r, b);
			hue = (b-r)/(chroma+2);
		} else if (biggest == b) {
			chroma = b - Math.min(r, g);
			hue = (r-g)/(chroma+4);
		} else {
			chroma = 0;
			hue = 0;
		}
		val = biggest;
		
		sat = chroma / val;
		
		return new HSVColor(hue, sat, val);
	}
	
	public Color toRGB(float alpha) {
		float r = 0, g = 0, b = 0;
		
		float chroma = this.v * this.s;
		float h1 = h/60,
				x = chroma * (1 - Math.abs((h1 % 2) - 1) );
		
		if ( 0 <= h1 && h1 < 1) {
			r = chroma;
			g = x;
		} else if ( 1 <= h1 && h1 < 2) {
			r = x;
			g = chroma;
		} else if ( 2 <= h1 && h1 < 3) {
			g = chroma;
			b = x;
		} else if ( 3 <= h1 && h1 < 4) {
			g = x;
			b = chroma;
		} else if ( 4 <= h1 && h1 < 5) {
			r = x;
			b = chroma;
		} else if ( 5 <= h1 && h1 < 6) {
			r = chroma;
			b = x;
		}
		
		return new Color(r, g, b, alpha);
	}
	
	public Color toRGB() {
		return this.toRGB(1);
	}
	
	public float getHue() {
		return this.h;
	}
	
	public void setHue(float hue) {
		this.h = hue;
	}
	
	public float getSaturation() {
		return this.s;
	}
	
	public void setSaturation(float saturation) {
		this.s = saturation;
	}
	
	public float getValue() {
		return this.v;
	}
	
	public void setValue(float value) {
		this.v = value;
	}
	
	@Override
	public String toString() {
		return "HSV: " + this.h + ", " + this.s + ", " + this.v;
	}
	
	public void rotate(float degrees) {
		this.h = (this.h + degrees) % 360;
	}

}
