package uk.co.samatkins.beardsim;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BeardSim extends Game {
	
	private Skin skin;
	
	private MenuScene menuScene;
	private PlayScene playScene;
	
	@Override
	public void create() {
		Gdx.graphics.setTitle("Beard Simulator");
		this.loadSkin();
		
		Gdx.input.setCatchBackKey(true);
		
		//TODO: Disable logging
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		this.setScreen(getPlayScene());
	}

	public Skin getSkin() {
		return this.skin;
	}
	
	private void loadSkin() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("packed.atlas"));
		BitmapFont font = new BitmapFont();
		skin = new Skin(atlas);
		skin.add("default-font", font);
		skin.load(Gdx.files.internal("skin.json"));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
///// SCENE ACCESSORS /////
	public MenuScene getMenuScene() {
		if (this.menuScene == null) {
			this.menuScene = new MenuScene(this);
		}
		
		return this.menuScene;
	}
	
	public PlayScene getPlayScene() {
		if (this.playScene == null) {
			this.playScene = new PlayScene(this);
		}
		
		return this.playScene;
	}
}
