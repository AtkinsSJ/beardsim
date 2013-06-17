package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIButton extends Button implements UIWidget {
	
	protected String styleName;

	public UIButton(Skin skin) {
		super(skin);
		this.styleName = "default";
	}
	
	public UIButton(Skin skin, String styleName) {
		super(skin, styleName);
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		setStyle(skin.get(styleName, ButtonStyle.class));
	}

}
