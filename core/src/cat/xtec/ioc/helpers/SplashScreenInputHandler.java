package cat.xtec.ioc.helpers;

import com.badlogic.gdx.InputProcessor;

import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.screens.SplashScreen;

public class SplashScreenInputHandler implements InputProcessor {


    // Objectes necessaris
    private SplashScreen screen;


    public SplashScreenInputHandler(SplashScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screen.getGame().setScreen(new GameScreen(screen.getStage().getBatch(), screen.getGame()));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
