package com.main.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * TestHud.java
 * 
 * @Author Andrew Fulton
 * Created on: May 9, 2016 at 7:43:41 PM
 */
public class TextureHud extends Hud {
	
	/**
	 * creates a new TextureHud
	 * @param x - screen X (Bottom Left = 0, GDX Coordinates)
	 * @param y - screen Y (Bottom Left = 0, GDX Coordinates)
	 * @param texture - texture/image to render the TestHud as.
	 */
	public TextureHud(float x, float y, Texture texture) {
		super(x, y);
		this.setTexture(texture);
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}
	
	/**
	 * creates a new TextureHud
	 * @param x - screen X (Bottom Left = 0, GDX Coordinates)
	 * @param y - screen Y (Bottom Left = 0, GDX Coordinates)
	 * @param texture - texture/image to render the TestHud as.
	 */
	public TextureHud(float x, float y, int width, int height, Texture texture) {
		super(x, y);
		this.setTexture(texture);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update(float delta) {}

	@Override
	public void render(SpriteBatch hudBatch) {
		hudBatch.draw(getTexture(), xPos, yPos, width, height);
	}

}
