package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.main.utils.Constants;

public class MouseTile extends Tile{

	public Tile tile;
	
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
		batch.begin();
		batch.draw(MapTerrainSheet.getTextureRegion(tile.textureX, tile.textureY), mouseWorldPos.x - Constants.TILE_SIZE / 2, mouseWorldPos.y - Constants.TILE_SIZE / 2,
				Constants.TILE_SIZE, Constants.TILE_SIZE);
		batch.end();
	}
}
