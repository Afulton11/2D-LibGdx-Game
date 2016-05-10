package com.main.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * TestHud.java
 * 
 * @Author Andrew Fulton
 * Created on: May 9, 2016 at 7:43:41 PM
 */
public class TestHud extends Hud {

	private Texture texture;
	
	/**
	 * creates a new TestHud
	 * @param x - screen X (Bottom Left = 0, GDX Coordinates)
	 * @param y - screen Y (Bottom Left = 0, GDX Coordinates)
	 * @param texture - texture/image to render the TestHud as.
	 */
	public TestHud(float x, float y, Texture texture) {
		super(x, y);
		Pixmap map = new Pixmap(128, 128, Format.RGBA8888);
		map.setColor(Color.FIREBRICK);
		map.fill();
		this.texture = new Texture(map);
		map.dispose();
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public void render(SpriteBatch hudBatch) {
		hudBatch.draw(texture, xPos, yPos);
	}

}
