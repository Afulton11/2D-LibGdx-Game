package com.main.tiles;


public class VoidTile extends Tile {
	
	/**
	 * No need to use this constructor for a void tile. Used for json loading.
	 */
	public VoidTile() {}
	
	public VoidTile(TileCoord coord) {
		this.setUpTile(0, 0, Tile.Type.VOID.getId(), coord);
	}
	
	public boolean isSolid() {
		return true;
	}

}
