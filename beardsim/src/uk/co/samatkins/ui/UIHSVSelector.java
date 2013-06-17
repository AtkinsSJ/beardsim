package uk.co.samatkins.ui;

import uk.co.samatkins.HSVColor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Pools;

public class UIHSVSelector extends Table implements UIWidget {

	private String styleName;
	
	private UISlider hueSlider;
	private UISlider satSlider;
	private UISlider valSlider;
	
	private Table slidersTable;
	private Image colorPreview;
	
	
	private HSVColor color;

	public UIHSVSelector(Skin skin) {
		super(skin);
		this.styleName = "default-horizontal";
		
		this.colorPreview = new Image(skin, "blank");
		
		this.color = new HSVColor(0, 0, 0);
		
		this.hueSlider = new UISlider(0, 360, 1, false, skin, this.styleName);
		this.satSlider = new UISlider(0, 1, 0.01f, false, skin, this.styleName);
		this.valSlider = new UISlider(0, 1, 0.01f, false, skin, this.styleName);
		
		this.slidersTable = new Table(skin);
		this.slidersTable.pad(10);
		this.slidersTable.defaults().fill();
		
		this.defaults().expand().fill();
		this.add(this.colorPreview).minWidth(20);
		this.add(this.slidersTable);
		
		this.slidersTable.add( new UILabel("Hue", skin) );
		this.slidersTable.add(hueSlider).expand().row();
		this.slidersTable.add( new UILabel("Sat", skin) );
		this.slidersTable.add(satSlider).expand().row();
		this.slidersTable.add( new UILabel("Val", skin) );
		this.slidersTable.add(valSlider).expand().row();
		
		this.hueSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				event.handle();
				event.stop();
				color.setHue(((UISlider)actor).getValue());
				colorPreview.setColor(color.toRGB());
				fireChangeEvent();
			}
		});
		
		this.satSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				event.handle();
				event.stop();
				color.setSaturation(((UISlider)actor).getValue());
				colorPreview.setColor(color.toRGB());
				fireChangeEvent();
			}
		});
		
		this.valSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				event.handle();
				event.stop();
				color.setValue(((UISlider)actor).getValue());
				colorPreview.setColor(color.toRGB());
				fireChangeEvent();
			}
		});
	}
	
	private void fireChangeEvent() {
		this.fire(Pools.obtain(ChangeEvent.class));
	}
	
	public void setValue(HSVColor color) {
		this.color = color;
		
		// Adjust the sliders!
		this.hueSlider.setValue(color.getHue());
		this.satSlider.setValue(color.getSaturation());
		this.valSlider.setValue(color.getValue());
	}
	
	public HSVColor getValue() {
		return this.color;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		// Do nada
	}

}
