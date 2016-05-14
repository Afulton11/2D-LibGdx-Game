package com.main.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.main.Main;
import com.main.utils.Utils;

public class MainMenuScreen implements Screen{

	private final Main game;	
	private String title, isLoading;
	private GlyphLayout titleLayout, isLoadingLayout;
	
	private long lastUpdateTime;
	
	private Stage stage;
	private Skin skin;
	
	private TextButton buttonPlay, buttonExit;
		
	public MainMenuScreen(final Main game) {
		this.game = game;
		this.stage = new Stage(new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT));
				
		title = "Project Ex";
		titleLayout = new GlyphLayout();
		titleLayout.setText(game.titleFont, title);
		isLoading = "";
		isLoadingLayout = new GlyphLayout();
		isLoadingLayout.setText(game.titleFont, isLoading);
		
	}
	
	@Override
	public void show() {
		System.out.println("MAIN MENU");
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		
		this.skin = new Skin();
		skin.addRegions(game.assets.get("ui/uiskin.atlas", TextureAtlas.class));
		skin.add("default-font", game.font16);
		skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		initButtons();
	}
	
	private void initButtons() {
		buttonPlay = new TextButton("Play", skin, "default");
		buttonPlay.setSize(buttonPlay.getWidth() * 3, buttonPlay.getHeight() * 1.5f);
		buttonPlay.setPosition(stage.getWidth() / 2 - buttonPlay.getWidth() / 2, stage.getHeight() / 2 + buttonPlay.getHeight() / 2);
		buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
		
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.playScreen);
			}
		});
		
		
		buttonExit = new TextButton("Quit", skin, "default");
		buttonExit.setSize(buttonPlay.getWidth(), buttonPlay.getHeight());
		buttonExit.setPosition(stage.getWidth() / 2 - buttonExit.getWidth() / 2, buttonPlay.getY() - buttonPlay.getHeight() - 10);
		buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));

		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		stage.addActor(buttonPlay);
		stage.addActor(buttonExit);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		game.shapesRenderer.setProjectionMatrix(stage.getCamera().combined);
		stage.draw();
		
		game.batch.begin();
		
		game.titleFont.draw(game.batch, title, (width / 2) - (titleLayout.width / 2), height - titleLayout.height - 25);
		game.titleFont.draw(game.batch, isLoading, (width / 2) - (isLoadingLayout.width / 2), +isLoadingLayout.height * 2);
		
		game.font16.draw(game.batch, "Screen: MAIN MENU", 20, 40);
		game.batch.end();
		
		if(TimeUtils.nanoTime() - lastUpdateTime > Main.UPDATE_INTERVALS) {
			lastUpdateTime = TimeUtils.nanoTime();
			update(delta);
		}

	}
	private int time = 0;
	private void update(float delta) {
		stage.act(delta);
		
		time++;
		if(time < 41 && time % 20 == 0) {
			isLoading = new String(".");
			isLoadingLayout.setText(game.titleFont, isLoading);
			
		} else if(time < 81 && time % 20 == 0) {
			isLoading = new String("..");
			isLoadingLayout.setText(game.titleFont, isLoading);
			
		} else if(time < 121 && time % 20 == 0) {
			isLoading = new String("...");
			isLoadingLayout.setText(game.titleFont, isLoading);
		} else if(time < 161 && time % 20 == 0) {
			isLoading = new String("..");
			isLoadingLayout.setText(game.titleFont, isLoading);
		} else if(time < 201 && time % 20 == 0) {
			isLoading = new String(".");
			isLoadingLayout.setText(game.titleFont, isLoading);			
		} else if(time < 241 && time % 20 == 0) {
			isLoading = new String("");
			isLoadingLayout.setText(game.titleFont, isLoading);
			time = 0;
		}
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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
		
	}

}
