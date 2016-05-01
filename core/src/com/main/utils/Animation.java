package com.main.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[] frames;
	private float time, delay;
	private int currentFrame, timesPlayed;
	
	public Animation() {}
	
	/**
	 * Creates a new Animation from the frames given with a default animation delay.
	 * @param frames - TextureRegion[]
	 */
	public Animation(TextureRegion[] frames) {
		setFrames(frames, Constants.DEFAULT_ANIM_DELAY);
	}
	
	/**
	 * Creates a new Animation from the frames and delay given.
	 * @param frames - TextureRegion[]
	 * @param delay - float
	 */
	public Animation(TextureRegion[] frames, float delay) {
		setFrames(frames, delay);
	}
	
	public void setFrames(TextureRegion[] frames, float delay) {
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
		timesPlayed = 0;
	}
	
	public void update(float delta) {
		if(delay <= 0) return;
		time+= delta;
		
		while(time >= delay) {
			step();
		}
	}
	
	private void step() {
		time -= delay;
		currentFrame++;
		if(currentFrame == frames.length) {
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public void setTextureRegion(TextureRegion[] reg) {
		this.frames = reg;
	}
	/**
	 * returns the current Frame of the animation
	 * @return TectureRegion
	 */
	public TextureRegion getFrame() { return frames[currentFrame]; }
	
	/**
	 * returns the number of loops this animation has completed.
	 * @return Integer
	 */
	public int getTimesPlayed() { return timesPlayed; }
	
}
