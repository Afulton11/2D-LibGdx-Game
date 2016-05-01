package com.main.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.main.utils.Direction;

public class Player extends B2DSprite{
	
	private Texture texture;
	private int texWidth, texHeight;
	
	private Direction dir;
	
	public Player(Body body, AssetManager assets) {
		super(body);
		dir = Direction.DOWN;
		offsetY = 16;
		texWidth = 32;
		texHeight = 48;
		
		texture = assets.get("imgs/player_sheet.png", Texture.class);
		TextureRegion[] sprites = TextureRegion.split(texture, texWidth, texHeight)[0];
		this.setAnimation(sprites, 1 / 2f);		
	}

	public void update(float delta) {
		super.update(delta);
		handleInput(delta);
	}
	
	private float speed = 150f;
	private void handleInput(float delta) {
		int horizForce = 0;
		int vertForce = 0;
		float totalSpeed = delta * speed;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizForce -= totalSpeed;
			if(dir != Direction.LEFT) {
				animation.setTextureRegion(TextureRegion.split(texture, texWidth, texHeight)[3]);
				dir = Direction.LEFT;
			}
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizForce += totalSpeed;
			if(dir != Direction.RIGHT) {
				animation.setTextureRegion(TextureRegion.split(texture, texWidth, texHeight)[2]);
				dir = Direction.RIGHT;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			vertForce += totalSpeed;
			if(dir != Direction.UP) {
				animation.setTextureRegion(TextureRegion.split(texture, texWidth, texHeight)[1]);
				dir = Direction.UP;
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			vertForce -= totalSpeed;
			if(dir != Direction.DOWN) {
				animation.setTextureRegion(TextureRegion.split(texture, texWidth, texHeight)[0]);
				dir = Direction.DOWN;
			}
		}
		
		body.setLinearVelocity(horizForce * 5, vertForce * 5);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
