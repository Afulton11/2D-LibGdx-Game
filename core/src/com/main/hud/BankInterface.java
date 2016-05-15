package com.main.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class BankInterface extends ExInterface{

	//TODO: Add an 'inventory' to the bank. This can be saved to a .json file.
	
	public BankInterface(AssetManager gameAssets, float screenX, float screenY, int width, int height) {
		super(screenX, screenY, width, height);
		setBackgroundHud(new TextureHud(screenX, screenY, width, height, gameAssets.get("imgs/bank_bg.png", Texture.class)));
	}

}
