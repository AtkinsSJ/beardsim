package uk.co.samatkins.beardsim.shaving;

import uk.co.samatkins.HSVColor;
import uk.co.samatkins.ui.UIHSVSelector;
import uk.co.samatkins.ui.UITextButton;
import uk.co.samatkins.ui.UIWindow;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ColorSelectionWindow extends UIWindow {
	
	private ShavingScene shavingScene;
	
	private UIHSVSelector colorSelector;
	private UITextButton closeButton;

	public ColorSelectionWindow(ShavingScene shavingScene, Skin skin) {
		super(skin);
		setVisible(false);
		
		this.shavingScene = shavingScene;
		
		colorSelector = new UIHSVSelector(skin);
		
		colorSelector.setValue(
				HSVColor.fromRGB(
						((ColorBrush)shavingScene.getTool("colorbrush"))
								.getColor()
				)
		);
		
		closeButton = new UITextButton("Done", skin);
		closeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				done();
			}
		});
		
		add(colorSelector).row();
		add(closeButton);
		
		pack();
		setFillParent(true);
	}
	
	public void done() {
		((ColorBrush)shavingScene.getTool("colorbrush"))
				.setColor(colorSelector.getValue().toRGB());
		
		setVisible(false);
	}
	

}
