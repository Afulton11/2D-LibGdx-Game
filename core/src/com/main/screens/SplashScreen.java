package com.main.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*; //instead of calling

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.main.Main;

public class SplashScreen implements Screen{

	private final Main game;
	private Stage stage;
	
	private Image splashImg;
	
	public SplashScreen(final Main game) {
		this.game = game;
		this.stage = new Stage(new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, game.camera));
		

	}
 	
	@Override
	public void show() {
		System.out.println("SPLASH");
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		
		if(game.assets.isLoaded("imgs/splash.png")) {
			Texture splashTexture = game.assets.get("imgs/splash.png", Texture.class);
			splashImg = new Image(splashTexture);
			splashImg.setOrigin(splashImg.getWidth() / 2, splashImg.getHeight() / 2);
			stage.addActor(splashImg);
		} else {
			game.setScreen(game.loadingScreen);
		}
		
		Runnable afterImgAnim = new Runnable() {
			@Override
			public void run() {
				game.setScreen(game.mainMenuScreen);
			}
		};
		
		//ANIMATION
		//import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
		//functions called from import static function. instead of calling Actions.method for every method needed to create a stage animation.
		splashImg.setPosition(stage.getWidth() / 2 + 64, stage.getHeight() / 2 + 64);
		splashImg.addAction(sequence(alpha(0f), scaleTo(.1f, .1f), 
				parallel(fadeIn(2f, Interpolation.pow2),
				scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
				moveTo((stage.getWidth() / 2) - (splashImg.getWidth() / 2), 
						(stage.getHeight() / 2) - (splashImg.getHeight() / 2), 2f, Interpolation.swing)), 
						delay(1.5f), fadeOut(1.25f), run(afterImgAnim)));
		

		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		stage.draw();
		
		game.batch.begin();
		game.font16.draw(game.batch, "Screen: Splash", 20, 40);
		game.batch.end();
		
	}
	
	private void update(float delta) {
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

}
