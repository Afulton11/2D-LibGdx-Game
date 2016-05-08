package evoloution;

import com.badlogic.gdx.math.Vector2;
import com.main.utils.Constants;

public class FitnessCalc {
	 static byte[] solution = new byte[64];
	 public static Vector2 goalPoint = new Vector2(Evoloution.WIDTH / 2 / Constants.PPM, Evoloution.HEIGHT / 2 / Constants.PPM);

	    /* Public methods */
	    // Set a candidate solution as a byte array
	    public static void setSolution(byte[] newSolution) {
	        solution = newSolution;
	    }

	    // To make it easier we can use this method to set our candidate solution 
	    // with string of 0s and 1s
	    static void setSolution(String newSolution) {
	        solution = new byte[newSolution.length()];
	        // Loop through each character of our string and save it in our byte 
	        // array
	        for (int i = 0; i < newSolution.length(); i++) {
	            String character = newSolution.substring(i, i + 1);
	            if (character.contains("0") || character.contains("1")) {
	                solution[i] = Byte.parseByte(character);
	            } else {
	                solution[i] = 0;
	            }
	        }
	    }

	    // Calculate inidividuals fittness by comparing it to our candidate solution
	    static int getFitness(Individual individual) {
	        int fitness = 0;
//	        // Loop through our individuals genes and compare them to our cadidates
//	        for (int i = 0; i < individual.size() && i < solution.length; i++) {
//	            if (individual.getGene(i) == solution[i]) {
//	                fitness++;
//	            }
//	        }
	        fitness = -(int) Vector2.dst(individual.body.getPosition().x * Constants.PPM, individual.body.getPosition().y * Constants.PPM, 
	        		goalPoint.x * Constants.PPM, goalPoint.y * Constants.PPM);
	        fitness -= (int) individual.getTime();
	        return fitness;
	    }
	    
	    // Get optimum fitness
	    static int getMaxFitness() {
//	        int maxFitness = solution.length;
//	        return maxFitness;
	    	return -1;
	    }
}
