package com.main.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class RightClickInterface {

	//TODO: Finish the right-click gui.
	
	private Array<String> options;
	private ExInterface clickInterface;
	
	public RightClickInterface(Hud backgroundHud) {
		clickInterface = new ExInterface(0, 0, 64, 10, backgroundHud);
	}
	
	
	public void render(SpriteBatch hudBatch) {
		clickInterface.render(hudBatch);
	}
	
	public void addOption(String Option) {
		options.add(Option);
	}
}
