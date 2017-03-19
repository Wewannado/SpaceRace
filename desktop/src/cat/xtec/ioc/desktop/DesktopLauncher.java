package cat.xtec.ioc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "SpaceRace";
        config.width = Settings.GAME_WIDTH * 3;
        config.height = Settings.GAME_HEIGHT * 3;

		new LwjglApplication(new SpaceRace(), config);
	}
}
