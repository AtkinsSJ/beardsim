package uk.co.samatkins.beardsim.shaving;

import java.text.DecimalFormat;

import uk.co.samatkins.Scene;
import uk.co.samatkins.beardsim.BeardSim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ShavingScene extends Scene {
	
	private Face face;
	private Beard beard;
	
	private ToolShelf toolShelf;
	private Tool currentTool;
	private Razor razor;
	private Comb comb;
	private ColorBrush colorBrush;
	
	private Label symmetryLabel;
	
	private Vector2 mousePos;
	
	public ShavingScene(BeardSim game) {
		super(game);
		backgroundColor = Color.WHITE;
		mousePos = new Vector2();
		
		Skin skin = game.getSkin();
		
		face = new Face(skin.getRegion("face"));
		beard = face.getBeard();
		
		toolShelf = new ToolShelf(game.getSkin());
		razor = new Razor(skin);
		comb = new Comb();
		colorBrush = new ColorBrush(Color.ORANGE);
		
		table.bottom();
		
		table.add("Symmetry: ");
		symmetryLabel = new Label("", game.getSkin());
		table.add(symmetryLabel).row();
		
		table.add(toolShelf).colspan(2);
	}
	
	@Override
	public void show() {
		add(face);
		super.show();
		add(razor);
		add(comb);
		add(colorBrush);
		
		setTool(colorBrush);
		
		setSymmetryLabel( beard.evaluateSymmetry());
	}
	
	public void setTool(Tool tool) {
		if (currentTool != null) {
			currentTool.setVisible(false);
		}
		
		currentTool = tool;
		currentTool.setVisible(true);
		setScrollFocus(currentTool);
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.SPACE) {
			beard.grow(0.5f);
			return true;
		} else if (keyCode == Keys.S) {
			// Evaluate symmetry
			Gdx.app.debug("Beard Symmetry", "" + beard.evaluateSymmetry());
		} else if (keyCode == Keys.NUM_1) {
			setTool(razor);
		} else if (keyCode == Keys.NUM_2) {
			setTool(comb);
		} else if (keyCode == Keys.NUM_3) {
			setTool(colorBrush);
		}
		return super.keyDown(keyCode);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		mousePos.set(x, this.getHeight() - y);
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		beard.shave(razor.getRotatedRectangle());
		currentTool.use(beard, new Vector2(screenX, getHeight() - screenY).sub(mousePos));
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		setSymmetryLabel(beard.evaluateSymmetry());
		return true;
	}
	
	public void setSymmetryLabel(float symmetry) {
		DecimalFormat fmt = new DecimalFormat("0.00%");
		symmetryLabel.setText(fmt.format(symmetry));
	}

}
