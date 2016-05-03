package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.main.utils.Constants;

public class MouseTile extends Tile{

	public Tile tile;
	
	private float pixelX, pixelY;
	
	public MouseTile() {}
	
	/**
	 * a mouseTile is rendered at the mouseX and y location.
	 * Used in MapCreator.java to place tiles on a map.
	 * @param tile 
	 */
	public MouseTile(Tile tile) {
		this.tile = tile;
	}
	
	public void render(SpriteBatch batch, Vector3 mouseWorldPos) {
		pixelX = mouseWorldPos.x - Constants.TILE_SIZE / 2;
		pixelY = mouseWorldPos.y - Constants.TILE_SIZE / 2;
		batch.begin();
		batch.draw(MapTerrainSheet.getTextureRegion(tile.textureX, tile.textureY), pixelX, pixelY,
				Constants.TILE_SIZE, Constants.TILE_SIZE);
		batch.end();
	}
	
	public Vector2 getPixelCoordinates() {
		return new Vector2(pixelX, pixelY);
	}
	
	public Vector2 getTileCoordinates() {
		return new Vector2((int) (pixelX / Constants.TILE_SIZE), (int)(pixelY / Constants.TILE_SIZE));
	}
	
	
	
}
