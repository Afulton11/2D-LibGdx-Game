package com.main.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.main.Main;
import com.main.entities.Player;
import com.main.screens.PlayScreen;
import com.main.utils.Constants;


public class Map{

	private int tileWidth, tileHeight;
	
	private Array<Tile> tiles;
	
	private Player client;
		
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
	
	public void setPlayer(Player client) {
		this.client = client;
	}

	public void render(SpriteBatch batch) {
		int tileStartX = client.getTileX() - (int)Math.floor(Main.V_WIDTH / 2 / Constants.TILE_SIZE) - 1;
		int tileStartY = client.getTileY() - (int)Math.floor(Main.V_HEIGHT / 2 / Constants.TILE_SIZE) - 1;
		int tileEndX = tileStartX + 2 + (int)Math.floor(Main.V_WIDTH / Constants.TILE_SIZE);
		int tileEndY = tileStartY + 6 + (int)Math.floor(Main.V_HEIGHT / Constants.TILE_SIZE);
		batch.begin();
		for(int y = tileStartY - 1; y < tileEndY;  y++) {
			for(int x = tileStartX - 1; x < tileEndX; x++) {
				int index = x + y * tileWidth;
				if(index < 0) continue;
				else if(index >= tiles.size) break;
				tiles.get(index).render(batch);
			}
		}
		batch.end();
	}
	
	public void createBounds() {
		int pixelWidth = tileWidth * Constants.TILE_SIZE;
		int pixelHeight = tileHeight * Constants.TILE_SIZE;
		int padding = 10;
		//BOTTOM WALL
		PlayScreen.createBox(pixelWidth / 2, -padding / 2, pixelWidth + padding, padding, BodyType.StaticBody);
		//LEFT WALL
		PlayScreen.createBox(-padding / 2, pixelHeight / 2, padding, pixelHeight + padding, BodyType.StaticBody);
		
		//RIGHT WALL
		PlayScreen.createBox(pixelWidth + padding / 2, pixelHeight / 2, padding, pixelHeight + padding, BodyType.StaticBody);
		//TOP WALL
		PlayScreen.createBox(pixelWidth / 2, pixelHeight + padding / 2, pixelWidth + padding, padding, BodyType.StaticBody);
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
	
	public void removeTile(Tile tileToRemove) {
		for(int i = 0; i < tiles.size; i++) {
			Tile t = tiles.get(i);
			if(t.position.coords.equals(tileToRemove.position.coords)) {
				tiles.set(i, new VoidTile(t.position));
			}
		}
	}
	
	public Tile getTileAt(int tileX, int tileY) {
		for(Tile t : tiles) {
			if(t.getTileCoords().coords.x == tileX && t.getTileCoords().coords.y == tileY) return t;
		}
		return null;
	}
	
	

}
