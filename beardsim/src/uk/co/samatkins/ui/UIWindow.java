package uk.co.samatkins.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class UIWindow extends Table implements UIWidget {
	
	private String styleName;
	private WindowStyle style;
	
	private boolean isModal = false;

	public UIWindow(Skin skin) {
		this(skin, "default");
	}

	public UIWindow(Skin skin, String styleName) {
		super(skin);
		
		this.setStyle(skin, styleName);
		this.styleName = styleName;
		
		setTouchable(Touchable.enabled);
		setClip(true);
		
		addCaptureListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				toFront();
				return false;
			}
		});
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				if (button == 0) {
//					dragging = isMovable && getHeight() - y <= getPadTop() && y < getHeight() && x > 0 && x < getWidth();
//					dragOffset.set(x, y);
//				}
//				return dragging || isModal;
				return isModal;
			}

			public void touchDragged (InputEvent event, float x, float y, int pointer) {
//				if (!dragging) return;
//				translate(x - dragOffset.x, y - dragOffset.y);
			}

			public boolean mouseMoved (InputEvent event, float x, float y) {
				return isModal;
			}

			public boolean scrolled (InputEvent event, float x, float y, int amount) {
				return isModal;
			}

			public boolean keyDown (InputEvent event, int keycode) {
				return isModal;
			}

			public boolean keyUp (InputEvent event, int keycode) {
				return isModal;
			}

			public boolean keyTyped (InputEvent event, char character) {
				return isModal;
			}
		});
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		Actor hit =  super.hit(x, y, touchable);
		
		if (hit == null && isModal &&
				(!touchable || getTouchable() == Touchable.enabled)) {
			return this;
		}
		
		return hit;
	}
	
	public boolean isModal() {
		return isModal;
	}
	
	public void setModal(boolean modal) {
		this.isModal = modal;
	}

	@Override
	public String getStyleName() {
		return this.styleName;
	}

	@Override
	public void reskin(Skin skin) {
		this.setStyle(skin.get(getStyleName(), WindowStyle.class));
	}
	
	public void setStyle(Skin skin, String styleName) {
		setStyle(skin.get(styleName, WindowStyle.class));
	}
	
	public void setStyle (WindowStyle style) {
		if (style == null) throw new IllegalArgumentException("style cannot be null.");
		this.style = style;
		setBackground(style.background);
//		titleCache = new BitmapFontCache(style.titleFont);
//		titleCache.setColor(style.titleFontColor);
//		if (title != null) setTitle(title);
		invalidateHierarchy();
	}

}
