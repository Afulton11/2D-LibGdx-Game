package com.main.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.main.Main;

public final class Utils {

	/**
	 * @param x - screenX
	 * @param y - screenY
	 * @param width - int
	 * @param height - int
	 * @return true if the Input is currently in the given bounds.
	 */
	public static final boolean isInputInRect(float x, float y, int width, int height) {
		return (getInputX() >= x && getInputX() <= x + width && getInputY() <= y + height && getInputY() >= y);
	}
	
	public static final double getInputX() {
		return Gdx.input.getX() * Main.SCREEN_TO_PIXEL_RATIO_WIDTH;
	}
	
	public static final double getInputY() {
		double unflipped = Gdx.input.getY() * Main.SCREEN_TO_PIXEL_RATIO_HEIGHT;
		return Math.abs(unflipped - Main.V_HEIGHT);
	}
	
	/**
	 * returns the Average color of a texture.
	 * @param texture - texture
	 * @return the average color of all the Texture's pixels.
	 */
	public static final int getAverageColor(Texture texture) {
		texture.getTextureData().prepare();
		Pixmap pix = texture.getTextureData().consumePixmap();
		float a = 0, r = 0, g = 0, b = 0;
		int total = texture.getWidth() * texture.getHeight();
		for (int y = 0; y < texture.getHeight(); y++) {
			for(int x = 0; x < texture.getWidth(); x++) {
				Color c = new Color(pix.getPixel(x, y));
				r += c.r;
				g += c.g;
				b += c.b;
			}
		}
		Color avg = new Color(r / total, g / total, b / total, 0);
		System.out.println(avg.toIntBits());
		return avg.toIntBits();
	}
}
