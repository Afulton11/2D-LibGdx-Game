package com.main.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
	private Color def_Color = new Color(200f, 200f, 200f, 190f);
	public Pixmap getTexturePixmap(int size) {
		Pixmap pix = new Pixmap(size, size, Format.RGBA8888);
		if(size > 1) {
			TextureData data = getTextureRegion().getTexture().getTextureData();
			data.prepare();
			Pixmap texturePix = data.consumePixmap();
			pix.drawPixmap(texturePix, 0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE, 0, 0, size, size);
		} else {
			switch(id) {
			case 1:
				pix.setColor(Color.GREEN);
				break;
			case 2:
				pix.setColor(Color.GRAY);
				break;
			default:
				pix.setColor(def_Color);
				break;
			}
			pix.drawPixel(0, 0);
		}
		return pix;
	}
	
	public Tile copy() {
		Tile copy = new Tile();
		copy.setUpTile(textureX, textureY, id, position);
		return copy;
	}
}
