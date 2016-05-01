package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bullet implements Poolable {

	public Vector2 position;
	public boolean alive;
	
	/**
	 * Bullet Constructor, initializing variables.
	 */
	public Bullet() {
		this.position = new Vector2();
		this.alive = false;
	}
	
	/**
	 * Initialize the bullet. Call this method after getting a bullet from the pool
	 * @param posX - bulletX
	 * @param posY - bulletY
	 */
	public void init(float posX, float posY) {
		position.set(posX, posY);
		alive = true;
	}
	/**
	 *	Callback method when the object is freed. Automatically called by Pool.free()
	 *	Must reset every meaningful field of this bullet.
	 */
	@Override
	public void reset() {
		position.set(0, 0);
		alive = false;
	}
	
	/**
	 * Method called each frame, which updates the bullet.
	 * @param delta - Gdx graphics delta time.
	 */
	public void update(float delta) {
		//update bullet position.
		position.add(delta * 60, delta * 60);
		
		// if the bullet is out of the screen viewport, set it to dead.
		if(isOutOfScreen()) alive = false;
	}
	
	private boolean isOutOfScreen() {
		return (position.x < 0 || position.y < 0 || position.x > Gdx.graphics.getWidth() || position.y > Gdx.graphics.getHeight());
	}

}
