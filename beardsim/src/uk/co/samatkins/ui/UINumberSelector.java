package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Pools;

public class UINumberSelector extends Table implements UIWidget {
	
	private String styleName;
	
	private int minValue;
	private int maxValue;
	private int value;
	
	UITextButton minusButton, plusButton;
	UILabel counterLabel;

	public UINumberSelector(int min, int max, Skin skin) {
		this(min, max, skin, "default", "default");
	}

	public UINumberSelector(int min, int max, Skin skin, String buttonStyleName, String labelStyleName) {
		super(skin);
		this.styleName = buttonStyleName;
		
		this.minValue = min;
		this.value = min;
		this.maxValue = max;
		
		minusButton = new UITextButton("-", skin, buttonStyleName);
		counterLabel = new UILabel(String.valueOf(this.value), skin, labelStyleName);
		plusButton = new UITextButton("+", skin, buttonStyleName);
		
		minusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				decrement();
			}
		});
		
		plusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				increment();
			}
		});
		
		this.add(minusButton).uniform().fill();
		this.add(counterLabel).pad(0, 5, 0, 5).expandX();
		this.add(plusButton).uniform().fill();
	}
	
	private void increment() {
		value++;
		if (value > maxValue) { value = maxValue; }
		
		counterLabel.setText(String.valueOf(value));
		
		this.fire(Pools.obtain(ChangeEvent.class));
	}
	
	private void decrement() {
		value--;
		if (value < minValue) { value = minValue; }
		
		counterLabel.setText(String.valueOf(value));
		
		this.fire(Pools.obtain(ChangeEvent.class));
	}
	
	public int getValue() {
		return this.value;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		
	}

}
