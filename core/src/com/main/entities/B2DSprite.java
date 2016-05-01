package com.main.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.main.utils.Animation;
import com.main.utils.Constants;

public class B2DSprite {

	protected Body body;
	protected Animation animation;
	protected float width, height;
	protected float offsetX, offsetY;
	
	public B2DSprite(Body body) {
		this.body = body;
		animation = new Animation();
	}
	
	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}
	
	public void update(float delta) {
		animation.update(delta);
	}
	
	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(animation.getFrame(), body.getPosition().x * Constants.PPM - width / 2 + offsetX, body.getPosition().y * Constants.PPM - height / 2 + offsetY);
		batch.end();
	}
	
	public Body getBody() { return body; }
	public Vector2 getPosition() { return body.getPosition(); }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
}
