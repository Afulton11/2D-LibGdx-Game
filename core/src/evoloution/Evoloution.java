package evoloution;

import static com.main.utils.Constants.PPM;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.main.Main;

public class Evoloution extends Game implements InputProcessor{
	public static final int WIDTH = 1280 , HEIGHT = 720;
	public static final int POP_SIZE = 150, GENE_LENGTH = 10;
	
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private OrthographicCamera cam;
	public static World world;
	public static Body platform, wall, wall2, wall3;
	public static Body leftWall, topWall, bottomWall, rightWall;
	public static Body goalPoint;
	public static Array<Body> walls;
	public static boolean requestClear;
	private Box2DDebugRenderer b2dr;
		
	private int generationCount = 0;
	
	private static Population myPop;
	private boolean solFound = false;
	
	private InputMultiplexer inputplexer;
	
	@Override
	public void create() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		Individual.setDefaultGeneLength(GENE_LENGTH);
		world = new World(new Vector2(0, 0), true);
		setContactListener();
		
		walls = new Array<Body>();
//		platform = createBox(WIDTH / 2 - 25, HEIGHT / 2 - 14, 50, 10, BodyType.StaticBody);
		
		wall = createBox(200, HEIGHT - 150, 10, 400, BodyType.StaticBody);
		wall2 = createBox(400, HEIGHT - 600, 10, 800, BodyType.StaticBody);
		wall3 = createBox(WIDTH / 2, HEIGHT / 2 - 10, 400, 10, BodyType.StaticBody);
		
		goalPoint = createBox(FitnessCalc.goalPoint.x * PPM, FitnessCalc.goalPoint.y * PPM, Individual.WIDTH, Individual.HEIGHT, BodyType.KinematicBody);
		
		leftWall = createBox(1, 0, 1, HEIGHT * 2, BodyType.StaticBody);
		rightWall = createBox(WIDTH - 1, 0, 1, HEIGHT * 2, BodyType.StaticBody);
		bottomWall = createBox(0, 1, WIDTH * 2, 1, BodyType.StaticBody);
		topWall = createBox(0, HEIGHT - 1, WIDTH * 2, 1, BodyType.StaticBody);
		
		walls.add(wall);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(leftWall);
		walls.add(rightWall);
		walls.add(bottomWall);
		walls.add(topWall);
		
		b2dr = new Box2DDebugRenderer();
		
		
		myPop = new Population(POP_SIZE, true);
		
		inputplexer = new InputMultiplexer(this);
		Gdx.input.setInputProcessor(inputplexer);
		
		
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
		b2dr.render(world, cam.combined);
		
	}
	
	private void update(float delta) {
		cam.update();
		if(requestClear && !world.isLocked()) {
			Evoloution.world.clearForces();
	    	Array<Body> bodies = new Array<Body>();
	    	Evoloution.world.getBodies(bodies);
	    	Iterator<Body> i = bodies.iterator();
	    	   while(i.hasNext()){
	    	      Body b = i.next();
	    	      if(b != platform)
	    	    	  Evoloution.world.destroyBody(b);
	    	   }
	    	i.remove();
	    	requestClear = false;
		}
		world.step(1f / Main.TARGET_UPS, 6, 2);
		
		if(!solFound)
			for(Individual i : myPop.individuals) {
				i.update(delta);
			}
		else {
			myPop.getFittest().update(delta);
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || myPop.doneMoving()) { //go to next generation.
			for(Individual i : myPop.individuals) {
				i.calculateFinalPos(delta);
			}
			if(myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
				
				generationCount++; 
				System.out.println("Generation: "+generationCount+" Fittest: "+myPop.getFittest().getFitness()); 
				System.out.println("Time: " + myPop.getFittest().getTime() + ", Collisions: " + myPop.getFittest().collisions);
				Gene avg = myPop.getFittest().getAverageGene();
				System.out.println("Average Gene of most fit: speed, " + avg.getSpeed() + ", direction, " + avg.getDir() + ", Time, " + avg.getTime());
				myPop = Algorithm.evolvePopulation(myPop); 
			} else {
				solFound = true;
				myPop.getFittest().geneIndex = 0;
				System.out.println("Solution found!"); 
				System.out.println("Generation: "+generationCount); 
				System.out.println("Genes:"); 
				System.out.println("Time taken: " + myPop.getFittest().getTime());
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.UP)) { //skip generations, May not do aswell as just letting the time pass. Part of the fitness calculations used the Gdx delta float for time.
			for(Individual i : myPop.individuals) {
				i.calculateFinalPos(delta);
			}
			if(myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
				generationCount++; 
				System.out.println("Generation: "+generationCount+" Fittest: "+ myPop.getFittest().getFitness()); 
				System.out.println("Time: " + myPop.getFittest().getTime() + ", Collisions: " + myPop.getFittest().collisions);
				myPop = Algorithm.evolvePopulation(myPop);
			} else {
				solFound = true;
				myPop.getFittest().geneIndex = 0;
				System.out.println("Solution found!"); 
				System.out.println("Generation: "+generationCount); 
				System.out.println("Genes:"); 
				System.out.println("Time taken: " + myPop.getFittest().getTime());
			} 
		} 
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			if(!solFound) {
				myPop.getFittest().geneIndex = 0;
				myPop.getFittest().body.getPosition().x = 150 / PPM;
				myPop.getFittest().body.getPosition().y = (Evoloution.HEIGHT - 100) / PPM;
			}
			solFound = true;
		} else {
			solFound = false;
		}
	}
	
	public static Body createBox(float x, float y, int width, int height, BodyDef.BodyType bodyType) {
		Body pBody;
		BodyDef def = new BodyDef();
		def.type = bodyType;
		
		def.position.set(x / PPM, y / PPM);
		
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.restitution = 0.02f;
		fixtureDef.filter.categoryBits = MONSTER << 1;
		fixtureDef.filter.maskBits = MONSTER;
		
		pBody.createFixture(fixtureDef);
		shape.dispose();
		
		return pBody;
	}
	
	final static short MONSTER = 0x0001;
	
	public static Body createMonsterBox(float x, float y, int width, int height, BodyDef.BodyType bodyType) {
		Body pBody;
		BodyDef def = new BodyDef();
		def.type = bodyType;
		def.position.set(x / PPM, y / PPM);
		
		
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
	
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.restitution = 0.02f;
//		fixtureDef.filter.categoryBits = MONSTER;
		fixtureDef.filter.maskBits = MONSTER << 1;
		
		FixtureDef sensor = new FixtureDef();
		sensor.isSensor = true;
		sensor.shape = shape;
		
		pBody.createFixture(fixtureDef);
		pBody.createFixture(sensor);
		shape.dispose();
		
		return pBody;
	}
	
	
	public void dispose() {
		for(Individual i : myPop.individuals) {
			i.dispose();
		}
		batch.dispose();
		shape.dispose();
		world.dispose();
	}

	public static void setContactListener() {
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				boolean isFixtureAWall = false;
				
				for(Body b : walls) {
					if(contact.getFixtureA().getBody().equals(b)) isFixtureAWall = true;
				}				if(isFixtureAWall) {
					for(Individual i : myPop.individuals) {
						if(i.body.equals(contact.getFixtureB().getBody())) {
							i.collisions++;
							break;
						}
					}
				} else {
					for(Individual i : myPop.individuals) {
						if(i.body.equals(contact.getFixtureA().getBody())) {
							i.collisions++;
							break;
						}
					}
				}
			}

			@Override
			public void endContact(Contact contact) {
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
			}
			
		});
	}
	

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		System.out.println("scroll");
		cam.zoom += amount;
		return false;
	}

}
