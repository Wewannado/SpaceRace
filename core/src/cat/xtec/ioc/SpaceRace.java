package cat.xtec.ioc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.screens.SplashScreen;
import cat.xtec.ioc.utils.Settings;

public class SpaceRace extends Game {

    private int puntuacio=0;
    private StretchViewport viewport;

    @Override
    public void create() {

        // A l'iniciar el joc carreguem els recursos
        AssetManager.load();
        // I definim la pantalla d'splash com a pantalla
        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);



        setScreen(new SplashScreen(this));

    }

    // Cridem per descartar els recursos carregats.
    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public StretchViewport getViewport() {
        return viewport;
    }

    public void aumentarPuntuacio(int i){
        puntuacio+=i;
    }

    public void restaPerMort(){
        puntuacio-=10;
    }

    public void resetPuntuacio(){
        puntuacio=0;
    }
}