package com.main.tiles;

public class GrassTile extends Tile{

	private static final int TEX_X = 0, TEX_Y = 1;
	
	public GrassTile() {
		this.textureX = TEX_X;
		this.textureY = TEX_Y;
		this.id = Tile.Type.GRASS.getId();
	}
	
	public GrassTile(TileCoord coords) {
		this.setUpTile(TEX_X, TEX_Y, Tile.Type.GRASS.getId(), coords);
	}
}
