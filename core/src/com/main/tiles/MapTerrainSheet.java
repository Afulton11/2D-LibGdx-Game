package com.main.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class MapTerrainSheet {

	private static TextureRegion[][] terrainSheet;
	
	/**
	 * creates a new MapTerrainSheet from the given sheetTexture
	 * @param sheetTexture - Texture
	 * @param splitSize - width/height of each sprite
	 */
	public static void init(Texture sheetTexture, int splitSize) {
		terrainSheet = TextureRegion.split(sheetTexture, splitSize, splitSize);
	}
	
	public static Texture getTextureAt(int x, int y) {
		return terrainSheet[x][y].getTexture();
	}
}
