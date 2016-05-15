package com.main.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * HudManager.java
 * 
 * @Author Andrew Fulton
 * Created on: May 9, 2016 at 7:46:50 PM
 */
public class HudManager {
	
	private Array<Hud> huds;
	
	/**
	 * Creates a new HudManager. The Hud Manager manages all the game huds, 
	 * each time a new Hud is created, it is added to hudManager.
	 */
	public HudManager() {
		huds = new Array<Hud>();
	}
	
	/**
	 * renders all huds.
	 * @param hudBatch - SpriteBatch that is using the 'hudCam'
	 */
	public void render(SpriteBatch hudBatch) {
		hudBatch.begin();
		for(Hud hud : huds) {
			if(hud.shouldRender()) hud.render(hudBatch);
		}
		hudBatch.end();
	}
	
	/**
	 * renders All Huds of the same class.
	 * @param hudBatch - SpriteBatch that is using the 'hudCam'
	 * @param hudClass - Class of the Huds to render.
	 */
	public void renderHudsOfType(SpriteBatch hudBatch, Class<Hud> hudClass) {
		hudBatch.begin();
		for(Hud hud : huds) {
			if(hud.shouldRender() && hud.getClass().equals(hudClass)) {
				hud.render(hudBatch);
			}
		}
		hudBatch.end();
	}
	
	/**
	 * updates all Hud.
	 * @param delta
	 */
	public void update(float delta) {
		for(Hud hud : huds) {
			if(hud.shouldUpdate())
				hud.update(delta);
		}
	}
	
	/**
	 * updates All Huds of the same class.
	 * @param delta
	 * @param hudClass  - Class of the Huds to render.
	 */
	public void updateHudsOfType(float delta, Class<Hud> hudClass) {
		for(Hud hud : huds) {
			if(hud.shouldUpdate() && hud.getClass().equals(hudClass)) {
				hud.update(delta);
			}
		}
	}
	
	/**
	 * adds a Hud to the huds Array.
	 * @param hud - Hud.
	 */
	public void addHud(Hud hud) {
		huds.add(hud);
	}

	public void dispose() {
		for(Hud h : huds) h.dispose();
	}

}
