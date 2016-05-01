package com.main.tiles;

import com.badlogic.gdx.math.Vector2;
import com.main.utils.Constants;

/**
 * TileCoord.java
 * 
 * Used like a Vector2, but can be coverted from tileCoords to pixel Coords.
 * 
 * @Author Andrew Fulton
 * Created on: Apr 30, 2016 at 4:19:19 PM
 */
public class TileCoord {

	public Vector2 coords;
	
	public TileCoord(int tileX, int tileY) {
		coords = new Vector2(tileX, tileY);
	}
	
	public void addCoords(TileCoord coords) {
		this.coords = new Vector2(this.coords.x + coords.coords.x, this.coords.y + coords.coords.x);
	}
	
	public void setCoords(int tileX, int tileY) {
		coords = new Vector2(tileX, tileY);
	}
	
	public Vector2 getPixelCoords() {
		return new Vector2(coords.x * Constants.TILE_SIZE, coords.y * Constants.TILE_SIZE);
	}
	
	public TileCoord getCopy() {
		return new TileCoord((int) coords.x, (int) coords.y);
	}

}
