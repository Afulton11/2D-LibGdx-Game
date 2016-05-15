package com.main.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * ExInterface.java
 * 
 * @Author Andrew Fulton
 * Created on: May 10, 2016 at 3:10:01 PM
 */
public class ExInterface extends Hud{

	private Hud backgroundHud;
	private Array<Hud> huds;
	
	public ExInterface(float screenX, float screenY, int width, int height) {
		super(screenX, screenY);
		huds = new Array<Hud>();
		this.width = width;
		this.height = height;
	}
	
	public ExInterface(float screenX, float screenY, Hud backgroundHud) {
		super(screenX, screenY);
		this.backgroundHud = backgroundHud;
		huds = new Array<Hud>();
	}
	
	public ExInterface(float screenX, float screenY, int width, int height, Hud backgroundHud) {
		super(screenX, screenY);
		this.backgroundHud = backgroundHud;
		this.width = width;
		this.height = height;
		huds = new Array<Hud>();
	}
	
	public void render(SpriteBatch hudBatch) {
		backgroundHud.render(hudBatch);
		for(Hud hud : huds) {
			hud.render(hudBatch);
		}
	}
	
	public void update(float delta) {
		backgroundHud.update(delta);
		for(Hud hud : huds) {
			hud.update(delta);
		}
	}
	
	/**
	 * creates a new texture and adds it to the interface.
	 * @param texture - texture to render on the hud
	 * @param x - x position of the hud, x = 0 is the bottom left of the Interface
	 * @param y - y position of the hud, y = 0 is the bottom left of the Interface
	 */
	public void addHud(Texture texture, float x, float y) {
		huds.add(new TextureHud(x + this.xPos, y + this.yPos, texture));
	}
	
	/**
	 * creates a new texture and adds it to the interface.
	 * @param texture - texture to render on the hud
	 * @param x - x position of the hud, x = 0 is the bottom left of the Interface
	 * @param y - y position of the hud, y = 0 is the bottom left of the Interface
	 * @param width - width of the sprite
	 * @param height - height of the sprite
	 */
	public void addHud(Texture texture, float x, float y, int width, int height) {
		huds.add(new TextureHud(x + this.xPos, y + this.yPos, width, height, texture));
	}
	
	public void addHud(Hud hud) {
		huds.add(hud);
	}
	
	public void addButton(Texture up, Texture down, float x, float y, int width, int height) {
		huds.add(new HudButton(x + this.xPos, y + this.yPos, width, height, up, down));
	}
	
	public void setPosition(Vector2 position) {
		this.xPos  = position.x;
		this.yPos = position.y;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public void setY(int y) {
		this.yPos = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBackgroundHud(Hud bgHud) {
		this.backgroundHud = bgHud;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			for(Hud h : huds) {
				h.startUpdating();
				h.startRendering();
			}
			startUpdating();
			startRendering();
		} else {
			for(Hud h : huds) {
				h.stopUpdating();
				h.stopRendering();
			}
			stopUpdating();
			stopRendering();
		}
	}
	
	public void dispose() {
		for(Hud h : huds) {
			h.dispose();
		}
		backgroundHud.dispose();
	}
}
