package evoloution;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Individual {

    static int defaultGeneLength = 64;
    private Color c;
    public int geneIndex;
    public float x = 150;
    private byte[] genes = new byte[defaultGeneLength];
    // Cache
    private int fitness = 0;
    private static Random rand = new Random(2134);
    private float time = 0;
    
    // Create a random individual
    public void generateIndividual() {
    	c = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }
    
    public float y = Evoloution.WIDTH / 2;
    public void render(ShapeRenderer shape) {
    	if(c == null) c = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
    	shape.setColor(c);
    	shape.rect(x, y, 8, 8);
    }
    
    public void update(float delta) {
    	move(delta);
    }
    
    private int speed = 2;
    private float damper = 0.5f;
    private void move(float delta) {
    	if(geneIndex < genes.length) {
    		time += delta;
    		if(genes[geneIndex] == 1) x -= speed * damper;
    		else if(genes[geneIndex] == 0) x += speed * damper;
    		if(genes[geneIndex + 1] == 1) y -= speed * damper;
    		else if(genes[geneIndex] == 0) y += speed * damper;
    		
//    		if(genes[geneIndex] == 1 && genes[geneIndex + 1] == 1) damper *= 0.5;
//    		else if(genes[geneIndex] == 0 && genes[geneIndex + 1] == 0) damper = 0.05f;
    		geneIndex += 2;
    	}
    }
    
    /*
     * Used for quick evoloution
     */
    public void calculateFinalPos(float delta) {
    	for(byte b : genes) {
    		move(delta);
    	}
    }
 
    
    public void dispose() {    	
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
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
