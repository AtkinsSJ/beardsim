package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ToolShelf extends Table {
	
	private ButtonGroup buttonGroup;

	public ToolShelf(ShavingScene shavingScene, Skin skin) {
		super(skin);
		
		this.defaults().expand().uniform();
		
		setBackground("shelf");
		
		ToolButton razorButton = new ToolButton(skin, "razor", shavingScene);
		ToolButton combButton = new ToolButton(skin, "comb", shavingScene);
		ToolButton colorBrushButton = new ToolButton(skin, "colorbrush", shavingScene);
		
		this.add(razorButton);
		this.add(combButton);
		this.add(colorBrushButton);
		
		buttonGroup = new ButtonGroup(razorButton, combButton, colorBrushButton);
		razorButton.setChecked(true);
	}
	
	public class ToolButton extends Button {
		
		private Tool tool;
		private ShavingScene shavingScene;
		
		public ToolButton(Skin skin, String toolName, ShavingScene shavingScene) {
			super(skin.getDrawable(toolName), null, skin.getDrawable("blank"));
			
			this.shavingScene = shavingScene;
			this.tool = shavingScene.getTool(toolName);
			
			addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					super.clicked(event, x, y);
					Gdx.app.debug("ToolButton", "Clicked!");
				}
			});
		}
		
		@Override
		public void setChecked(boolean isChecked) {
			super.setChecked(isChecked);
			
			if (isChecked) {
				shavingScene.setTool(tool);
			}
		}
		
		@Override
		public float getPrefWidth() {
			return super.getPrefWidth() / 2;
		}
		
		@Override
		public float getPrefHeight() {
			return super.getPrefHeight() / 2;
		}
	}

}
