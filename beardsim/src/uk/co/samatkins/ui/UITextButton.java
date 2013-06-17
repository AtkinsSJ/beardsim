package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UITextButton extends TextButton implements UIWidget {

	protected String styleName;
	protected boolean maintainAspectRatio = true;
	
	protected float aspectRatioWidth = 0, aspectRatioHeight = 0;
	
	public UITextButton(String text, Skin skin) {
		super(text, skin);
		this.styleName = "default";
	}

	public UITextButton(String text, Skin skin, String styleName) {
		super(text, skin, styleName);
		this.styleName = styleName;
	}
	
	public void setMaintainAspectRatio(boolean maintain) {
		maintainAspectRatio = maintain;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), TextButtonStyle.class));
	}
	
	@Override
	public void layout() {
		if (maintainAspectRatio && this.getStyle().up != null) {
			float wRatio = super.getPrefWidth() / getStyle().up.getMinWidth();
			float hRatio = super.getPrefHeight() / getStyle().up.getMinHeight();
			
			float ratio = Math.max(wRatio,  hRatio);
			
			aspectRatioWidth = ratio * getStyle().up.getMinWidth();
			aspectRatioHeight = ratio * getStyle().up.getMinHeight();
			
			invalidateHierarchy();
		}
		super.layout();
	}
	
	@Override
	public float getPrefWidth() {
		return Math.max(super.getPrefWidth(), aspectRatioWidth);
	}
	
	@Override
	public float getPrefHeight() {
		return Math.max(super.getPrefHeight(), aspectRatioHeight);
	}

}
