package uk.co.samatkins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class CSSColor {

	public static Color fromString(String input) {
		
		Color c = Color.BLACK;
		
		Gdx.app.debug("Color", "Parsing: " + input);

		if (input.charAt(0) == '#') { // Hex!
			if (input.length() == 4) { // #xxx
				c = new Color(
						Integer.parseInt(input.substring(1, 2), 16) / 15.0f,
						Integer.parseInt(input.substring(2, 3), 16) / 15.0f,
						Integer.parseInt(input.substring(3, 4), 16) / 15.0f,
						1
				);
			} else { // #xxxxxx
				c = new Color(
						Integer.parseInt(input.substring(1, 3), 16) / 255.0f,
						Integer.parseInt(input.substring(3, 5), 16) / 255.0f,
						Integer.parseInt(input.substring(5, 7), 16) / 255.0f,
						1
				);
			}
			
		} else if (input.matches("?irgb(.+)")) {

		} else {
			Gdx.app.debug("Color", "Didn't parse colour.");
		}
		
		return c;
	}

}
