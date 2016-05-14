package com.main.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.main.utils.Utils;

public class HudButton extends Hud {

	private Texture up, down;
	private HudButtonListener listener;
	
	public HudButton(float x, float y, int width, int height, Texture up, Texture down) {
		super(x, y);
		this.width = width;
		this.height = height;
		setTexture(up);
		this.up = up;
		this.down = down;
	}
	
	//helper bool for pressing/releasing the button. (Allows the listener methods to be called only once)
	private boolean pressed;
	@Override
	public void update(float delta) {
		if(!pressed && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Utils.isInputInRect(xPos, yPos, width, height)) {
			setTexture(down);
			pressed = true;
			if(listener != null) {
				listener.onPress();
			}
		} else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && pressed){
			setTexture(up);
			pressed = false;
			if(listener != null) {
				listener.onRelease();
			}
		}
	}

	@Override
	public void render(SpriteBatch hudBatch) {
		hudBatch.draw(getTexture(), xPos, yPos, width, height);
	}
	
	public void setListener(HudButtonListener listener) {
		this.listener = listener;
	}
}
