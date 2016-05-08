package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.main.screens.PlayScreen;
import com.main.utils.Constants;

public class Tile {
	
	public static enum Type {
		VOID(0), GRASS(1), STONE(2);
		
		private final int id;
		
		Type(final int id) {
			this.id = id;
		}
		
		int getId() {
			return id;
		}
	}
	
	protected int id;
	protected int textureX, textureY;
	protected TileCoord position;
	
	public Tile(){}
	
	/**
	 * Initializes the tile with a position, id, tile coordinate, and a texture position on the mapTerrainSheet.
	 * @param textureX - x position on the spriteSheet (sheetTile size).
	 * @param textureY - y position on the spriteSheet (sheetTile size).
	 * @param id - id/type of tile.
	 * @param coord - TileCoord, position of the Tile on the map in tile coordinates.
	 */
	public void setUpTile(int textureX, int textureY, int id, TileCoord coord) {
		this.position = coord;
		this.textureX = textureX;
		this.textureY = textureY;
		this.id = id;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(MapTerrainSheet.getTextureRegion(textureX, textureY), position.getPixelCoords().x, position.getPixelCoords().y);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	
	public int getId() {
		return id;
	}

	public void setTileCoordinates(TileCoord coord) {
		this.position = coord;
	}
	
	public TileCoord getTileCoords() {
		return position;
	}
	
	public Vector2 getPixelCoords() {
		return position.getPixelCoords();
	}
	
	public Vector2 getMiddlePixelCoords() {
		return new Vector2(position.getPixelCoords().x + Constants.TILE_SIZE / 2, position.getPixelCoords().y + Constants.TILE_SIZE / 2);
	}
	
	public TextureRegion getTextureRegion() {
		return MapTerrainSheet.getTextureRegion(textureX, textureY);
	}
	
	public Tile copy() {
		Tile copy = new Tile();
		copy.setUpTile(textureX, textureY, id, position);
		return copy;
	}
}
