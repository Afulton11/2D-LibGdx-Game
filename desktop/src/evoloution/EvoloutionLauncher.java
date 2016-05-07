package evoloution;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class EvoloutionLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project Ex - Evoloution";
		config.width = 1280;
		config.height = 720;
		config.addIcon("imgs/icon.png", Files.FileType.Internal);
		config.backgroundFPS = 120;
		config.foregroundFPS = 120;
		new LwjglApplication(new Evoloution(), config);
	}
}
