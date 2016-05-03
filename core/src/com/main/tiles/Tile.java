package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	
	public static enum Type {
		VOID(0, VoidTile.class), GRASS(1, GrassTile.class);
		
		private final int id;
		private final Class<? extends Tile> tileClass;
		
		Type(final int id, final Class<? extends Tile> tileClass) {
			this.id = id;
			this.tileClass = tileClass;
		}
		
		int getId() {
			return id;
		}
		
		Class<? extends Tile> getTileClass() {
			return this.tileClass;
			
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
	
	public TextureRegion getTextureRegion() {
		return MapTerrainSheet.getTextureRegion(textureX, textureY);
	}
	
	public Tile copy() {
		Tile copy = new Tile();
		copy.setUpTile(textureX, textureY, id, position);
		return copy;
	}
	
	public Class<? extends Tile> getTileClass() {
		for(int i = 0; i < Type.values().length; i++) {
			if(Type.values()[i].getId() == id) {
				return Type.values()[i].getTileClass();
			}
		}
		return null;
	}
}
