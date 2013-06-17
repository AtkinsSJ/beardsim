package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface UIWidget {
	
	public String getStyleName();
	
	public void reskin(Skin skin);
}
