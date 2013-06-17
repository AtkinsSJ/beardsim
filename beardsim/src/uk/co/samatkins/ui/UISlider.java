package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class UISlider extends Slider implements UIWidget {
	
	private String styleName;
	private boolean vertical;

	public UISlider(float min, float max, float stepSize, boolean vertical,
			Skin skin) {
		super(min, max, stepSize, vertical, skin);
		
		this.vertical = vertical;
		this.styleName = vertical ? "default-vertical" : "default-horizontal";
	}

	public UISlider(float min, float max, float stepSize, boolean vertical,
			Skin skin, String styleName) {
		super(min, max, stepSize, vertical, skin, styleName);

		this.styleName = styleName;
		this.vertical = vertical;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}
	
	public boolean isVertical() {
		return this.vertical;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), SliderStyle.class));
	}

}
