package com.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.main.Main;
import com.main.tiles.Map;
import com.main.tiles.MapLoader;

public class LoadingScreen implements Screen {

	private final Main game;
	private ShapeRenderer shapeRenderer;
	
	private long lastUpdateTime;
	
	private TiledMap map;
	
	/**
	 * how much the asset manager has loaded.
	 */
	private float progress;
	
	private final String loading;
	private final GlyphLayout loadingLayout;
	
	public LoadingScreen(final Main game) {
		this.game = game;
		this.shapeRenderer = new ShapeRenderer();
		loading = "Screen: Loading";
		loadingLayout = new GlyphLayout(game.font16, loading);
	}
	
	@Override
	public void show() {
		System.out.println("LOADING");
		this.progress = 0;
		
		queueAssets();
	}
	
	private void queueAssets() {
		game.assets.load("imgs/splash.png", Texture.class);
		game.assets.load("ui/uiskin.atlas", TextureAtlas.class);
		game.assets.load("imgs/player_sheet.png", Texture.class);
		game.assets.load("imgs/hud.png", Texture.class);
		game.assets.setLoader(Map.class, new MapLoader(new InternalFileHandleResolver()));
		game.assets.load("maps/map.json", Map.class);
		game.assets.load("maps/sprite_sheet.png", Texture.class);
		
		game.assets.load("imgs/outline.png", Texture.class);
		game.assets.finishLoading();
	}

	private void update(float delta) {
		progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f); //lerp = (a + (b - a) * damper
		
		if(game.assets.update() && game.assets.getProgress() - progress < 0.05f) { //returns false until all the assets have finished loading.
			game.setScreen(game.splashScreen);
//			game.setScreen(game.mainMenuScreen);
		}
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(TimeUtils.nanoTime() - lastUpdateTime > Main.UPDATE_INTERVALS) {
			lastUpdateTime = TimeUtils.nanoTime();
			update(delta);
		}
		
		shapeRenderer.setProjectionMatrix(game.camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, game.camera.viewportWidth - 64, 16);
		

		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, progress * (game.camera.viewportWidth - 64), 16);
		shapeRenderer.end();
		
		game.batch.begin();
		
		game.font16.draw(game.batch, loading, 20, loadingLayout.height + 20);
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {

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
		shapeRenderer.dispose();
	}
	
	public TiledMap getMap() {
		return map;
	}

}
