package com.main.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData.AssetData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.main.Main;

public class UniversalLoadingScreen implements Screen {

	private final AssetManager assets;
	private ShapeRenderer shapeRenderer;
	
	private long lastUpdateTime;
	
	private TiledMap map;
	
	private Array<AssetData<?>> assetsToLoad;
	
	/**
	 * how much the asset manager has loaded.
	 */
	private float progress;
	
	private final String loading;
	private final GlyphLayout loadingLayout;
	private BitmapFont font;
	private Screen nextScreen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Game game;
	
	public UniversalLoadingScreen(final AssetManager assets, BitmapFont font,  Game game, Screen nextScreen, OrthographicCamera camera, SpriteBatch batch) {
		this.assets = assets;
		this.shapeRenderer = new ShapeRenderer();
		loading = "Screen: Loading";
		loadingLayout = new GlyphLayout(font, loading);
		
		this.font = font;
		this.nextScreen = nextScreen;
		this.game = game;
		this.camera = camera;    
		this.batch = batch;
	}
	
	public void setAssetsToLoad(AssetData<?>...assetDatas) {
		assetsToLoad = new Array<AssetData<?>>();
		assetsToLoad.addAll(assetDatas);
	}
	
	@Override
	public void show() {
		System.out.println("LOADING");
		this.progress = 0;
		
		queueAssets();
	}
	
	private void queueAssets() {
		for(AssetData<?> assetData : assetsToLoad) {
			assets.load(assetData.filename, assetData.type);
		}
		assets.finishLoading();
	}

	private void update(float delta) {
		progress = MathUtils.lerp(progress, assets.getProgress(), .1f); //lerp = (a + (b - a) * damper
		
		if(assets.update() && assets.getProgress() - progress < 0.05f) { //returns false until all the assets have finished loading.
			game.setScreen(nextScreen);
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
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(32, camera.viewportHeight / 2 - 8, camera.viewportWidth - 64, 16);
		
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(32, camera.viewportHeight / 2 - 8, progress * (camera.viewportWidth - 64), 16);
		shapeRenderer.end();
		
		batch.begin();
		
		font.draw(batch, loading, 20, loadingLayout.height + 20);
		
		batch.end();
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