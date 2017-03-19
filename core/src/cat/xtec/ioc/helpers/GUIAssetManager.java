package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GUIAssetManager {

    //Botons
    public static TextureRegionDrawable fire, joystickUp, joystickDown, joystickLeft, joystickRight;
    // Nau i fons
    public static TextureRegion spacecraft, spacecraftDown, spacecraftUp, background;
    // Asteroid
    public static TextureRegion[] asteroid;
    public static Animation asteroidAnim;
    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;
    private static Music music;
    // Font
    public static BitmapFont fontTitle, fontLVL;
    // Sprite Sheet
    private static Texture joystickSheet;
    // Sons
    private static Sound explosionSound;

    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        joystickSheet = new Texture(Gdx.files.internal("previewJoystick.png"));
        joystickSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        //Sprites de la GUI

        //Boto disparar

        Texture myTexture;
        TextureRegion myTextureRegion;
        TextureRegionDrawable myTexRegionDrawable;
        ImageButton button;

        myTexture = new Texture(Gdx.files.internal("button_fire.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTextureRegion.flip(false, true);
        fire = new TextureRegionDrawable(myTextureRegion);
        //fire = new TextureRegion(joystickSheet, )


        /*
        // Sprites de la nau
        spacecraft = new TextureRegion(joystickSheet, 0, 0, 36, 15);
        spacecraft.flip(false, true);

        spacecraftUp = new TextureRegion(joystickSheet, 36, 0, 36, 15);
        spacecraftUp.flip(false, true);

        spacecraftDown = new TextureRegion(joystickSheet, 72, 0, 36, 15);
        spacecraftDown.flip(false, true);

        // Carreguem els 16 estats de l'asteroid
        asteroid = new TextureRegion[16];
        for (int i = 0; i < asteroid.length; i++) {

            asteroid[i] = new TextureRegion(joystickSheet, i * 34, 15, 34, 34);
            asteroid[i].flip(false, true);

        }

        // Creem l'animació de l'asteroid i fem que s'executi contínuament en sentit anti-horari
        asteroidAnim = new Animation(0.05f, asteroid);
        asteroidAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Creem els 16 estats de l'explosió
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(joystickSheet, j * 64,  i * 64 + 49, 64, 64);
                explosion[index-1].flip(false, true);
            }
        }
        */


    }

    public static void dispose() {

        // Descrtem els recursos
        joystickSheet.dispose();
        explosionSound.dispose();
        music.dispose();

    }
}
