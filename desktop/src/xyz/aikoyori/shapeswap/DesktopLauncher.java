package xyz.aikoyori.shapeswap;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import xyz.aikoyori.shapeswap.ShapeSwap;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowSizeLimits(256,144,99999,99999);
		config.setWindowIcon( Files.FileType.Internal,"assets/textures/shapeswap.png");
		config.setWindowedMode(1280,720);
		config.setBackBufferConfig(8, 8, 8,8, 1,0, 16);
		new Lwjgl3Application(new ShapeSwap(), config);
	}
}
