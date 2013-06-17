package uk.co.samatkins.beardsim.shaving;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class Face extends Table {

	public Face(TextureRegion faceRegion) {
		Image faceImage = new Image(faceRegion);
		faceImage.setScaling(Scaling.fill);
		faceImage.setAlign(Align.center);
		add(faceImage).expand().fill();
		
		this.setFillParent(true);
	}
	
	

}
