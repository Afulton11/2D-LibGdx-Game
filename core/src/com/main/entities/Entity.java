package com.main.entities;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Entity {

	private Vector2 position;
	private int width, height;
	
	private Sprite sprite;
	
	public Entity(Sprite sprite, float x, float y, int width, int height) {
		this.sprite = sprite;
		this.position = new Vector2(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Entity(Sprite sprite, Rectangle rect) {
		new Entity(sprite, rect.x, rect.y, (int) rect.width, (int) rect.height);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite.getTexture(), position.x, position.y);
	}
	
	public void update(float delta) {
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		this.position = new Vector2(x, y);
	}
	
	public void addPosition(Vector2 position) {
		this.position = new Vector2(this.position.x + position.x, this.position.y + position.y);
	}
	
	public void addPosition(float x, float y) {
		this.position = new Vector2(this.position.x + x, this.position.y + y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
} 
