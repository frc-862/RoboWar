package com.lightningrobotics.robowar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lightningrobotics.robowar.BarrelBounce;
import static com.lightningrobotics.robowar.Constants.defaultPixelHeight;
import static com.lightningrobotics.robowar.Constants.defaultPixelWidth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RoboWar";
		config.width = defaultPixelWidth;
		config.height = defaultPixelHeight;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new BarrelBounce(), config);
	}
}
