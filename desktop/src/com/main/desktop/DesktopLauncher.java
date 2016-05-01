package com.main.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.main.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project Ex";
		config.width = 800;
		config.height = 600;
		config.addIcon("imgs/icon.png", Files.FileType.Internal);
		config.backgroundFPS = 120;
		config.foregroundFPS = 120;
		new LwjglApplication(new Main(), config);
	}
}
