package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIImageButton extends ImageButton implements UIWidget {
	
	protected String styleName;

	public UIImageButton(Skin skin) {
		super(skin);
		this.styleName = "default";
	}

	public UIImageButton(Skin skin, String styleName) {
		super(skin, styleName);
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), ImageButtonStyle.class));
	}

}
