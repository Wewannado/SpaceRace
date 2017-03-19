package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;

public class Spacecraft extends Actor {

    // Distintes posicions de la spacecraft, recta, pujant i baixant
    private static final int SPACECRAFT_STRAIGHT = 0;
    private static final int SPACECRAFT_UP = 1;
    private static final int SPACECRAFT_DOWN = 2;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;
    private GameScreen gameScreen;

    private Rectangle collisionRect;

    public Spacecraft(float x, float y, int width, int height, GameScreen gameScreen) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        this.gameScreen = gameScreen;

        // Inicialitzem la spacecraft a l'estat normal
        direction = SPACECRAFT_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

    public void act(float delta) {

        // Movem la spacecraft depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case SPACECRAFT_UP:
                if (this.position.y - gameScreen.spacecraft_Velocity * delta >= 0) {
                    this.position.y -=gameScreen.spacecraft_Velocity * delta;
                }
                break;
            case SPACECRAFT_DOWN:
                if (this.position.y + height + gameScreen.spacecraft_Velocity * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += gameScreen.spacecraft_Velocity * delta;
                }
                break;
            case SPACECRAFT_STRAIGHT:
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, 10);
        setBounds(position.x, position.y, width, height);

    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = SPACECRAFT_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = SPACECRAFT_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = SPACECRAFT_STRAIGHT;
    }

    // Obtenim el TextureRegion depenent de la posició de la spacecraft
    private TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case SPACECRAFT_STRAIGHT:
                return GameAssetManager.spacecraft;
            case SPACECRAFT_UP:
                return GameAssetManager.spacecraftUp;
            case SPACECRAFT_DOWN:
                return GameAssetManager.spacecraftDown;
            default:
                return GameAssetManager.spacecraft;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.SPACECRAFT_STARTX;
        position.y = Settings.SPACECRAFT_STARTY;
        direction = SPACECRAFT_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public void shoot(ScrollHandler scrollHandler) {
        if (gameScreen.getGame().getEnergia() > 0) {
            for (Actor actor : gameScreen.getStage().getActors()) {
                if (actor.getName() != null && actor.getName().equalsIgnoreCase("spacecraft")) {
                    gameScreen.getStage().addActor(new Laser(actor.getX() + actor.getWidth(), actor.getY() + actor.getHeight() / 2, 22, 5, scrollHandler));
                    gameScreen.getGame().setEnergia(gameScreen.getGame().getEnergia() - 1);
                    break;
                }
            }
        }
    }

    Rectangle getCollisionRect() {
        return collisionRect;
    }
}
