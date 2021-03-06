package uk.co.samatkins.beardsim;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "beardsim";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 640;
		
		new LwjglApplication(new BeardSim(), cfg);
	}
}
