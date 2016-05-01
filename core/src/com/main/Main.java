package com.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.main.screens.LoadingScreen;
import com.main.screens.MainMenuScreen;
import com.main.screens.PlayScreen;
import com.main.screens.SplashScreen;

public class Main extends Game {
		
	
	public static final int TARGET_UPS = 120;
	public static final long UPDATE_INTERVALS = 1000000000 / TARGET_UPS;
	
	public static final String TITLE = "Project Ex";
	public static final int V_WIDTH = 400, V_HEIGHT = 300;
	
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public ShapeRenderer shapesRenderer;
	
	public BitmapFont titleFont, font16;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontParameter parameter;
	
	public AssetManager assets;
	
	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assets = new AssetManager();
	
		batch = new SpriteBatch();
		shapesRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
			
		queueFonts();

		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		playScreen = new PlayScreen(this);
	
		
		setScreen(loadingScreen);
	
	}
	
	@Override
	public void render () {
		super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
	}
	
	private void queueFonts() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MainFont.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		parameter.borderWidth = 10;
		parameter.borderGamma = 2f;
		parameter.color = Color.WHITE;
		
		titleFont = generator.generateFont(parameter);
		
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
		parameter.size = 24;
		parameter.borderWidth = 0.1f;
		parameter.borderGamma = 1;
		parameter.color = Color.BLACK;
		font16 = generator.generateFont(parameter);
		
		
		generator.dispose();
	}
	

	
	@Override
	public void dispose() {
		batch.dispose();
		shapesRenderer.dispose();
		titleFont.dispose();
		assets.dispose();
		
		loadingScreen.dispose();
		splashScreen.dispose();
		mainMenuScreen.dispose();
		playScreen.dispose();
	}
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}

