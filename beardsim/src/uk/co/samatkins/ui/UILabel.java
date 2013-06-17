package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UILabel extends Label implements UIWidget {
	
	private String styleName;

	public UILabel(CharSequence text, Skin skin) {
		super(text, skin);
		this.styleName = "default";
	}
	
	public UILabel(CharSequence text, Skin skin, String styleName) {
		super(text, skin, styleName);
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), LabelStyle.class));
	}

}
