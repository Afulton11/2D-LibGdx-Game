package com.main.tiles;


public class VoidTile extends Tile {
	
	private static final int TEX_X = 0, TEX_Y = 0;
	
	/**
	 * Creates a blank voidTile, only setting the tile's id and textureX.
	 */
	public VoidTile() {
		this.textureX = TEX_X;
		this.textureY = TEX_Y;
		this.id = Tile.Type.VOID.getId();
	}
	
	public VoidTile(TileCoord coord) {
		this.setUpTile(TEX_X, TEX_Y, Tile.Type.VOID.getId(), coord);
	}
	
	public boolean isSolid() {
		return true;
	}

}
