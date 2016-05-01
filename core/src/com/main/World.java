package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

public class World {

	private final Array<Bullet> activeBullets = new Array<Bullet>();
	
	private long lastBulletSpawn;
	
	//bullet pool
	private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
	
		@Override
		protected Bullet newObject() {
			return new Bullet();
		}
	};
	
	private TiledMap map;
	
	public World() {
		map = new TiledMap();		
	}
	
	public void update(float delta) {
		if(TimeUtils.millis() - lastBulletSpawn > 1) spawnBullet(delta); 
		for(Bullet b : activeBullets) {
			b.update(delta);
			if(!b.alive)
				freeBullet(b);
			else {
				b.position.x += 200 * delta;
				b.position.y += 200 * delta;
			}
		}
	}
	
	public void render(SpriteBatch batch, float delta) {
		for(Bullet b : activeBullets) {
//			batch.draw(Main.bucketImage, b.position.x, b.position.y);
		}
	}
	
	public void dispose() {
		map.dispose();
	}
	
	//free dead bullets, return them to the pool.
	private void freeBullet(Bullet bullet) {
		for(int i = activeBullets.size; i-- > 0;) {
			bullet = activeBullets.get(i);
			if(!bullet.alive) {
				activeBullets.removeIndex(i);
				bulletPool.free(bullet);
			}
		}
	}
	
	private void spawnBullet(float delta) {
		
		lastBulletSpawn = TimeUtils.millis();
		if(bulletPool.getFree() > 0) {
			Bullet bullet = bulletPool.obtain();
			bullet.init((int) (Math.random() * 1000 * delta), (int) (Math.random() * 2000 * delta));
			activeBullets.add(bullet);
		} else {
			Bullet bullet = new Bullet();
			bullet.init((int) (100 * delta), (int) (200 * delta));
			activeBullets.add(bullet);
		}
	}
}
