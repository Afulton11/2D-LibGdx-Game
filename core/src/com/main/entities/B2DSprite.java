package com.main.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.main.tiles.Map;
import com.main.tiles.Tile;
import com.main.tiles.TileCoord;
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
	
	public Vector2 getWorldPos() {
		return new Vector2(getBodyPositionX(), getBodyPositionY());
	}
	
	public int getTileX() {
		return (int) Math.floor(getBodyPositionX() / Constants.TILE_SIZE);
	}
	
	public int getTileY() {
		return (int) Math.floor(getBodyPositionY() / Constants.TILE_SIZE);
	}
	
	protected void addBodyPosition(float xAmt, float yAmt) {
		body.getPosition().x += xAmt / Constants.PPM;
		body.getPosition().y += xAmt / Constants.PPM;
	}
	
	protected void setBodyPosition(float x, float y) {
		body.getPosition().x = x / Constants.PPM + width / 2 + offsetX;
		body.getPosition().y = y / Constants.PPM + width / 2 + offsetX;
	}
	
	protected float getBodyPositionX() {
		return body.getPosition().x * Constants.PPM + offsetX;
	}
	
	protected float getBodyPositionY() {
		return body.getPosition().y * Constants.PPM + offsetX;
	}
}
