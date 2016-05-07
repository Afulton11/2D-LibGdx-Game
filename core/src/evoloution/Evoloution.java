package evoloution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Evoloution extends Game {
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static final int POP_SIZE = 250, GENE_LENGTH = 2000;
	
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private OrthographicCamera cam;
		
	private int generationCount = 0;
	
	private Population myPop;
	private boolean solFound = false;
	
	@Override
	public void create() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		Individual.setDefaultGeneLength(GENE_LENGTH);
		
		myPop = new Population(POP_SIZE, true);
		
		
	}
	
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(Gdx.graphics.getDeltaTime());
		shape.setProjectionMatrix(cam.combined);
		shape.begin(ShapeType.Filled);
		if(!solFound)
			for(Individual i : myPop.individuals) {
				i.render(shape);
			}
		else
			myPop.getFittest().render(shape);
		shape.end();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		batch.end();
		cam.update();
	}
	
	private void update(float delta) {
		if(!solFound)
			for(Individual i : myPop.individuals) {
				i.update(delta);
			}
		else {
			myPop.getFittest().update(delta);
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) { //go to next generation.
			if(myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
				
				generationCount++; 
				System.out.println("Generation: "+generationCount+" Fittest: "+myPop.getFittest().getFitness()); 
				System.out.println("Time: " + myPop.getFittest().getTime());
				myPop = Algorithm.evolvePopulation(myPop); 
			} else {
				solFound = true;
				myPop.getFittest().geneIndex = 0;
				System.out.println("Solution found!"); 
				System.out.println("Generation: "+generationCount); 
				System.out.println("Genes:"); 
				System.out.println(myPop.getFittest()); 
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.UP)) { //skip generations, May not do aswell as just letting the time pass. Part of the fitness calculations used the Gdx delta float for time.
			for(Individual i : myPop.individuals) {
				i.calculateFinalPos(delta);
			}
			if(myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
				generationCount++; 
				System.out.println("Generation: "+generationCount+" Fittest: "+ myPop.getFittest().getFitness()); 
				System.out.println("Time: " + myPop.getFittest().getTime());
				myPop = Algorithm.evolvePopulation(myPop);
			} else {
				solFound = true;
				myPop.getFittest().geneIndex = 0;
				System.out.println("Solution found!"); 
				System.out.println("Generation: "+generationCount); 
				System.out.println("Genes:"); 
				System.out.println(myPop.getFittest()); 
				System.out.println("Time taken: " + myPop.getFittest().getTime());
			} 
		} 
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			if(!solFound) {
				myPop.getFittest().geneIndex = 0;
				myPop.getFittest().x = 150;
				myPop.getFittest().y = WIDTH / 2;
			}
			solFound = true;
		} else {
			solFound = false;
		}
	}
	
	public void dispose() {
		for(Individual i : myPop.individuals) {
			i.dispose();
		}
		batch.dispose();
		shape.dispose();
	}

}
