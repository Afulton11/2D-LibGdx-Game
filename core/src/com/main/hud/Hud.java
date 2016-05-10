package com.main.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Hud.java
 * 
 * The abstract class used for creating hud for the PlayScreen.
 * 
 * @Author Andrew Fulton
 * Created on: May 9, 2016 at 7:10:19 PM
 */
public abstract class Hud {

	private boolean update, render;
	
	protected float xPos, yPos;
	protected static HudManager manager;
	
	/**
	 * Creates a new Hud at the x, y location.
	 * @param x - screen coordinate X (Bottom Left = 0, GDX Coordinates)
	 * @param y - screen coordinate Y (Bottom Left = 0, GDX Coordinates)
	 */
	public Hud(float x, float y) {
		this.xPos = x;
		this.yPos = y;
		if(manager == null) {
			System.err.println("HUD CAN'T RENDER OR UPDATE HUD WITHOUT A HUD-MANAGER!");
			System.err.println("Please Instansiate the HudManager for all Huds by using Hud.setHudManager(HudManager hudManager).");
		}
		manager.addHud(this);
	}
	
	/**
	 * Sets the hudManager for all Hud's.
	 * @param hudManager
	 */
	public static void setHudManager(HudManager hudManager) {
		manager = hudManager;
	}
	
	/**
	 * Renders the Hud, handles inputs/clicks/animations..etc
	 * @param delta
	 */
	public abstract void update(float delta);
	/**
	 * draws/renders the Hud onto the screen.
	 * @param hudBatch
	 */
	public abstract void render(SpriteBatch hudBatch);
	
	public void setVisible(boolean visible) {
		if(visible) {
			startUpdating();
			startRendering();
		} else {
			stopUpdating();
			stopRendering();
		}
	}
	
	/**
	 * true if the hud should be rendered
	 * @return
	 */
	public boolean shouldRender() {
		return render;
	}
	
	/**
	 * true if the hud should be updated.
	 * @return
	 */
	public boolean shouldUpdate() {
		return update;
	}
	
	/**
	 * Start Updating a HUD so it can reply to actions
	 */
	private void startUpdating() {
		if(update) {
			System.err.println("Attempted to start an update on a HUD even though it is already being updated!");
			return;
		}
		update = true;
	}
	
	private void stopUpdating() {
		if(!update) {
			System.err.println("Attempted to stop an update on a HUD even though it isn't being updated!");
			return;
		}
		update = false;
	}
	
	/**
	 * start rendering the HUD.
	 */
	private void startRendering() {
		if(render) {
			System.err.println("Attempted to start a render on a HUD even though it is already being rendered!");
			return;
		}
		render = true;
	}
	
	/**
	 * stop rendering the HUD.
	 */
	private void stopRendering() {
		if(!render) {
			System.err.println("Attempted to stop a render on a HUD even though it isn't being rendered!");
			return;
		}
		render = false;
	}
	
}
