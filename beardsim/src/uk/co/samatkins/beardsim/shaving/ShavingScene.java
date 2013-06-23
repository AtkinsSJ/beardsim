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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

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
	
	private ColorSelectionWindow colorWindow;
	
	public ShavingScene(BeardSim game) {
		super(game);
		backgroundColor = Color.WHITE;
		mousePos = new Vector2();
		
		Skin skin = game.getSkin();
		
		face = new Face(skin.getRegion("face"));
		beard = face.getBeard();
		
		razor = new Razor(skin);
		comb = new Comb(skin);
		colorBrush = new ColorBrush(skin, Color.ORANGE);
		
		toolShelf = new ToolShelf(this, game.getSkin());
		
		table.bottom();
		
		table.add("Symmetry: ");
		symmetryLabel = new Label("", game.getSkin());
		table.add(symmetryLabel).row();
		
		table.add(toolShelf).colspan(2);
		
		// Colour selection window
		colorWindow = new ColorSelectionWindow(this, skin);
	}
	
	@Override
	public void show() {
		super.show();
		add(face);
		face.toBack();
		add(razor);
		add(comb);
		add(colorBrush);
		
		addActor(colorWindow);
		
		setTool(razor);
		
		setSymmetryLabel( beard.evaluateSymmetry());
	}
	
	public void setTool(Tool tool) {
		if (currentTool != null) {
			currentTool.setVisible(false);
		}
		
		if (tool == colorBrush) {
			// Show colour picker
			colorWindow.toFront();
			colorWindow.setVisible(true);
		}
		
		currentTool = tool;
		currentTool.setVisible(true);
		setScrollFocus(currentTool);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			beard.grow(delta * 2.0f);
		}
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		super.touchDown(x, y, pointer, button);
		mousePos.set(x, this.getHeight() - y);
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		super.touchDragged(screenX, screenY, pointer);
//		beard.shave(razor.getRotatedRectangle());
		currentTool.use(beard, new Vector2(screenX, getHeight() - screenY).sub(mousePos));
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		super.touchUp(screenX, screenY, pointer, button);
		setSymmetryLabel(beard.evaluateSymmetry());
		return true;
	}
	
	public void setSymmetryLabel(float symmetry) {
		DecimalFormat fmt = new DecimalFormat("0.00%");
		symmetryLabel.setText(fmt.format(symmetry));
	}

	public Tool getTool(String toolName) {
		if (toolName.equals("razor")) {
			return razor;
		} else if (toolName.equals("comb")) {
			return comb;
		} else if (toolName.equals("colorbrush")) {
			return colorBrush;
		}
		
		return null;
	}

}
