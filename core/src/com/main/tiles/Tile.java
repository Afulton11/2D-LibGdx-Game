package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	public static enum Type {
		GRASS(0), STONE(1), WOOD_PLANK(2);
		
		private final int id;
		
		Type(final int id) {
			this.id = id;
		}
		
		int getId() {
			return id;
		}
	}
	
	private int id;
	
	private TextureRegion region;
	private TileCoord position;
	
	public Tile(TextureRegion textureRegion, int id, TileCoord coord) {
		this.position = coord;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(region, position.getPixelCoords().x, position.getPixelCoords().y);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	
	public int getId() {
		return id;
	}

	public TileCoord getTileCoords() {
		return position;
	}
	
	public Vector2 getPixelCoords() {
		return position.getPixelCoords();
	}
}
