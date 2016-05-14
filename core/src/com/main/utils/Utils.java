package com.main.utils;

import com.badlogic.gdx.Gdx;
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
		double ratio = (double) Main.V_WIDTH / Gdx.graphics.getWidth();
		return Gdx.input.getX() * ratio;
	}
	
	public static final double getInputY() {
		double ratio = (double)Main.V_HEIGHT / Gdx.graphics.getHeight();
		double unflipped = Gdx.input.getY() * ratio;
		return Math.abs(unflipped - Main.V_HEIGHT);
	}
}
