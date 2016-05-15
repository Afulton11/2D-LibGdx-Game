package com.main.tools;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import net.dermetfan.gdx.scenes.scene2d.ui.FileChooser;
import net.dermetfan.gdx.scenes.scene2d.ui.TreeFileChooser;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData.AssetData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.main.tiles.GrassTile;
import com.main.tiles.Map;
import com.main.tiles.MapTerrainSheet;
import com.main.tiles.MouseTile;
import com.main.tiles.StoneTile;
import com.main.tiles.Tile;
import com.main.tiles.TileCoord;
import com.main.tiles.VoidTile;
import com.main.utils.Constants;

public class MapCreator extends Game {

	private static final int WIDTH = 1280, HEIGHT = 720;
	
	private SpriteBatch batch;

	private OrthographicCamera cam;
	
	private BitmapFont font16;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	
	private AssetManager assets;
	private UniversalLoadingScreen loadingScreen;
	private CreationScreen creationScreen;
			
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		assets = new AssetManager(new InternalFileHandleResolver());
		
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		
		loadFonts();
		creationScreen = new CreationScreen();
		loadingScreen = new UniversalLoadingScreen(assets, font16, this, creationScreen, cam, batch);
		loadingScreen.setAssetsToLoad(new AssetData<TextureAtlas>("ui/uiskin.atlas", TextureAtlas.class),
				new AssetData<Texture>("maps/sprite_sheet.png", Texture.class));
		
		setScreen(loadingScreen);

	}
	
	private void loadFonts() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.borderWidth = 0.1f;
		parameter.borderGamma = 1;
		parameter.color = Color.BLACK;
		font16 = generator.generateFont(parameter);

		generator.dispose();
	}
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		creationScreen.dispose();
		loadingScreen.dispose();
	}
	
	private class CreationScreen extends InputMultiplexer implements Screen {
		
		private static final String LOCATION_PROJECT = "A:/eclipse/workspace/LibGdxPlayArea/Project Ex/Core/assets/maps/";
		private static final float DEFAULT_ZOOM = 1.0f;
		
		private ShapeRenderer shapeRenderer;
		private OrthographicCamera mapCam, hudCam;

		private Stage hudStage, mapStage;
		private Skin skin;			
		private TextButton openBtn, saveBtn, newBtn;
		
		private TreeFileChooser fileChooser;
		private Window fileWindow;
		
		private Tile[] tiles;
		private Array<Tile> placedTiles;
		private Map currentMap;
		
		private MouseTile mouseTile;
		private Tile[] mouseTiles; 
		private int currentMouseTileIndex;
				
		public CreationScreen() {
			shapeRenderer = new ShapeRenderer();
			mapCam = new OrthographicCamera();
			mapCam.setToOrtho(false, WIDTH, HEIGHT);
			hudCam = new OrthographicCamera();
			hudCam.setToOrtho(false, WIDTH, HEIGHT);
						
			hudStage = new Stage();
			hudStage.setViewport(new StretchViewport(WIDTH, HEIGHT, hudCam));
			mapStage = new Stage();
			mapStage.setViewport(new StretchViewport(WIDTH, HEIGHT, mapCam));
			
			mouseTile = new MouseTile(new GrassTile());
			currentMouseTileIndex = 1;
			mouseTiles = new Tile[]{new VoidTile(), new GrassTile(), new StoneTile()};
			placedTiles = new Array<Tile>();
		}
		
		
		@Override
		public void show() {
			System.out.println("CREATION");
			hudStage.clear();
			this.addProcessor(hudStage);
			Gdx.input.setInputProcessor(this);
			
			skin = new Skin();
			skin.addRegions(assets.get("ui/uiskin.atlas", TextureAtlas.class));
			skin.load(Gdx.files.internal("ui/uiskin.json"));
							
			MapTerrainSheet.init(assets.get("maps/sprite_sheet.png", Texture.class), Constants.TILE_SIZE);
			createSelectFileWindow();
			loadBtns();			
		}
		
		private void createSelectFileWindow() {
			fileWindow = new Window("Choose A File to Load", skin);
			fileChooser = new TreeFileChooser(skin, "default", new FileChooser.Listener() {
				
				@Override
				public void choose(Array<FileHandle> files) {
					System.out.println("ChooseMultiple");
					
					new Dialog("ERROR!", skin) {
						{
							text("You Can Only Choose 1 File to Load!");
							button("Cancel");
						}
						
						@Override
						public void result(Object object) {
							this.remove();
						}
					}.show(hudStage);
					
				}
				
				@Override
				public void choose(FileHandle file) {
					System.out.println("File Chosen: " + file);
					loadMap(file);
					cancel();
				}
				
				@Override
				public void cancel() {
					fileWindow.setVisible(false);
				}
			});
			
			fileWindow.setSize(400, 400);
			fileChooser.setVisible(true);
			fileChooser.setShowHidden(false);
			fileChooser.add(Gdx.files.absolute(LOCATION_PROJECT));
			fileChooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().contains(".json");
				}
			});
			
			fileWindow.add(fileChooser);
			fileWindow.setVisible(false);
			
			
			hudStage.addActor(fileWindow);
		}
		
		
		
		private void loadMap(FileHandle handle) {
			System.out.println("Loading Map...");
			Json json = new Json();
			currentMap = json.fromJson(Map.class, handle);
			
			System.out.println("Loaded Map!");
		}
		
		private void loadBtns() {
			openBtn = new TextButton("Open...", skin, "default");
			openBtn.setSize(openBtn.getWidth() * 1.25f, openBtn.getHeight() * 1.1f);
			openBtn.setPosition(5, hudStage.getHeight() - openBtn.getHeight() - 5);
			openBtn.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
			
			openBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					fileWindow.setPosition(WIDTH / 2 - fileWindow.getWidth() / 2, HEIGHT / 2 - fileWindow.getHeight() / 2);
					fileWindow.setVisible(true);
				}
			});
			
			saveBtn = new TextButton("Save As...", skin, "default");
			saveBtn.setSize(saveBtn.getWidth() * 1.25f, openBtn.getHeight());
			saveBtn.setPosition(openBtn.getX() + openBtn.getWidth() + 10, openBtn.getY());
			saveBtn.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
			
			saveBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					new SaveDialog("Save...", skin).show(hudStage);
				}
			});
			
			newBtn = new TextButton("New...", skin, "default");
			newBtn.setSize(newBtn.getWidth() * 1.25f, openBtn.getHeight());
			newBtn.setPosition(saveBtn.getX() + saveBtn.getWidth() + 10, openBtn.getY());
			newBtn.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
			
			newBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					new Dialog("New Map...", skin) {
						
						private TextArea widthArea, heightArea;
						{
							text("What is the size of the map?");
							widthArea = new TextArea("TileWidth", skin);
							heightArea = new TextArea("TileHeight", skin);
							row();
							Table areaTable = new Table(skin);
							areaTable.defaults().space(25);
							areaTable.add(widthArea);
							areaTable.add(heightArea);
							add(areaTable).expand().fill();
							row();
							button("Create Map", true);
							button("Cancel", false);
						}
						
						@Override
						protected void result(Object object) {
							if((Boolean) object) {
								createNewMap(Integer.parseInt(widthArea.getText()), 
										Integer.parseInt(heightArea.getText()));
								this.setVisible(false);
							} else {
								this.setVisible(false);
							}
						}
					}.show(hudStage);
				}
			});
			
			hudStage.addActor(saveBtn);
			hudStage.addActor(openBtn);
			hudStage.addActor(newBtn);
		}
		
		private void createNewMap(int tileWidth, int tileHeight) {
			this.currentMap = new Map(tileWidth, tileHeight);
			this.tiles = new Tile[tileWidth * tileHeight];
			
			for(int y = 0; y < tileHeight; y++) 
				for(int x = 0; x < tileWidth; x++) 
					tiles[x + y * tileWidth] = new VoidTile(new TileCoord(x, y));
			this.currentMap.setTiles(tiles);
		}
		

		@Override
		public void render(float delta) {
			update(delta);
			
			Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			batch.setProjectionMatrix(mapCam.combined);
			shapeRenderer.setProjectionMatrix(mapCam.combined);
			drawMapOutline();
			mouseTile.render(batch, mapCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
			hudStage.draw();
		}
		
		
		private void drawMapOutline() {
			shapeRenderer.begin(ShapeType.Line);
			Rectangle camRect = new Rectangle();
			camRect.set(mapCam.position.x - mapCam.viewportWidth, mapCam.position.y - mapCam.viewportHeight, mapCam.viewportWidth * mapCam.zoom * 2, 
					mapCam.viewportWidth * mapCam.zoom * 2);
			if(currentMap != null) {
				shapeRenderer.setColor(1, 1, 1, 0.5f);
				for(Tile t: currentMap.getTiles()) {
					if(camRect.contains(t.getPixelCoords())) {
						shapeRenderer.rect(t.getPixelCoords().x, t.getPixelCoords().y, Constants.TILE_SIZE, Constants.TILE_SIZE);
						batch.begin();
						t.render(batch);
						batch.end();
					}
				}
			}
			shapeRenderer.end();
		}
		
		private int moveSpeed = 200; //arrow key moveSpeed.
		private void update(float delta) {
			handleInput(delta);	
			handleTilePlacement(delta);
			handleTileSelection(delta);
		}
		
		private void handleInput(float delta) {
			if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				showExitDialog();
			}
			
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
				mapCam.translate(moveSpeed * delta, 0);
				
			} else if(Gdx.input.isKeyPressed(Keys.LEFT)) {
				mapCam.translate(-moveSpeed * delta, 0);
			}
			
			if(Gdx.input.isKeyPressed(Keys.UP)) {
				mapCam.translate(0, moveSpeed * delta);
				
			} else if(Gdx.input.isKeyPressed(Keys.DOWN)) {
				mapCam.translate(0, -moveSpeed * delta);
			}
			
			//RIGHT CLICK TO PAN
			if(Gdx.input.isButtonPressed(1)) {
				Vector3 position = mapCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				mapCam.position.slerp(position, delta * 2f);
			}
			
			//Reset Zoom
			if(Gdx.input.isButtonPressed(2)) {
				mapCam.zoom = DEFAULT_ZOOM;
			}
			mapCam.update();	
		}
		
		private boolean touchReleased = true;
		private long lastUndoTime = 0;
		private void handleTilePlacement(float delta) {
			if(touchReleased && Gdx.input.isButtonPressed(0)) {
				if(currentMap != null) {
					int index = (int) (mouseTile.getTileCoordinates().x + mouseTile.getTileCoordinates().y * currentMap.getTileWidth());
					if(index > -1 && index < currentMap.getTiles().size) {
						Tile t = mouseTile.tile.copy();
						t.setTileCoordinates(new TileCoord(mouseTile.getTileCoordinates()));
						currentMap.getTiles().set(index, t);
						placedTiles.add(t);
					}
				}
			} 
			if(placedTiles.size > 0 && TimeUtils.millis() - lastUndoTime > 100 &&  
				Gdx.input.isKeyPressed(Keys.Z) && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
				lastUndoTime = TimeUtils.millis();
				currentMap.removeTile(this.placedTiles.get(placedTiles.size - 1));
				placedTiles.removeIndex(placedTiles.size - 1);
			}
		}
		//Tile Selection is used by pressing the plus key and Enter key on the numpad. Enter goes down in the tile list, while the plus goes up in the tile list.
		private void handleTileSelection(float delta) {
			if(Gdx.input.isKeyJustPressed(Keys.PLUS) && currentMouseTileIndex < mouseTiles.length - 1) {
				currentMouseTileIndex++;
				mouseTile.tile = mouseTiles[currentMouseTileIndex];
			} else if(Gdx.input.isKeyJustPressed(Keys.ENTER) && currentMouseTileIndex > 0) {
				currentMouseTileIndex--;
				mouseTile.tile = mouseTiles[currentMouseTileIndex];
			}
		}
		
		private void showExitDialog() {
			new Dialog("Leave the Map Creator?", skin) {
    			{
    				text("Do you really want to leave?");
    				button("Yes", true);
    				button("No", false);
    			}

    			protected void result(final Object object) {
    				if((Boolean) object) {
    					Gdx.app.exit();
    				} else {
    					this.setVisible(false);
    				}
    			}
    			
    		}.show(hudStage);
		}
		@Override
		public void resize(int width, int height) {
			mapCam.setToOrtho(false, width, height);
			hudStage.getViewport().update(width, height);
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
			skin.dispose();
			shapeRenderer.dispose();
			hudStage.dispose();
			mapStage.dispose();
		}
		
		private void saveMap(String path) throws IOException {
			System.out.println("Saving Map...");
			
			Json json = new Json();			
			JsonWriter writer = new JsonWriter(Gdx.files.absolute(path).writer(false));
			writer.write(json.prettyPrint(currentMap));
			writer.close();
			System.out.println("Successfully Saved Map!");
		}
		
		@Override
		public boolean scrolled(int amount) {
			if(mapCam.zoom == 0.2f && amount > 0) {
				mapCam.zoom += amount * 0.1f;
			}
			if(mapCam.zoom > 0.2f) 
				mapCam.zoom += amount * 0.1f;
			else 
				mapCam.zoom = 0.2f;
			return false;
		}
		
		private class SaveDialog extends Dialog {
			
			private TextArea area;

			public SaveDialog(String title, Skin skin) {
				super(title, skin);
			}
			
			{
				text("Where would you like to save your map?").setHeight(50);
				area = new TextArea(LOCATION_PROJECT + "map.json", skin);
				area.setWidth(WIDTH / 2);
				row();
				Table areaTable = new Table(skin);
				areaTable.defaults().size(WIDTH / 2, 75);
				areaTable.defaults().space(25);
				areaTable.add(area);
				add(areaTable).expand().fill();
				row();
				
				
				button("Save", true);
				button("Cancel", false);		
			}
			
			@Override
			protected void result(Object object) {
				remove();
				if((Boolean) object) {
					final Dialog parent = this;
					new Dialog("Confirm Save", skin) {
						{
							text("Save Map to: " + area.getText());
							button("Yes", true);
							button("Cancel", false);
						}
						@Override
						protected void result(Object obj) {
							if((Boolean) obj) {
								try {
									saveMap(area.getText());
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else 
								parent.show(hudStage);
								
							remove();
						}
						
					}.show(hudStage);
				}
			}	
		}
	}
}
