package com.Android.games.List_Files;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainActivityDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "List_Files";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new Main(), cfg);
	}
}
