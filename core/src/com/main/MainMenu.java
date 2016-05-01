package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu {
	
	private final BitmapFont font;
	
	private final String title, play;
	private final GlyphLayout titleLayout, playLayout;
	
	public MainMenu(final BitmapFont font) {
		this.font = font;
		
		title = "Project Ex";
		titleLayout = new GlyphLayout();
		titleLayout.setText(font, title);
		play = "Tap to continue...";
		playLayout = new GlyphLayout();
		playLayout.setText(font, play);
	}
	
	public void render(SpriteBatch batch, float delta) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		font.setColor(Color.BLACK);
		font.draw(batch, title, (width / 2) - titleLayout.width / 2, height - titleLayout.height - 20);
		font.draw(batch, play, (width / 2) - playLayout.width / 2, (height / 2) - (titleLayout.height / 2));
	}
	
	public void update(float delta) {
		
	}

}
