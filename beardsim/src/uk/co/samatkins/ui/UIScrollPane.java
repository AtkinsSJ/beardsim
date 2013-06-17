package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIScrollPane extends ScrollPane implements UIWidget {
	
	private String styleName;

	public UIScrollPane(Actor widget, Skin skin) {
		super(widget, skin);
		this.styleName = "default";
	}

	public UIScrollPane(Actor widget, Skin skin, String styleName) {
		super(widget, skin, styleName);
		this.styleName = styleName;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), ScrollPaneStyle.class));
	}

}
