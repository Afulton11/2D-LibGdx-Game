package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonActor extends Actor{

	private final ShapeRenderer shapes;
	private final Color released, pressed;
	
	private boolean isPressed;
	
	private Rectangle rect;
	
	public ButtonActor(final ShapeRenderer shapes, Color released, Color pressed, Rectangle rect) {
		this.shapes = shapes;
		this.released = released;
		this.pressed = pressed;
		this.rect = rect;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		shapes.updateMatrices();
		shapes.begin(ShapeType.Filled);
		
		shapes.setColor((isPressed) ? pressed : released);
		shapes.rect(rect.x, rect.y, rect.width, rect.height);
		
		shapes.end();
		
		if(Gdx.input.isTouched() && rect.contains(Gdx.input.getX(), Gdx.input.getY())) isPressed = true;
		else isPressed = false;
		
	}
}
