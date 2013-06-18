package uk.co.samatkins.beardsim;

import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.shaving.Beard;
import uk.co.samatkins.beardsim.shaving.Face;
import uk.co.samatkins.beardsim.shaving.Razor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class PlayScene extends Scene {
	
	private Face face;
	private Beard beard;
	private Razor razor;
	
	private boolean shaving = false;

	public PlayScene(BeardSim game) {
		super(game);
		backgroundColor = Color.WHITE;
		
		face = new Face(game.getSkin().getRegion("face"));
		beard = face.getBeard();
		razor = new Razor();
	}
	
	@Override
	public void show() {
		super.show();
		add(face);
		add(razor);
		setScrollFocus(razor);
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.SPACE) {
			beard.grow(0.5f);
			return true;
		} else if (keyCode == Keys.S) {
			// Evaluate symmetry
			Gdx.app.debug("Beard Symmetry", "" + beard.evaluateSymmetry());
		}
		return super.keyDown(keyCode);
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
