package com.main.screens;

import static com.main.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.main.Main;
import com.main.entities.DummyMob;
import com.main.entities.Player;
import com.main.hud.Hud;
import com.main.hud.HudManager;
import com.main.hud.TestHud;
import com.main.tiles.Map;
import com.main.tiles.MapTerrainSheet;
import com.main.tiles.Tile;
import com.main.utils.Constants;


public class PlayScreen implements Screen {
	
	private static boolean DEBUG = true;
	
	private final Main game;
	
	private long lastUpdateTime;
	
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;
	private Box2DDebugRenderer b2dr;
	
	//Box2D world that handles all box2D physics.
	private static World world;
	private Player player;
	private DummyMob mob;
	
	private Texture hudTex;
	private Map map;
	
	private HudManager hudManager;
	private TestHud testHud;
	
	public PlayScreen(final Main game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
		
		world = new World(new Vector2(0, 0), false);
		b2dr = new Box2DDebugRenderer();
		
	}
	
	@Override
	public void show() {

		map = game.assets.get("maps/map.json", Map.class);
		MapTerrainSheet.init(game.assets.get("maps/sprite_sheet.png", Texture.class), Constants.TILE_SIZE);
		lastUpdateTime = 0;
		hudTex = game.assets.get("imgs/hud.png", Texture.class);
		player = new Player(createBox(Main.V_WIDTH / 2, Main.V_HEIGHT / 2, 16, 16, BodyDef.BodyType.DynamicBody), game.assets);
		mob = new DummyMob(createBox(15, 15, 16, 16, BodyDef.BodyType.DynamicBody), game.assets);
		
		for(Tile t : map.getTiles()) {
			if(t.isSolid()) {
				PlayScreen.createBox((int)(t.getPixelCoords().x + Constants.TILE_SIZE / 2), 
						(int)(t.getPixelCoords().y + Constants.TILE_SIZE / 2), 
						Constants.TILE_SIZE, Constants.TILE_SIZE, 
						BodyDef.BodyType.StaticBody);
			}
		}
		
		hudManager = new HudManager();
		Hud.setHudManager(hudManager);
		testHud = new TestHud(1, 1, null);
		testHud.setVisible(true);
	}

	@Override
	public void render(float delta) {
		if(TimeUtils.nanoTime() - lastUpdateTime > Main.UPDATE_INTERVALS) {
			lastUpdateTime = TimeUtils.nanoTime();
			update(delta);
		}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(camera.combined);
		map.render(game.batch);
		
		mob.render(game.batch);
		player.render(game.batch);
		if(DEBUG) 
			b2dr.render(world, camera.combined.scl(PPM));

		game.batch.setProjectionMatrix(hudCam.combined);
		game.batch.begin();
		game.batch.draw(hudTex, 0, 0);
		game.font16.draw(game.batch, "Screen: Play", 20, 40);
		game.batch.end();
		hudManager.render(game.batch);
	}
	
	private void update(float delta) {
		world.step(1f / Main.TARGET_UPS, 6, 2);
		player.update(delta);
		hudManager.update(delta);
		mob.update(delta);
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			mob.setTarget(player.getWorldPos());
		}
		cameraUpdate(delta);
	}
	
	private void cameraUpdate(float delta) {
		Vector3 position = camera.position;
		position.x = player.getPosition().x * PPM / 2 + (Main.V_WIDTH / 8 - player.getWidth() / 2);
		position.y = player.getPosition().y * PPM / 2 - (Main.V_HEIGHT / 8 - player.getHeight());
		
//		camera.position.set(position);
//		camera.position.slerp(position, delta);
		camera.translate(position);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, Main.V_WIDTH, Main.V_HEIGHT);
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
		world.dispose();
		b2dr.dispose();
		hudTex.dispose();
		
	}
	
	public static Body createBox(int x, int y, int width, int height, BodyDef.BodyType bodyType) {
		Body pBody;
		BodyDef def = new BodyDef();
		def.type = bodyType;
		
		def.position.set(x / PPM, y / PPM);
		
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
		
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		
		return pBody;
	}
	
	public Player getPlayer() {
		return player;
	}
	

}
