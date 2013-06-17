package uk.co.samatkins.beardsim;

import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.shaving.Razor;

public class PlayScene extends Scene {
	
	private Razor razor;

	public PlayScene(BeardSim game) {
		super(game);
		
		razor = new Razor();
	}
	
	@Override
	public void show() {
		super.show();
		add(razor);
		setScrollFocus(razor);
	}

}
