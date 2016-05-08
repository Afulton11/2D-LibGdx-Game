package evoloution;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.main.utils.Constants;

public class Individual {

    static int defaultGeneLength = 64;
    private Color c;
    public int geneIndex;
    private float x = 150;
    private float y = Evoloution.WIDTH / 2;
    private Gene[] genes = new Gene[defaultGeneLength];
    // Cache
    private int fitness = 0;
    private static Random rand = new Random(2134);
    private float time = 0;
    public Body body;
    
    private static final int WIDTH = 8, HEIGHT = 8;
    
    public Individual() {
    	body = Evoloution.createMonsterBox((int)x, (int)y, WIDTH, HEIGHT, BodyType.DynamicBody);
    	
    	c = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
    }
    
    // Create a random individual
    public void generateIndividual() {
    	body = Evoloution.createMonsterBox((int)x, (int)y, WIDTH, HEIGHT, BodyType.DynamicBody);
    	c = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
        for (int i = 0; i < size(); i++) {
            genes[i] = new Gene((float) (Math.random() * 500), (int)(Math.random() * 4), (float) (Math.random() * 2));
        }
    }
    
    public void render(ShapeRenderer shape) {
    	if(c == null) c = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
    	shape.setColor(c);
    	shape.rect(body.getPosition().x - WIDTH / Constants.PPM / 2, body.getPosition().y - HEIGHT / Constants.PPM / 2, WIDTH / Constants.PPM, HEIGHT /  Constants.PPM);
    }
    
    public void update(float delta) {
    	move(delta);
    }
    
    private float timeToWait = 0;
    private float waitTimer = 0;
    float velX = 0, velY = 0;
    private void move(float delta) {
    	if(geneIndex < genes.length) {
	    	waitTimer += delta;
	    	time += delta;
    		if(waitTimer >= timeToWait) {
    			timeToWait = 0;
	    		float speed = genes[geneIndex].getSpeed();
	    		if(genes[geneIndex].getDir() == 0) velX += speed * delta;
	    		else if(genes[geneIndex].getDir() == 1) velX -= speed * delta;
	    		else if(genes[geneIndex].getDir() == 2) velY += speed * delta;
	    		else if(genes[geneIndex].getDir() == 3) velY -= speed * delta;
	    		
	    		waitTimer = 0;
	    		timeToWait = genes[geneIndex].getTime();	    		
//	    		body.applyForce(velX, velY, 0, 0, true);
	    		geneIndex++;
	    	}
    	}
    	velX -= (velX > 0) ? delta : -delta;
    	velY -= (velY > 0) ? delta : -delta;
    	body.setLinearVelocity(velX, velY);
    }
    
    /*
     * Used for quick evoloution
     */
    public void calculateFinalPos(float delta) {
    	for(Gene b : genes) {
    		if(body != null) {
    			move(delta);
    		}
    	}
    }
 
    
    public void dispose() {    	
    	
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    public Gene getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, Gene gene) {
        genes[index] = gene;
        fitness = 0;
    }
    
    public Gene getAverageGene() {
    	float totalSpeed = 0;
    	int totalDirection = 0;
    	float totalTime = 0;
    	float averageSpeed = 0;
    	int averageDirection = 0;
    	float averageTime = 0;
    	for(int i = 0; i < genes.length; i++) {
    		totalSpeed += genes[i].getSpeed();
    		totalDirection += genes[i].getDir();
    		totalTime += genes[i].getTime();
    	}
    	
    	averageSpeed = totalSpeed / genes.length;
    	averageDirection = totalDirection / genes.length;
    	averageTime = totalTime / genes.length;
    	
    	return new Gene(averageSpeed, averageDirection, averageTime);
    }
    
    public float getTime() {
    	return time;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
  

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
