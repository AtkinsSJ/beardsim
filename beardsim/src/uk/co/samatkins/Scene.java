package uk.co.samatkins;

import uk.co.samatkins.beardsim.BeardSim;
import uk.co.samatkins.ui.UIWidget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

public abstract class Scene extends Stage implements Screen, GestureListener {
	
	public BeardSim game;
	protected InputMultiplexer inputs;
	protected Color backgroundColor = Color.BLACK;
	
	protected Table table;
	
	public Scene(BeardSim game) {
		super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		this.game = game;
		
		this.inputs = new InputMultiplexer(this);
		this.inputs.addProcessor(new GestureDetector(this));
		
		this.table = new Table(game.getSkin());
		this.table.setFillParent(true);
	}
	
	public void add(Entity entity) {
		this.addActor(entity);
		entity.setScene(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		this.update(delta);
		this.draw();
	}

	public void update(float delta) {
		this.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		this.setViewport(width, height, false);
		this.invalidateChildren(this.table);
		this.table.validate();
	}
	
	private void invalidateChildren(WidgetGroup group) {
		SnapshotArray<Actor> children = group.getChildren();
		
		Actor actor;
		for (int i=0; i<children.size; i++) {
			actor = children.get(i);
			if (actor instanceof Layout) {
				// UIWidgets are the best! :D
				((Layout)actor).invalidate();
			}
			
			if (actor instanceof WidgetGroup) {
				// If it's a group, recurse
				invalidateChildren((WidgetGroup)actor);
			}
		}
		
		group.invalidate();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.inputs);
		addActor(table);
	}

	@Override
	public void hide() {
		this.clear();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	/**
	 * Recursively refresh the skin for each Widget
	 * @param group
	 */
	public void reskin(WidgetGroup group) {
		Skin skin = game.getSkin();
		SnapshotArray<Actor> children = group.getChildren();
		
		/**
		 * This code is horrendously ugly. I'm so sorry. :(
		 * However, I can't think of a better way to do it.
		 */
		Actor actor;
		for (int i=0; i<children.size; i++) {
			actor = children.get(i);
			if (actor instanceof UIWidget) {
				// UIWidgets are the best! :D
				((UIWidget)actor).reskin(skin);
			}
			
			if (actor instanceof WidgetGroup) {
				// If it's a group, recurse
				reskin((WidgetGroup)actor);
			}
		}
		
		group.validate();
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.BACK){
			back();
			return true;
		}
		
		return super.keyDown(keyCode);
	}
	
	public void back() {
		
	}

//// GESTURES
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
