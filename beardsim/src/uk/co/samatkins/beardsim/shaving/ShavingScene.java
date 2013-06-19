package uk.co.samatkins.beardsim.shaving;

import java.text.DecimalFormat;

import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.BeardSim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShavingScene extends Scene {
	
	private Face face;
	private Beard beard;
	private Razor razor;
	
	private Label symmetryLabel;
	
	private boolean shaving = false;

	public ShavingScene(BeardSim game) {
		super(game);
		backgroundColor = Color.WHITE;
		
		face = new Face(game.getSkin().getRegion("face"));
		beard = face.getBeard();
		razor = new Razor();
		
		table.bottom();
		
		table.add("Symmetry: ");
		symmetryLabel = new Label("", game.getSkin());
		table.add(symmetryLabel).row();
	}
	
	@Override
	public void show() {
		add(face);
		super.show();
		add(razor);
		setScrollFocus(razor);
		
		setSymmetryLabel( beard.evaluateSymmetry());
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
		setSymmetryLabel(beard.evaluateSymmetry());
		return true;
	}
	
	public void setSymmetryLabel(float symmetry) {
		DecimalFormat fmt = new DecimalFormat("0.00%");
		symmetryLabel.setText(fmt.format(symmetry));
	}

}
