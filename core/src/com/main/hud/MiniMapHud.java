package com.main.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.main.entities.Player;
import com.main.tiles.Map;
import com.main.tiles.Tile;

public class MiniMapHud extends Hud{

	private static final int RADIUS = 25; //how many tiles can be seen from the player's tile posiiton.
	
	private Map map;
	private Player player;
	private int miniMapWidth, miniMapHeight;
	private Pixmap mapPix;
	private int tileX, tileY;
	
	public MiniMapHud(Map map, Player player, float x, float y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.map = map;
		this.player = player;
				
		miniMapWidth = miniMapHeight = RADIUS * 2;
		mapPix = new Pixmap(miniMapWidth, miniMapHeight, Format.RGBA8888);
		mapPix.setColor(Color.BLACK);
		updateTexture();

	}

	@Override
	public void update(float delta) {
		updateTexture();
	}

	@Override
	public void render(SpriteBatch hudBatch) {
		hudBatch.end();
		hudBatch.disableBlending();
		hudBatch.begin();
		hudBatch.draw(getTexture(), xPos, yPos, width, height);
		hudBatch.end();
		hudBatch.begin();
		hudBatch.enableBlending();
	}
	
	private void updateTexture() {
		mapPix.fillRectangle(0, 0, miniMapWidth, miniMapHeight);
		for(int yy = 0; yy < miniMapHeight; yy++) {
			for(int xx = 0; xx < miniMapWidth; xx++) {
				tileX = player.getTileX() + xx - RADIUS;
				tileY = player.getTileY() - yy + RADIUS;
				if(tileX == player.getTileX() && tileY == player.getTileY()) {
					mapPix.drawPixel(xx, yy, 0xFFFF00FF);
				} else {
					Tile t = map.getTileAt(tileX, tileY);
					if(t != null) {
						Pixmap tilePix = t.getTexturePixmap(1);	
						mapPix.drawPixmap(tilePix, xx, yy);
					} else {
						mapPix.drawPixel(xx, yy, 0);
					}
				}
			}
		}
		setTexture(new Texture(mapPix));
	}
	
	public void dispose() {
		super.dispose();
	}

}
