package com.main.tiles;

public class StoneTile extends Tile{
	
	private static final int TEX_X = 1, TEX_Y = 0;
	
	public StoneTile() {
		this.textureX = TEX_X;
		this.textureY = TEX_Y;
		this.id = Tile.Type.STONE.getId();
	}
	
	public StoneTile(TileCoord coords) {
		this.setUpTile(TEX_X, TEX_Y, Tile.Type.STONE.getId(), coords);
	}
	
//	public boolean isSolid(){
//		return true;
//	}
}
