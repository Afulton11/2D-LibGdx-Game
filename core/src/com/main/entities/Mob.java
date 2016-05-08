package com.main.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mob extends Entity{
	
	public Mob(Sprite sprite, float x, float y, int width, int height) {
		super(sprite, x, y, width, height);
	}

	public abstract void render(SpriteBatch batch);
	
	public abstract void update(float delta);

}
