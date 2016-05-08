package evoloution;


public class Gene {

	private float time;
	private float speed;
	private int direction;
	
	public Gene(float speed, int direction, float time) {
		this.speed = speed;
		this.direction = direction;
		this.time = time;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public int getDir() {
		return direction;
	}
	
	/**
	 * the time to wait before checking the next gene.
	 * @return
	 */
	public float getTime() {
		return time;
	}
}
