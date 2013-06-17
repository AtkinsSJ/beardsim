package uk.co.samatkins.beardsim;

import com.badlogic.gdx.graphics.Color;

import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.shaving.Beard;
import uk.co.samatkins.beardsim.shaving.Razor;

public class PlayScene extends Scene {
	
	private Beard beard;
	private Razor razor;

	public PlayScene(BeardSim game) {
		super(game);
		backgroundColor = Color.WHITE;
		
		beard = new Beard();
		beard.setColor(Color.BLACK);
		razor = new Razor();
	}
	
	@Override
	public void show() {
		super.show();
		add(beard);
		add(razor);
		setScrollFocus(razor);
	}

}
