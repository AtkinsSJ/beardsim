package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIList extends List implements UIWidget {
	
	private String styleName;

	public UIList(Object[] items, Skin skin) {
		super(items, skin);
		this.styleName = "default";
	}

	public UIList(Object[] items, Skin skin, String styleName) {
		super(items, skin, styleName);
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), ListStyle.class));
	}

}
