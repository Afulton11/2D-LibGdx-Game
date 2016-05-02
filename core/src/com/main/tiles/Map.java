package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class Map{

	private int tileWidth, tileHeight;
	
	private Array<Tile> tiles;
		
	public Map() {}
	
	public Map(int tileWidth, int tileHeight, Array<Tile> tiles) {
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		
		this.tiles = tiles;
	}
	
	public Map(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tiles = new Array<Tile>();
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		for(int y = 0; y < tileHeight; y++) {
			for(int x = 0; x < tileWidth; x++) {
				tiles.get(x + y * tileWidth).render(batch);
			}
		}
		
		batch.end();
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public Array<Tile> getTiles() {
		return tiles;
	}
	
	public void setTiles(Tile... tiles) {
		this.tiles.addAll(tiles);
	}
	
	public Tile getTileAt(TileCoord tileCoord) {
		return tiles.get((int) (tileCoord.coords.x * tileCoord.coords.y * tileWidth));
	}

}
