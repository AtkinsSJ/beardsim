package uk.co.samatkins.beardsim;

import uk.co.samatkins.Scene;

public class PlayScene extends Scene {

	public PlayScene(BeardSim game) {
		super(game);
		
		table.add("Today").colspan(2).row();
		table.add("Money").row();
		table.add("Happiness").row();
		table.add("Social").row();
	}

}
