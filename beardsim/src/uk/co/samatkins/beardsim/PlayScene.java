package uk.co.samatkins.beardsim;

import com.badlogic.gdx.graphics.Color;

import uk.co.samatkins.RotatedRectangle;
import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.shaving.Beard;
import uk.co.samatkins.beardsim.shaving.Razor;

public class PlayScene extends Scene {
	
	private Beard beard;
	private Razor razor;
	
	private boolean shaving = false;

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
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		shaving = true;
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		beard.shave(razor.getRotatedRectangle());
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		shaving = false;
		return true;
	}

}
