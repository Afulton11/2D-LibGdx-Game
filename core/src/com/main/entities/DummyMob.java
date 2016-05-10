package com.main.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.main.utils.Direction;

public class DummyMob extends B2DSprite{

	private Vector2 targetPos;
	private int texWidth, texHeight;
	private Texture texture;
		
	private Direction dir;
	
	public DummyMob(Body body, AssetManager assets) {
		super(body);
		
		dir = Direction.DOWN;
		offsetY = 16;
		texWidth = 32;
		texHeight = 48;
		
		texture = assets.get("imgs/player_sheet.png", Texture.class);
		TextureRegion[] sprites = TextureRegion.split(texture, texWidth, texHeight)[0];
		this.setAnimation(sprites, 1 / 2f);	
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(animation.getFrame(), getBodyPositionX(), getBodyPositionY());
		batch.end();
		
	}

	@Override
	public void update(float delta) {
		move(delta);
	}
	
	public void move(float delta) {
		addBodyPosition(-20 * delta, 0);
		if(targetPos != null) {
			if(this.getBodyPositionX() >= targetPos.x) {
				setBodyPosition(targetPos.x, targetPos.y);
				targetPos = null;
			}
		}
	}
	
	public void setTarget(Vector2 target) {
		this.targetPos = target;
	}

}
