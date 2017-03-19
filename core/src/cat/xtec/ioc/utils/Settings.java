package cat.xtec.ioc.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // Propietats de la nau
    public static final float EASY_SPACECRAFT_VELOCITY = 50;
    public static final float MEDIUM_SPACECRAFT_VELOCITY = 100;
    public static final float HARD_SPACECRAFT_VELOCITY = 150;
    public static final int SPACECRAFT_WIDTH = 36;
    public static final int SPACECRAFT_HEIGHT = 15;
    public static final float SPACECRAFT_STARTX = 20;
    public static final float SPACECRAFT_STARTY = GAME_HEIGHT / 2 - SPACECRAFT_HEIGHT / 2;

    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    // Configuració Scrollable
    public static final int EASY_ASTEROID_SPEED = -150;
    public static final int MEDIUM_ASTEROID_SPEED = -200;
    public static final int HARD_ASTEROID_SPEED = -300;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = -100;


    //Integer nivells
    public static final int NIVELL_FACIL=1;
    public static final int NIVELL_MITJ=2;
    public static final int NIVELL_DIFICIL=3;
}
